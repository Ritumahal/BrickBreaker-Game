package brickBreaker;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.*;
import javax.swing.JPanel;

import javax.swing.Timer;

public class GamePlay extends JPanel implements KeyListener, ActionListener {
	private boolean play = false;
	private int score = 0;
	private int totalBricks = 21;
	private Timer timer;// set time of ball ,how fast it should moveee!!
	private int delay = 8;// given to timer
	private int playerX = 310;// starting point of slider
	private int ballPosX = 120;// starting position for ball for X
	private int ballPosY = 350;// starting position for ball for Y
	// setting direction of ball
	private int ballXdir = -1;
	private int ballYdir = -2;
	private MapGenerator map;

	public GamePlay() {
		map=new MapGenerator(3,7);
		addKeyListener(this);// keyListener for Arrows moving
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);
		timer = new Timer(delay, this);
		timer.start();
	}

	public void paint(Graphics g) {
		// background(here for everything we need to draw)
		g.setColor(Color.black);
		g.fillRect(1, 1, 692, 592);
		
		//drawing map
		
		map.draw((Graphics2D)g);
		
		// borders
		g.setColor(Color.red);
		// (making three Rectangles For Border)
		g.fillRect(0, 0, 3, 592);
		g.fillRect(0, 0, 692, 3);
		g.fillRect(691, 0, 3, 592);
		
		//scores
		g.setColor(Color.orange);
		g.setFont(new Font("serif",Font.BOLD,24));
		g.drawString(""+score,590,30);
		
		// paddle
		g.setColor(Color.green);
		g.fillRect(playerX, 550, 100, 8);

		// ball
		g.setColor(Color.yellow);
		g.fillOval(ballPosX, ballPosY, 20, 20);
		if(totalBricks<=0){
			play=false;
			ballXdir=0;
			ballYdir=0;
			g.setColor(Color.orange);
			g.setFont(new Font("serif",Font.BOLD,35));
			g.drawString("You WON: "+score,260,300);
			
			
			g.setFont(new Font("serif",Font.BOLD,20));
			g.drawString("Press Enter to Restart",230,350);
		}
		if(ballPosY>570){
			play=false;
			ballXdir=0;
			ballYdir=0;
			g.setColor(Color.orange);
			g.setFont(new Font("serif",Font.BOLD,35));
			g.drawString("Game Over,Scores: "+score,190,300);
			
			
			g.setFont(new Font("serif",Font.BOLD,20));
			g.drawString("Press Enter to Restart",230,350);
		}
		g.dispose();
	}

	public void actionPerformed(ActionEvent arg0) {
		timer.start();
		// for ball,to see if its touching left,top,or right
		if (play) {

			if (new Rectangle(ballPosX, ballPosY, 20, 20).intersects(new Rectangle(playerX, 550, 100, 8))) {
				ballYdir = -ballYdir;
			}
			A: for(int i=0;i<map.map.length;i++){
				for(int j=0;j<map.map[0].length;j++){
				if(map.map[i][j]>0){
					//detecting position of ball and brick with respect to the height and width of the brick
					int brickX=j*map.brickWidth+80;
					int brickY=i*map.brickHeight+50;
					int brickWidth=map.brickWidth;
					int brickHeight=map.brickHeight;
					
					
					//Create Rectangle aroun brick
					Rectangle rect=new Rectangle(brickX,brickY,brickWidth,brickHeight);
					//Rectangle around the ball in order to detect intersection
					Rectangle ballRect=new Rectangle(ballPosX,ballPosY,20,20);
					Rectangle brickRect=rect;
					if(ballRect.intersects(brickRect)){
						map.setBricksValue(0, i, j);
						totalBricks--;
						score+=5;
						
						if(ballPosX+19<=brickRect.x || ballPosX+1>=brickRect.x+brickRect.width){
							ballXdir=-ballXdir;
						}
						else{
							ballYdir=-ballYdir;
						}
						break A;
					}
					
				}
				}
			}

			ballPosX += ballXdir;
			ballPosY += ballYdir;
			// for left
			if (ballPosX < 0) {
				ballXdir = -ballXdir;
			}
			// for top
			if (ballPosY < 0) {
				ballYdir = -ballYdir;
			}
			// for right
			if (ballPosX > 670) {
				ballXdir = -ballXdir;
			}
		}

		repaint();// calling the paint method in order to redraw the paddle
					// after the changes of increment or decrement in it!!

	}

	public void keyPressed(KeyEvent e) {
		// moving paddle with arrow keys,Using KeyListener
		// Inside KeyPressed Event,We Are Detecting Arrow Keys
		if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			if (playerX >= 600) {
				playerX = 600;
			} else {
				moveRight();
			}
		}
		if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			if (playerX < 10) {
				playerX = 10;
			} else {
				moveLeft();
			}
		}
		if(e.getKeyCode()==KeyEvent.VK_ENTER){
			if(!play){
				play=true;
				ballPosX=120;
				ballPosY=350;
				ballXdir=-1;
				ballYdir=-2;
				playerX=310;
				score=0;
				totalBricks=21;
				map=new MapGenerator(3,7);
				repaint();
			}
		}

	}

	public void moveRight() {
		play = true;
		playerX += 20;
	}

	public void moveLeft() {
		play = true;
		playerX -= 20;
	}

	public void keyReleased(KeyEvent arg0) {
	}

	public void keyTyped(KeyEvent arg0) {
	}

}
