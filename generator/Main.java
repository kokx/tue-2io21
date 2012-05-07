import java.io.*;
import java.util.*;

class Main {
    Scanner sc;

    public final static int CLUSTERS = 6;
    public final static int CLUSTER_MIN_SIZE = 100;
    public final static int CLUSTER_MAX_SIZE = 500;
    public final static int CLUSTER_MIN_DENSITY = 5000;
    public final static int CLUSTER_MAX_DENSITY = 25000;

    public Main()
    {
        sc = new Scanner(System.in);
    }

    void run()
    {
        Generator generator = new Generator(5000, 5000);

        generator.createUniformNoise(300);

        Random random = generator.getRandomGenerator();

        for (int i = 0; i < CLUSTERS; i++) {
            int size = CLUSTER_MIN_SIZE + random.nextInt(CLUSTER_MAX_SIZE - CLUSTER_MIN_SIZE);
            int density = CLUSTER_MIN_DENSITY + random.nextInt(CLUSTER_MAX_DENSITY - CLUSTER_MIN_DENSITY);

            generator.generateCluster(size, density);
        }

        generator.generateGaussianNoise(10, 600, 1800);

        output(generator.getField());
    }

    void output(Field field)
    {
        Collection<Point> points = field.getAllPoints();

        System.out.println("find 2 to 4 clusters");
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
