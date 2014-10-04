import java.util.*;
import java.awt.*;
import java.awt.event.*;

public class Wall {
	private GamePanel gp;
	private Point location;
	private Color color;
	private final int WIDTH;
	private final int HEIGHT;
	
	//Constructors
	public Wall(GamePanel gp, int x, int y, int w, int h, Color c) {
		this.gp = gp;
		location = new Point(x,y);
		WIDTH = w;
		HEIGHT = h;
		color = c;
	}
	public void checkHit() {
		for (int i = 0; i < gp.bullets.size(); i++) {
			Bullet b = gp.bullets.elementAt(i);
			int x = b.getX();
			int y = b.getY();
			int r = b.getRadius();
			if ((x >= location.x-r && x <= location.x+WIDTH+r) && (y >= location.y-r && y <= location.y+HEIGHT+r)) {
				gp.bullets.removeElement(b);
			}
		}
		for (int i = 0; i < gp.rockets.size(); i++) {
			Rocket ro = gp.rockets.elementAt(i);
			if (!ro.getCollision()) {
				int x = ro.getX();
				int y = ro.getY();
				int r = ro.getRadius();
				if ((x >= location.x-r && x <= location.x+WIDTH+r) && (y >= location.y-r && y <= location.y+HEIGHT+r)) {
					ro.setCollision();
					new SoundPlayer("explosion.wav",false,3).start();
				}
			}
		}
		for (int i = 0; i < gp.players.size(); i++) {
			Player p = gp.players.elementAt(i);
			int x = p.getX();
			int y = p.getY();
			int r = p.getRadius();
			if ((x >= location.x-r && x <= location.x+WIDTH+r) && (y >= location.y-r && y <= location.y+HEIGHT+r)) {
				if (x < location.x) 	   { p.setX(location.x-r);		  }
				if (x > location.x+WIDTH)  { p.setX(location.x+WIDTH+r);  }
				if (y < location.y) 	   { p.setY(location.y-r); 	  	  }
				if (y > location.y+HEIGHT) { p.setY(location.y+HEIGHT+r); }
			}
		}
	}
	//Repaint method
	public void paintHere(Graphics g,int xOff, int yOff){
		checkHit();
		g.setColor(color);
		g.fillRect(xOff+location.x,yOff+location.y,WIDTH,HEIGHT);
	}
}