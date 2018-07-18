import java.awt.Color;

import javax.swing.JFrame;

public class Snake {

	public static void main(String[] args) {
	
		//create new frame
		JFrame frame = new JFrame();
		
		//obj to get inside Gameplay class
		Gameplay gameplay = new Gameplay();
		
		//add obj gameplay to obj JFrame
		frame.add(gameplay);
		
		//frame features
		frame.setBounds(10,10, 905, 700);
		frame.setTitle("Snake--->");
		frame.setBackground(Color.DARK_GRAY);
		frame.setResizable(false);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			
	
		
		
		
	}
	
	

}
