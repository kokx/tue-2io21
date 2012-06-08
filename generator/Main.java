package generator;

import java.io.*;
import java.util.*;

import model.*;

class Main {
    Scanner sc;

    public final static int CLUSTERS = 4;
    public final static int CLUSTER_MIN_SIZE = 200;
    public final static int CLUSTER_MAX_SIZE = 1200;
    public final static int CLUSTER_MIN_DENSITY = 5000;
    public final static int CLUSTER_MAX_DENSITY = 25000;
    public final static int FIELD_SIZE = 100000;

    public Main()
    {
        sc = new Scanner(System.in);
    }

    void run()
    {
        int field = FIELD_SIZE;
        Generator generator = new Generator(8000, 8000);

        generator.createUniformNoise(100);
        generator.generateGaussianNoise(10, 700, 900);
        field -= 300;
        field -= 1800;

        Random random = generator.getRandomGenerator();

        // create an array with cluster sizes
        int clusters[] = new int [CLUSTERS];
        int points = 0;

        for (int i = 0; i < CLUSTERS; i++) {
            clusters[i] = CLUSTER_MIN_SIZE + random.nextInt(CLUSTER_MAX_SIZE - CLUSTER_MIN_SIZE);
            points += clusters[i];
        }

        // density, number of points per square in the grid
        double density = field / ((double) points);

        //System.out.println("Dens: " + density + " Field: " + field + " Points: " + points);

        for (int i = 0; i < CLUSTERS; i++) {
            generator.generateCluster(clusters[i], (int) (density * clusters[i]));
        }

        output(generator.getField());
    }

    void output(Field field)
    {
        Collection<Point> points = field.getAllPoints();

        System.out.println("find " + CLUSTERS + " clusters");
        System.out.println(field.size() + " points");

        for (Point p : points) {
            System.out.println(p.getX() + " " + p.getY());
        }
    }

    public static void main(String args[])
    {
        new Main().run();
    }
}
