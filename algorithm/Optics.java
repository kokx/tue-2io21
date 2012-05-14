package algorithm;

import java.io.*;
import java.util.*;

import model.*;
import algorithm.*;

/**
 * Implementation of OPTICS.
 */
public class Optics extends Algorithm
{

    public final static double UNDEFINED = Double.POSITIVE_INFINITY;
    public final static int DISTANCE_METRIC = Calculations.DISTANCE_MANHATTAN;

    /**
     * Seeds queue.
     */
    PriorityQueue<OpticsPoint> seeds;

    /**
     * Map of points.
     */
    TreeMap<Long, OpticsPoint> points;

    /**
     * Parameters.
     */
    double epsilon;
    int minPts;


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
    public void findParameters(int ci, int cj, int n)
    {
        this.epsilon = 10000.0;
        this.minPts = 300;
    }

    public void run()
    {
        points = new TreeMap<Long,OpticsPoint>();

        for (Point p : field.getAllPoints()) {
            OpticsPoint op = new OpticsPoint(p);
            points.put(op.getKey(), op);
        }

        // initialize the Priority Queue
        seeds = new PriorityQueue<OpticsPoint>();

        for (OpticsPoint p : points.values()) {
            if (p.isProcessed()) {
                continue;
            }
            p.process();

            expandClusterOrder(p);
        }
    }

    void expandClusterOrder(OpticsPoint p)
    {
        Pair<List<PrioPair<OpticsPoint,Double>>, List<PrioPair<OpticsPoint,Double>>> nn = getNeighbours(p);
        List<PrioPair<OpticsPoint,Double>> N = nn.getV();

        write(p);
        
        if (coreDistance(N, p) != UNDEFINED) {
            update(N, p);

            OpticsPoint q;

            while ((q = seeds.poll()) != null) {
                Pair<List<PrioPair<OpticsPoint,Double>>, List<PrioPair<OpticsPoint,Double>>> nn_ = getNeighbours(p);
                List<PrioPair<OpticsPoint,Double>> N_ = nn_.getV();
                q.process();

                write(q);
                
                if (coreDistance(N, q) != UNDEFINED) {
                    update(N_, q);
                }
            }
        }
    }

    double coreDistance(List<PrioPair<OpticsPoint,Double>> N, OpticsPoint p)
    {
        if (N.size() >= minPts) {
            return N.get(0).getV().doubleValue();
        }
        return UNDEFINED;
    }

    /**
     * O(n) finding of the neighbour.
     *
     * @return A pair of lists, the first one contains the epsilon distances and the
     *         second one all the points on minPts distance
     */
    Pair<List<PrioPair<OpticsPoint,Double>>, List<PrioPair<OpticsPoint,Double>>> getNeighbours(OpticsPoint p)
    {
        List<PrioPair<OpticsPoint,Double>> list = new ArrayList<PrioPair<OpticsPoint,Double>>();
        List<PrioPair<OpticsPoint,Double>> nextNeighbours = new ArrayList<PrioPair<OpticsPoint,Double>>();

        PriorityQueue<PrioPair<OpticsPoint,Double>> pq = new PriorityQueue<PrioPair<OpticsPoint,Double>>();

        for (OpticsPoint q : points.values()) {
            double dist = Calculations.distance(p.getPoint(), q.getPoint(), DISTANCE_METRIC);

            PrioPair<OpticsPoint,Double> pair = new PrioPair<OpticsPoint,Double>(q, dist);

            if (dist <= epsilon) {
                list.add(pair);
            }

            // add the pair
            if (pq.size() < minPts) {
                pq.add(pair);
            } else {
                if (dist < ((Double) pq.peek().getV())) {
                    // remove the highest element
                    pq.poll(); 
                    pq.add(pair);
                }
            }
        }

        for (PrioPair<OpticsPoint,Double> pair : pq) {
            nextNeighbours.add(pair);
        }

        return new Pair<List<PrioPair<OpticsPoint,Double>>, List<PrioPair<OpticsPoint,Double>>>(list, nextNeighbours);
    }

    /**
     * Update method
     */
    void update(List<PrioPair<OpticsPoint,Double>> N, OpticsPoint p)
    {
        double coredist = coreDistance(N, p);

        for (PrioPair<OpticsPoint,Double> pair : N) {
            OpticsPoint o = pair.getT();
            if (!pair.getT().isProcessed()) {
                double newReachabilityDistance = Math.max(coredist, Calculations.distance(o.getPoint(), p.getPoint(), DISTANCE_METRIC));

                if (o.getReachabilityDistance() == UNDEFINED) {
                    o.setReachabilityDistance(newReachabilityDistance);
                    seeds.add(o);
                } else {
                    if (newReachabilityDistance < o.getReachabilityDistance()) {
                        seeds.remove(o);
                        seeds.add(o);
                    }
                }
            }
        }
    }

    void write(OpticsPoint op)
    {
        Point p = op.getPoint();
        //System.out.println("X: " + p.getX() + " Y: " + p.getY() + " Reach: " + op.getReachabilityDistance());
    }

    /**
     * Point for OPTICS.
     */
    class OpticsPoint implements Comparable<OpticsPoint>
    {
        double reachabilityDistance = UNDEFINED;

        Point point;

        boolean processed = false;

        public OpticsPoint(Point point)
        {
            this.point = point;
        }

        public void setReachabilityDistance(double distance)
        {
            reachabilityDistance = distance;
        }

        public double getReachabilityDistance()
        {
            return reachabilityDistance;
        }

        public int compareTo(OpticsPoint point)
        {
            if (reachabilityDistance > point.getReachabilityDistance()) {
                return 1;
            } else if (reachabilityDistance < point.getReachabilityDistance()) {
                return -1;
            } else {
                return 0;
            }
        }

        public boolean isProcessed()
        {
            return processed;
        }

        public void process()
        {
            processed = true;
        }

        /**
         * Get the point.
         */
        public Point getPoint()
        {
            return point;
        }

        /**
         * Get a key for the tree.
         */
        public long getKey()
        {
            long key = (long) point.getX();
            key = key << 32;
            key += (long) point.getY();
            return key;
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
            // to reverse the priority queue
            return v.compareTo(c.getV()) * -1;
        }
    }
}
