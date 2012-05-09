package quality;

import java.util.*;
import model.*;

class Main {
    private Scanner sc = new Scanner(System.in);
    private ArrayList<Cluster> clusters = new ArrayList<Cluster>();
	
	public final static int DISTANCE_METRIC = 2;
    
    void run() {
    	while (sc.hasNextInt()) {
    		processOneInput(sc.nextInt(), sc.nextInt(), sc.nextInt());
    	}
		System.out.println(new Quality(clusters, DISTANCE_METRIC).getScore())
    }
	
	private void processOneInput(int x, int y, int id) {
		if (!checkClusterExists(id)) {
			clusters.add(new Cluster(id));
		}
		getCluster(id).addPoint(new Point(x, y, id));
	}
    
    private boolean checkClusterExists(int IDtoCheck) {
    	for (Cluster cluster : clusters) {
    		if (cluster.getId() == IDtoCheck) {
    			return true;
    		}
    	}
    	return false;
    }
    
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