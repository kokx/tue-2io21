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

	boolean densityBased = true;
	private double density = 0.7;

	Sprayer()
	{

	}
	public void clear()
	{
		field.clear();
	}

	public void beginSpray(int x,int y,int spraySize,String distributionType)
	{
		distribution = distributionType;
	}

	//generates the points as long as the mouse is hold
	public TreeMap<Long, Point> spray(int spraySize,int mouseX,int mouseY,double density)
	{
		int pointX = 0;
		int pointY = 0;


		if ("Uniform".equals(distribution))
		{
			density = (int) (density*Math.PI*Math.pow(spraySize/2,2));
			for(int i=0; i<density;i++)
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
		return(field);
	}
}