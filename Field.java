import java.io.*;
import java.util.*;

public class Field
{

    int min_x;
    int max_x;

    int min_y;
    int max_y;

    ArrayList<Point> points;

    public Field()
    {
        min_x = Integer.MAX_INT;
        max_x = 0;
        min_y = Integer.MAX_INT;
        max_y = 0;
        points = new ArrayList<Point>();
    }

    public void addPoint(Point p)
    {
        points.add(p);

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
}
