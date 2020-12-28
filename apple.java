package snake;

import java.awt.Image;
import javax.swing.ImageIcon;

public class apple {
	public int apple_x = -20;
	public int apple_y = -20;
	Image food;
	public int state;
	public int icons = 2;	
	private ImageIcon apple;
	//private ImageIcon apple2;
	
	public final int green = 0;
	public final int blue = 1;
	
	public apple() {
		//almahely(); 
	}
	
	public void applecounts() {
		int r = (int ) (Math.random() * board.getPos());
		apple_x = r * board.getSpeed();
		r = (int) (Math.random() * board.getPos());
		apple_y = r * board.getSpeed();
		int s = (int) (Math.random() * icons);
	    if(board.multi) state = s; 
	    else state = 0;
	    
	    if (state == green) { apple = new ImageIcon(this.getClass().getResource("apple.png")); food = apple.getImage(); }
	    if (state == blue) { apple = new ImageIcon(this.getClass().getResource("apple2.png")); food = apple.getImage(); }
	    
	}
	
	public void setX(int x) {
		apple_x = x;
	}
	public void sety(int y) {
		apple_y = y;
	}
	public int getX() {
		return apple_x;
	}
	public int getY() {
		return apple_y;
	}
	public Image getImg() {
		return food;
	}        
	public int getState() {
		return state;
	}
}