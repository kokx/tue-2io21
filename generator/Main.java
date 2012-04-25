import java.io.*;
import java.util.*;

class Main {
    Scanner sc;

    public Main()
    {
        sc = new Scanner(System.in);
    }

    void run()
    {
        Generator generator = new Generator(1000, 1000);
        output(generator.generate(0, 3, 50, 6000));
    }

    void output(Field field)
    {
        ArrayList<Point> points = field.getAllPoints();

        System.out.println("find 2 to 4 clusters");
        System.out.println(field.getAllPoints().size() + " points");

        for (Point p : points) {
            System.out.println(p.getX() + " " + p.getY());
        }
    }

    public static void main(String args[])
    {
        new Main().run();
    }
}
