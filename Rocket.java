import java.util.*;
import java.awt.*;
import java.awt.event.*;

public class Rocket {
	private Point location;
	private GamePanel gp;
	private double angle;
	private int radius = 8;
	private int frame = 0;
	private final int SPEED = 10;
	private final int PARENT;
	private boolean collision = false;
	
	//Constructors
	public Rocket(GamePanel gp, int parent, int x, int y, double angle) {
		this.gp = gp;
		PARENT = parent;
		this.angle = angle;
		x = x + (int)(25*Math.cos(angle));
		y = y + (int)(25*Math.sin(angle));
		location = new Point(x,y);
	}
	
	//Gets
	public int getX() {
		return location.x;
	}
	public int getY() {
		return location.y;
	}
	public int getRadius() {
		return radius;
	}
	public boolean getCollision(){
		return collision;
	}
	public void setCollision(){
		collision = true;
	}
	//moves to a specific location
	public void move() {
		Point p = new Point();
		p.x = location.x + (int)(SPEED*Math.cos(angle));
		p.y = location.y + (int)(SPEED*Math.sin(angle));
		location = p;
	}
	public void checkLimit() {
		if ((location.x < 0 || location.x > gp.ARENA_X) || (location.y < 0 || location.y > gp.ARENA_Y)) {
			collision = true;
			new SoundPlayer("explosion.wav",false,3).start();
		}
	}
	public void checkHit() {
		for (int i = 0; i < gp.players.size(); i++) {
			Player p = gp.players.elementAt(i);
			int x = (p.getX()-location.x)*(p.getX()-location.x) + (p.getY()-location.y)*(p.getY()-location.y);
			int y;
			if (!collision) { y = 400;}
			else { y = radius*radius;}
			if (x <= y && !collision) {
				p.decreaseHealth(20,PARENT);
				collision = true;
				new SoundPlayer("explosion.wav",false,3).start();
			}
			if (x <= y && collision) {
				p.decreaseHealth(10,PARENT);
			}
		}
	}
	//Repaint method
	public void paintHere(Graphics g,int xOff, int yOff){
		int r = 0;
		if (!collision) {
			move();
			checkLimit();
			checkHit();
			r = radius;
			g.setColor(Color.BLACK);
			g.fillOval(xOff+location.x-(r+1),yOff+location.y-(r+1),(r+1)*2,(r+1)*2);
			g.setColor(Color.GRAY);
			g.fillOval(xOff+location.x-r,yOff+location.y-r,r*2,r*2);
		}
		else if (frame <= 20){
			if (frame%3==0) { checkHit();}
			r = frame*4;
			radius = r;
			g.setColor(Color.YELLOW);
			g.fillOval(xOff+location.x-r,yOff+location.y-r,r*2,r*2);
			frame++;
		}
		else { gp.rockets.removeElement(this);}
	}
}