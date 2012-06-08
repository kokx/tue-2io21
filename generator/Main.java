package generator;

import java.io.*;
import java.util.*;

import model.*;

class Main {
    Scanner sc;

    public final static int CLUSTERS = 5;
    public final static int CLUSTER_MIN_SIZE = 1000;
    public final static int CLUSTER_MAX_SIZE = 5000;
    public final static int CLUSTER_MIN_DENSITY = 500;
    public final static int CLUSTER_MAX_DENSITY = 50000;
    public final static int FIELD_SIZE = 150000;

    public Main()
    {
        sc = new Scanner(System.in);
    }

    void run()
    {
        int field = FIELD_SIZE;
        Generator generator = new Generator(50000, 50000);

        generator.createUniformNoise(100);
        generator.generateGaussianNoise(10, 5000, 500);
        field -= 500;
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
