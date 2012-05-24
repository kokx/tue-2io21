package algorithm;

import java.io.*;
import java.util.*;

import model.*;

public abstract class Algorithm
{

    public final static double UNDEFINED = Double.POSITIVE_INFINITY;

    /**
     * Ratio for determining if a point is a local maxima point.
     */
    public final static double DENSITY_RATIO = 0.75;

    /**
     * Treshold for determining if a local maxima is too similiar to its parent.
     */
    public final static double SIMILARITY_TRESHOLD = 0.04;

    // variables

    protected Field field;

    protected int minClusterSize;

    /**
     * Reachability plot
     */
    ArrayList<AlgorithmPoint> reachabilityPlot;

    /**
     * Constructor.
     */
    public Algorithm()
    {
    }

    // abstract methods

    /**
     * Find the parameters
     *
     * @param ci Minimum number of clusters
     * @param cj Maximum number of clusters
     * @param n Number of points
     */
    public abstract void findParameters(int ci, int cj, int n);

    /**
     * Run the algorithm.
     */
    public abstract void run();

    // implemented methods

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
     * Cluster the data.
     */
    public void cluster()
    {
    }

    /**
     * Cluster the tree.
     *
     * @param N Node to cluster
     * @param parent Parent of N, null if N is the root
     * @param L List of local maxima points, sorted in descending order of
     *          reachability.
     */
    public void clusterTree(ClusterNode N, ClusterNode parent, PriorityQueue<AlgorithmPoint> L)
    {
        if (L.size() == 0) {
            // parent is a leaf
            return;
        }

        // take the next largest local maxima point as a possible separation between clusters.
        N.splitPoint = L.poll();

        ClusterNode N1 = new ClusterNode(N);
        ClusterNode N2 = new ClusterNode(N);
        
        N.addChild(N1);
        N.addChild(N2);

        double avg = 0.0;
        int size = N.getPoints().size() - 1;
        // first loop through all points in N, and also determine the average
        for (AlgorithmPoint p : N.getPoints()) {
            if (p.getX() < N.splitPoint.getX()) {
                N1.addPoint(p);
                avg += p.getReachabilityDistance() / size;
            } else if (p.getX() != N.splitPoint.getX()) {
                N2.addPoint(p);
                avg += p.getReachabilityDistance() / size;
            }
        }

        // now keep popping elements from L
        PriorityQueue<AlgorithmPoint> L1 = new PriorityQueue<AlgorithmPoint>();
        PriorityQueue<AlgorithmPoint> L2 = new PriorityQueue<AlgorithmPoint>();

        // we don't need any particular order, thus we simply loop through it
        for (AlgorithmPoint p : L) {
            if (p.getX() < N.splitPoint.getX()) {
                L1.add(p);
            } else if (p.getX() != N.splitPoint.getX()) {
                L2.add(p);
            }
        }

        // avg is the average reachability distance in any node of N
        if (avg / N.splitPoint.getReachabilityDistance() > DENSITY_RATIO) {
            // ignore the split point and continue
            clusterTree(N, parent, L);
        } else {
            if (N1.getPoints().size() > minClusterSize && N2.getPoints().size() > minClusterSize) {
                return;
            }

            // if (N.splitPoint.getReachabilityDistance() and
            //     parent.splitPoint.getReachabilityDistance()
            //     are approximately the same)
            // Then the points of the parent are the points of N
            // Other options:
            // - Take the size of N compared to parent, and see if they are
            //   approximately the same.
            // - Take the average of the reachability distances of N and parent,
            //   and see if they are approximately the same.
            //
            // Source of these ideas:
            // https://github.com/amyxzhang/OPTICS-Automatic-Clustering/blob/master/AutomaticClustering.py
            double diff = Math.abs(N.splitPoint.getReachabilityDistance() / parent.splitPoint.getReachabilityDistance());
            if (diff < 1.0 + SIMILARITY_TRESHOLD && diff > 1.0 - SIMILARITY_TRESHOLD) {
                /*
                 * the split points are approximately the same
                 * we will bypass the current node, remove it from its parent,
                 * and add its children to the parent.
                 * 
                 * This will make the tree more compressed in height. And it
                 * will also make sure it is not a binary tree.
                 */
                parent.getChildren().remove(N);
                N = parent;
            }

            if (N1.getPoints().size() > minClusterSize) {
                clusterTree(N1, N, L1);
            }
            if (N2.getPoints().size() > minClusterSize) {
                clusterTree(N2, N, L2);
            }
        }
    }

    /**
     * Get the local maxima.
     *
     * @return local maxima
     */
    private PriorityQueue<AlgorithmPoint> findLocalMaxima()
    {
        PriorityQueue<AlgorithmPoint> maxima = new PriorityQueue<AlgorithmPoint>();

        /*
         * We go through the reachability plot from left to right. For
         * each element, we check if the average reachability of the reachabilityPlot
         * left and right of the current point are lower than or equal to
         * (point * DENSITY_RATIO). If this is the case, it is a local
         * maxima.
         *
         * There are two edge cases: the first and the last element. For
         * both it holds that their neighbour (they only have one) has to
         * be higher than (point * DENSITY_RATIO) to be a local maxima.
         */
        for (int i = 0; i < reachabilityPlot.size(); i++) { //going through the reachability plot from left to right
            if (i == 0) {
                // first element
                if (reachabilityPlot.get(0).getReachabilityDistance() * DENSITY_RATIO > reachabilityPlot.get(1).getReachabilityDistance()) {
                    maxima.add(reachabilityPlot.get(0));
                }
            } else if (i == reachabilityPlot.size() - 1) {
                // last element
                if (reachabilityPlot.get(i).getReachabilityDistance() * DENSITY_RATIO > reachabilityPlot.get(i - 1).getReachabilityDistance()) {
                    maxima.add(reachabilityPlot.get(i));
                }
            } else {
                // average neighbours and compare
                double avg = reachabilityPlot.get(i - 1).getReachabilityDistance() + reachabilityPlot.get(i + 1).getReachabilityDistance();
                if (reachabilityPlot.get(i).getReachabilityDistance() * DENSITY_RATIO > avg) {
                    maxima.add(reachabilityPlot.get(i));
                }
            }
        }

        return maxima;
    }

    /**
     * Print reachability distances.
     */
    public void printReachability()
    {
        for (AlgorithmPoint p : reachabilityPlot) {
            System.out.println(p.getCluster() + " " + p.getReachabilityDistance());
        }
    }

    /**
     * Cluster tree.
     */
    class ClusterNode
    {
        /**
         * Children of this node.
         */
        List<ClusterNode> children = new ArrayList<ClusterNode>();

        /**
         * Points in this node.
         */
        List<AlgorithmPoint> points = new ArrayList<AlgorithmPoint>();

        /**
         * This node's parent.
         */
        ClusterNode parent;

        /**
         * The split point.
         */
        AlgorithmPoint splitPoint;

        /**
         * Constructor.
         *
         * @param parent Parent of the created node
         */
        public ClusterNode(ClusterNode parent)
        {
            this.parent = parent;
        }

        /**
         * Get the children.
         *
         * @return children
         */
        public List<ClusterNode> getChildren()
        {
            return children;
        }

        /**
         * Get the points.
         *
         * @return points
         */
        public List<AlgorithmPoint> getPoints()
        {
            return points;
        }

        /**
         * Get the parent.
         *
         * @return parent
         */
        public ClusterNode getParent()
        {
            return parent;
        }

        /**
         * Add a child.
         *
         * @param child Child of the node.
         */
        public void addChild(ClusterNode child)
        {
            children.add(child);
        }

        /**
         * Add a point.
         *
         * @param point Point of this node.
         */
        public void addPoint(AlgorithmPoint point)
        {
            points.add(point);
        }
    }

    /**
     * Point for the algorithms.
     */
    class AlgorithmPoint implements Comparable<AlgorithmPoint>
    {
        double reachabilityDistance = UNDEFINED;
        // location in the reachability plot
        int x;

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

        /**
         * Set the x.
         */
        public void setX(int x)
        {
            this.x = x;
        }

        /**
         * Get the x.
         */
        public int getX()
        {
            return x;
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

}
