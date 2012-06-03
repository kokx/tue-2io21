package model;

import java.io.*;
import java.util.*;

public class Field
{

    int min_x;
    int max_x;

    int min_y;
    int max_y;

    TreeMap<Long,Point> points;

    public Field()
    {
        min_x = Integer.MAX_VALUE;
        max_x = 0;
        min_y = Integer.MAX_VALUE;
        max_y = 0;
        points = new TreeMap<Long,Point>();
    }

    public void addPoint(Point p)
    {
        if (hasPoint(p.getX(), p.getY())) {
            return;
        }
        points.put(getKey(p.getX(), p.getY()), p);

        if (p.getX() < min_x) {
            min_x = p.getX();
        }
        if (p.getX() > max_x) {
            max_x = p.getX();
        }

        if (p.getY() < min_y) {
            min_y = p.getY();
        }
        if (p.getY() > max_y) {
            max_y = p.getY();
        }
    }

    public int size()
    {
        return points.size();
    }

    public Collection<Point> getAllPoints()
    {
        return points.values();
    }

    public TreeMap<Long,Point> getPointsMap()
    {
        return points;
    }

    public Point getPoint(int x, int y)
    {
        return points.get(getKey(x, y));
    }

    public boolean hasPoint(int x, int y)
    {
        return points.containsKey(getKey(x, y));
    }

    public long getKey(int x, int y)
    {
        long key = x;
        key = key << 32;
        key += y;
        return key;
    }

    /**
     * Get the width of the field.
     */
    public long getWidth()
    {
        return (long) (max_x - min_x);
    }

    /**
     * Get the height of the field.
     */
    public long getHeight()
    {
        return (long) (max_y - min_y);
    }

    public int getMaxX()
    {
        return max_x;
    }

    public int getMinX()
    {
        return min_x;
    }

    public int getMaxY()
    {
        return max_y;
    }

    public int getMinY()
    {
        return min_y;
    }
}
