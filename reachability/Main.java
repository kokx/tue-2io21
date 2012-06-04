package reachability;

import java.io.*;
import java.util.*;
import java.awt.image.*;
import java.awt.*;
import javax.imageio.*;

import model.*;

class Main {
    Scanner sc;
    BufferedImage img;

    public final static int MAX_SIZE = 1000;

    double max;
    double scale_x;
    double scale_y;

    ArrayList<Integer> cluster = new ArrayList<Integer>();
    ArrayList<Double> reachability = new ArrayList<Double>();

    public Main()
    {
        sc = new Scanner(System.in);
    }

    void run()
    {
        max = 0;

        for (int i = 0; sc.hasNextInt(); i++) {
            cluster.add(sc.nextInt());
            reachability.add(Double.parseDouble(sc.next()));

            if (reachability.get(i) > max) {
                max = reachability.get(i);
            }
        }

        scale_y = (MAX_SIZE*1.0) / max;
        scale_x = (MAX_SIZE*1.0) / (reachability.size()*1.0);

        draw();
    }

    void draw()
    {
        // now start creating the image
        img = new BufferedImage(MAX_SIZE, MAX_SIZE, BufferedImage.TYPE_INT_RGB);

        Graphics2D g = img.createGraphics();

        g.setColor(Color.WHITE);

        g.fillRect(0, 0, MAX_SIZE, MAX_SIZE);

        for (int i = 0; i < reachability.size(); i++) {
            double distance = (double) reachability.get(i);
            int x = (int) (i * scale_x);
            int y = (int) (distance * scale_y);
            g.setColor(getColor(cluster.get(i)));

            g.drawLine(x, MAX_SIZE - y, x, MAX_SIZE);
        }

        try {
            File outputfile = new File("reach.png");
            ImageIO.write(img, "png", outputfile);
        } catch (IOException e) {
            System.out.println("Something went wrong");
        }
    }

    Color getColor(int cluster)
    {
        if (cluster == 0) {
            return Color.BLACK;
        }

        switch (cluster % 5) {
            case 0:
                return Color.PINK;
            case 1:
                return Color.ORANGE;
            case 2:
                return Color.GREEN;
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
