import java.util.ArrayList;


public class Box {

	int x;
	int y;
	int width;
	int height;
	int number;
	Box parent;
	ArrayList<Point> points = new ArrayList<Point>();
	int pointAmount

	Box(int x, int y, int width, int height, int number) //this box does not have a parante
	{
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.number = number;

		if (field.getPoints() > 2)
		{
			pointAmount = field.getPoints();
		}
		else
		{
			for(Point i: field.getPoints()) //change this to an iteration over all points in the field
			{
				points.add(i);
			}
			pointAmount = points.size();
		}
	}

	Box(int x, int y, int width, int height, int number, Box parent) //this box does not have a parante
	{
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.number = number;
		this.parent = parent;

			for(Point i: parent.getPoints()) //change this to an iteration over all points in the field
			{
				if i.getX() > x && i.getX <= i.getX+width
				points.add(i);
			}
			pointAmount = points.size();
		}
	}



}
