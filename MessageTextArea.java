import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.color.*;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class MessageTextArea extends JTextArea{
	private String message = new String("Welcome to my dictionary!");
	//The x-coordinate where the message is displayed
	private int xCoordinate = 50;
	//the y-coordinate where the message is displayed
	private int yCoordinate = 60;
	//Indicate whether the message is displayed in the center
	private boolean centered;
	//The interval for moving the message horizontally and vertically
	private int interval = 10;
	//Construct with default properties
	public MessageTextArea(){
		setEditable(false);//Avoid editing
		setLineWrap(true);
		setWrapStyleWord(true);
		//JScrollPane scrollPane = new JScrollPane(this);
		setBackground(Color.LIGHT_GRAY);
		setFont(new Font("楷体",Font.PLAIN,25));
	}
	
	public MessageTextArea(String message,int x,int y){
		super(message,x,y);
		setEditable(false);//Avoid editing
		setWrapStyleWord(true);
		//JScrollPane scrollPane = new JScrollPane(this);
		setBackground(Color.YELLOW);
		setFont(new Font("楷体",Font.PLAIN,20));
	}
	public MessageTextArea(String message){
		super(message);
		setEditable(false);//Avoid editing
		setWrapStyleWord(true);
		//JScrollPane scrollPane = new JScrollPane(this);
		setBackground(Color.YELLOW);
		setFont(new Font("楷体",Font.PLAIN,20));
	}
	
	public void setMessage(String message){
		this.message = message;
		repaint();
	}
	
	public int getXCoordinate(){
		return xCoordinate;
	}
	
	public int getYCoordinate(){
		return yCoordinate;
	}
	
	public void setXCoordinate(int x){
		this.xCoordinate = x;
		repaint();
	}
	
	public void setYCoordinate(int y){
		this.yCoordinate = y;
		repaint();
	}
	
	public boolean isCentered(){
		return centered;
	}
	
	public void setCentered(boolean centered){
		this.centered = centered;
		repaint();
	}
	
	public int getInterval(){
		return interval;
	}
	
	public void setInterval(int interval){
		this.interval = interval;
		repaint();
	}
	//Paint the message
	protected void paintComponent(Graphics g){
		super.paintComponent(g);
		
		if(centered){
			//get font metrics for the current font
			FontMetrics fm = g.getFontMetrics();
			
			//Find the center location to display
			int stringWidth = fm.stringWidth(message);
			int stringAscent = fm.getAscent();
			//Get the position of the leftmost character in the baseline
			xCoordinate = getWidth()/2 - stringWidth/2;
			yCoordinate = getHeight()/2 + stringAscent/2;
		}
		
		g.drawString(message,xCoordinate,yCoordinate);
		
	}
	
	//Move the message left
	public void moveLeft(){
		xCoordinate -= interval;
		repaint();
	}
	
	//Move the message right
	public void moveRight(){
		xCoordinate += interval;
		repaint();
	}
	
	//Move the message up
	public void moveUp(){
		yCoordinate -= interval;
	}
	
	//Move the message down
	public void moveDown(){
		yCoordinate += interval;
	}
	
}

