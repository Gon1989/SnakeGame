import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.RenderingHints.Key;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;
import java.util.logging.Logger;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Gameplay extends JPanel implements KeyListener, ActionListener{

	//fields
	private ImageIcon titleImage;
	private int[] snakeXLength = new int[750];
	private int[] snakeYLength = new int[750];
	
	//Enemy
	private ImageIcon enemyImage;
	private Random random = new Random();
	
	private int xpos = random.nextInt(34); // 34 is total number of x positions on horizontal stage
	private int ypos = random.nextInt(23); // 23 is total number of y positions on vertical stage
	
	//Enemy position
	private int[] enemyX = {25,50,75,100,125,150,175,200,225,250,275,300,325,350,375,400,425,450,475,500,
							525,550,575,600,625,650,675,700,25,750,775,800,825,850};
	
	private int[] enemyY = {75,100,125,150,175,200,225,250,275,300,325,350,375,400,425,450,475,500,
			525,550,575,600,625};
	
	
	
	//check what of 4 sides I'm going
	private boolean left = false;
	private boolean right = false;
	private boolean up = false;
	private boolean down = false;
	
	//sprites to get each facing
	private ImageIcon facingRight;
	private ImageIcon facingLeft;
	private ImageIcon facingUp;
	private ImageIcon facingDown;
	private ImageIcon snakeImage;
	
	
	private Timer timer;
	private int delay = 100;
	
	//Snake
	private int snakeLength = 3;
	private int moves = 0;
	private int score = 0;
	
	//constructor
	public Gameplay() { 
		addKeyListener(this);
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);
		timer = new Timer(delay, this);
		timer.start(); //start timer
		
	}


	//method to paint all things
	public void paint(Graphics g) {

		if (moves == 0) {
			
			snakeXLength[2] = 50;
			snakeXLength[1] = 75;
			snakeXLength[0] = 100;
			
			snakeYLength[2] = 100;
			snakeYLength[1] = 100;
			snakeYLength[0] = 100;
		}
		
		
		//draw image title border first
		g.setColor(Color.black);
		
		g.drawRect(24, 10, 851, 55);
		
		//draw title image
		titleImage = new ImageIcon("snaketitle.jpg"); //name of the title file
		titleImage.paintIcon(this, g, 25, 11); //paint the image to the frame

		//draw border for the gameplay
		g.setColor(Color.WHITE);
		
		g.drawRect(24, 74, 851, 577);
		
		//draw background for the gameplay
		g.setColor(Color.BLACK);
		g.fillRect(25, 75, 850, 575);
		
		//set sprites
		facingRight = new ImageIcon("rightmouth.png");
		facingRight.paintIcon(this, g, snakeXLength[0], snakeYLength[0]);
		//loop thru snake to create face
		for (int i = 0; i < snakeLength; i++) {
			if (i == 0 && right == true) { //if right and starting
				facingRight = new ImageIcon("rightmouth.png"); //set sprite to right
				facingRight.paintIcon(this, g, snakeXLength[i], snakeYLength[i]); //paint face sprite in [i]loop
			}
			///LEFT
			if (i == 0 && left == true) { //if left and starting
				facingRight = new ImageIcon("leftmouth.png"); //set sprite to left
				facingRight.paintIcon(this, g, snakeXLength[i], snakeYLength[i]); //paint face sprite in [i]loop
			}
			//UP
			if (i == 0 && up == true) { //if up and starting
				facingRight = new ImageIcon("upmouth.png"); //set sprite to up
				facingRight.paintIcon(this, g, snakeXLength[i], snakeYLength[i]); //paint face sprite in [i]loop
			}
			//DOWN
			if (i == 0 && down == true) { //if right and down
				facingRight = new ImageIcon("downmouth.png"); //set sprite to down
				facingRight.paintIcon(this, g, snakeXLength[i], snakeYLength[i]); //paint face sprite in [i]loop
			}
			
			//create body
			if (i != 0) { //if is not the face, any other number = body
				snakeImage = new ImageIcon("snakeimage.png"); 
				snakeImage.paintIcon(this, g, snakeXLength[i], snakeYLength[i]); //paint body
			}
			
			
		}
		
		
		//paint enemy
		enemyImage = new ImageIcon("enemy.png");
				
		//check if enemy in horizotal collides with snake length[0] first element(head)
		if ((enemyX[xpos]) == snakeXLength[0] && enemyY[ypos] == snakeYLength[0]) {
			snakeLength++;
			score++; //add to the final score
			xpos = random.nextInt(34);
			ypos = random.nextInt(23);
					
		}
				
		//paint item
		enemyImage.paintIcon(this, g, enemyX[xpos], enemyY[ypos]);
		
		//GAME OVER
		for (int i = 0; i < snakeLength; i++) {
			if (snakeXLength[i] == snakeXLength[0] && snakeYLength[i] == snakeYLength[0]) {
				right = false;
				left = false;
				up = false;
				down = false;
				
				//display game over message
				g.setColor(Color.WHITE);
				g.setFont(new Font("arial", Font.BOLD, 50));
				g.drawString("Game Over", 300, 300);
				
				g.setFont(new Font("arial", Font.BOLD, 20));
				g.drawString("Space to RESTART ", 350, 340);
				
			}
		}
		
		//draw scores
		g.setColor(Color.WHITE);
		g.setFont(new Font("arial", Font.PLAIN, 14));
		g.drawString("Scores: " + score, 780, 30);
		
		//draw length of snake
		g.setColor(Color.WHITE);
		g.setFont(new Font("arial", Font.PLAIN, 14));
		g.drawString("Length: " + snakeLength, 780, 50);
		
		g.dispose();
	}

	//input
	@Override
	public void actionPerformed(ActionEvent e) {
		//start timer
		timer.start();
		
		//right
		if (right) {
			for (int i = snakeLength - 1; i >= 0; i--) { //check for second position and -- to get last one 
				snakeYLength[i + 1] = snakeYLength[i];
			}
			
			for (int i = snakeLength - 1; i >= 0; i--) { 
				if (i == 0) { 
					snakeXLength[i] = snakeXLength[i] + 25; //change position X
				} else {
					snakeXLength[i] = snakeXLength[i - 1]; 
				}
				
				//check for head collision
				if (snakeXLength[i] > 850) { 
					snakeXLength[i] = 25; 
				}
			}
			
			repaint();
		}
		
		//Left
		if (left) {
			for (int i = snakeLength - 1; i >= 0; i--) {
				snakeYLength[i + 1] = snakeYLength[i];
			}
			
			for (int i = snakeLength - 1; i >= 0; i--) { 
				if (i == 0) { 
					snakeXLength[i] = snakeXLength[i] -25; //change position X
				} else {
					snakeXLength[i] = snakeXLength[i - 1]; 
				}
				
				//check for head collision
				if (snakeXLength[i] < 25) {  //if we are < left border
					snakeXLength[i] = 850; 
				}
			}
			
			repaint();
					
		}
		
		//UP
		if (up) {
			for (int i = snakeLength - 1; i >= 0; i--) {
				snakeXLength[i + 1] = snakeXLength[i];
			}
			
			for (int i = snakeLength - 1; i >= 0; i--) { 
				if (i == 0) { 
					snakeYLength[i] = snakeYLength[i] - 25; //change position X
				} else {
					snakeYLength[i] = snakeYLength[i - 1]; 
				}
				
				//check for head collision
				if (snakeYLength[i] < 75) { //bottom 
					snakeYLength[i] = 625; //top of the screen 
				}
			}
			
			repaint();
		}
		
		//DOWN
		if (down) {
			
			for (int i = snakeLength - 1; i >= 0; i--) {
				snakeXLength[i + 1] = snakeXLength[i];
			}
			
			for (int i = snakeLength - 1; i >= 0; i--) { 
				if (i == 0) { 
					snakeYLength[i] = snakeYLength[i] + 25; //change position X
				} else {
					snakeYLength[i] = snakeYLength[i - 1]; 
				}
				
				//check for head collision
				if (snakeYLength[i] > 625) { //bottom 
					snakeYLength[i] = 75; //top of the screen 
				}
			}
			
			repaint();
		}
		
		
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
	
		//Space
		if (e.getKeyCode() == KeyEvent.VK_SPACE) {
			moves = 0;
			score = 0;
			snakeLength = 3;
			repaint();
			
		}
		
		//right
		if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			//snake moves 1 position
			moves += 1;
			right = true;
			
			if (!left) {
				right = true;
			} else {
				right = false;
				left = true;
			}
			up = false;
			down = false;
		}
		
		//left
		if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			//snake moves 1 position
			moves += 1;
			left = true;
					
			if (!right) {
				left = true;
			} else {
				left = false;
				right = true;
			}
				up = false;
				down = false;
			}
		
		//up
		if (e.getKeyCode() == KeyEvent.VK_UP) {
			//snake moves 1 position
			moves += 1;
			up = true;
							
			if (!down) {
				up = true;
			} else {
				up = false;
				down = true;
			}
			
			left = false;
			right = false;
			
		}
		
		//down
				if (e.getKeyCode() == KeyEvent.VK_DOWN) {
					//snake moves 1 position
					moves += 1;
					down = true;
									
					if (!up) {
						down = true;
					} else {
						up = true;
						down = false;
					}
					
					left = false;
					right = false;
					
				}
		
		
		
	}
	

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	
	
	

}




















