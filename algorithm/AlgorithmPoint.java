package algorithm;

import java.io.*;
import java.util.*;

import model.*;

/**
 * Point for the algorithms.
 */
class AlgorithmPoint implements Comparable<AlgorithmPoint>
{

    double reachabilityDistance = Algorithm.UNDEFINED;

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
