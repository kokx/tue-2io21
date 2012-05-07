import java.util.ArrayList;


public class Cluster {
	private ArrayList<Point> pointsList;
	private int clusterID;
	private double quality;
	private double scatter;
	private Point centroid;
	
	Cluster(int ID) {
		clusterID = ID;
	}
	
	public void addPoint(Point point) {
		pointsList.add(point);
	}
	
	public void setQuality(double score) {
		quality = score;
	}
	
	public double getQuality() {
		return quality;
	}
	
	public int getID() {
		return clusterID;
	}
	
	public void setScatter(double score) {
		scatter = score;
	}
	
	public double getScatter() {
		return scatter;
	}
	
	public int size() {
		return pointsList.size();
	}
	
	public void setCentroid(Point point) {
		centroid = point;
	}
	
	public ArrayList<Point> getPointsList() {
		return pointsList;
	}
	
}