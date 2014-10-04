import java.util.*;
import java.awt.*;
import java.awt.event.*;

public class Item {
	private GamePanel gp;
	private Point location;
	private final int TYPE;
	private final int ID;
	private boolean paint = true;
	private int s = 40;
	
	public Item(GamePanel gp, int x, int y, int type, int id) {
		this.gp = gp;
		TYPE = type; 
		ID = id;
		location = new Point(x,y);
	}
	public int getX() {
		return location.x;
	}
	public int getY() {
		return location.y;
	}
	public void setLocation(int x, int y) {
		location = new Point(x,y);
	}
	public void resetState() {
		paint = true;
	}
	public void checkHit() {
		for (int i = 0; i < gp.players.size(); i++) {
			Player p = gp.players.elementAt(i);
			int x = p.getX();
			int y = p.getY();
			int r = p.getRadius();
			if ((x >= location.x-r && x <= location.x+s+r) && (y >= location.y-r && y <= location.y+s+r)) {
				System.out.println("Item collected: " + TYPE);
				if (TYPE == 0) {
					p.changeGrenades(3);
					new SoundPlayer("pickup.wav",false,0.8).start();
				}
				if (TYPE == 1) {
					int h = p.getHealth();
					h += 50;
					if (h >= 100) h = 100;
					p.setHealth(h);
					new SoundPlayer("health.wav",false,0.8).start();
				}
				if (TYPE == 2) {
					p.setWeapon(1);
					p.setAmmo(30);
					new SoundPlayer("pickup.wav",false,0.8).start();
				}
				if (TYPE == 3) {
					p.setWeapon(2);
					p.setAmmo(10);
					new SoundPlayer("pickup.wav",false,0.8).start();
				}
				setLocation(-gp.getWidth(),-gp.getHeight());
				paint = false;
				new QueueThread(gp,1,ID,10).start();
			}
		}
	}
	//Repaint method
	public void paintHere(Graphics g,int xOff, int yOff){
		if (paint) {
			checkHit();
			g.setColor(Color.BLACK);
			g.fillRect(xOff+location.x-1,yOff+location.y-1,s+4,s+4);
			if (TYPE == 0) { g.setColor(new Color(0,75,0));}
			if (TYPE == 1) { g.setColor(Color.WHITE);}
			if (TYPE > 1) { g.setColor(Color.DARK_GRAY);}
			g.fillRect(xOff+location.x+1,yOff+location.y+1,s,s);
			Font f = new Font("Impact", Font.PLAIN, 36);
			g.setFont(f);
			String l = "";
			if (TYPE == 0) { g.setColor(Color.YELLOW); l = "G";}
			if (TYPE == 1) { g.setColor(Color.RED); l = "H";}
			if (TYPE == 2)  { g.setColor(Color.YELLOW); l = "M";}
			if (TYPE == 3)  { g.setColor(Color.YELLOW); l = "R";}
			g.drawString(l, xOff+location.x+10, yOff+location.y+35);
		}
	}
}