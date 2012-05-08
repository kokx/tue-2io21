package algorithm;

import java.io.*;
import java.util.*;

import model.*;

public abstract class Algorithm
{

    protected Field field;

    /**
     * Constructor.
     *
     * @param field
     */
    public Algorithm(Field field)
    {
        this.field = field;
    }

    /**
     * Run the algorithm.
     */
    public abstract void run();
    
    /**
     * Determine the distance between sourcePoint and destPoint.
     * 
     * @param sourcePoint Source point
     * @param destPoint Destination point
     * @param m 2 = Euclidean, 1 = Manhatten
     * @return Distance from sourcePoint to destPoint
     */
    public double Distance(Point sourcePoint, Point destPoint, int m)
    {
        long dx = (long) Math.abs(destPoint.getX() - sourcePoint.getX());
        long dy = (long) Math.abs(destPoint.getY() - sourcePoint.getY());

        switch (m) {
            case 1: 
                return (double) (dx + dy);
            default:
                return Math.pow(Math.pow(dx, m) + Math.pow(dy, m), 1.0/m);
        }
    }
}
