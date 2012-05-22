package algorithm;

import java.io.*;
import java.util.*;

import model.*;

public abstract class Algorithm
{

    public final static double UNDEFINED = Double.POSITIVE_INFINITY;

    protected Field field;

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
        // TODO: Implement
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

}
