//////////////////////
// Name: Miguel		//
// Datum: 11.6.2018	//
// Version: 1.0		//
//////////////////////

import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Arc2D;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import javax.swing.JComponent;
import javax.swing.event.*;
import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.util.ArrayList;

public class Spielfeld extends JPanel implements MouseListener
{	
	private int xCoords;
	private int yCoords;
	
	private Color cGreen;
	private Color cRed;
	private Color cYel;
	private Color cBlue;
	
	private ArrayList<Integer> cache;
	
	Spielfeld()
	{
		setBackground(Color.black);
		setPreferredSize(new Dimension(800,800));
		
		cGreen = Color.green.darker();
		cRed = Color.red.darker();
		cYel = Color.yellow.darker();
		cBlue = Color.blue.darker();
		
		cache = new ArrayList<Integer>();
		
		addMouseListener(this);
		
	}
	
	@Override public void mousePressed(MouseEvent me)
	{
		xCoords = me.getX();
		yCoords = me.getY();
		if(istKreisTeil() != 0)
			cache.add(istKreisTeil());
		System.out.println(istKreisTeil());
	}
	
	@Override public void mouseClicked(MouseEvent me){}
	@Override public void mouseReleased(MouseEvent me){}
	@Override public void mouseEntered(MouseEvent me){}
	@Override public void mouseExited(MouseEvent me){}
		
	@Override public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		// grüner kreisteil 90 bis 180 grad
		g.setColor(cGreen);
		g.fillArc(0, 0, 800, 800, 90, 90);
		
		// roter kreisteil 0 bis 90 grad
		g.setColor(cRed);
		g.fillArc(0, 0, 800, 800, 0, 90);
		
		// gelber kreisteil 180 bis 270 grad
		g.setColor(cYel);
		g.fillArc(0, 0, 800, 800, 180, 90);
		
		// blauer kreisteil 270 bis 0 grad
		g.setColor(cBlue);
		g.fillArc(0, 0, 800, 800, 270, 90);
	}
	
	public int istKreisTeil()
	{	
		int x = xCoords;
		int y = yCoords;
		if(((x-400)*(x-400) + (y-400)*(y-400)) < ( 400*400))
		{
			if( x < 400 && y < 400 )
				return 1; //green
			else if( ( x >= 400 && x < 800) && y < 400 )
				return 2; // red
			else if( x < 400 && ( y >= 400 && y < 800))
				return 3; // yellow
			else if( ( x >= 400 && x < 800) && ( y >= 400 && y < 800))
				return 4; // blue
		}
		return 0; // nicht im kreis
	}
	
	public int getXCoords()
	{
		return xCoords;
	}
	
	public int getYCoords()
	{
		return yCoords;
	}
	
	public void setCGreen(Color c)
	{
		cGreen = c;
	}
	
	public void setCRed(Color c)
	{
		cRed = c;
	}

	public void setCYel(Color c)
	{
		cYel = c;
	}	
	
	public void setCBlue(Color c)
	{
		cBlue = c;
	}	
	
	public ArrayList<Integer> getCache()
	{
		return cache;
	}
	
	public void clearCache()
	{
		cache.clear();
	}
	public void malen()
	{
		revalidate();
        repaint();
	}
}	