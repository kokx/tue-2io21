package model;

import java.util.ArrayList;

import algorithm.Calculations;


public class Cluster {
	private ArrayList<Point> points = new ArrayList<Point>();
	private int clusterId;
	private double scatter;
	private Point centroid;
	
	public Cluster(int Id) {
		clusterId = Id;
	}
	
	public void addPoint(Point point) {
		points.add(point);
	}
	
	public int getId() {
		return clusterId;
	}
	
	public double getScatter(int mode) {
		if (scatter == 0) {
			double totalscore = 0;
			for (Point point : points) {
				totalscore += Calculations.distance(centroid, point, mode);
			}
			scatter = totalscore * Math.pow(this.size(), 1/mode);
		}
		return scatter;
	}
	
	public int size() {
		return points.size();
	}
	
	public Point getCentroid() {
        if (centroid == null) {
            long totalx = 0;
            long totaly = 0;

            for (Point point : points) {
                totalx += point.getX();
                totaly += point.getY();
            }
            centroid = new Point((int) (totalx / (long) this.size()), (int) (totaly / (long) this.size()), -1);
        }
        return centroid;
	}
	
}
