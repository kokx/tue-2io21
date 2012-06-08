import java.util.ArrayList;
import java.util.Scanner;
import java.util.TreeMap;

import javax.swing.*;

import sun.java2d.loops.DrawLine;

import java.awt.*;
import java.awt.event.*;

class Display extends JPanel implements ActionListener, MouseWheelListener, MouseListener, MouseMotionListener
{
	Sprayer sprayer;
	public int spraySize = 50; //size of the distribution
	public int sprayAmount = 800;

	public int mulX = 104729;
	public int mulY = mulX;
	
	private int mouseX = 0;
	private int mouseY = 0;

	private Canvas canvas = new Canvas();
	private JTextField clusters = new JTextField("find 0 to 4 clusters");;
	private JTextField density = new JTextField("density: 0.8");

	public String distribution = "Uniform";

	int width; //contains the width of the field
	int height; //contains the height of the field

	boolean spraying;
	Timer timer = new Timer(20,this);

	JButton buttonDistribution; //button for the distributions


	private TreeMap<Long, Point> points;
	
	public TreeMap<Long, Point> getPoints()
	{
		return(points);
	}

	private void updateDisplay()
	{
		canvas.updateDisplay(points);
	}

	public void actionPerformed(ActionEvent evt)
	{  
		if (evt.getSource() == timer)
		{
			spray();
			timer.stop();
		}

		
		if (("Take input").equals(evt.getActionCommand())) //if the start/stop button has been pressed
		{
			points.clear();
			canvas.clear();
			sprayer.clear();
			System.out.println(points.size());
			//takeInput();
		}

		if (("Normal").equals(evt.getActionCommand())) //if the start/stop button has been pressed
		{
			buttonDistribution.setLabel("Uniform");
			distribution = "Uniform";
			repaint();
		}
		
		if (("Uniform").equals(evt.getActionCommand())) //if the start/stop button has been pressed
		{
			buttonDistribution.setLabel("Normal");
			distribution = "Normal";
			repaint();
		}

		if (("Run Algorithm").equals(evt.getActionCommand())) //if the start/stop button has been pressed
		{
			giveOutput();
		}
	}

	private void giveOutput()
	{        

		System.out.println(clusters.getText());
		System.out.println(points.size()+ " points");
		for(Point i: points.values())
		{
			System.out.println(i.getX()*mulX+ " " +i.getY()*mulY);
		}
	}
	
	public void initializeSprayer()
	{
		sprayer = new Sprayer();	
	}

	//@pre: true
	//@post: a Jframe has been created, with a Jpanel and with  the buttons Nextgen and Start/Stop
	//@modifies: shouldPrint
	void displayInitialize()
	{
		JFrame frame = new JFrame("Test");

		frame.add(this);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(1000, 700);
		frame.setLocation(200, 100);
		frame.setVisible(true);
		frame.addMouseListener(this);
		frame.addMouseWheelListener(this);
		frame.addMouseMotionListener(this);

		this.setSize(1,1);

		frame.add(canvas);

		JButton button = new JButton("Take input");
		button.addActionListener(this);

		// write 'find $C_i$ to $C_j$ clusters'
		// write '$n$ points'
		// write '$x$ $y$' for each point

		JButton button2 = new JButton("Run Algorithm");
		button2.addActionListener(this);

		buttonDistribution = new JButton("Normal");
		buttonDistribution.addActionListener(this);

		//frame.add(buttonDistribution,BorderLayout.WEST);
		frame.add(density,BorderLayout.WEST);
		
		frame.add(button2,BorderLayout.SOUTH);
		frame.add(button,BorderLayout.NORTH);
		frame.add(clusters,BorderLayout.EAST);
	}







	Display(int width, int height)
	{ 
		points = new TreeMap<Long, Point>();

		initializeSprayer();
		initializeGrid(points); //initializes the grids and adds the cells to the panel
		displayInitialize();
	}

	void initializeGrid(TreeMap<Long, Point> basePoints)
	{ 
		for(Point i: basePoints.values())
		{
			points.put((long) ((long) (i.getX() << 32)+i.getY()),i);
		}
		updateDisplay();
		
	}


	public void spray()
	{
		//points = (sprayer.spray(spraySize,mouseX,mouseY,sprayAmount));
		points = sprayer.spray(spraySize,mouseX,mouseY,Double.parseDouble(density.getText().replaceAll("density: ", "")));
		canvas.points = points;
		updateDisplay();
	}
	

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		spraying = true;
		timer.start();
		updateDisplay();
		sprayer.beginSpray(e.getX(), e.getY(), spraySize,distribution);
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		spraying = false;
		timer.stop();
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		// TODO Auto-generated method stub
		spraySize = (int) Math.max(10,spraySize+e.getWheelRotation()*(5*Math.max(1,Math.floor(spraySize/200))));
		canvas.updateSpray(mouseX,mouseY,spraySize);
		
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		mouseX = e.getX()-80;		
		mouseY = e.getY()-55;	
		canvas.updateSpray(mouseX,mouseY,spraySize);
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		mouseX = e.getX()-80;		
		mouseY = e.getY()-55;		
		canvas.updateSpray(mouseX,mouseY,spraySize);

	}



}

