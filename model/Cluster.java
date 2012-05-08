package model;

import java.util.ArrayList;


public class Cluster {
	private ArrayList<Point> points = new ArrayList<Point>();
	private int clusterId;
	private double quality;
	private double scatter;
	private Point centroid;
	
	public Cluster(int Id) {
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
	
	//TODO: fix ability to call distence in algorithm class.
	public double getScatter(int mode) {
		if (scatter == 0) {
			double totalscore = 0;
			for (Point point : points) {
				totalscore += distance(centroid, point, mode);
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
	
	public ArrayList<Point> getPoints() {
		return points;
	}
	
	//TODO: remove
    public double distance(Point sourcePoint, Point destPoint, int m)
    {
        long dx = (long) Math.abs(destPoint.getX() - sourcePoint.getX());
        long dy = (long) Math.abs(destPoint.getY() - sourcePoint.getY());

        switch (m) {
            case 1: 
                return (double) (dx + dy);
            default:
                return Math.pow(Math.pow(dx, m) + Math.pow(dy, m), 1.0/m);
        }
    }
	
}
