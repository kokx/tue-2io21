package algorithm;

import java.util.*;
import java.io.*;

import model.*;

/**
 * RTree.
 */
public class RTree
{

    /**
     * Root of the tree.
     */
    protected RTreeNode root;

    /**
     * Create an RTree.
     *
     * @param points Points in the tree.
     * @param startx Start of the bounding box, x-coordinate
     * @param starty Start of the bounding box, y-coordinate
     * @param size Size of the bounding box (both width and height).
     */
    public RTree(Collection<AlgorithmPoint> points, int startx, int starty, int size)
    {
        // create and build the tree
        root = new RTreeNode(null, startx, starty, size + 1, points);
        root.build();
    }

    /**
     * Epsilon neighbourhood.
     *
     * @param point Point to search from
     * @param epsilon Distance in all directions to search
     *
     * @return Epsilon neighbourhood
     */
    public List<AlgorithmPoint> getNeighbours(AlgorithmPoint point, double epsilon)
    {
        int eps = (int) Math.ceil(epsilon);
        int x = point.getPoint().getX() - eps;
        int y = point.getPoint().getY() - eps;

        // bounding box for the query
        RTreeNode box = new RTreeNode(null, x, y, eps * 2);

        List<AlgorithmPoint> temp = root.findOverlapPoints(box);

        List<AlgorithmPoint> result = new ArrayList<AlgorithmPoint>();

        // filter out points not in epsilon range
        for (AlgorithmPoint p : temp) {
            if (Calculations.distance(point.getPoint(), p.getPoint(), Algorithm.DISTANCE_METRIC) <= epsilon && p != point) {
                result.add(p);
            }
        }

        return result;
    }

    /**
     * Get the k-th nearest point.
     *
     * @param point Point to search from
     *
     * @return K nearest neighbours, sorted, K-th neighbour first
     */
    public List<AlgorithmPoint> getNeighbours(AlgorithmPoint point, double epsilon, int k)
    {
        List<AlgorithmPoint> neighbours = getNeighbours(point, epsilon);

        // first we create a max priority queue

        PriorityQueue<PrioPair<AlgorithmPoint,Double>> pq = new PriorityQueue<PrioPair<AlgorithmPoint,Double>>();

        for (AlgorithmPoint q : neighbours) {
            double dist = Calculations.distance(point.getPoint(), q.getPoint(), Algorithm.DISTANCE_METRIC);

            PrioPair<AlgorithmPoint,Double> pair = new PrioPair<AlgorithmPoint,Double>(q, dist);

            if (pq.size() <= k) {
                pq.add(pair);
            } else {
                if (dist < ((Double) pq.peek().getV())) {
                    // remove the highest element
                    pq.poll();
                    pq.add(pair);
                }
            }
        }

        // now we make the list

        List<AlgorithmPoint> result = new ArrayList<AlgorithmPoint>();
        PrioPair<AlgorithmPoint,Double> pair;

        while ((pair = pq.poll()) != null) {
            result.add(pair.getT());
        }

        return result;
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
            // we do this to reverse the priority queue, to get a max-PriorityQueue
            return v.compareTo(c.getV()) * -1;
        }
    }
}
