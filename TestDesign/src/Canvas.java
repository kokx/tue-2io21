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
	int mouseX = 0;
	int mouseY = 0;
	int spraySize = 5;
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
		g.drawOval(mouseX-spraySize/2, mouseY-spraySize/2, spraySize, spraySize);

	}

	public void updateSpray(int mouseX, int mouseY, int spraySize)
	{
		this.mouseX = mouseX;
		this.mouseY = mouseY;
		this.spraySize = spraySize;
		updateDisplay(points);
	}

}
