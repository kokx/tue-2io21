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
            if (Calculations.distance(point.getPoint(), p.getPoint(), Algorithm.DISTANCE_METRIC) <= epsilon) {
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
    public List<AlgorithmPoint> getNeighbours(AlgorithmPoint point, int k)
    {
        // TODO: Implement this method

        // first we create a max priority queue

        // then we search for the neighbours in the rectangles next to the
        // point

        List<AlgorithmPoint> result = new ArrayList<AlgorithmPoint>();

        return result;
    }
}
