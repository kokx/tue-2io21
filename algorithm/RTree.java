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
        root = new RTreeNode(startx, starty, size + 1, points);
        root.build();
    }

    /**
     * Query the tree.
     *
     * @param x X coordinate to look for.
     * @param y Y coordinate to look for.
     *
     * @return The point we have found, null if there is no point in that position.
     */
    public RTreeNode findBox(AlgorithmPoint p)
    {
        return null;
    }
}
