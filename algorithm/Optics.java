package algorithm;

import java.io.*;
import java.util.*;

import model.*;

/**
 * Implementation of OPTICS.
 */
public class Optics extends Algorithm
{

    public final static int UNDEFINED = -1;

    /**
     * Seeds queue.
     */
    PriorityQueue<OpticPoint> seeds;

    /**
     * Map of points.
     */
    TreeMap<Long, OpticPoint> points;

    /**
     * Parameters.
     */
    int eps;
    int minPts;

    /**
     * Constructor.
     *
     * @param field
     */
    public Optics(Field field, int eps, int minPts)
    {
        super(field);
        this.eps = eps;
        this.minPts = minPts;
    }

    public void run()
    {
        for (Point p : field.getAllPoints()) {
            OpticPoint op = new OpticPoint(p);
            points.put(op.getKey(), op);
        }

        // initialize the Priority Queue
        seeds = new PriorityQueue<OpticPoint>();

        for (Iterator<OpticPoint> it = points.values().iterator(); it.hasNext(); ) {
            OpticPoint p = it.next();

            it.remove();

            // TODO output p to the ordered list

            expandClusterOrder(p);
        }
    }

    void expandClusterOrder(OpticPoint p)
    {
        List N = getNeighbors();
    }

    int coreDistance(OpticsPoint p)
    {
    }

    /**
     * Point for OPTICS.
     */
    class OpticPoint implements Comparable<OpticPoint>
    {
        int reachabilityDistance = UNDEFINED;

        Point point;

        public OpticPoint(Point point)
        {
            this.point = point;
        }

        public void setReachabilityDistance(int distance)
        {
            reachabilityDistance = distance;
        }

        public int getReachabilityDistance()
        {
            return reachabilityDistance;
        }

        public int compareTo(OpticPoint point)
        {
            if (reachabilityDistance > point.getReachabilityDistance()) {
                return 1;
            } else if (reachabilityDistance < point.getReachabilityDistance()) {
                return -1;
            } else {
                return 0;
            }
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
