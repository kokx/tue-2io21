import java.io.*;
import java.util.*;

class Main {
    private Scanner sc;
    private ArrayList<Cluster> clusterList = new ArrayList<Cluster>();

    public Main()
    {
        sc = new Scanner(System.in);
    }
    
    //TODO: fix skip of input
    void run() {
    	int xtemp, ytemp, idtemp;
		xtemp = sc.nextInt();
		ytemp = sc.nextInt();
		idtemp = sc.nextInt();
    	while (sc.hasNextInt()) {
    		xtemp = sc.nextInt();
    		ytemp = sc.nextInt();
    		idtemp = sc.nextInt();
    		Point newPoint = new Point(xtemp, ytemp, idtemp);
    		if (!checkClusterExists(idtemp)) {
    			Cluster newCluster = new Cluster(idtemp);
    			clusterList.add(newCluster);
    			newCluster.addPoint(newPoint);
    		} else {
    			getCluster(idtemp).addPoint(newPoint);
    		}
    	}
    	Quality result = new Quality(clusterList);
    	System.out.print(result.getScore());
    }
    
    private boolean checkClusterExists(int IDtoCheck) {
    	boolean exists = false;
    	if (clusterList != null) {
    		for (Cluster cluster : clusterList) {
    			if (cluster.getId() == IDtoCheck) {
    				exists = true;
    				return exists;
    			}
    		}
    	}
    	return exists;
    }
    
    private Cluster getCluster(int clusterID) {
    	for (Cluster cluster : clusterList) {
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