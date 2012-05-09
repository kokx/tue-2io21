package quality;

import java.util.ArrayList;
import algorithm.Calculations;
import model.Point;
import model.Cluster;


public class Quality {
	private ArrayList<Cluster> clusters;
	private double score;
	
	public Quality(ArrayList<Cluster> input, int mode) {
		clusters = input;
		calcQualityIndex(mode);
	}
	
	private void calcQualityIndex(int mode) {
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
		
	private double calcMaxScatterDistenceFactor(Cluster cluster, int mode) {
		double result = 0;
		ArrayList<Double> temps = new ArrayList<Double>();
		for (Cluster othercluster : clusters) {
			double temp;
			if (othercluster.getId() != 0 && othercluster != cluster) {
				temp = Calculations.distance(cluster.getCentroid(), othercluster.getCentroid(), mode);
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

    
    public double getScore() {
    	return score;
    }
}
