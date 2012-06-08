package worstcase;

import java.util.ArrayList;

import algorithm.*;
import model.*;

public class Circle {
	private int radius;
	private Point center;
	private ArrayList<Point> points = new ArrayList<Point>();
	
	public Circle(int radius, int amountOfPoints, int xcenter, int ycenter) {
		this.radius = radius;
		this.center = new Point(xcenter, ycenter, 0);
		generateCircle(amountOfPoints);
	}	
	
    private void generateCircle(int numberOfPoints) {        
        for (int i = 0; i < numberOfPoints; i++) {
            double distance = 2 * Math.PI * i / numberOfPoints;
            int x = (int) Math.round(getXCenter() + radius * Math.cos(distance));
            int y = (int) Math.round(getYCenter() + radius * Math.sin(distance));
            points.add(new Point(x, y, 0));
        }
    }
    
    public int getRadius() {
    	return radius;
    }
	
    public int getNumberOfPoints() {
    	return points.size();
    }

    public int getXCenter() {
    	return center.getX();
    }
    
    public int getYCenter() {
    	return center.getY();
    }
    
    public ArrayList<Point> getPoints() {
    	return points;
    }
    
    public Point getCenter() {
    	return center;
    }

}
