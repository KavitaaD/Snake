package snake;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.Timer;

@SuppressWarnings("serial")
public class board extends JPanel implements ActionListener {
	
	private final int width = 320;
	private final int height = 300;
	public final static int speed = 10;
	private int delay = 60;
	private int key_speed = 0;
	public final static int rand_pos = 20;
	private final int all_dot = 900;
	
	private int game = 0;
	private boolean arrowRight;
	private boolean arrowDown;
	private boolean arrowLeft;
	private boolean arrowUp;
	
	private boolean right2;
	private boolean down2;
	private boolean left2;
	private boolean up2;
	
	public static boolean multi = false;
	
	private int x[] = new int[all_dot];
	private int y[] = new int[all_dot];
	private int x2[] = new int[all_dot];
	private int y2[] = new int[all_dot];
	//private int apple_x,apple_y;
	//private int alma_x2,alma_y2;
	private int dots,dots2;
        private int apple1,apple2;
	private int max;
	private int player1 = 0;
	private int player2 = 0;
	//private Image food;
	private Image dot;
	private Image dot2;
        private Image greenapp;
	private Image blueapp;
	private Image head;
	private Image head2;
	private Timer timer;
	
	private JButton button;
        private JButton multiplayer;
	private JCheckBox mp;
	
	private static final int applef = 4;
        apple[] food = new apple[applef];
	private boolean pick = false;
	private int appleC;
	private int field[][] = new int[100][100];
	
	static String redDots = "Red Length" ;
	static String yellowDots = "Yellow Length"; 
	static String lred = "red";
	static String lyellow = "yellow";
	static String collect = "Collected";
		
   public board() {
		setBackground(Color.black);
	    addKeyListener(new AL());
	    setLayout(null);
	    button();
	    this.setFocusable(true);
            
            ImageIcon apple1 = new ImageIcon(this.getClass().getResource("apple.png"));
	    greenapp = apple1.getImage();
            ImageIcon apple2 = new ImageIcon(this.getClass().getResource("apple.png"));
	    blueapp = apple2.getImage();
	        
	    ImageIcon dota = new ImageIcon(this.getClass().getResource("dot.png"));
	    dot = dota.getImage();
	    ImageIcon doti = new ImageIcon(this.getClass().getResource("dot2.png"));
	    dot2 = doti.getImage();
	    ImageIcon heads = new ImageIcon(this.getClass().getResource("head.png"));
	    head = heads.getImage();
	    ImageIcon heads2 = new ImageIcon(this.getClass().getResource("head2.png"));
	    head2 = heads2.getImage();
	    setFocusable(true);
	    // start(g);
	    for(int i = 0; i < applef; i++) { food[i] = null; }
	    initGame();
		timer = new Timer(delay, this);
	    timer.start();
	}


	
	public void button() {
		
		button = new JButton( "Single player" );
		button.setEnabled( true );
		button.addActionListener(this);
		button.setBounds(100,100,120,50);
                button.setBackground(Color.GREEN);
		add(button);
                
                multiplayer = new JButton( "Multiplayer" );
		multiplayer.setEnabled( true );
		multiplayer.addActionListener(this);
		multiplayer.setBounds(100,160,120,50);
                multiplayer.setBackground(Color.GREEN);
		add(multiplayer);
		
        name();
	}
	
	public void name() {
		button.setText( "Single player" );
                multiplayer.setText( "Multiplayer" );
	}
	
	public void initGame() {
		dots = dots2 = max = 3;
		appleC = 0;
		player1=0;
		player2=0;
		for(int i=0; i<dots; i++) {
			if(multi) {
				x[i] = 250;
				y[i] = 300 + i*10;
				x2[i] = 50;
				y2[i] = 300 + i*10;
			}
			else {
				x[i] = 50;
				y[i] = 300 + i*10;
				x2[i] = -20;
				y2[i] = -20 - i*10;
			}
		}
		for(int i=3; i<all_dot; i++) {
			x[i] = -10;
			y[i] = -10;
			x2[i] = -20;
			y2[i] = -20;
		}
		for(int i = 0; i < applef; i++) {
			food[i] = new apple();
                        food[i].applecounts();
                }
		
		for(int i = 0; i < 100; i++) {
			for(int j = 0; j < 100; j++) {
				field[i][j] = 0;
			}
		}
		
		//almahely2();
		arrowRight = false;
		arrowDown = true;
		arrowLeft = false;
		arrowUp = false;
		
		if (multi) {
			right2 = false;
			down2 = true;
			left2 = false;
			up2 = false;
		}
	}
	
	public void paint(Graphics g) {
		super.paint(g);
		g.fillRect(320, 0, 5, 340);
		
		if (game == 1) {
			for(int i = 0; i < applef; i++) {
                           g.drawImage(food[i].getImg(), food[i].getX(), food[i].getY(),  this);
                           g.drawImage(food[i].getImg(), food[i].getX(), food[i].getY(),  this);

                        }
                        
			for(int i=0; i < dots; i++) {
				if (i != 0) g.drawImage( dot, x[i], y[i], this);
			}
			for(int i=0; i < dots2; i++) {
				if (i != 0) g.drawImage(dot2, x2[i], y2[i], this);
			}
			g.drawImage(head, x[0], y[0], this);
			g.drawImage(head2, x2[0], y2[0], this);		
			debug(g);
			
			Toolkit.getDefaultToolkit().sync();
	        g.dispose();
		} else if (game == 2) {
			vege(g);
			button.setVisible(true);
                        multiplayer.setVisible(true);
		}
	}
	
	public void vege(Graphics g) {
        String msg = "GAME OVER" ;
         Font small = new Font("Helvetica", Font.BOLD, 18);
         FontMetrics metr = this.getFontMetrics(small);

         g.setColor(Color.white);
         g.setFont(small);
         g.drawString(msg, (width - metr.stringWidth(msg)) / 2-5,
                      height / 2-110);

         
         if (multi) {
         
         
	         if (player1 == player2) g.drawString("Equal score " , (width - metr.stringWidth(msg)) / 2, height / 2-90);
	         if (player1 > player2) g.drawString("RED WON!!! ", (width - metr.stringWidth(msg)) / 2, height / 2-90);
	         if (player1 < player2) g.drawString("YELLOW WON!!!" , (width - metr.stringWidth(msg)) / 2-20, height / 2-90);
                
                 String msg2;
	         msg = Integer.toString(player1);
	         msg2 = Integer.toString(player2);
	         g.drawString("YELLOW" + " :" + msg2 + "   " + "RED" +" :" + msg, (width - metr.stringWidth(msg)) / 2-80, height / 2-70);
         }
                 
         else {
         String msg1 = ("Your score : " + player1);
         g.drawString(msg1, (width - metr.stringWidth(msg1)) / 2-5,
                      height / 2-90);
         }
         
         
               
        }    
                  
         
             
	public void alma() {
		for(int i = 0; i < applef; i++) {
			if ((x[0] == food[i].getX()) && (y[0] == food[i].getY())) {
				if(food[i].getState() == food[i].green) player1++;
				else player1--;
				dots++;
				appleC++;
				do {
					food[i].applecounts();
				}  while(field[food[i].getX()/10][food[i].getY()/10] != 0);
				
				pick = true;
			}
			if ((x2[0] == food[i].getX()) && (y2[0] == food[i].getY())) {
				if(food[i].getState() == food[i].blue) player2++;
				else player2--;
				dots2++;
				appleC++;
				do {
					food[i].applecounts();
				}  while(field[food[i].getX()/10][food[i].getY()/10] != 0);
				
				pick = true;
			}
		}
		//if ((x[0] == alma_x2) && (y[0] == alma_y2)) { dots++; player1++; almahely2(); }
		//if ((x2[0] == alma_x2) && (y2[0] == alma_y2)) { dots2++; player2++; almahely2(); }
	}
	

	
	public void move() {

		
		for(int i=dots; i > 0; i--) {
			x[i] = x[(i - 1)];
			y[i] = y[(i - 1)];
		}
		if (multi) {
			for(int i=dots2; i > 0; i--) {
				x2[i] = x2[(i - 1)];
				y2[i] = y2[(i - 1)];
			}
		}
		if (arrowRight) x[0] -= speed;
		if (arrowLeft) x[0] += speed;
		if (arrowDown) y[0] -= speed;
		if (arrowUp) y[0] += speed;
		
		if (multi) {
			if (down2) y2[0] -= speed;
			if (up2) y2[0] += speed;
			if (left2) x2[0] += speed;
			if (right2) x2[0] -= speed;
		}
		
		int xx,yy;
		if (x[0] <= 0) { xx = 0; }
		else xx = x[0]/10;
		if (y[0] <= 0) { yy = 0; }
		else yy = y[0]/10;
		field[xx][yy] = 1;
		
		if (multi) {
			if (x2[0] <= 0) { xx = 0; }
			else xx = x2[0]/10;
			if (y2[0] <= 0) { yy = 0; }
			else yy = y2[0]/10;
			field[xx][yy] = 2;
		}
		
		if (x[1] <= 0) { xx = 0; }
		else xx = x[1]/10;
		if (y[1] <= 0) { yy = 0; }
		else yy = y[1]/10;
		field[xx][yy] = 0;
		
		if (multi) {
			if (x2[1] <= 0) { xx = 0; }
			else xx = x2[1]/10;
			if (y2[1] <= 0) { yy = 0; }
			else yy = y2[1]/10;
			field[xx][yy] = 0;
		}	
	}	
	
	public void jatek() {
		
		for(int i = dots; i > 0; i--) {
			if ((i > 4) && (x[0] == x[i]) && (y[0] == y[i])) {
				game = 2;
			}
		}
		if (multi) {
			for(int i = dots2; i > 0; i--) {
				if ((i > 4) && (x2[0] == x2[i]) && (y2[0] == y2[i])) {
					game = 2;
				}
			}
		}
                
		if (dots < dots2) max = dots2;
		if (dots == dots2) max = dots2; 
		if (dots > dots2) max = dots;

                
		for (int i = 1; i <= dots; i++) {
			if (x2[0] == x[i] && y2[0] == y[i]) { max = dots; }
		}
		for (int i = 1; i <= dots2; i++) {
			if (x[0] == x2[i] && y[0] == y2[i]) { max = dots2; }
		}
		
		for(int i = max; i > 0; i--) {
			if (x2[0] == x[i] && y2[0] == y[i]) { for (int j=i; j <= dots; j++){ x[j]= -30; y[j]= -50; } dots-=(max-i); dots2+=(max-i); if (dots < 1) dots = 1;  }
			if (x [0] ==x2[i] && y [0] ==y2[i]) { for (int j=i; j <= dots2; j++){ x2[j]= -30; y2[j]= -50; } dots+=(max-i); dots2-=(max-i); if (dots2 < 1) dots2 = 1; }
			
		}
                if (y[0] >= height) {
                game = 2;
                }
                if (y[0] < 0) {
                game = 2;
                 }
                 if (x[0] >= width) {
                 game = 2;
                 }
                if (x[0] < 0 ) {
                game = 2;
                }
                
                if (multi){
                
                    if (y[0] >= height) {
                    game = 2;
                    }
                    if (y[0] < 0) {
                    game = 2;
                    }
                    if (x[0] >= width) {
                    game = 2;
                    }
                    if (x[0] < 0 ) {
                    game = 2;   
                    }
                    
                    if (y2[0] >= height) {
                    game = 2;
                    }
                    if (y2[0] < 0) {
                    game = 2;
                    }
                    if (x2[0] >= width) {
                    game = 2;
                    }
                    if (x2[0] < 0 ) {
                    game = 2;
                    }
                }
        }
		

		
	

	/*public void applecounts() {
		do {
			int r = (int) (Math.random() * rand_pos);
			apple_x = ((r * speed))+100;
			r = (int) (Math.random() * rand_pos);
			apple_y = ((r * speed))+100;
		} while(apple_x == 0 && apple_y == 0);
	}
	
	public void almahely2() {
		do {
			int r = (int) (Math.random() * rand_pos);
			alma_x2 = ((r * speed));
			r = (int) (Math.random() * rand_pos);
			alma_y2 = ((r * speed));
		} while(alma_x2 == 0 && alma_y2 == 0);
	}*/
	
       
        @Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == button) {
			game = 1;
                        multi = false;
			button.setVisible(false);
			multiplayer.setVisible(false);
			initGame();
                }
                if (e.getSource() == multiplayer) {
                    game = 1;
                    multi = true;
                    button.setVisible(false);
                    multiplayer.setVisible(false);
                    initGame();
                }
			
		if (game == 1) {
			//if (x[0] == x2[0] && y[0] == y2[0]) game = false;
			
			if(key_speed == 1) {
				timer.stop();
				delay = 300;
				timer = new Timer(delay, this);
				timer.start();
			}
			if(key_speed == 2) {
				timer.stop();
				delay = 150;
				timer = new Timer(delay, this);
				timer.start();
			}
			if(key_speed == 3){
				timer.stop();
				delay = 60;
				timer = new Timer(delay, this);
				timer.start();
			}    
			move();
			jatek();
               
			//move();
                        alma();
                }
        
        repaint();
    }
        
	
	public static int getPos() {
		return rand_pos;
	}
	
	public static int getSpeed() {
		return speed;
	}
	
	private class AL extends KeyAdapter { 
	     public void keyPressed(KeyEvent e) {
	 		int key = e.getKeyCode();
			if ((key == KeyEvent.VK_LEFT) && (!arrowLeft)) {
				arrowDown = false;
				arrowUp = false;
				arrowRight = true; 
			}
			if ((key == KeyEvent.VK_RIGHT) && (!arrowRight)) {
				arrowDown = false;
				arrowUp = false;
				arrowLeft = true;
			} 
			if ((key == KeyEvent.VK_UP) && (!arrowUp)) {
				arrowLeft = false;
				arrowRight = false;
				arrowDown = true;
			} 
			if ((key == KeyEvent.VK_DOWN) && (!arrowDown)) {
				arrowLeft = false;
				arrowRight = false;
				arrowUp = true;
			}
			
			if (multi) {
				if (key == KeyEvent.VK_A && (!left2)) {
					down2 = false;
					up2 = false;
					right2 = true; 
				}
				if (key == KeyEvent.VK_D && !right2) {
					down2 = false;
					up2 = false;
					left2 = true;
				} 
				if (key == KeyEvent.VK_W && (!up2)) {
					left2 = false;
					right2 = false;
					down2 = true;
				} 
				if (key == KeyEvent.VK_S && (!down2)) {
					left2 = false;
					right2 = false;
					up2 = true;
				}
			}
			else {
				if ((key == KeyEvent.VK_A) && (!arrowLeft)) {
					arrowDown = false;
					arrowUp = false;
					arrowRight = true; 
				}
				if ((key == KeyEvent.VK_D) && (!arrowRight)) {
					arrowDown = false;
					arrowUp = false;
					arrowLeft = true;
				} 
				if ((key == KeyEvent.VK_W) && (!arrowUp)) {
					arrowLeft = false;
					arrowRight = false;
					arrowDown = true;
				} 
				if ((key == KeyEvent.VK_S) && (!arrowDown)) {
					arrowLeft = false;
					arrowRight = false;
					arrowUp = true;
				}
			}
			if (key == KeyEvent.VK_1) key_speed = 1; 
			if (key == KeyEvent.VK_2) key_speed = 2; 
			if (key == KeyEvent.VK_3) key_speed = 3;
			//if (key == KeyEvent.VK_ENTER) enter = 1;
		}
	}
	
	public void debug(Graphics g) {
		//Font small = new Font("Helvetica", Font.BOLD, 14);
        //FontMetrics metr = this.getFontMetrics(small);
        g.setColor(Color.white);
        
        String msg = Integer.toString( dots );
       // String s_x,s_y,s_i;
      /*  for(int i=dots; i > 0; i--) {
        	s_x = Integer.toString(x[i]);
        	s_y = Integer.toString(y[i]);
        	s_i = Integer.toString(i);
        	g.drawString("x", 10 ,i*10);
        	g.drawString(s_i, 18 ,i*10);
        	g.drawString("=", 25 ,i*10);
        	g.drawString(s_x, 35 ,i*10);
        	
        	g.drawString("y", 70 ,i*10);
        	g.drawString(s_i, 78 ,i*10);
        	g.drawString("=", 85 ,i*10);
        	g.drawString(s_y, 95 ,i*10);
        }*/
        g.drawString(redDots + " = " + msg, 331 ,10);
        //g.drawString(msg, 370 ,10);
        msg = Integer.toString(dots2);
        
      /*  for(int i=dots2; i > 0; i--) {
        	s_x = Integer.toString(x2[i]);
        	s_y = Integer.toString(y2[i]);
        	s_i = Integer.toString(i);
        	g.drawString("x", 130 ,i*10);
        	g.drawString(s_i, 138 ,i*10);
        	g.drawString("=", 145 ,i*10);
        	g.drawString(s_x, 155 ,i*10);
        	
        	g.drawString("y", 190 ,i*10);
        	g.drawString(s_i, 198 ,i*10);
        	g.drawString("=", 205 ,i*10);
        	g.drawString(s_y, 215 ,i*10);
        }*/
        
        if (multi) {
	        g.drawString(yellowDots + " = " + msg, 331 ,30);
	       // g.drawString(msg, 370 ,30);
	        
	        msg = Integer.toString(max);
	        g.drawString("Highest" + " = " + msg, 331 ,50);
	        //g.drawString(msg, 370 ,50);
        }
        msg = Integer.toString(player1);
        g.drawString("red" + " = " + msg, 331 ,70);
        //g.drawString(msg, 390 ,70);
        
        if (multi) {
	        msg = Integer.toString(player2);
	        g.drawString("Yellow" + " = " + msg, 331 ,90);
	        //g.drawString(msg, 390 ,90);
        }
        msg = Integer.toString(appleC);
        g.drawString(collect + " = " + msg, 331 ,130);
        //g.drawString(msg, 390 ,130);
        /*int xxx = 0;
        int yyy = 10;
        for (int i = 0; i < 32; i++) {
        	for(int j = 0; j < 32; j++) {
        		msg = Integer.toString(field[j][i]);
                g.drawString(msg, xxx ,yyy);
                xxx+=10;
        	}
        	yyy += 10;
        	xxx = 0;
        }*/
	}
}