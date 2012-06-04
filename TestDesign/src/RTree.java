import java.util.ArrayList;
import java.util.PriorityQueue;




public class RTree {

    //format of tree: 4 regions: leftTop:1 rightTop:2 leftBottom:3 rightBottom: 4
    //parents number is prefixed, so a point that is in the leftbottom region of the entire field called A, and in the rightbottom field of A
    //has number 34.
    final static int DISTANCEMEASURE = 2;
    Box topBox;
    /*Build the R-tree. It will store the bounding boxes into Box classes which are stored in fields
     * which is implemented as an arrayList.
     *
     */
    private void build(Field field)
    {
        int x = 0;
        int y = 0;

        int width = field.getMaxX;
        int height = field.getMaxY;

        if (width < height) //make the field a square
        {
            height = width;
        }
        else
        {
            width = height;
        }
        topBox = new Box(0,0,width,height,0);

        splitBox(topBox);

    }


    //splits every box
    void splitBox(Box box)
    {
        int x = box.x;
        int y = box.y;

        int width = box.width;
        int height = box.height;

        int number = box.number;

        if (box.countPoints > 2) //enough points to make a split
        {
            Box topLeft = new Box(box.x,box.y,	 					 width/2,height/2, number*10+1, box);
            Box topRight = new Box(box.x+width/2,box.y,				 width/2+width/2,height/2, number*10+2, box);
            Box bottomLeft = new Box(box.x,box.y+height/2,			 width/2,height/2+height/2, number*10+3, box);
            Box bottomRight = new Box(box.x+width/2,box.y+height/2,	 width/2+width/2,height/2+height/2, number*10+4, box);


            splitBox(topLeft);
            splitBox(topRight);
            splitBox(bottomLeft);
            splitBox(bottomRight);
        }
        else
        {
            box.addPoints(box.x,box.y,box.width,box.height);
        }
    }

    public PriorityQueue<Point> kNNPoints(Point pt,int n, int eps)
    {
        ArrayList<Point> points = kNN(pt,eps);

        PriorityQueue<Point> nPoints = new PriorityQueue<Point>();

        for(Point i : points)
        {
            if (distance(pt, i, DISTANCEMEASURE) <= eps) //change to unsquared eps
                nPoints.add(points.get(i));
        }
    }

    public Point kNNPoint(Point pt,int n int eps) //Implement with max priority queue, pair of (Int, point)
    {
        ArrayList<Point> points = kNN(pt,eps);

        PriorityQueue<Point> nPoints = new PriorityQueue<Point>();

        int nMinDist = Integer.MAX_VALUE;

        int currentDist = 0;

        for(int i : points)
        {
            if (nPoints.size() < n)
            {
                nPoints.add(n);
            }
            if (nPoints.size() == n)
            {
                currentDist = distance(i,pt,DISTANCEMEASURE);
                if (currentDist < nPoints.peek()) //change this to correct first element of pair getter
                {
                    nPoints.remove();
                    nPoints.add() //change to the the correct pair insertion
                }
            }
        }

        return(nPoints.poll());
    }

    //returns all the point within a square epsilon range of point p
    private ArrayList<Point> kNN(Point pt, int eps)
    {
        Box searchBox = new Box(pt.getX()-eps,pt.getY()-eps,pt.getX()+eps,pt.getY()+eps);
        return(returnOverlapPoint(topBox, searchBox));
    }

    //returns all the points in searchBox that overlap with the currentBox. Call this using kNN(Point pt, int eps)
    private ArrayList<Point> returnOverlapPoint(Box currentBox, Box searchBox)
    {
        ArrayList<Point> points = new ArrayList<Point>();
        if (currentBox.completeOverlap(searchBox))
        {
            points.addAll(currentBox.getPoints());
            return(points);
        }
        else if (currentBox.partialOverlap(searchBox))
        {
            points.addAll(returnOverlapPoint(currentBox.getChild(1),searchBox));
            points.addAll(returnOverlapPoint(currentBox.getChild(2),searchBox));
            points.addAll(returnOverlapPoint(currentBox.getChild(3),searchBox));
            points.addAll(returnOverlapPoint(currentBox.getChild(4),searchBox));
        }
        else //no overlap
        {
            return(null);
        }
    }

    /*
     * returns the box that the point is contained in
     */
    Box findBox(Point pt)
    {
        int x = pt.getX();
        int y = pt.getY();

        Box box = topBox;

        while(box.hasChild() == true)
        {
            if (x < box.getX() )
            {
                if (y < box.getY())
                {
                    box = box.getChild(1);
                }
                else
                {
                    box = box.getChild(3);
                }
            }
            else //x is in the right field
            {
                if (y < box.getY())
                {
                    box = box.getChild(2);
                }
                else
                {
                    box = box.getChild(4);
                }
            }
        }
        return (box);
    }

    /*@param:
      / 		originalnumber: the number for which we search the right neighbor
      / 		number:		  the number we are currently looking from.
      /
      / @returns: Returns the number of the right neighbor of the original number, or 0 if no right neighbor
      */

    /*@param: number is the box we are looking for, going for the best box we can find
     * e.a. furthest down the tree.
     * @return: returns the box we are looking for.
     */
    Box returnBox(int number)
    {
        Box box = topBox;
        int i = 0;
        while(true)
        {

            int childNumber = getDigitN(number,i);

            if (box.hasChild == true)
            {
                box = topBox.getChild(childNumber);
            }
            else
            {
                return(box);
            }

            i++;
            if (getLength(number) < i) //if there are no more digits
            {
                return(box);
            }
        }
    }


    /*@pre: n is smaller than number.length-1 and greater than 0
     *@param: 0 is the first digit, n is the last digit
     *@returns: Returns the n'th digit of the number
     */
    int getDigitN(int number, int n)
    {
        int length = getLength(number);
        int iteration = 0;
        while(length-(n+1) != iteration) //correction for the loop going to far
        {
            number = (number%10/10);
        }

        return(number%10);
    }

    /*
     *@return: returns  the length of the number from 1 to ...
     */
    int getLength(int number)
    {
        int length = 0;
        while (number < 10)
        {
            number = (number %10)/10;
            length++;
        }
        if (number >0)
        {
            length++;
        }
        return(length);
    }

}
