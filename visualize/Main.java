import java.io.*;
import java.util.*;
import java.awt.image.*;
import java.awt.*;
import javax.imageio.*;

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

        // now start creating the image
        img = new BufferedImage((max_x - min_x) + 2, (max_y - min_y) + 2, BufferedImage.TYPE_INT_RGB);

        Graphics2D g = img.createGraphics();

        g.setColor(Color.WHITE);

        g.fillRect(0, 0, (max_x - min_x) + 2, (max_y - min_y) + 2);

        g.setColor(Color.BLACK);
        for (int i = 0; i < points_x.size(); i++) {
            g.fillRect(points_x.get(i), points_y.get(i), 1, 1);
        }

        try {
            File outputfile = new File("temp.png");
            ImageIO.write(img, "png", outputfile);
        } catch (IOException e) {
            System.out.println("Something went wrong");
        }
    }

    public static void main(String args[])
    {
        new Main().run();
    }
}
