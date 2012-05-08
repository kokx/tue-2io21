package visualize;

import java.io.*;
import java.util.*;
import java.awt.image.*;
import java.awt.*;
import javax.imageio.*;

import model.*;

class Main {
    Scanner sc;
    BufferedImage img;

    public final static long MAX_SIZE = 1000;
    public final static double SCALE_POINT = 1.0;
    public final static boolean ALL_BLACK = false;

    long min_x;
    long max_x;
    long min_y;
    long max_y;

    long size_x;
    long size_y;

    double scale_x;
    double scale_y;

    long[][] map;

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

        // calculate image dimensions, we add two to the diff, so the edge
        // values will also be displayed correctly
        long diff_x = max_x - min_x + 2;
        long diff_y = max_y - min_y + 2;

        if (diff_x > diff_y) {
            size_x = MAX_SIZE;
            size_y = ((MAX_SIZE * diff_y) / diff_x);

            scale_x = size_x / (diff_x * 1.0);
            scale_y = size_y / (diff_y * 1.0);
        } else {
            size_y = MAX_SIZE;
            size_x = (MAX_SIZE * diff_x) / diff_y + ((int) (0.05 * MAX_SIZE));

            scale_y = size_y / (diff_y * 1.0);
            scale_x = size_x / (diff_x * 1.0);
        }

        //System.out.println("X scale: " + scale_x + " diff: " + diff_x);
        //System.out.println("Y scale: " + scale_y + " diff: " + diff_y);
        //System.out.println("");
        //System.out.println("Points: " + points_x.size());

        calculatePoints();

        draw();
    }

    void calculatePoints()
    {
        map = new long[(int) size_x][(int) size_y];

        for (int i = 0; i < size_x; i++) {
            for (int j = 0; j < size_y; j++) {
                map[i][j] = 0;
            }
        }

        for (int i = 0; i < points_x.size(); i++) {
            int x = (int) ((points_x.get(i) - min_x) * scale_x);
            int y = (int) ((points_y.get(i) - min_y) * scale_y);
            //System.out.println("Iep: " + i + " X: " + x + " Y: " + y + " W: " + width + " H: " + height);
            map[x][y]++;
        }
    }

    void draw()
    {
        // now start creating the image
        img = new BufferedImage((int) size_x, (int) size_y, BufferedImage.TYPE_INT_RGB);

        Graphics2D g = img.createGraphics();

        g.setColor(Color.WHITE);

        g.fillRect(0, 0, (int) size_x, (int) size_y);

        for (int i = 0; i < points_x.size(); i++) {
            g.setColor(getColor(points_c.get(i)));
            int x = (int) ((points_x.get(i) - min_x) * scale_x);
            int y = (int) ((points_y.get(i) - min_y) * scale_y);
            int width = ((int) ((2 + scale_x) * SCALE_POINT /* * Math.log(map[x][y]) */));
            int height = ((int) ((2 + scale_y) * SCALE_POINT /* * Math.log(map[x][y]) */));
            g.fillOval(x, y, width, height);
            //System.out.println("Iep: " + i + " X: " + x + " Y: " + y + " W: " + width + " H: " + height);
        }

        try {
            File outputfile = new File("output.png");
            ImageIO.write(img, "png", outputfile);
        } catch (IOException e) {
            System.out.println("Something went wrong");
        }
    }
    
    Color getColor(int cluster)
    {
        if (cluster == 0 || ALL_BLACK) {
            return Color.BLACK;
        }

        switch (cluster % 5) {
            case 0:
                return Color.GREEN;
            case 1:
                return Color.ORANGE;
            case 2:
                return Color.PINK;
            case 3:
                return Color.YELLOW;
            case 4:
                return Color.CYAN;
            default:
                return Color.GRAY;
        }
    }

    public static void main(String args[])
    {
        new Main().run();
    }
}
