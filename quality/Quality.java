package quality;

import java.util.ArrayList;
import algorithm.*

import model.*;


public class Quality {
	ArrayList<Cluster> clusters;
	double score;
	
	Quality(ArrayList<Cluster> inputList, int mode) {
		clusters = inputList;
		calcQualityIndex(mode);
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
