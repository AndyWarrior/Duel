import java.util.*;
import java.awt.*;
import java.awt.event.*;

public class HUD {
	private GamePanel gp;
	private final int ID;
	private int health;
	private int ammo;
	private int wType;
	private int gren;
	private int kills;
	private Color pColor;
	
	public HUD(GamePanel gp, int id) {
		this.gp = gp;
		ID = id;
		update();
	}
	
	public void update() {
		Player pp = gp.players.elementAt(ID);
		health = pp.getHealth();
		ammo = pp.getAmmo();
		wType = pp.getWeapon();
		gren = pp.getGrenades();
		pColor = pp.getColor();
		//kills = pp.getKills();
	}
	
	//Repaint method
	public void paintHere(Graphics g){
		update();
		int r = pColor.getRed();
		int gr = pColor.getGreen();
		int b = pColor.getBlue();
		Font f = new Font("Impact", Font.PLAIN, 36);
		Graphics2D g2d = (Graphics2D)g;
		Color wb = new Color(255,255,255,210);
		Color bb = new Color(0,0,0,210);
		Color c = new Color(r,gr,b,160);
		g2d.setColor(wb);
		g2d.drawRect(25,450,250,100);
		g2d.setColor(c);
		g2d.fillRect(25,450,250,100);
		g2d.setFont(f);
		g2d.setColor(wb);
		int offset = (int)(health*0.8);
		g2d.fillRect(70,460+(80-offset),25,offset);
		if (offset > 54) {
			g2d.fillRect(44,486,26,28);
			g2d.fillRect(95,486,26,28);
		} else if (offset <= 54 && offset >= 26) {
			int offset2 = (offset-26);
			g2d.fillRect(44,486+(28-offset2),26,offset2);
			g2d.fillRect(95,486+(28-offset2),26,offset2);
		}
		if (wType == 0) { g2d.drawString("-", 230, 490); } 
		else { g2d.drawString(""+ammo, 230, 490); }
		g2d.drawString(""+gren, 230, 530);
		g2d.setColor(bb);
		if (health >= 100) { g2d.drawString(""+health, 55, 514); }
		else {	g2d.drawString(""+health, 63, 514); }
	}
}