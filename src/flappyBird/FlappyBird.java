package flappyBird;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.Timer;
import javax.swing.text.Highlighter.Highlight;
import javax.swing.JFrame;

public class FlappyBird implements ActionListener, MouseListener, KeyListener {
	
	public static FlappyBird flappyBird;
	
	public final int WIDTH = 1000, HEIGHT = 800;
	
	public Renderer renderer;
	
	public Rectangle bird;
	
	public ArrayList<Rectangle> columns;
	
	public Random rand;
	
	public int ticks,yMotion,score;
	
	public boolean gameOver , started;
	
	public FlappyBird() {
		JFrame jframe = new JFrame();
		renderer= new Renderer();
		rand = new Random();
		Timer timer = new Timer(20, this);
		
		jframe.add(renderer);
		jframe.setTitle("Flappy Bird");
		jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jframe.setSize(WIDTH,HEIGHT);
		jframe.addKeyListener(this);
		jframe.addMouseListener(this);
		jframe.setResizable(false);
		jframe.setVisible(true);		
		
		bird = new Rectangle(WIDTH/2 -10, HEIGHT/2-10,20,20);
		columns = new ArrayList<Rectangle>();
		
		addColumn(true);
		addColumn(true);
		addColumn(true);
		addColumn(true);
		
		timer.start();
	}	
	public void addColumn(boolean start) {
		int space = 300;
		int width = 100;
		int height = 50+rand.nextInt(300);
		if(start) {
			columns.add(new Rectangle(WIDTH+width+columns.size()*300,HEIGHT-height-150,width,height));
			columns.add(new Rectangle(WIDTH+ width+(columns.size()-1)*300,0,width,HEIGHT-height-space));
		}
		else {
			columns.add(new Rectangle(columns.get(columns.size()-1).x+600,HEIGHT-height-150,width,height));
			columns.add(new Rectangle(columns.get(columns.size()-1).x,0,width,HEIGHT-height-space));
		}
	}
	
	public void paintColumn(Graphics g,Rectangle column) {
		g.setColor(Color.green.darker());
		g.fillRect(column.x, column.y, column.width, column.height);
	}
	
	private void jump() {
		// TODO Auto-generated method stub
		if(gameOver) {
			bird = new Rectangle(WIDTH/2 -10, HEIGHT/2-10,20,20);
			columns.clear();
			yMotion=0;
			score = 0;
			
			addColumn(true);
			addColumn(true);
			addColumn(true);
			addColumn(true);
			gameOver = false;
		}
		
		if(!started) {
			started = true;
		}
		else if (!gameOver) {
			if(yMotion>0) {
				yMotion=0;
			}
			yMotion -=10;
		}
	}
	
	public void actionPerformed(ActionEvent e) {		
				
		int speed = 10;
		
		ticks++;
		if(started) {
			for(int i=0;i<columns.size();i++) {
				Rectangle column = columns.get(i);
				column.x-=speed;
			}
			
			if(ticks%2==0 && yMotion<15) {
				yMotion+=2;
			}
			
			for(int i=0;i<columns.size();i++) {
				Rectangle column = columns.get(i);
				if(column.x+column.width<0) {
					columns.remove(column);
					if(column.y==0) {
						addColumn(false);
					}
				}
			}
			
			bird.y += yMotion;
			
			
			for(Rectangle column : columns) {
				if(column.y==0 && bird.x + bird.width/2 > column.x+column.width/2-10 && bird.x+bird.width/2<column.x+column.width/2+10) {
					score+=5;
				}
				if(column.intersects(bird)) {
					gameOver = true;
					
					if(bird.x<=column.x) {					
						bird.x = column.x-bird.width;
					}
					else {
						if(column.y!=0) {
							bird.y=column.y-bird.height-20;
						}
						else if(bird.y<column.height) {
							bird.y= column.height-20;
						}
					}
					
				}
			}
			
			if(bird.y>HEIGHT-150||bird.y==0) {				
				gameOver = true;
			}	
			
			if(bird.y + yMotion >=HEIGHT-150) {
				bird.y = HEIGHT-150 - bird.height;
			}
			
			
		}
		renderer.repaint();	
	}
	public void repaint(Graphics g) {
		g.setColor(Color.cyan);
		g.fillRect(0, 0, WIDTH, HEIGHT);
		
		g.setColor(Color.orange);
		g.fillRect(0, HEIGHT-150, WIDTH, 150);
		
		g.setColor(Color.green);
		g.fillRect(0, HEIGHT-150, WIDTH, 20);
		
		g.setColor(Color.black);		
		g.fillOval(bird.x,bird.y,bird.width,bird.height);
		
		for(Rectangle column : columns) {
			paintColumn(g,column);
		}
		
		g.setColor(Color.white);
		g.setFont(new Font("Arial",1,100));
		if(!started) {
			g.drawString("Click to start!", 200	, HEIGHT/2);
		}
		if(gameOver) {
			g.drawString("Game Over!", 200, HEIGHT/2-100);
			g.drawString("Click to restart!", 100, 500);
		}
		if(!gameOver&& started) {
			g.drawString(String.valueOf(score),100,100);
		}
		
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		jump();
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		if(e.getKeyCode()==KeyEvent.VK_SPACE) {
			jump();
		}
		
	}
	
	public static void main(String[] args) {
		flappyBird = new FlappyBird();
		
	}




	

}
