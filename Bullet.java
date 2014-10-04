import java.util.*;
import java.awt.*;
import java.awt.event.*;

public class Bullet {
	private Point location;
	private GamePanel gp;
	private double angle;
	private final int RADIUS = 5;
	private final int BASE_SPEED = 10;
	private final int SPEED;
	private final int PARENT;
	private int damage = 0;
	String enviarbala;
	
	
	//Constructors
	public Bullet(GamePanel gp, int parent, int x, int y, double angle, int speedMod) {
		this.gp = gp;
		PARENT = parent;
		this.angle = angle;
		x = x + (int)(25*Math.cos(angle));
		y = y + (int)(25*Math.sin(angle));
		location = new Point(x,y);
		SPEED = BASE_SPEED + 5*speedMod;
		damage = 10+(speedMod-1)*5;
		
	}
	
	//Gets
	public int getX() {
		return location.x;
	}
	public int getY() {
		return location.y;
	}
	public int getRadius() {
		return RADIUS;
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
			gp.bullets.removeElement(this);
		}
	}
	public void checkHit() {
		for (int i = 0; i < gp.players.size(); i++) {
			if (i != PARENT) {
				Player p = gp.players.elementAt(i);
				int x = (p.getX()-location.x)*(p.getX()-location.x) + (p.getY()-location.y)*(p.getY()-location.y);
				int y = 400;
				if (x <= y) {
					p.decreaseHealth(damage,PARENT);
					gp.bullets.removeElement(this);
				}
			}
		}
	}
	//Repaint method
	public void paintHere(Graphics g,int xOff, int yOff){
		/*if(gp.depende==1){
			for(int i=0; i<gp.bullets.size(); i++){
			enviarbala="";
				enviarbala="B"+"/"+gp.bullets.elementAt(i).getX()+"/"+gp.bullets.elementAt(i).getY();
				be.mandarBala(enviarbala);
			}
			}
			*/
		move();
		checkLimit();
		checkHit();
		int r = RADIUS;
		g.setColor(Color.BLACK);
		g.fillOval(xOff+location.x-(r+1),yOff+location.y-(r+1),(r+1)*2,(r+1)*2);
		g.setColor(Color.YELLOW);
		g.fillOval(xOff+location.x-r,yOff+location.y-r,r*2,r*2);
	}
}