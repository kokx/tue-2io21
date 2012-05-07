import java.util.ArrayList;


public class Quality {
	private ArrayList<Cluster> clusterList;
	
	Quality(ArrayList<Cluster> inputList) {
		clusterList = inputList;
	}

    private Point calcCentroid(Cluster cluster) {
		int size = cluster.size();
		long totalx = 0;
		long totaly = 0;
		int centroidx, centroidy;
		ArrayList<Point> pointList;
		pointList = cluster.getPointsList();
		for (Point point : pointList) {
			totalx += point.getX();
			totaly += point.getY();
		}

        return new Point(centroidx, centroidy, -1);
	}

}
