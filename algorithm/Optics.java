package algorithm;

import java.io.*;
import java.util.*;
import java.lang.reflect.Array;

import model.*;
import algorithm.*;

/**
 * Implementation of OPTICS.
 */
public class Optics extends Algorithm
{

    // maximum epsilon
    public final static double MAX_EPSILON_NEIGHBOURHOOD = 1000;
    public final static double IDEAL_EPSILON_NEIGHBOURHOOD = 300;

    /**
     * Seeds queue.
     *
     * This is a min-PriorityQueue.
     */
    PriorityQueue<AlgorithmPoint> seeds;

    /**
     * Map of points, using a spatial index structure to speed up things.
     */
    RTree points;

    /**
     * Parameters.
     */
    double epsilon;
    int minPts;

    /**
     * Value that shows if we restart the algorithm.
     *
     * This is used to lower the epsilon-value if the epsilon-neighbourhood is too large.
     */
    boolean restart = false;

    /**
     * Constructor.
     */
    public Optics()
    {
        super();
    }

    /**
     * Find the parameters
     *
     * @param ci Minimum number of clusters
     * @param cj Maximum number of clusters
     * @param n Number of points
     */
    public void findParameters(int ci, int cj, int n, long width, long height, int startx, int starty)
    {
        super.findParameters(ci, cj, n, width, height, startx, starty);

        // first the epsilon
        // Z = w + h
        // epsilon = Z / sqrt(n)
        double Z = width + height;

        epsilon = Z / Math.pow(n, 1.0/2.1);
        //System.out.println("E: " + epsilon + " n: " + n + " Z: " + Z + " w: " + width + " h: " + height);
        //epsilon = 39138;

        // now the minPts. We want to make sure that this is not too big,
        // because that will decrease the running time fast
        // thus, we give it a maximum value of 40, also, a minimum of 8
        // We'll take the 2.5th root of (n / cj), and add 5
        minPts = (int) Math.pow(n / cj, 1.0/2.5) + 5;

        if (minPts < 8) {
            minPts = 8;
        } else if (minPts > 40) {
            minPts = 40;
        }
        //System.out.println("minPts: " + minPts + " n: " + n + " cj: " + cj + " n/cj: " + n / cj);

        // the minClusterSize is slightly different, making this bigger on
        // bigger datasets, will make the finding of clusters faster. But
        // making it too small on big datasets will make it a weird output.
        minClusterSize = (n/3) / cj;

        if (minClusterSize > 300) {
            minClusterSize = 300;
        }
        //System.out.println("minClusterSize: " + minClusterSize + " n / 4: " + n / 4 + " cj: " + cj);

        if (DISTANCE_METRIC == Calculations.DISTANCE_EUCLIDIAN_SQ) {
            epsilon = epsilon * epsilon;
        }
    }

    public void run()
    {
        // start out with a simple list as storage
        List<AlgorithmPoint> data = new ArrayList<AlgorithmPoint>(field.size());

        for (Point p : field.getAllPoints()) {
            AlgorithmPoint op = new AlgorithmPoint(p);
            data.add(op);
        }

        data.addAll(createNoise());

        // determine the dimensions of the data field

        int size = (int) height;

        if (width > height) {
            size = (int) width;
        }

        // initialize the RTree
        points = new RTree(data, startx, starty, size);

        do {
            reachabilityPlot = new ArrayList<AlgorithmPoint>(field.size());

            for (AlgorithmPoint p : data) {
                p.setReachabilityDistance(UNDEFINED);
            }

            restart = false;

            // initialize the seeds Priority Queue
            seeds = new PriorityQueue<AlgorithmPoint>();

            for (AlgorithmPoint p : data) {
                if (p.isProcessed()) {
                    continue;
                }

                expandClusterOrder(p);

                if (restart) {
                    break;
                }
            }
        } while (restart);

        // read the reachability plot and make the clustering
        cluster();
    }

    void expandClusterOrder(AlgorithmPoint p)
    {
        List<List<AlgorithmPoint>> N = getNeighbours(p);

        if (checkEpsilon(N)) {
            return;
        }

        write(p);

        double coredist = coreDistance(N, p);

        if (coredist != UNDEFINED) {
            update(N, p, coredist);

            AlgorithmPoint q;

            while ((q = seeds.poll()) != null) {
                List<List<AlgorithmPoint>> N_ = getNeighbours(q);

                if (checkEpsilon(N_)) {
                    return;
                }

                write(q);

                double coredist_ = coreDistance(N_, q);

                if (coredist_ != UNDEFINED) {
                    update(N_, q, coredist_);
                }
            }
        }
    }

    double coreDistance(List<List<AlgorithmPoint>> N, AlgorithmPoint p)
    {
        if (N.get(1).size() >= minPts) {
            return Calculations.distance(N.get(0).get(0).getPoint(), p.getPoint(), DISTANCE_METRIC);
        }
        return UNDEFINED;
    }

    /**
     * O(n) finding of the neighbours.
     *
     * With R-trees, the running time should decrease to (expected) O(n log n)
     *
     * @return A list with two elements.
     *          - The first one contains the nearest neighbours. (size should be minPts)
     *          - The second one contains the epsilon-neighbourhood.
     */
    List<List<AlgorithmPoint>> getNeighbours(AlgorithmPoint p)
    {
        return points.getNeighbours(p, epsilon, minPts);
    }

    /**
     * Update method
     */
    void update(List<List<AlgorithmPoint>> N, AlgorithmPoint p, double coredist)
    {
        for (AlgorithmPoint o : N.get(1)) {
            if (!o.isProcessed() || o.getReachabilityDistance() == UNDEFINED) {
                double newReachabilityDistance = Math.max(coredist, Calculations.distance(o.getPoint(), p.getPoint(), DISTANCE_METRIC));

                if (o.getReachabilityDistance() == UNDEFINED) {
                    o.setReachabilityDistance(newReachabilityDistance);
                    seeds.add(o);
                } else {
                    if (newReachabilityDistance < o.getReachabilityDistance()) {
                        seeds.remove(o);
                        o.setReachabilityDistance(newReachabilityDistance);
                        seeds.add(o);
                    }
                }
            }
        }
    }

    /**
     * Check if the epsilon-neighbourhood is too big, and decrease the epsilon if so.
     */
    boolean checkEpsilon(List<List<AlgorithmPoint>> N)
    {
        if (N.get(1).size() > MAX_EPSILON_NEIGHBOURHOOD) {
            // calculate new epsilon
            double factor = N.get(1).size() / IDEAL_EPSILON_NEIGHBOURHOOD;
            epsilon = epsilon / factor;

            // and restart
            restart = true;
            return true;
        }

        return false;
    }

    /**
     * Write.
     */
    void write(AlgorithmPoint op)
    {
        if (!op.isProcessed()) {
            op.process();
            op.setX(reachabilityPlot.size());
            reachabilityPlot.add(op);
        }
    }

    class Pair<T,V>
    {
        T t;
        V v;

        public Pair(T t, V v)
        {
            this.t = t;
            this.v = v;
        }

        public T getT()
        {
            return t;
        }

        public V getV()
        {
            return v;
        }
    }

    class PrioPair<T,V extends Comparable<V>> implements Comparable<PrioPair<T,V>>
    {
        T t;
        V v;

        public PrioPair(T t, V v)
        {
            this.t = t;
            this.v = v;
        }

        public T getT()
        {
            return t;
        }

        public V getV()
        {
            return v;
        }

        public int compareTo(PrioPair<T,V> c)
        {
            // to reverse the priority queue, to a max-PriorityQueue
            return v.compareTo(c.getV()) * -1;
        }
    }
}
