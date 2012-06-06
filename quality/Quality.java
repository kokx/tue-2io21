package quality;

import java.util.*;
import algorithm.*;
import model.*;

/**
 * Quality calculates the quality score of a given set of Clusters.
 *
 * @author s115966
 * @version 1.0
 */
public class Quality {
    /**
     * List of Cluster over which the quality is calculated.
     */
    private ArrayList<Cluster> clusters;
    /**
     * Quality score of the clustering.
     */
    private double score;
    /**
     * Determines the mode of the distance calculation (1 = Manhattan 2 = Euclidean).
     */
    private int mode;

    /**
     * Constructor of the Quality object, links the input clustering and distence mode with the class variables.
     *
     * @param input list of Clusters to be processed
     * @param mode mode of the distance calculation (1 = Manhattan 2 = Euclidean)
     */
    Quality(ArrayList<Cluster> input, int mode) {
        clusters = input;
        this.mode = mode;
    }

    /**
     * Returns the sum of the highest scatter indexes of all Clusters.
     *
     * @return sum of maxScatterDistenceFactors of all Clusters
     */
    private double totalScatterIndex() {
        double total = 0;
        for (Cluster cluster : clusters) {
            if (cluster.getId() != 0) {
                total += maxScatterDistenceFactor(cluster);
            }
        }
        return total;
    }

    /**
     * Returns the highest scatter distence factor of one Cluster compared to all other Clusters.
     *
     * @param cluster the Cluster whose max scatter distence factors gets calculated
     * @return highest scatter distance factor of a cluster.
     */
    private double maxScatterDistenceFactor(Cluster cluster) {
        double result = 0;
        for (Cluster otherCluster : clusters) {
            if (otherCluster.getId() != 0 && otherCluster != cluster && scatterDistenceFactor(cluster, otherCluster) > result) {
                result = scatterDistenceFactor(cluster, otherCluster);
            }
        }
        return result;
    }

    /**
     * Returns the factor of the scatter values of the two clusters and their Centroid distances.
     *
     * @param cluster first cluster
     * @param otherCluster second cluster
     * @return factor between scatters and Centroid distence
     */
    private double scatterDistenceFactor(Cluster cluster, Cluster otherCluster) {
        return (cluster.getScatter(mode) + otherCluster.getScatter(mode)) / centroidDistence(cluster, otherCluster);
    }

    /**
     * Returns the distance between the centroid of two clusters.
     *
     * @param cluster first cluster
     * @param otherCluster second cluster
     * @return distance between Centroid of cluster and otherCluster
     */
    private double centroidDistence(Cluster cluster, Cluster otherCluster) {
        return Calculations.distance(cluster.getCentroid(), otherCluster.getCentroid(), mode);
    }

    /**
     * Returns the score of the input Clustering.
     *
     * @return the quality score of the clustering
     */
    public double getScore() {
        if (score == 0) {
            score = totalScatterIndex() / (clusters.size()-1);
        }
        return score;
    }
}
