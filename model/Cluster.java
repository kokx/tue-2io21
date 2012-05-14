package model;

import java.util.*;
import algorithm.*;

/**
 * A Cluster containing a group of Points.
 * 
 * @author s115966
 * @version 1.0
 */
public class Cluster {
	/**
	 * List containing all the Points in this Cluster
	 */
	private ArrayList<Point> points = new ArrayList<Point>();
	/**
	 * ID number to identify this Cluster.
	 */
	private int clusterId;
	/**
	 *TODO:this
	 */
	private double scatter;
	/**
	 * A point that defines the center of the Cluster. Centroid is not part of the points list. The Centroid is assigned to the -1(= nonexist) Cluster because it is not an actual point in the database.
	 */
	private Point centroid;
	
	/**
	 * Constructor of the Cluster object.
	 * 
	 * @param Id ID that will be given to the Cluster
	 */
	public Cluster(int Id) {
		clusterId = Id;
	}
	
	/**
	 * Adds a point to this Cluster.
	 * 
	 * @param point The point to be added
	 */
	public void addPoint(Point point) {
		points.add(point);
	}
	
	/**
	 * Gives the ID of this Cluster.
	 * 
	 * @return returns the clusterID
	 */
	public int getId() {
		return clusterId;
	}
	
	/**
	 * Returns the scatter score of this Cluster, if the score hasn't been calculated yet it first calculates it.
	 * 
	 * @param mode mode of the distance calculation (1 = Manhattan 2 = Euclidean)
	 * @return scatter score of the Cluster
	 */
	public double getScatter(int mode) {
		if (scatter == 0) {
			double totalscore = 0;
			for (Point point : points) {
				totalscore += Calculations.distance(getCentroid(), point, mode);
			}
			scatter = Math.pow((1.0/this.size())*totalscore, 1.0/mode);
		}
		return scatter;
	}
	
	/**
	 * Returns the number of Points in this Cluster.
	 * 
	 * @return the number of Points in this Cluster
	 */
	public int size() {
		return points.size();
	}
	
	/**
	 * Returns the Centroid Point of the Cluster, if the Centroid doesn't exist yet it is also generated.
	 * 
	 * @return Centroid of this Cluster
	 */
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
	
	/**
	 * Returns the ArrayList containing the Points of this Cluster.
	 * 
	 * @return ArrayList of the Points in this Cluster
	 */
	public ArrayList<Point> getPoints() {
		return points;
	}
}
