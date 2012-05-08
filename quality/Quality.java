package quality;

import java.util.ArrayList;

import model.*;


public class Quality {
	private ArrayList<Cluster> clusterList;
	
	Quality(ArrayList<Cluster> inputList) {
		clusterList = inputList;
	}

    private Point calcCentroid(Cluster cluster) {
		int size = cluster.size();
		long totalx = 0;
		long totaly = 0;
		int centroidx = 0, centroidy = 0;
		ArrayList<Point> pointList;
		ArrayList<Point> points = cluster.getPoints();
		for (Point point : points) {
			totalx += point.getX();
			totaly += point.getY();
		}

        return new Point(centroidx, centroidy, -1);
	}

}
