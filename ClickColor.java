//////////////////////
// Name: Miguel		//
// Datum: 11.6.2018	//
// Version: 1.0		//
//////////////////////

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.JComponent;
import javax.swing.event.*;
import javax.swing.Timer;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Arc2D;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.awt.event.KeyEvent;
import java.awt.event.InputEvent;
import java.awt.event.MouseAdapter;

import java.util.ArrayList;

import java.util.Random;

public class ClickColor extends JFrame 
{
	private JLabel labelTxt;
	private JButton btnNewGame;
	
	private String text;
	
	private ArrayList<Integer> spielCache;
	
	private Spielfeld spielFeld;
	private Timer timeOne;
	private boolean spielerPhase;
	private boolean blink;
	private boolean nextLevel;
	private int zaehler = 0;
	private int tmp = 0;
	
	ClickColor()
	{
		super("ClickColor");
		
		text = "Bist Du bereit?";
		spielerPhase = false;
		blink = false;
		nextLevel = false;
		
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		// menubar start ****
		JMenuBar menuBar = new JMenuBar();
		JMenu menuSpiel = new JMenu("Spiel");
		
		JMenuItem menuNeu = new JMenuItem("Neu");
		menuNeu.addActionListener((ae) -> newGame());
		menuNeu.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_DOWN_MASK));
		menuSpiel.add(menuNeu);

		JMenuItem menuEnde = new JMenuItem("Ende");
		menuEnde.addActionListener((ae) -> endGame());
		menuEnde.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, InputEvent.CTRL_DOWN_MASK));
		menuSpiel.add(menuEnde);
		
		menuBar.add(menuSpiel);

		setJMenuBar(menuBar);
		// menubar ende ****
		
		spielFeld = new Spielfeld();
		JPanel panSouth = new JPanel();
		labelTxt = new JLabel(text);
		labelTxt.setForeground(Color.white);
		btnNewGame = new JButton("Neues Spiel");
		
		panSouth.setBackground(Color.black);
		setLayout(new BorderLayout());
		
		panSouth.add(labelTxt);
		panSouth.add(btnNewGame);
		
		add(panSouth, BorderLayout.SOUTH);
		
		add(spielFeld);
		addMouseListener(spielFeld);
		
		spielCache = new ArrayList<Integer>();
		
		pack();
		
		//ActionListener
		btnNewGame.addActionListener( (ae) -> startGame() );
		// timer
		timeOne = new Timer(500, new ActionListener()
		{
			@Override public void actionPerformed(ActionEvent ae)
			{	
				if(blink == false && nextLevel == false)
				{
					labelTxt.setText("Spiel laeuft...");
					Random r = new Random();
					spielCache.add(r.nextInt(4) +1);
					zaehler++;
					nextLevel = true;
					System.out.println(spielCache);
				}
				else if(blink == true)
				{	
					blink = false;
					spielFeld.setCGreen(Color.green.darker());
					spielFeld.setCRed(Color.red.darker());
					spielFeld.setCYel(Color.yellow.darker());
					spielFeld.setCBlue(Color.blue.darker());
					spielFeld.malen();
				}
				else if( tmp < zaehler && blink == false)
				{	
					blink = true;
					switch(spielCache.get(tmp))
					{	
						// green
					case 1: spielFeld.setCGreen(Color.green); break;
						//red	
					case 2: spielFeld.setCRed(Color.red); break;
						// yellow	
					case 3: spielFeld.setCYel(Color.yellow); break;
						// blue	
					case 4: spielFeld.setCBlue(Color.blue); break;
					}
					tmp++;
					spielFeld.malen();
				}
				else if(nextLevel == true && tmp == zaehler && blink == false)
				{	
					labelTxt.setText("Du bist dran...");
					if(spielFeld.getCache().size() == spielCache.size())
					{
						if(!spielCache.equals(spielFeld.getCache()))
							{
									labelTxt.setText("Leider Falsch!");
									timeOne.stop();
							}
					nextLevel = false;
					spielFeld.clearCache();
					tmp= 0;
					}
				}
			}
		});
	}
	
	public void newGame()
	{	
		timeOne.stop();
		labelTxt.setText(text);
		spielCache.clear();
		zaehler = 0;
		blink = false;
		spielerPhase = false;
		spielFeld.setCGreen(Color.green.darker());
		spielFeld.setCRed(Color.red.darker());
		spielFeld.setCYel(Color.yellow.darker());
		spielFeld.setCBlue(Color.blue.darker());
		spielFeld.clearCache();
		spielFeld.revalidate();
		spielFeld.repaint();
	}
	
	public void endGame()
	{
		System.exit(0);
	}
	
	public void startGame()
	{
		newGame();
		tmp = 0;
		labelTxt.setText("Spiel laeuft...");
		timeOne.start();
	}
	
	public static void main(String[] argv)
	{
		SwingUtilities.invokeLater(new Runnable()
		{
			public void run()
			{
				ClickColor c = new ClickColor();
				c.setVisible(true);
			}
		});
	}
}