import java.util.*;
import java.awt.*;
import java.awt.event.*;

public class Grenade {
	private Point location;
	private GamePanel gp;
	private double angle;
	private int frame = 0;
	private int radius = 7;
	private final int BASE_SPEED = 5;
	private final int SPEED;
	private final int PARENT;
	private boolean collision = false;
	
	//Constructors
	public Grenade(GamePanel gp, int parent, int x, int y, double angle, int speedMod) {
		this.gp = gp;
		PARENT = parent;
		SPEED = BASE_SPEED + speedMod;
		this.angle = angle;
		x = x + (int)(25*Math.cos(angle));
		y = y + (int)(25*Math.sin(angle));
		location = new Point(x,y);
	}
	public void checkHit() {
		for (int i = 0; i < gp.players.size(); i++) {
			Player p = gp.players.elementAt(i);
			int x = (p.getX()-location.x)*(p.getX()-location.x) + (p.getY()-location.y)*(p.getY()-location.y);
			int y = radius*radius;
			if (x <= y) {
				p.decreaseHealth(10,PARENT);
			}
		}
	}
	public void move() {
		Point p = new Point();
		p.x = location.x + (int)(SPEED*Math.cos(angle));
		p.y = location.y + (int)(SPEED*Math.sin(angle));
		location = p;
	}
	public void checkLimit() {
		if (location.x <= 0) 		 {location.x = 0; 			collision = true;}
		if (location.x >= gp.ARENA_X) {location.x = gp.ARENA_X;  collision = true;}
		if (location.y <= 0) 		 {location.y = 0; 			collision = true;}
		if (location.y >= gp.ARENA_Y) {location.y = gp.ARENA_Y;  collision = true;}
	}
	//Repaint method
	public void paintHere(Graphics g,int xOff, int yOff){
		int r=5;
		if (frame <= 30) {
			if (frame <= 10) { r = 7+(frame/3);}
			else { r = 12 - (frame-5)/3;}
			if (!collision) {checkLimit(); move();}
			g.setColor(Color.BLACK);
			g.fillOval(xOff+location.x-(r+1),yOff+location.y-(r+1),(r+1)*2,(r+1)*2);
			Color c = new Color(0,75,0);
			g.setColor(c);
			g.fillOval(xOff+location.x-r,yOff+location.y-r,r*2,r*2);
		}
		else if (frame <= 60){
			g.setColor(Color.BLACK);
			g.fillOval(xOff+location.x-(r+1),yOff+location.y-(r+1),(r+1)*2,(r+1)*2);
			Color c;
			if ((frame%4)==0) {c = new Color(0,75,0);}
			else { c = new Color(102,255,51);}
			g.setColor(c);
			g.fillOval(xOff+location.x-r,yOff+location.y-r,r*2,r*2);
			if(frame==60){
				new SoundPlayer("explosion.wav",false,3).start();
			}
		}
		else  if (frame <= 80) {
			if (frame%3==0) { checkHit();}
			int fr = frame-60;
			r = fr*4;
			radius = r;
			g.setColor(Color.YELLOW);
			g.fillOval(xOff+location.x-r,yOff+location.y-r,r*2,r*2);
		}
		else { gp.grenades.removeElement(this);}
		frame++;
	}
}