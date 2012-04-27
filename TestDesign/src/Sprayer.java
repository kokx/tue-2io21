import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.sql.Time;
import java.util.Random;
import java.util.TreeMap;


public class Sprayer{

	Random generator = new Random();
	TreeMap<Long, Point> field = new TreeMap<Long, Point>();


	String distribution;

	Sprayer()
	{

	}

	public void beginSpray(int x,int y,int spraySize,String distributionType)
	{
		distribution = distributionType;
	}

	//generates the points as long as the mouse is hold
	public TreeMap<Long, Point> spray(int spraySize,int mouseX,int mouseY,int amount)
	{
		int pointX = 0;
		int pointY = 0;


		if ("Uniform".equals(distribution))
		{
			for(int i=0; i<amount;i++)
			{
				pointX = 0;
				pointY = 0;

				pointX = mouseX-spraySize/2+generator.nextInt(spraySize);
				pointY = mouseY-spraySize/2+generator.nextInt(spraySize);
				if ((Math.pow(Math.abs(pointX-mouseX), 2)+Math.pow(Math.abs(pointY-mouseY),2)) <= Math.pow(spraySize/2,2))
				{
					if (pointX>0 && pointY >0)
					{
						long longX = (long) pointX << 32;
						if (!field.containsKey(((long) longX+pointY)))
						{
							Point p = (new Point(pointX,pointY,0));						
							field.put((long) (longX+pointY), p);

						}
					}
				}
			}
		}



		if ("Normal".equals(distribution)) //might need to be changed, not a perfect normal distribution
		{
			for(int i =0;i<amount;i++)
			{
				pointX = 0;
				pointY = 0;

				int distance = (generator.nextInt(spraySize/2));
				double angle = (generator.nextDouble()*(2*Math.PI));
				pointX = mouseX+(int) (Math.sin(angle)*distance);
				pointY = mouseY+(int) (Math.cos(angle)*distance);

				if (pointX>0 && pointY >0)
				{
					long longX = (long) pointX << 32;
					if (!field.containsKey(((long) longX+pointY)))
					{
						Point p = (new Point(pointX,pointY,0));						
						field.put((long) (longX+pointY), p);

					}
				}
			}
		}
		return(field);
	}


}
