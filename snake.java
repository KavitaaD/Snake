package snake;

import javax.swing.JFrame;

@SuppressWarnings("serial")
public class snake extends JFrame {
	  public snake() {
	    	add(new board());
	    	setTitle("Snake");
	        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        setSize(440, 340);
	        setResizable(false);
	        setVisible(true);
	        setLocationRelativeTo(null);
	        }
	        public static void main(String[] args) {
	                new snake();
	        
	       }
}