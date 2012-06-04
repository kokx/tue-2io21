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
        root = new RTreeNode(startx, starty, size, points);
    }
}
