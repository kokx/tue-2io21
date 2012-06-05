package algorithm;

import java.io.*;
import java.util.*;

import model.*;

public abstract class Algorithm
{

    public final static double UNDEFINED = Double.POSITIVE_INFINITY;

    // DISTANCE_MANHATTAN, DISTANCE_EUCLIDIAN or DISTANCE_EUCLIDIAN_SQ
    // Euclidian Sq is the same as Euclidian, but does not take the square root,
    // but instead squares the epsilon parameter. Thus it is a lot faster.
    public final static int DISTANCE_METRIC = Calculations.DISTANCE_MANHATTAN;

    /**
     * Ratio for determining if a point is a local maxima point.
     */
    public final static double DENSITY_RATIO = 0.90;

    /**
     * Treshold for determining if a local maxima is too similiar to its parent.
     *
     * A higher treshold means that the tree will have less levels.
     */
    public final static double SIMILARITY_TRESHOLD = 0.04;

    // variables

    protected Field field;

    protected int minClusterSize;

    /**
     * Mininmum (ci) and maximum (cj) of found clusters.
     */
    protected int ci, cj;

    /**
     * Width and height.
     */
    protected long width, height;

    /**
     * Startx and starty.
     */
    protected int startx, starty;

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
     * Find the parameters.
     *
     * @param ci Minimum number of clusters
     * @param cj Maximum number of clusters
     * @param n Number of points
     * @param width Field width
     * @param height Field height
     */
    public void findParameters(int ci, int cj, int n, long width, long height, int startx, int starty)
    {
        this.ci = ci;
        this.cj = cj;

        this.width = width;
        this.height = height;

        this.startx = startx;
        this.starty = starty;
    }

    /**
     * Create noise.
     *
     * We create at least 5*5 = 25 points, and at most 50*50=2500 points
     *
     * Input size / 100.
     */
    public List<AlgorithmPoint> createNoise()
    {
        ArrayList<AlgorithmPoint> noise = new ArrayList<AlgorithmPoint>();

        Random rand = new Random();

        int num = field.size() / 75;

        if (num < 4) {
            num = 4;
        } else if (num > 50) {
            num = 50;
        }

        int width = (int) field.getWidth() / num;
        int height = (int) field.getHeight()/ num;

        for (int i = 0; i < num; i++) {
            for (int j = 0; j < num; j++) {
                int x, y;
                do {
                    x = width * i + rand.nextInt(width);
                    y = height * j + rand.nextInt(height);
                } while (field.hasPoint(x, y));
                noise.add(new AlgorithmPoint(new Point(x, y, 0)));
            }
        }

        return noise;
    }

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
        // first make the root node
        ClusterNode root = new ClusterNode(null);

        for (AlgorithmPoint p : reachabilityPlot) {
            if (p.getReachabilityDistance() == UNDEFINED) {
                p.setReachabilityDistance(0);
                p.setCluster(0);
            } else {
                root.addPoint(p);
            }
        }

        PriorityQueue<AlgorithmPoint> localMaxima = findLocalMaxima();

        clusterTree(root, null, localMaxima);

        // debug
        //printTree(root);

        Set<ClusterNode> clusters = extractClusters(root);

        int id = 1;

        for (ClusterNode cluster : clusters) {
            for (AlgorithmPoint p : cluster.getPoints()) {
                if (p.getCluster() == 0) {
                    p.setCluster(id);
                }
            }

            id++;
        }
    }

    protected int count(ClusterNode c)
    {
        int num = 1;

        for (ClusterNode child : c.getChildren()) {
            num += count(child);
        }

        return num;
    }

    /**
     * Extract clusters from the tree.
     *
     * @param tree Root node of the tree.
     */
    protected Set<ClusterNode> extractClusters(ClusterNode tree)
    {
        // since tree is the root node, we can simply copy the array of its
        // children
        Set<ClusterNode> current = new HashSet<ClusterNode>(tree.getChildren());

        //System.out.println("bla: " + tree.getChildren().size());

        boolean changed = true;

        // now loop until current.size() >= ci, or we don't change anything in a round
        while (current.size() < ci && changed) {
            changed = false;

            Set<ClusterNode> newCurrent = new HashSet<ClusterNode>(current.size() * 2);
            for (ClusterNode node : current) {
                //System.out.println("size: " + node.getChildren().size());
                if (node.getChildren().size() > 0) {
                    newCurrent.addAll(node.getChildren());
                    changed = true;
                } else {
                    newCurrent.add(node);
                }
            }

            current = newCurrent;
        }

        // create a set of interesting parents
        Set<ClusterNode> parents = new HashSet<ClusterNode>(current.size() * 2);

        for (ClusterNode node : current) {
            if (node.getParent().getChildren().size() > 1) {
                // we only add parents with at least 2 children, otherwise
                // they are not interesting
                parents.add(node.getParent());
            }
        }

        PriorityQueue<ClusterCombination> combinations = new PriorityQueue<ClusterCombination>();

        // for each parent, check all combinations of all their children
        for (ClusterNode parent : parents) {
            // make all combinations of its children
            ClusterNode children[] = (ClusterNode[]) parent.getChildren().toArray(new ClusterNode[0]);

            for (int i = 0; i < children.length - 1; i++) {
                for (int j = i + 1; j < children.length; j++) {
                    // add the combination
                    combinations.add(new ClusterCombination(children[i], children[j]));
                }
            }
        }

        /*
         * We have at least ci nodes in the current list.
         *
         * If we have more than cj nodes, we will have to merge some of these
         * nodes, however.
         */
        while (current.size() > cj) {
            ClusterCombination comb;
            // first get the next existing combination
            do {
                comb = combinations.poll();
            } while (null != comb && !comb.exists());

            current = comb.merge(current, combinations);
        }

        return current;
    }

    /**
     * Cluster the tree.
     *
     * @param N Node to cluster
     * @param parent Parent of N, null if N is the root
     * @param L List of local maxima points, sorted in descending order of
     *          reachability.
     */
    protected void clusterTree(ClusterNode N, ClusterNode parent, PriorityQueue<AlgorithmPoint> L)
    {
        if (L.size() == 0) {
            // parent is a leaf
            return;
        }

        // take the next largest local maxima point as a possible separation between clusters.
        N.splitPoint = L.poll();

        ClusterNode N1 = new ClusterNode(N);
        ClusterNode N2 = new ClusterNode(N);

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
        // this makes it O(n) instead of O(n log n)
        for (AlgorithmPoint p : L) {
            if (p.getX() < N.splitPoint.getX()) {
                L1.add(p);
            } else if (p.getX() > N.splitPoint.getX()) {
                L2.add(p);
            }
        }

        // avg is the average reachability distance in any node of N
        if (avg / N.splitPoint.getReachabilityDistance() > DENSITY_RATIO) {
            // ignore the split point and continue
            clusterTree(N, parent, L);
        } else {
            if (N1.getPoints().size() < minClusterSize && N2.getPoints().size() < minClusterSize) {
                return;
            }

            /*
             * Determine if N and parent are approximately the same cluster.
             *
             * If that is the case, we remove N as a child of parent, and make
             * the children of N, children of parent.
             *
             * There are three ways to check if N and parent are approximately
             * the same:
             *  1) Take the reachability distance of the split points of N and
             *      parent, and check if they are approximately the same.
             *  2) Take the size of N compared to parent and see if the
             *      sizes are approximately the same.
             *  3) Take the average of the reachability distances of N and
             *      parent and see if they are approximately the same.
             *
             * Source of some of these ideas:
             * https://github.com/amyxzhang/OPTICS-Automatic-Clustering/blob/master/AutomaticClustering.py
             */
            if (parent != null) {
                double diff = Math.abs(N.splitPoint.getReachabilityDistance() - parent.splitPoint.getReachabilityDistance());
                if (diff < 0.0001) {
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
            }

            if (N1.getPoints().size() >= minClusterSize) {
                N.addChild(N1);
                clusterTree(N1, N, L1);
            }
            if (N2.getPoints().size() >= minClusterSize) {
                N.addChild(N2);
                clusterTree(N2, N, L2);
            }
        }
    }

    /**
     * Get the local maxima.
     *
     * @return local maxima
     */
    protected PriorityQueue<AlgorithmPoint> findLocalMaxima()
    {
        PriorityQueue<AlgorithmPoint> maxima = new PriorityQueue<AlgorithmPoint>(20, new Comparator<AlgorithmPoint>()
                {
                    public int compare(AlgorithmPoint a, AlgorithmPoint b) {
                        return b.compareTo(a);
                    }
                });

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
                double avg = (reachabilityPlot.get(i - 1).getReachabilityDistance() + reachabilityPlot.get(i + 1).getReachabilityDistance()) / 2;
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
     * Print the cluster tree.
     */
    public void printTree(ClusterNode node)
    {
        System.out.println("Ch: " + node.getPoints().size() + " {");

        for (ClusterNode child : node.getChildren()) {
            printTree(child);
        }

        System.out.println("}");
    }

    /**
     * A cluster combination.
     */
    class ClusterCombination implements Comparable<ClusterCombination>
    {
        ClusterNode c1;
        ClusterNode c2;

        double diff = 0;

        public ClusterCombination(ClusterNode c1, ClusterNode c2)
        {
            this.c1 = c1;
            this.c2 = c2;

            /**
             * TODO.
             *
             * Take a random point from each of the cluster nodes. Take the
             * distance between these points. That is the 'diff'.
             *
             * TODO
             *
             * Investigate if we can find the point in the cluster with the
             * lowest reachability distance. This is O(n), we need to check
             * if we can do this fast enough. Or we take the centroid, also
             * O(n), but with higher constants.
             */
            int p1 = c1.getPoints().get(minClusterSize).getX();
            int p2 = c2.getPoints().get(minClusterSize).getX();

            diff = (double) Math.abs(p1 - p2);

            //System.out.println("D: " + diff + " S1: " + c1.getPoints().size() + " S2: " + c2.getPoints().size());
        }

        /**
         * Compare clusters.
         *
         * This makes it a min-priorityqueue
         */
        public int compareTo(ClusterCombination o)
        {
            if (diff < o.diff) {
                return -1;
            } else if (diff > o.diff) {
                return 1;
            } else {
                return 0;
            }
        }

        /**
         * It exists if none of the nodes are removed.
         */
        public boolean exists()
        {
            return !c1.isRemoved() && !c2.isRemoved();
        }

        /**
         * Merge the combination.
         */
        public Set<ClusterNode> merge(Set<ClusterNode> current, PriorityQueue<ClusterCombination> combinations)
        {
            current.remove(c1);
            current.remove(c2);

            // mark as removed
            c1.remove();
            c2.remove();

            if (c1.getParent().getChildren().size() == 2) {
                // simply replace with parent
                current.add(c1.getParent());
            } else {
                // create a new node
                ClusterNode node = new ClusterNode(c1.getParent());

                // remove them from the parent
                c1.getParent().getChildren().remove(c1);
                c1.getParent().getChildren().remove(c2);

                // merge
                node.addPoints(c1.getPoints());
                node.addPoints(c2.getPoints());

                // add this node to the combinations
                for (ClusterNode c : c1.getParent().getChildren()) {
                    combinations.add(new ClusterCombination(c, node));
                }

                // finish the merge
                c1.getParent().addChild(node);
                current.add(node);
            }


            return current;
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
         * Removed marker.
         */
        boolean removed = false;


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

        /**
         * Add multiple points.
         *
         * @param points points to add
         */
        public void addPoints(Collection<AlgorithmPoint> c)
        {
            points.addAll(c);
        }

        /**
         * Remove.
         */
        public void remove()
        {
            removed = true;
        }

        /**
         * Is removed.
         */
        public boolean isRemoved()
        {
            return removed;
        }
    }

}
