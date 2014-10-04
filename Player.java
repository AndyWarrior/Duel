import java.util.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;

public class Player {
	private Point location;
	private GamePanel gp;
	private double angle = 0;
	private final int SPEED = 4;
	private final int PLAYER_NO;
	private Color color;
	private int dirX = 0;
	private int dirY = 0;
	private int radius = 20;
	private int health = 100;
	private int ammo = 0;
	private int weaponType = 0;
	private int grenadeAmmo = 3;
	private int kills = 0;
	private int deaths = 0;
	private int lasthit;
	private boolean dead = false;
	private Point puntoMouse;
	
	//Constructors
	//Specific
	public Player(GamePanel gp, int x, int y, Color c, int pnum) {
		this.gp = gp;
		color = c;
		location = new Point(x,y);
		PLAYER_NO = pnum;
		lasthit = pnum;
		puntoMouse= new Point(500,100);
	}
	
	//Gets
	public int getX() {
		return location.x;
	}
	public int getY() {
		return location.y;
	}
	public double getAngle() {
		return angle;
	}
	public int getRadius() {
		return radius;
	}
	public int getHealth() {
		return health;
	}
	public int getAmmo() {
		return ammo;
	}
	public int getWeapon() {
		return weaponType;
	}
	public int getGrenades(){
		return grenadeAmmo;
	}
	public Color getColor() {
		return color;
	}
	public boolean getState() {
		return dead;
	}
	public void setX(int x){
		location.x = x;
	}
	public void setY(int y){
		location.y = y;
	}
	public void setMouse(Point po){
		puntoMouse=po;
		
	}
	
	public void setMousex(int x){
		puntoMouse.x = x;
	}
	public void setMousey(int y){
		puntoMouse.y = y;
	}
	public int getMousex(){
		
		
		return puntoMouse.x;
	}
	public int getMousey(){
		
		
		return puntoMouse.y;
	}
	
	public void decreaseHealth(int x, int enemy) {
		health -= x;
		System.out.println("Player_"+(PLAYER_NO+1)+" got hit by Player_"+(enemy+1));
		if (enemy != PLAYER_NO) { lasthit = enemy;}
		if (health <= 0) {
			dead = true;
			setX(-gp.getWidth()-200);
			setY(-gp.getHeight()-200);
			new QueueThread(gp,0,PLAYER_NO,5).start();
			if (lasthit != PLAYER_NO) {
				System.out.println("Player_"+(PLAYER_NO+1)+" got killed by Player_"+(lasthit+1));
				Player pp = gp.players.elementAt(lasthit);
				pp.addKill();
				deaths++;
			} else {
				System.out.println("Player_"+(PLAYER_NO+1)+" suicided");
				deaths++;
			}
			System.out.println("Player_"+(PLAYER_NO+1)+" K/D "+ kills + ":" + deaths);
		}
	}
	public void setHealth(int x) {
		health = x;
	}
	public void setGrenades(int x) {
		grenadeAmmo = x;
	}
	public void respawn(int x, int y) {
		health = 100;
		grenadeAmmo = 3;
		dead = false;
		location = new Point(x,y);
	}
	public void addKill() {
		kills++;
	}
	public void setWeapon(int x) {
		weaponType = x;
	}
	public void setAmmo(int x) {
		ammo = x;
	}
	public void decreaseAmmo() {
		ammo--;
	}
	//set Angle in relation to where the mouse is
	public void setAngle(Point p) {
		int x = p.x-(gp.getWidth()/2);
		int y = p.y-(gp.getHeight()/2);
		double ang = Math.atan2(y,x);
		angle = ang;
	}
	
	public void setAngulito(int x, int y){
		x = x-(gp.getWidth()/2);
		y =y-(gp.getHeight()/2);
		double ang = Math.atan2(y,x);
		angle = ang;
	}
	
	public void setDirection(int x, int y) {
		dirX = x;
		dirY = y;
	}
	public void changeGrenades(int x) {
		grenadeAmmo += x;
	}
	//moves to a specific location
	public void move(int x, int y) {
		Point p = new Point();
		p.x = location.x + x*SPEED;
		p.y = location.y + y*SPEED;
		location = p;
	}
	public void checkLimit() {
		if (location.x < 0) 		 {location.x = 0;}
		if (location.x > gp.ARENA_X) {location.x = gp.ARENA_X;}
		if (location.y < 0) 		 {location.y = 0;}
		if (location.y > gp.ARENA_Y) {location.y = gp.ARENA_Y;}
	}
	//Repaint method
	public void paintHere(Graphics g,int xOff, int yOff){
		if (!dead) {
			move(dirX,dirY);
			checkLimit();
			Graphics2D g2d = (Graphics2D)g;
			int r = radius;
			int o1 = (int)(25*Math.cos(angle));
			int o2 = (int)(25*Math.sin(angle));
			Line2D line = new Line2D.Double(xOff+location.x, yOff+location.y, xOff+location.x+o1, yOff+location.y+o2);
			g2d.setColor(Color.BLACK);
			g2d.setStroke(new BasicStroke(10));
			g2d.draw(line);
			g2d.fillOval(xOff+location.x-(r+1),yOff+location.y-(r+1),(r+1)*2,(r+1)*2);
			g2d.setColor(color);
			g2d.fillOval(xOff+location.x-r,yOff+location.y-r,r*2,r*2);
		}
	}
}