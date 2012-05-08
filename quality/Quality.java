import java.util.ArrayList;


public class Quality {
	ArrayList<Cluster> clusters;
	double score;
	
	//TODO: unhardcode mode.
	Quality(ArrayList<Cluster> inputList) {
		clusters = inputList;
		calcQualityIndex(1);
	}
	
	public void calcQualityIndex(int mode) {
		ArrayList<Double> ScatterDistenceFactors = new ArrayList<Double>();
		double quality = 0;
		for (Cluster cluster : clusters) {
			if (cluster.getId() != 0) {
				double tempdouble = calcMaxScatterDistenceFactor(cluster, mode);
				ScatterDistenceFactors.add(tempdouble);
			}
		}
		for (double factor : ScatterDistenceFactors) {
			quality += factor;
		}
		quality = quality / ScatterDistenceFactors.size();
		score = quality;
	}
		
	//TODO: distance call.
	private double calcMaxScatterDistenceFactor(Cluster cluster, int mode) {
		double result = 0;
		ArrayList<Double> temps = new ArrayList<Double>();
		for (Cluster othercluster : clusters) {
			double temp;
			if (othercluster.getId() != 0 && othercluster != cluster) {
				temp = distance(cluster.getCentroid(), othercluster.getCentroid(), mode);
				temp = (cluster.getScatter(mode) + othercluster.getScatter(mode)) / temp;
				temps.add(temp);
			}
		}
		for (double temp : temps) {
			if (temp > result) {
				result = temp;
			}
		}
		return result;
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
    
    public double getScore() {
    	return score;
    }


}
