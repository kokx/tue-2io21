
public class RTree {

	//format of tree: 4 regions: leftTop:1 rightTop:2 leftBottom:3 rightBottom: 4
	//parents number is prefixed, so a point that is in the leftbottom region of the entire field called A, and in the rightbottom field of A
	//has number 34


	private void knn()
	{
		if (lastDigit == 1)
		{
			Above =
					Left =
					Right = 
					Below =
					leftTop = 
					rightTop = 
					leftBottom =
					rightBottom =
		}

		if (lastDigit == 2)
		{
			Above =
					Left = 
					Right = 
					Below =
					leftTop = 
					rightTop = 
					leftBottom =
					rightBottom =
		}

		if (lastDigit == 3)
		{
			Above =
					Left = 
					Right = 
					Below =
					leftTop = 
					rightTop = 
					leftBottom =
					rightBottom =
		}


		if (lastDigit == 4)
		{
			above = number-2;//same cluster, but right top region
			leftTop = number-3;
			Left = number-1;



			Right = findRight(number,number,0);

			Below =

					rightTop = 
					leftBottom =
					rightBottom =
		}
	}

	//returns the right neighbour, or 0 if no neighbour
	private int findRight(int number,int originalNumber,int count)
	{
		this.number = number;

		if ((number % 10) == 4 || (number % 10) == 2 )
		{
			number = (number-(number%10))/10;
			count ++; //keeps track how many digits have been procesed
			findRight(number,originalNumber,count);
		}
		else if (number == 0) //no right neighbour
		{
			return(0);
		}
		else //number%10 == 2 || number% 10 == 3
		{
			originalNumber += Math.pow(10, count);
			getRectangleNumber(originalNumer);
		}
	}

	//returns the rectangle or the rectangle that is closest to it
	private int getRectangleNumber(int number)
	{
		
	}



}