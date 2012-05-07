public abstract class Algorithm {
    
    /**
     * 
     * @param Point sourcePoint
     * @param Point destPoint
     * @param type; 2 = Euclidean, 1 = Manhatten
     * @return Distance from sourcePoint to destPoint
     */
    int Distance(Point sourcePoint, Point destPoint, int m) {
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
