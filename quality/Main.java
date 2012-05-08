package quality;

import java.io.*;
import java.util.*;
import java.awt.image.*;
import java.awt.*;
import javax.imageio.*;

class Main {
    Scanner sc;

    long min_x;
    long max_x;
    long min_y;
    long max_y;

    ArrayList<Integer> points_x = new ArrayList<Integer>();
    ArrayList<Integer> points_y = new ArrayList<Integer>();
    ArrayList<Integer> points_c = new ArrayList<Integer>(); // cluster

    public Main()
    {
        sc = new Scanner(System.in);
    }

    void run()
    {
        min_x = Integer.MAX_VALUE;
        max_x = 0;
        min_y = Integer.MAX_VALUE;
        max_y = 0;

        for (int i = 0; sc.hasNextInt(); i++) {
            points_x.add(sc.nextInt());
            points_y.add(sc.nextInt());
            points_c.add(sc.nextInt());

            if (points_x.get(i) < min_x) {
                min_x = points_x.get(i);
            }
            if (points_x.get(i) > max_x) {
                max_x = points_x.get(i);
            }
            if (points_y.get(i) < min_y) {
                min_y = points_y.get(i);
            }
            if (points_y.get(i) > max_y) {
                max_y = points_y.get(i);
            }
        }
    }

    public static void main(String args[])
    {
        new Main().run();
    }
}
