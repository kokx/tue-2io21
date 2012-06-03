package algorithm;

import java.io.*;
import java.util.*;
import java.lang.reflect.Array;

import model.*;
import algorithm.*;

/**
 * Stub algorithm.
 */
public class Allnoise extends Algorithm
{
    /**
     * Constructor.
     */
    public Allnoise()
    {
        super();
    }

    /**
     * Find the parameters
     *
     * @param ci Minimum number of clusters
     * @param cj Maximum number of clusters
     * @param n Number of points
     */
    public void findParameters(int ci, int cj, int n, long width, long height)
    {
        super.findParameters(ci, cj, n, width, height);
    }

    public void run()
    {
        for (Point p : field.getAllPoints()) {
            p.setCluster(0);
        }
    }
}
