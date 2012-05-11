package quality;

import java.util.*;
import model.*;

/**
 * Runs the quality measure calculation on a input and prints the score.
 * 
 * @author s115966
 * @version 1.0
 */
class Main {
	/**
	 * ArrayList containing the Clusters to be analyzed plus a noise Cluster.
	 */
    private ArrayList<Cluster> clusters = new ArrayList<Cluster>();
    /**
     * DISTENCE_METRIC determines the mode of the distance calculation function in the quality measure.
     */
	public final static int DISTANCE_METRIC = 2;
    
	/**
	 * Runs the quality program, reading the input and printing the output.
	 */
    void run() {
    	Scanner sc = new Scanner(System.in);
    	processOneInput(sc.nextInt(), sc.nextInt(), sc.nextInt());
    	while (sc.hasNextInt()) {
    		processOneInput(sc.nextInt(), sc.nextInt(), sc.nextInt());
    	}
		System.out.println(new Quality(clusters, DISTANCE_METRIC).getScore());
    }
	
    /**
     * Process one point of the input stream, creating it's Point object and adds it to the correct Cluster.
     * 
     * @param x x coordinate of the Point to be created
     * @param y y coordinate of the Point to be created
     * @param id Cluster the new Point belongs to
     */
	private void processOneInput(int x, int y, int id) {
		if (!checkClusterExists(id)) {
			clusters.add(new Cluster(id));
		}
		getCluster(id).addPoint(new Point(x, y, id));
	}
	
    /**
     * Checks if there exists a Cluster with the IDtoCheck.
     * 
     * @param IDtoCheck ID of the Cluster whose existence is checked
     * @return True if the Cluster exists, False otherwise
     */
    private boolean checkClusterExists(int IDtoCheck) {
    	for (Cluster cluster : clusters) {
    		if (cluster.getId() == IDtoCheck) {
    			return true;
    		}
    	}
    	return false;
    }
    
    /**
     * Returns the Cluster with the given clusterID
     * 
     * @param clusterID ID number of the Cluster to be returned
     * @return Cluster with ID clusterID
     */
    private Cluster getCluster(int clusterID) {
    	for (Cluster cluster : clusters) {
    		if (cluster.getId() == clusterID) {
    			return cluster;
    		}
    	}
    	return null;
    }

    public static void main(String args[])
    {
        new Main().run();
    }
}