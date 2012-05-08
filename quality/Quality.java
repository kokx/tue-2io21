import java.util.ArrayList;


public class Quality {
	ArrayList<Cluster> clusters;
	
	Quality(ArrayList<Cluster> inputList) {
		clusters = inputList;
	}
	
	public double calcQualityIndex(int mode) {
		double[] ScatterDistenceFactors = new double[clusters.size()-1];
		double quality = 0;
		for (Cluster cluster : clusters) {
			if (cluster.getId() != 0) {
				calcMaxScatterDistenceFactor(cluster, mode);
			}
		}
		for (double factor: ScatterDistenceFactors) {
			quality += factor;
		}
		quality = quality / ScatterDistenceFactors.length;
		return quality;
	}
		
	//TODO: distance call.	
	private double calcMaxScatterDistenceFactor(Cluster cluster, int mode) {
		double temp;
		ArrayList<double> temps;
		for (Cluster othercluster : clusters) {
			if (othercluster.getId() != 0 && othercluster != cluster) {
				temp = distence(cluster.getCentroid(), othercluster.getCentroid(), mode);
				temp = (cluster.getScatter(mode) + othercluster.getScatter(mode)) / temp;
				temps.add(temp);
			}
		}
		return temps.max();
	}

	

}
