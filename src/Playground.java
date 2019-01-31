
import java.applet.Applet;
import java.awt.*;
import java.awt.event.*;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.awt.Color;

import javax.swing.Timer;

import java.util.ArrayList;
import java.awt.geom.Area;

import javax.swing.JOptionPane;

/**
 * 
 * @author Caleb Kinmon (UNI: cgk2128)
 * <br><br>
 * This class is based off John Kender's program on Courseworks.
 * <br><br>
 * This class creates an applet that takes parameters from an HTML
 * file to construct a game in which rock, paper, scissors, lizard,
 * spock and black hole are represented as strings that move around
 * the screen and collide into each other.  When collisions occur,
 * some objects destroy others, and then grow in size. 
 * <br><br>
 * This class also uses the Decider class to help compute the outcome 
 * during collisions.
 * <br><br>
 * Occasionally, when testing for collisions, the game will incorrectly
 * remove a string from the screen before a collision occurs.  The 
 * collision will obviously occur, but the intersect() method tests true
 * too soon.  I've tried to refactor and fix, but ran out of time.
 *
 */
public class Playground extends Applet {

	/**
	 * This method is required and initializes the applet. It calls
	 * helper methods to get the HTML parameters, which contains details
	 * about the name, color, and font of the parameter.
	 */
	public void init() {
		
		getParameters();
		setColors();
		setFonts();
		Graphics2D g2D = (Graphics2D) getGraphics();
		FontRenderContext throwContext = g2D.getFontRenderContext();
		setRectangleBoundaries(throwContext);
		htmlDelay = Integer.parseInt(getParameter("delay"));
		htmlGameOverTimer = Integer.parseInt(getParameter("stop"));
		gameTimer = 0;

		appletTimer = new Timer(htmlDelay, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				// This section updates positions of strings
				
				for(int i = 0; i < htmlArrayThrowNames.size(); i++){
					int tempX = Integer.parseInt(htmlArrayThrowNames.get(i)[3]);
					tempX += Integer.parseInt(htmlArrayThrowNames.get(i)[5]);
					int tempY = Integer.parseInt(htmlArrayThrowNames.get(i)[4]);
					tempY += Integer.parseInt(htmlArrayThrowNames.get(i)[6]);
					String[] temp = htmlArrayThrowNames.get(i);
					temp[3] = Integer.toString(tempX);
					temp[4] = Integer.toString(tempY);
					htmlArrayThrowNames.set(i, temp);
					checkEdges(tempX, tempY, i);
					htmlThrowName = htmlArrayThrowNames.get(i)[0];
				}
				checkCollision();
				gameTimer++;
				if(gameTimer > htmlGameOverTimer){
					stop();
				}
				repaint();
			}
		});
	}
	
	/**
	 * This method is required and starts the applet.
	 */
	public void start() {
		appletTimer.start();
	}

	/**
	 * This method is required and creates the strings
	 * that are moving on the screen.
	 */
	public void paint(Graphics g) {
		for(int i = 0; i < htmlArrayThrowNames.size(); i++){
			g.setFont(throwFont.get(i));
			g.setColor(colors.get(i));
			g.drawString(htmlArrayThrowNames.get(i)[0], Integer.parseInt(htmlArrayThrowNames.get(i)[3]), Integer.parseInt(htmlArrayThrowNames.get(i)[4]));
		}
	}

	/**
	 * This method is required and halts the program when
	 * the game is over or the user exits.
	 */
	public void stop() {
		appletTimer.stop();
		JOptionPane.showMessageDialog(null, "Game Over!");
	}

	/**
	 * This method is required and deconstructs the program.
	 */
	public void destroy() {
	}
	
	/**
	 * This is a helper method that gets the HTML parameters and stores
	 * them in a data structure.
	 */
	private void getParameters(){
		htmlThrowName = getParameter("throwRock");	
		htmlArrayThrowNames.add(htmlThrowName.split(","));
		htmlThrowName = getParameter("throwPaper");	
		htmlArrayThrowNames.add(htmlThrowName.split(","));
		htmlThrowName = getParameter("throwScissors");	
		htmlArrayThrowNames.add(htmlThrowName.split(","));
		htmlThrowName = getParameter("throwLizard");	
		htmlArrayThrowNames.add(htmlThrowName.split(","));
		htmlThrowName = getParameter("throwSpock");	
		htmlArrayThrowNames.add(htmlThrowName.split(","));
		htmlThrowName = getParameter("throwBlackHole");	
		htmlArrayThrowNames.add(htmlThrowName.split(","));
		htmlThrowName = getParameter("throwBlackHole2");	
		htmlArrayThrowNames.add(htmlThrowName.split(","));
	}
	
	/**
	 * This is a helper method that sets the colors of each string.
	 */
	private void setColors(){
		colors.add(brown);
		colors.add(Color.black);
		colors.add(Color.gray);
		colors.add(Color.green);
		colors.add(Color.blue);
		colors.add(Color.black);
		colors.add(Color.black);
	}
	
	/**
	 * This is a helper method that creates the font of each string.
	 */
	private void setFonts(){
		throwFont.add(new Font(htmlArrayThrowNames.get(0)[1], Font.BOLD, Integer.parseInt(htmlArrayThrowNames.get(0)[2])));
		throwFont.add(new Font(htmlArrayThrowNames.get(1)[1], Font.ITALIC, Integer.parseInt(htmlArrayThrowNames.get(1)[2])));
		throwFont.add(new Font(htmlArrayThrowNames.get(2)[1], Font.BOLD, Integer.parseInt(htmlArrayThrowNames.get(2)[2])));
		throwFont.add(new Font(htmlArrayThrowNames.get(3)[1], Font.BOLD, Integer.parseInt(htmlArrayThrowNames.get(3)[2])));
		throwFont.add(new Font(htmlArrayThrowNames.get(4)[1], Font.BOLD, Integer.parseInt(htmlArrayThrowNames.get(4)[2])));
		throwFont.add(new Font(htmlArrayThrowNames.get(5)[1], Font.BOLD, Integer.parseInt(htmlArrayThrowNames.get(5)[2])));
		throwFont.add(new Font(htmlArrayThrowNames.get(6)[1], Font.BOLD, Integer.parseInt(htmlArrayThrowNames.get(6)[2])));
	}
	
	/**
	 * This is a helper method that sets the boundaries of the rectangle that
	 * encompasses the string in an Array List.
	 * @param throwContext of type FontRenderContext.
	 */
	private void setRectangleBoundaries(FontRenderContext throwContext){
		throwRectangle.add(throwFont.get(0).getStringBounds(htmlArrayThrowNames.get(0)[0], throwContext));
		throwRectangle.add(throwFont.get(1).getStringBounds(htmlArrayThrowNames.get(1)[0], throwContext));
		throwRectangle.add(throwFont.get(2).getStringBounds(htmlArrayThrowNames.get(2)[0], throwContext));
		throwRectangle.add(throwFont.get(3).getStringBounds(htmlArrayThrowNames.get(3)[0], throwContext));
		throwRectangle.add(throwFont.get(4).getStringBounds(htmlArrayThrowNames.get(4)[0], throwContext));
		throwRectangle.add(throwFont.get(5).getStringBounds(htmlArrayThrowNames.get(5)[0], throwContext));
		throwRectangle.add(throwFont.get(6).getStringBounds(htmlArrayThrowNames.get(6)[0], throwContext));
	}
	
	/**
	 * This method resets the game timer when a collision occurs.
	 * After a user-specified amount of time, without a collision, the 
	 * game will end.
	 */
	private void resetGameTimer(){
		gameTimer = 0;
	}
	
	/**
	 * This helper method checks the position of each string, such that when
	 * a string reaches the edges of the screen, it wraps around the screen.
	 * @param tempX of type int.
	 * @param tempY of type int.
	 * @param index of type int.
	 */
	private void checkEdges(int tempX, int tempY, int index){
		if (tempX + throwRectangle.get(index).getWidth() < 0)
			tempX = getWidth();
			htmlArrayThrowNames.get(index)[3] = Integer.toString(tempX);
		if (tempX > getWidth())
			tempX = 0;
			htmlArrayThrowNames.get(index)[3] = Integer.toString(tempX);
		if (tempY > getHeight())
			tempY = 0;
			htmlArrayThrowNames.get(index)[4] = Integer.toString(tempY);
		if (tempY < 0)
			tempY = getHeight();
			htmlArrayThrowNames.get(index)[4] = Integer.toString(tempY);
	}
	
	/**
	 * This helper method constructs a rectangle based on the coordinates
	 * of a string, so that it can be compared to see if it intersects with
	 * another rectangle.
	 * @param index of type int.
	 * @return Rectangle2D.Double. 
	 */
	private Rectangle2D.Double constructRectangle(int index){
		double x, y, w, h;
		x = Double.parseDouble(htmlArrayThrowNames.get(index)[3]);
		y = Double.parseDouble(htmlArrayThrowNames.get(index)[4]);
		w = throwRectangle.get(index).getWidth();
		h = throwRectangle.get(index).getHeight();
		return new Rectangle2D.Double(x, y, w, h);
	}
	
	/**
	 * This helper method checks to see if two strings collide. If they
	 * collide, it calls the computeOutcome() method from Decider to
	 * determine what string won the collision.
	 */
	private void checkCollision(){
		for(int i = 0; i < throwRectangle.size(); i++){
			Rectangle2D.Double r1 = constructRectangle(i);
			for(int a = 0; a < throwRectangle.size(); a++){
				Rectangle2D.Double r2 = constructRectangle(a);
				if(r1.intersects(r2) && a != i){
					resetGameTimer();
					String name1 = htmlArrayThrowNames.get(i)[0];
					String name2 = htmlArrayThrowNames.get(a)[0];
					decider.computeOutcome(name1, name2, i, a);
				}
			}
		}
	}
	
	/**
	 * This method is used to increase the size of a string
	 * that survived a collision.
	 * @param winner of type int.
	 */
	public void computeNewSize(int winner){
		String[] s1 = htmlArrayThrowNames.get(winner);
		int newSize = Integer.parseInt(s1[2]);
		s1[2] = Integer.toString(newSize + 20);
		htmlArrayThrowNames.set(winner, s1);
		throwFont.set(winner, new Font(htmlArrayThrowNames.get(winner)[1], Font.BOLD, Integer.parseInt(htmlArrayThrowNames.get(winner)[2])));		
	}
	
	/**
	 * This method removes the string that lost in a collision. It removes
	 * its name, rectangle, color, and font.
	 * @param index of type int.
	 */
	public void removeThrow(int index){
		htmlArrayThrowNames.remove(index);
		throwRectangle.remove(index);
		colors.remove(index);
		throwFont.remove(index);
	}

	private String htmlThrowName;
	private int throwX, throwY; 
	private Font specificFont;
	private int htmlDelay;
	private int htmlGameOverTimer;
	private int gameTimer;

	private Timer appletTimer;
	
	private ArrayList<String[]> htmlArrayThrowNames = new ArrayList<String[]>();
	private Color brown = new Color(165, 42, 42);
	private ArrayList<Color> colors = new ArrayList<Color>();
	private ArrayList<Font> throwFont = new ArrayList<Font>();
	private ArrayList<Rectangle2D> throwRectangle = new ArrayList<Rectangle2D>();

	private Decider decider = new Decider(this);
}
