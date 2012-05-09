package quality;

import java.util.Scanner;
import java.util.ArrayList;

import model.*;

class Main {
    private Scanner sc = new Scanner(System.in);
    private ArrayList<Cluster> clusters = new ArrayList<Cluster>();
	
	public final static int DISTANCE_METRIC = 2;
    
    void run() {
    	while (sc.hasNextInt()) {
    		processOneInput();
    	}
    	Quality result = new Quality(clusters, DISTANCE_METRIC);
    	System.out.println(result.getScore());
    }
    
    private void processOneInput() {
    	int xtemp, ytemp, idtemp;
		xtemp = sc.nextInt();
		ytemp = sc.nextInt();
		idtemp = sc.nextInt();
		Point newPoint = new Point(xtemp, ytemp, idtemp);
		if (!checkClusterExists(idtemp)) {
			Cluster newCluster = new Cluster(idtemp);
			clusters.add(newCluster);
			newCluster.addPoint(newPoint);
		} else {
			getCluster(idtemp).addPoint(newPoint);
		}
    }
    
    private boolean checkClusterExists(int IDtoCheck) {
    	boolean exists = false;
    	for (Cluster cluster : clusters) {
    		if (cluster.getId() == IDtoCheck) {
    			exists = true;
    			return exists;
    		}
    	}
    	return exists;
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
