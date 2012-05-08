package model;

import java.util.ArrayList;


public class Cluster {
	private ArrayList<Point> points;
	private int clusterId;
	private double quality;
	private double scatter;
	private Point centroid;
	
	Cluster(int Id) {
		clusterId = Id;
	}
	
	public void addPoint(Point point) {
		points.add(point);
	}
	
	public void setQuality(double score) {
		quality = score;
	}
	
	public double getQuality() {
		return quality;
	}
	
	public int getId() {
		return clusterId;
	}
	
	public void setScatter(double score) {
		scatter = score;
	}
	
	public double getScatter() {
		return scatter;
	}
	
	public int size() {
		return points.size();
	}
	
	public Point getCentroid() {
        if (centroid != null) {
            long totalx = 0;
            long totaly = 0;

            for (Point point : pointList) {
                totalx += point.getX();
                totaly += point.getY();
            }

            centroid = new Point((int) (totalx / (long) this.size()), (int) (totaly / (long) this.size()), -1);
        }
        return centroid;
	}
	
	public ArrayList<Point> getPoints() {
		return points;
	}
	
}
