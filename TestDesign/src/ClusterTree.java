
public class ClusterTree {

	public ClusterTree()
	{
		
	}
	

	public int getHeight(int index)
	{
		return(-1);
	}

	public Cluster getCluster(int index)
	{
		return(null);
	}
	
	
	public int size()
	{
		return(-1);
	}
	
	
	
	public void add(Cluster cluster)
	{
		
	}
	

	public boolean isLeaf(int index)
	{
		return(true);
	}
	
	public Cluster getClusterParent(Cluster cluster)
	{
		return(null);
	}
	
	//@return: Returns the average reachabilityCluster in the Cluster
	public int getReachabilityDistance(int index)
	{
		return(-1);
	}
	
	public void combineClusters(Cluster startCluster, Cluster mergeCluster)
	{
	
	}
	
	public int getIndex(Cluster cluster)
	{
		return(-1);
	}
	
	//@return: Substracts the lowest density from the highest density of the two clusters
	public int reachabilityDifference(int cluster1,int cluster2)
	{
		return(Math.max(getReachabilityDistance(cluster1),getReachabilityDistance(cluster2))
				-Math.min(getReachabilityDistance(cluster1),getReachabilityDistance(cluster2)));
	}
	
	public void remove(Cluster cluster)
	{
	
	}
	
}
