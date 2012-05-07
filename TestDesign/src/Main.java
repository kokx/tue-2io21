
import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author s112622
 */
public class Main extends JFrame implements ActionListener
{
	private static int WIDTH = 6000;
	private static int HEIGHT = 6000;
	Display display;
	/**
	 * @param args the command line arguments
	 */
	public void Main()
	{
		initDisplay();
	}

	public void initDisplay()
	{
		Display display = new Display(WIDTH,HEIGHT); //creates the field according to the specifications
	}

	public static void main(String[] args) 
	{
		new Main().initDisplay();				
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

	}

}
