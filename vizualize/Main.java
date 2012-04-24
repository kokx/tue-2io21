import java.io.*;
import java.util.*;
import java.awt.image.*;

class Main {
    Scanner sc;
    BufferedImage img;

    public Main()
    {
        sc = new Scanner(System.in);
    }

    void run()
    {
        ArrayList<Integer> points_x = new ArrayList<Integer>();
        ArrayList<Integer> points_y = new ArrayList<Integer>();
        ArrayList<Integer> points_c = new ArrayList<Integer>(); // cluster

        int min_x = Integer.MAX_VALUE;
        int max_x = 0;
        int min_y = Integer.MAX_VALUE;
        int max_y = 0;

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

        System.out.println("X min: " + min_x + " max: " + max_x + " size: " + (max_x - min_x));
        System.out.println("Y min: " + min_y + " max: " + max_y + " size: " + (max_x - min_x));

        // now start creating the image
        img = new BufferedImage((max_x - min_x) + 2, (max_y - min_y) + 2, BufferedImage.TYPE_INT_RGB);

    }

    public static void main(String args[])
    {
        new Main().run();
    }
}
