import java.util.ArrayList;


public class ReachabilityPlot {

	ArrayList<Point> points;
	public static double DENSDIFF = 0.75;


	public ReachabilityPlot(ArrayList<Point> points)
	{
		this.points = points;
	}

	public void findLocalMaxima() //reachabilityplot contains the xCoordinate in the reachability plot
	//,the reachability distance and the point  
	{
		ArrayList<Point> localMaxima = new ArrayList<Point>();
		for(int i = 0; i<points.size();i++) //going through the reachability plot from left to right
		{
			if (getX(i) != 0) //it is not the first element in the reachability plot
			{
				if (getX(i) != getSize())//it is not the last or the first element in the reachability plot
				{
					if ((getReachability(i-1)+getReachability(i+1))/2 < getReachability(i)*DENSDIFF )
					{
						localMaxima.add(getPoint(i));
					}
				}
				else //last element
				{
					if (getReachability(i-1) < getReachability(i)*DENSDIFF )
					{
						localMaxima.add(getPoint(i));
					}
				}
			}
			else //first elemement
			{
				if (getReachability(i+1) < getReachability(i)*DENSDIFF )
				{
					localMaxima.add(getPoint(i));
				}
			}
		}
	}


	public int getReachability(int index)
	{
		return(points.get(index).getX()); //change to reachability
	}

	public int getX(int index)
	{
		return(points.get(index).getX());
	}

	public int getY(int index)
	{
		return(points.get(index).getY());
	}

	public int getSize()
	{
		return(points.size());
	}

	public Point getPoint(int index)
	{
		return(points.get(index));
	}


	private ClusterTree extractClusterTree(ClusterTree clusterTree,int maxClusters, int minClusters)
	{
		//for loop will go through the leaves first, then 1 height up and so on. Leaves don't have to be on the same level
		ClusterTree clusters = new ClusterTree();
		int height = 0;

		for(int i=0;i<clusterTree.size();i++)
		{
			if (height == clusterTree.getHeight(i))
			{
				clusters.add(clusterTree.getCluster(i));
			}
			else //we moved one level up in the tree
			{
				clusters = correctClusters(clusters,maxClusters,minClusters);
				return(clusters);
			}
		}
		clusters = correctClusters(clusters,maxClusters,minClusters);
		return(clusters);
	}


	private ClusterTree correctClusters(ClusterTree clusterTree, int maxCluster, int minClusters)
	{
		ClusterTree clusters = new ClusterTree();
		//for loop will go through the leaves first, then 1 height up and so on. Leaves don't have to be on the same level
		for(int i=0;i<clusterTree.size();i++)
		{	
			if (clusterTree.isLeaf(i))
			{
				clusters.add(clusterTree.getCluster(i));
			}
		}

		int minCluster =0;
		double maxDensity = 0;

		while (clusters.size() > maxCluster)
		{
			minCluster = -1;
			maxDensity = -1 ;
			for(int i=0;i<clusters.size();i++)
			{	
				if (i != clusters.size())
				{
					if (clusterTree.getClusterParent(clusters.getCluster(i)) == clusterTree.getClusterParent(clusters.getCluster(i+1)))
					{  //if they have the same parent, and thus can be combined according to the clusterTree
						if (maxDensity < clusters.reachabilityDifference(i, i+1));
						{					//get the density difference
							maxDensity = clusters.reachabilityDifference(i, i+1);
							//store the density difference
							minCluster = i;
							//store the worst cluster
						}
					}
				}
			}
			if (minCluster == -1)
			{
				break;
			}
			clusters.combineClusters(clusters.getCluster(minCluster),clusters.getCluster(minCluster+1)); //combines second cluster with the first cluster in the first cluster
			clusters.remove(clusters.getCluster(minCluster+1)); //removes the second cluster
		}

		boolean completed = false;
		double tempMaxDensity = maxDensity;

		while(completed == false)
		{
			for(int i=0;i<clusters.size();i++)
			{	
				if (i != clusters.size())
				{
					if (tempMaxDensity < clusters.reachabilityDifference(i, i+1));
					{
						tempMaxDensity = clusters.reachabilityDifference(i, i+1);
						minCluster = i;
						//calculates the density difference
					}
				}
			}

			if (tempMaxDensity <= maxDensity*DENSDIFF)
			{
				clusters.combineClusters(clusters.getCluster(minCluster),clusters.getCluster(minCluster+1)); //combines second cluster with the first cluster in the first cluster
				clusters.remove(clusters.getCluster(minCluster+1)); //removes the second cluster	
			}

			if (clusters.size() == minClusters)
			{
				completed = true;			
			}
		}
		return(clusters);
	}
}
