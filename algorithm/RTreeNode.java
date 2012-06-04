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
    RTreeNode child1;
    RTreeNode child2;
    RTreeNode child3;
    RTreeNode child4;

    /**
     * Create an RTreeNode with a set of points.
     *
     * @param startx start of the bounding box x-coordinate
     * @param starty start of the bounding box y-coordinate
     * @param size size of the bounding box
     * @param points Collection of points to add to the node.
     */
    public RTreeNode(int startx, int starty, int size, Collection<AlgorithmPoint> points)
    {
        this(startx, starty, size);

        addAll(points);
    }

    /**
     * Create an RTreeNode.
     *
     * @param startx start of the bounding box x-coordinate
     * @param starty start of the bounding box y-coordinate
     * @param size size of the bounding box
     */
    public RTreeNode(int startx, int starty, int size)
    {
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

        return p.getX() >= startx && p.getY() >= starty && p.getX() < startx + size && p.getY() < starty + size;
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
        // TODO: first determine the bounding boxes of the children

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
}
