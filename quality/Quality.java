package quality;

import java.util.*;
import algorithm.*;
import model.*;


public class Quality {
	private ArrayList<Cluster> clusters;
	private double score;
	
	Quality(ArrayList<Cluster> inputs, int mode) {
		clusters = inputs;
		calcQualityIndex(mode);
	}
	
	public void calcQualityIndex(int mode) {
		score = totalScatterIndex(mode) / (clusters.size()-1);
	}
	
	private double totalScatterIndex(int mode) {
		double total;
		for (Cluster cluster : clusters) {
			if (cluster.getId() != 0) {
				total += calcMaxScatterDistenceFactor(cluster, mode)
			}
		}
		return total;
	}
	
	//TODO: review
	private double maxScatterDistenceFactor(Cluster cluster, int mode) {
		double result = 0;
		for (Cluster otherCluster : clusters) {
			if (otherCluster.getId() != 0 && otherCluster != cluster && scatterDistenceFactor(cluster, otherCluster, mode > result)) {
				result = scatterDistenceFactor(cluster, otherCluster, mode;
			}
		}
		return result;
	}
	
	//TODO: review
	private double scatterDistenceFactor(Cluster cluster, Cluster otherCluster, int mode) {
		return (cluster.getScatter() + otherCluster.getScatter()) / entroidDistence(cluster, otherCluster, mode);
	}
	
	//TODO: review
	private double centroidDistence(Cluster cluster, Cluster otherCluster, int mode) {
		return Math.pow(2 * calculations.distence(cluster, othercluster, mode) , 1/mode);
	}
    
    public double getScore() {
    	return score;
    }
}
