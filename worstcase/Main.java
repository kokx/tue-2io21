package worstcase;

import java.io.*;
import java.util.*;

import algorithm.*;
import model.*;

public class Main {
    private int numberOfCircles, radius, pointsPerCircle, displacements, offset, randomPoints, fieldSize;
    private Field field = new Field();
    private ArrayList<Point> changedPoints = new ArrayList<Point>(); 
    private ArrayList<Circle> circles = new ArrayList<Circle>();

    public void run() throws IOException {
        readInput();
        generateCircles();
        moveRandomPoints();
        addNewPoints();
        output();	
    }

    private void readInput() {
        Scanner scanner = new Scanner(System.in);
        /*System.out.println("Please give the following values: number of circles, points per circle, radius and fieldsize.");
    	numberOfCircles = scanner.nextInt();
    	pointsPerCircle = scanner.nextInt();
    	radius = scanner.nextInt();
    	fieldSize = scanner.nextInt();*/
        numberOfCircles = 3;
        pointsPerCircle = 100;
        radius = 1000000;
        fieldSize = 10000000;
        System.out.println("Number of displacements, offset of displacements and amount of randomly added points");
        displacements = 3; //scanner.nextInt();
        offset = radius/2; //scanner.nextInt();
        randomPoints = 0; //scanner.nextInt();
    }

    private void generateCircles() {
        for (int i = 0; i < numberOfCircles; i++) {
            int x, y;
            Circle newCircle;
            do {
                x = (int) Math.round(Math.random() * (fieldSize - 2 * radius) + radius);
                y = (int) Math.round(Math.random() * (fieldSize - 2 * radius) + radius);
                newCircle = new Circle(radius, pointsPerCircle, x, y);
            } while (!checkNewCircle(newCircle));
            circles.add(newCircle);
        }
        for (Circle circle : circles) {
            for (Point point : circle.getPoints()) {
                field.addPoint(point);
            }
        }
        System.out.println("Circles generated");
    }

    private boolean checkNewCircle(Circle checkCircle) {
        for (Circle circle : circles) {
            if (Calculations.distance(checkCircle.getCenter(), circle.getCenter(), 2) < (4 * radius)) {
                return false;
            }
        }
        return true;
    }

    private void moveRandomPoints() {
        int circleId = 0;
        
        for (int i = 0 ; i < displacements ; i++) {
            int x, y;
            Point point = getRandomPoint(circleId);
            Point centerPoint = getCircle(point);
            
            double dir = direction(centerPoint.getX(),centerPoint.getY(),point.getX(),point.getY());

            x=centerPoint.getX()+lenDirX(offset+radius,dir);
            y=centerPoint.getY()+lenDirY(offset+radius,dir);
            
            movePoint(point, x, y);
            
            circleId ++;
            circleId = circleId % 3;
        }
    }

    private Point getCircle(Point p) {
        int dist = Integer.MAX_VALUE;
        Point returnPoint = null;
        
        for(Circle c: circles) {
            if(Calculations.distance(c.getCenter(), p, 2) < dist) {
                dist = (int) Calculations.distance(c.getCenter(), p, 2);
                returnPoint = c.getCenter();
            }   
        }
        
        return returnPoint;
    }
    
    private Point getRandomPoint(int circleid) {
        ArrayList<Point> points = circles.get(circleid).getPoints();
        
        return points.get((int) (Math.random()*points.size())); 
    }

    private void movePoint(Point point, int x, int y) {
        if(field.hasPoint(point.getX(), point.getY())) {
            field.points.remove(field.getKey(point.getX(), point.getY()));
            Point newPoint = new Point(x, y, 0);
            changedPoints.add(newPoint);
        }
    }

    private void addNewPoints() {
        int circleId = 0;
        for (int i = 0 ; i < randomPoints; i++) {
            int x, y;
            //int circleId = (int) (Math.random()*circles.size());

            x = (int) (Math.random() * fieldSize);
            y = (int) (Math.random() * fieldSize);
            double dir = direction(circles.get(circleId).getXCenter(),circles.get(circleId).getYCenter(),x,y);

            x=circles.get(circleId).getXCenter()+lenDirX(offset+radius,dir);
            y=circles.get(circleId).getYCenter()+lenDirY(offset+radius,dir);
            
            addNewPoint(x, y);
            circleId ++;
            circleId = circleId % 3;
        }

    }

    private double direction(int x1, int y1, int x2, int y2) {
        int dx = x2 - x1;
        int dy = y2 - y1;
        
        return Math.toDegrees(Math.atan2(dy, dx)); // * 360;
    }
    
    private int lenDirX(int r, double dir) {
        return (int) (r * Math.cos(Math.toRadians(dir))); // * Math.PI / 180));
    }
    
    private int lenDirY(int r, double dir) {
        return (int) (r * Math.sin(Math.toRadians(dir))); // * Math.PI / 180));
    }
    
    private void addNewPoint(int x, int y) {
        Point newPoint = new Point(x, y, 0);
        changedPoints.add(newPoint);    
    }

    private void theCalculation(int totalClusters) {
        double result = 0;

        for(Point p : changedPoints) {
            result = result + findNearestPointDistance(p);
        }

        result = result / changedPoints.size();
        result = result * totalClusters;

        System.out.println("Running time is goed");
    }

    private double findNearestPointDistance(Point point) {
        double distance = Integer.MAX_VALUE;

        Collection<Point> points = field.getAllPoints();

        for(Point p : points) {
            if(Calculations.distance(point, p, 2) < distance) {
                distance = Calculations.distance(point, p, 2);
            }
        }
        return distance; 
    }

    private void output() throws IOException {
        int num = 0;
        File testFile;

        do {
            num++;
            testFile = new File("WorstCase" + num);    
        } while(testFile.exists());

        FileOutputStream file = new FileOutputStream("WorstCase" + num);
        for(Point p : changedPoints) {
            field.addPoint(p);
        }
        System.out.println("find " + numberOfCircles + " clusters");
        System.out.println(field.size() + " points");
        new PrintStream(file).println("find " + numberOfCircles + " clusters");
        new PrintStream(file).println(field.size() + " points");
        for (Point p : field.getAllPoints()) {
            System.out.println(p.getX() + " " + p.getY() + " 0");
            new PrintStream(file).println(p.getX() + " " + p.getY());
        }
        file.close();
    }


    public static void main(String args[]) throws IOException {
        new Main().run();
    }

}
