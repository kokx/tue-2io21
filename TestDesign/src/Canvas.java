import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.TreeMap;

import javax.swing.*;

import sun.java2d.loops.DrawLine;

import java.awt.*;
import java.awt.event.*;


public class Canvas extends JPanel{

	TreeMap<Long, Point> points = new TreeMap<Long, Point>();
	
	Canvas()
	{
		super();
	}
	
	public void updateDisplay(TreeMap<Long, Point> allPoints)
	{
		points = allPoints;
		repaint();
	}

	public void paintComponent(Graphics g) //
	{
		super.paintComponent(g);

		for(Point i : points.values())
		{
			g.drawLine(i.getX(), i.getY(), i.getX(), i.getY());

		}

	}

}
