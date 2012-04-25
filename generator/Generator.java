import java.io.*;
import java.util.*;

class Generator {

    public final static int MAX_OFFSET = 100000;

    Field field;

    int offset_x;
    int offset_y;
    int width;
    int height;

    Random random;

    public Generator()
    {
        field = new Field();
    }

    /**
     * Generate.
     */
    public Field generate(int width, int height, int noise_density, int clusters, int cluster_size, int cluster_density)
    {
        random = new Random();
        offset_x = random.nextInt(MAX_OFFSET);
        offset_y = random.nextInt(MAX_OFFSET);

        this.width = width;
        this.height = height;

        int max_x = offset_x + width;
        int max_y = offset_y + height;

        for (int i = 0; i < clusters; i++) {
            int mean_x = offset_x + random.nextInt(width - (2 * cluster_size)) + cluster_size;
            int mean_y = offset_y + random.nextInt(height - (2 * cluster_size)) + cluster_size;

            createUniformCluster(mean_x, mean_y, cluster_size, cluster_density);
        }

        createUniformNoise(noise_density);

        return field;
    }

    /**
     * Create a cluster using a uniform distribution.
     */
    public void createUniformCluster(int mean_x, int mean_y, int r, int density)
    {
        for (int i = 0; i < density; i++) {
            int x;
            int y;
            do {
                x = random.nextInt(r*2) - r;
                y = random.nextInt(r*2) - r;
            } while (x*x + y*y > r*r);

            field.addPoint(new Point(mean_x + x, mean_y + y, 0));
        }
    }

    /**
     * Create a cluster using a normal distribution.
     */
    public void createGaussianCluster(int mean_x, int mean_y, int stddev, int density)
    {
    }

    /**
     * Create noise using a universe distribution.
     */
    public void createUniformNoise(int density)
    {
        for (int i = 0; i < density; i++) {
            int x = offset_x + random.nextInt(width);
            int y = offset_y + random.nextInt(height);
            field.addPoint(new Point(x, y, 0));
        }
    }
}
