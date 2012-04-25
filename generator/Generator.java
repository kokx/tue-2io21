import java.io.*;
import java.util.*;

class Generator {

    public final static int DEFAULT_MAX_OFFSET = 100000;

    Field field;

    int offset_x;
    int offset_y;
    int width;
    int height;

    Random random;

    public Generator(int width, int height)
    {
        this(width, height, DEFAULT_MAX_OFFSET);
    }

    public Generator(int width, int height, int max_offset)
    {
        field = new Field();
        random = new Random();

        this.width = width;
        this.height = height;

        offset_x = random.nextInt(max_offset);
        offset_y = random.nextInt(max_offset);
    }

    /**
     * Get the generated field.
     */
    public Field getField()
    {
        return field;
    }

    /**
     * Generate.
     */
    public void generate(int noise_density, int clusters, int cluster_size, int cluster_density)
    {
        generateClusters(clusters, cluster_size, cluster_density);

        createUniformNoise(noise_density);
    }

    /**
     * Generate multiple clusters with the same size and density.
     */
    public void generateClusters(int clusters, int size, int density)
    {
        for (int i = 0; i < clusters; i++) {
            int mean_x = random.nextInt(width - (2 * size)) + size;
            int mean_y = random.nextInt(height - (2 * size)) + size;

            createUniformCluster(mean_x, mean_y, size, density);
        }
    }

    /**
     * Generate multiple noise points with the same size and density.
     */
    public void generateGaussianNoise(int clusters, int size, int density)
    {
        for (int i = 0; i < clusters; i++) {
            int mean_x = random.nextInt(width - (2 * size)) + size;
            int mean_y = random.nextInt(height - (2 * size)) + size;

            createGaussianNoise(mean_x, mean_y, size, density);
        }
    }

    /**
     * Create a cluster using a uniform distribution.
     */
    public void createUniformCluster(int mean_x, int mean_y, int r, int density)
    {
        mean_x += offset_x;
        mean_y += offset_y;

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
     * Create noise using a normal distribution.
     */
    public void createGaussianNoise(int mean_x, int mean_y, int r, int density)
    {
        mean_x += offset_x;
        mean_y += offset_y;

        for (int i = 0; i < density; i++) {
            int x;
            int y;
            do {
                x = mean_x - (int) (r * random.nextGaussian());
                y = mean_y - (int) (r * random.nextGaussian());
            } while (x < offset_x || x > offset_x + width || y < offset_y || y > offset_y + height);

            field.addPoint(new Point(x, y, 0));
        }
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
