package algorithm;

import java.io.*;
import java.util.*;

import model.*;

public abstract class Algorithm
{

    public final static double UNDEFINED = Double.POSITIVE_INFINITY;

    protected Field field;

    /**
     * Constructor.
     */
    public Algorithm()
    {
    }

    /**
     * Find the parameters
     *
     * @param ci Minimum number of clusters
     * @param cj Maximum number of clusters
     * @param n Number of points
     */
    public abstract void findParameters(int ci, int cj, int n);

    /**
     * Set the field.
     *
     * @param field Field
     */
    public void setField(Field field)
    {
        this.field = field;
    }

    /**
     * Run the algorithm.
     */
    public abstract void run();

    /**
     * Cluster the data.
     */
    public void cluster(ArrayList<AlgorithmPoint> reachabilityPlot)
    {
        /*
        // TODO: Define L

        // create the root node
        Node<AlgorithmPoint> root = new Node<AlgorithmPoint>(reachabilityPlot.get(0), null, L);
        */
    }

    /**
     * Cluster the tree.
     */
    public void cluster_tree(Node<AlgorithmPoint> N, Node<AlgorithmPoint> parent, PriorityQueue<AlgorithmPoint> L)
    {
        /*
        if (null == L || L.size() == 0) {
            // parent_of_N is a leaf
            return;
        }

        // take the next largest local maximum as possible separation between clusters
        N.split_point = s = L.poll();

        N1_points = null; // TODO: all points left of s (from N.points)
        N2_points = null; // TODO: all points right of s (from N.points)
        L1 = null; // TODO: all local maxima left of s (from L)
        L2 = null; // TODO: all local maxima right of s (from L)
        // TODO: NodeList NL = [(N1, L1), (N2, L2)]
        
        if ((average reachability value in any node in NL) / s.r_dist > 0.75) {
            // s is not significant
            cluster_tree(N, parent, L);
        } else {
            // remove clusters that are too small
            if (N1.points.size() < min_cluster_size) NL.remove(N1, L1);
            if (N2.points.size() < min_cluster_size) NL.remove(N1, L1);
            if (NL.size() < 10) return; // now parent is a leaf

            if (s.reachability_distance and parent_of_N.split_point.reachability_distance are approximately the same) {
                // let parent point to all nodes in NL instead of N
            } else {
                // let N point to all nodes in NL
            }
            for ((N_i, L_i) in NL) {
                cluster_tree(N_i, parent, L_i);
            }
        }
        */
    }

    /**
     * Point for the algorithms.
     */
    class AlgorithmPoint implements Comparable<AlgorithmPoint>
    {
        double reachabilityDistance = UNDEFINED;

        Point point;

        boolean processed = false;

        public AlgorithmPoint(Point point)
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

        public int compareTo(AlgorithmPoint point)
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
         * Set the cluster.
         */
        public void setCluster(int c)
        {
            point.setCluster(c);
        }

        /**
         * Get the cluster.
         */
        public int getCluster()
        {
            return point.getCluster();
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

    /**
     * Tree
     */
    class Node<T>
    {

        Node<T> parent;

        /**
         * Children.
         */
        ArrayList<Node<T>> children;

        /**
         * The actual data.
         */
        T data;

        public Node(T data)
        {
            this(data, null);
        }
        
        public Node(T data, Node<T> parent)
        {
            this.parent = parent;
            this.data = data;
            children = new ArrayList<Node<T>>();
        }

        public Node<T> getParent()
        {
            return parent;
        }

        /**
         * Create a child.
         */
        public void createChild(T c)
        {
            children.add(new Node<T>(c, this));
        }
    }
}
