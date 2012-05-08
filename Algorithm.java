public abstract class Algorithm
{

    protected Field field;

    /**
     * Constructor.
     *
     * @param field
     */
    public Algorithm(Field field)
    {
        this.field = field;
    }

    /**
     * Run the algorithm.
     */
    public abstract void run()
    
    /**
     * Determine the distance between sourcePoint and destPoint.
     * 
     * @param sourcePoint Source point
     * @param destPoint Destination point
     * @param m 2 = Euclidean, 1 = Manhatten
     * @return Distance from sourcePoint to destPoint
     */
    

	/**
	 * @param p: Point from which we want the distance to another point
	 * @param number: The number'th closest point to p.
	 * 
	 * @pre: minDistances are calculated
	 * @return: returns the point with the number'th minimal distance to p
	 */ 
	Point minDistancePoint(Point p,int number)
	{
		return(null);
	}

	/**
	 * @param minPoints: Minimal amount of points that should be accounted for in the densityDistance
	 * @param sourcePoint: Source point
	 * @param destPoint: Destination point
	 * @param m: 2 = Euclidean, 1 = Manhatten
	 *
	 * @pre minDistances are calculated
	 * @return Returns the density distance between point sourcePoint and point endPoint
	 */
	double densityDistance(int minPoints, Point sourcePoint, Point endPoint,int m)
	{		
		return(Math.max(distance(sourcePoint,minDistancePoint(sourcePoint,minPoints),m),distance(sourcePoint,endPoint,m)));
	}


    
    public int Distance(Point sourcePoint, Point destPoint, int m) {
        switch (m) {
            case 1: 
                return Math.abs(destPoint.getX() - sourcePoint.getX()) 
                    + Math.abs(destPoint.getY() - sourcePoint.getY());
                break;
            case 2:
                return Math.sqrt(Math.pow(Math.abs(destPoint.getX() - sourcePoint.getX()), 2) 
                        + Math.pow(Math.abs(destPoint.getY() - sourcePoint.getY()), 2));
                break;
        }
    }

}
