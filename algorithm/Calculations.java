package algorithm;

import java.util.*;
import java.io.*;

import model.*;

/**
 * Handy calculations.
 */
public class Calculations
{
    public static double distance(Point sourcePoint, Point destPoint, int m)
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
