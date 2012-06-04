package algorithm;

import java.util.*;
import java.io.*;

import model.*;

/**
 * RTree Node
 */
public class RTreeNode
{

    /**
     * Max number of nodes in bounding box.
     */
    public final static int MIN_POINTS = 2;

    /**
     * Start of the bounding box.
     */
    int startx, starty;

    /**
     * Size of the bounding box.
     */
    int size;

    /**
     * Points in the node.
     */
    List<AlgorithmPoint> points = new ArrayList<AlgorithmPoint>();

    /**
     * Child nodes.
     *
     * Order of child nodes in the bounding box of this object:
     *
     * +---+---+
     * | 1 | 2 |
     * +---+---+
     * | 3 | 4 |
     * +---+---+
     */
    RTreeNode child1, child2, child3, child4;

    /**
     * Parent node.
     */
    RTreeNode parent;

    /**
     * Create an RTreeNode with a set of points.
     *
     * @param startx start of the bounding box x-coordinate
     * @param starty start of the bounding box y-coordinate
     * @param size size of the bounding box
     * @param points Collection of points to add to the node.
     */
    public RTreeNode(RTreeNode parent, int startx, int starty, int size, Collection<AlgorithmPoint> points)
    {
        this(parent, startx, starty, size);

        addPoints(points);
    }

    /**
     * Create an RTreeNode.
     *
     * @param startx start of the bounding box x-coordinate
     * @param starty start of the bounding box y-coordinate
     * @param size size of the bounding box
     */
    public RTreeNode(RTreeNode parent, int startx, int starty, int size)
    {
        this.parent = parent;
        this.startx = startx;
        this.starty = starty;
        this.size = size;
    }

    /**
     * Check if a point is in the bounding box of this node.
     *
     * @param ap The point to check
     *
     * @return If the point is in the bounding box of this node.
     */
    public boolean inBoundingBox(AlgorithmPoint ap)
    {
        Point p = ap.getPoint();

        return inBoundingBox(p.getX(), p.getY());
    }

    /**
     * Check if a point is in the bounding box of this node.
     *
     * @param x X coordinate of the point
     * @param y Y coordinate of the point
     *
     * @return If the point is in the bounding box of this node.
     */
    public boolean inBoundingBox(int x, int y)
    {
        return x >= startx && y >= starty && x < startx + size && y < starty + size;
    }

    /**
     * Add a point.
     *
     * @param p Point to add
     */
    public void addPoint(AlgorithmPoint p)
    {
        points.add(p);
    }

    /**
     * Add multiple points at once.
     *
     * @param points Collection of points to add.
     */
    public void addPoints(Collection<AlgorithmPoint> points)
    {
        this.points.addAll(points);
    }

    /**
     * Build the node.
     *
     * @param points Collection of points
     */
    public void build()
    {
        // if we do not have enough points, we don't build this node
        if (points.size() <= MIN_POINTS) {
            return;
        }
        int newSize = size / 2;

        /*
         * +---+---+
         * | 1 | 2 |
         * +---+---+
         * | 3 | 4 |
         * +---+---+
         */
        child1 = new RTreeNode(this, startx, starty, newSize);
        child2 = new RTreeNode(this, startx + newSize, starty, size - newSize);
        child3 = new RTreeNode(this, startx, starty + newSize, size - newSize);
        child4 = new RTreeNode(this, startx + newSize, starty + newSize, size - newSize);

        // partition the points into the children
        for (AlgorithmPoint p : points) {
            if (child1.inBoundingBox(p)) {
                child1.addPoint(p);
            } else if (child2.inBoundingBox(p)) {
                child2.addPoint(p);
            } else if (child3.inBoundingBox(p)) {
                child3.addPoint(p);
            } else if (child4.inBoundingBox(p)) {
                child4.addPoint(p);
            }
        }

        // recursively call this method on the children
        child1.build();
        child2.build();
        child3.build();
        child4.build();
    }

    /**
     * Find all the nodes in the box.
     *
     * @pre The box has overlap with this node
     *
     * @param box
     *
     * @return nodes in the box
     */
    public List<AlgorithmPoint> findOverlapPoints(RTreeNode box)
    {
        // if we have complete overlap, return all points
        if (hasCompleteOverlap(box) || points.size() <= MIN_POINTS) {
            return points;
        }

        List<AlgorithmPoint> result = new ArrayList<AlgorithmPoint>();

        if (child1.hasOverlap(box)) {
            result.addAll(child1.findOverlapPoints(box));
        }
        if (child2.hasOverlap(box)) {
            result.addAll(child2.findOverlapPoints(box));
        }
        if (child3.hasOverlap(box)) {
            result.addAll(child3.findOverlapPoints(box));
        }
        if (child4.hasOverlap(box)) {
            result.addAll(child4.findOverlapPoints(box));
        }

        return result;
    }

    /**
     * Check if the method has overlap with a bounding box.
     *
     * @param box
     *
     * @return boolean
     */
    public boolean hasOverlap(RTreeNode box)
    {
        return false;
    }

    /**
     * Check if the method has complete overlap with a bounding box.
     *
     * @param box
     *
     * @return boolean
     */
    public boolean hasCompleteOverlap(RTreeNode box)
    {
        return false;
    }
}
