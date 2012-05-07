import java.io.*;
import java.util.*;

public class Point
{

    int x;
    int y;
    int c; // cluster number

    public Point(int x, int y, int c)
    {
        this.x = x;
        this.y = y;
        this.c = c;
    }

    public int getX()
    {
        return x;
    }

    public int getY()
    {
        return y;
    }

    public int getCluster()
    {
        return c;
    }

    public void setCluster(int c)
    {
        this.c = c;
    }
}
