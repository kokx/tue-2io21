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
