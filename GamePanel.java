import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.net.*;
import java.io.*;

public class GamePanel extends JPanel implements KeyListener, MouseListener, MouseMotionListener {
	Vector<Player> players = new Vector<Player>();
	Vector<Bullet> bullets = new Vector<Bullet>();
	Vector<Rocket> rockets = new Vector<Rocket>();
	Vector<Grenade> grenades = new Vector<Grenade>();
	Vector<Item> items = new Vector<Item>();
	Vector<Wall> walls = new Vector<Wall>();
	private final int MAXPLAYERS;
	private final int PLAYER_NO;
	public final int ARENA_X;
	public final int ARENA_Y;
	public final int[][] SPAWN;
	public final int[][] WALLPOS;
	public final int[][] ITEMPOS;
	private final int[][] RGB = {{255,0,0},{0,0,255},{0,225,0},{255,179,0},{61,255,255},{153,51,255},{50,50,50},{255,255,255}};
	int dirX = 0, dirY = 0;
	int timeHeld = 0;
	int xOffset = 0, yOffset = 0;
	int number, controlar;
	HUD hud;
	WeaponThread wt;
	ReceptorUDP rudp;
	DuelFrame df;
	String ipaddress;
	String enviarbala;
	int depende;
	String enviargranada;
	
	ClientesEnvio ce=new ClientesEnvio(5555,this,rudp,df);
	
	public GamePanel(int num, int pnum, int x, int y, int[][] sp, int[][] wp, int[][] itp, ReceptorUDP rudp, DuelFrame df) {
		ipaddress=df.dirIP;
		this.rudp=rudp;
		this.df=df;
		depende=df.sc;
		MAXPLAYERS = num;
		PLAYER_NO = pnum;
		number=pnum;
		controlar=num;
		ARENA_X = x;
		ARENA_Y = y;
		SPAWN = sp;
		WALLPOS = wp;
		ITEMPOS = itp;
		gameStart();
		setBackground(Color.BLACK);
		addKeyListener(this);
		addMouseListener(this);
		addMouseMotionListener(this);
		setFocusable(true);
		grabFocus();
		//ReceptorThread2 rt2=new ReceptorThread2(6667,this,rudp);
		//ReceptorClienteThread2 rct2=new ReceptorClienteThread2(7777,this,rt2);
		
		if(df.sc==0){
		new ReceptorThread(5555,this,rudp).start();
		new ReceptorThread2(6667,this,rudp).start();
		}
		
		else if(df.sc==1){
		new ClientesEnvio(5555,this,rudp,df).start();
		new ReceptorClienteThread(5555,this,rudp).start();
		new ReceptorClienteThread2(7777,this).start();
		}
		
		new SoundPlayer("big-blue.wav",true,135).start();
		new PaintThread(this).start();
	}
	
	public void gameStart() {
		for (int i = 0; i < MAXPLAYERS; i++) {
			try {
				Color c = new Color(RGB[i][0],RGB[i][1],RGB[i][2]);
				Player pp = new Player(this,SPAWN[i][0],SPAWN[i][1], c, i);
				players.addElement(pp);
			} catch (Exception e) {
				System.out.println("ERROR:" + e.getMessage());
			}
		}
		for (int i = 0; i < WALLPOS.length; i++) {
			try {
				Color c = new Color(0,46,84);
				Wall w = new Wall(this,WALLPOS[i][0],WALLPOS[i][1],WALLPOS[i][2],WALLPOS[i][3],c);
				walls.addElement(w);
			} catch (Exception e) {
				System.out.println("ERROR:" + e.getMessage());
			}
		}
		for (int i = 0; i < ITEMPOS.length; i++) {
			try {
				Item it = new Item(this,ITEMPOS[i][0],ITEMPOS[i][1],ITEMPOS[i][2],i);
				items.addElement(it);
			} catch (Exception e) {
				System.out.println("ERROR:" + e.getMessage());
			}
		}
		hud = new HUD(this, PLAYER_NO);
	}

	public void paintComponent(Graphics g){
		super.paintComponent(g);
		Player pp = players.elementAt(PLAYER_NO);
		if (!pp.getState()) {
			xOffset = (getWidth()/2)-pp.getX();
			yOffset = (getHeight()/2)-pp.getY();
		}
		g.setColor(Color.LIGHT_GRAY);
		g.fillRect(xOffset,yOffset,ARENA_X,ARENA_Y);
		for (int i = 0; i < items.size(); i++) {
			Item it = items.elementAt(i);
			it.paintHere(g,xOffset,yOffset);
		}
		for (int i = 0; i < players.size(); i++) {
			Player p = players.elementAt(i);
			p.paintHere(g,xOffset,yOffset);
		}
		for (int i = 0; i < walls.size(); i++) {
			Wall w = walls.elementAt(i);
			w.paintHere(g,xOffset,yOffset);
		}
		for (int i = 0; i < bullets.size(); i++) {
			Bullet b = bullets.elementAt(i);
			b.paintHere(g,xOffset,yOffset);
		}
		for (int i = 0; i < rockets.size(); i++) {
			Rocket r = rockets.elementAt(i);
			r.paintHere(g,xOffset,yOffset);
		}
		for (int i = 0; i < grenades.size(); i++) {
			Grenade gr = grenades.elementAt(i);
			gr.paintHere(g,xOffset,yOffset);
		}
		hud.paintHere(g);
	}
	/**Mouse Listener Methods**/
	public void mouseClicked(MouseEvent e) {}
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}
	public void mousePressed(MouseEvent e) {
		wt = new WeaponThread(this);
		wt.start();
		Player p = players.elementAt(PLAYER_NO);
		p.setAngle(e.getPoint());
		p.setMouse(e.getPoint());
		if (e.getModifiers() == InputEvent.BUTTON1_MASK) {
			if (p.getWeapon() == 0) {
				Bullet b = new Bullet(this, PLAYER_NO, p.getX(), p.getY(), p.getAngle(), 1);
				bullets.addElement(b);
				new SoundPlayer("pistol-01.wav",false,0.8).start();
				if(df.sc==1){
				enviarbala="";
				enviarbala="B"+"/"+PLAYER_NO+"/"+p.getX()+"/"+p.getY()+"/"+p.getAngle()+"/"+1;
				BulletEnvio be=new BulletEnvio(6667,this,rudp);
				be.mandarBala(enviarbala);
				}
				else if(df.sc==0){
					
					for(int i=1; i<rudp.direcciones.length; i++){
						if(rudp.direcciones[i]==null || rudp.direcciones[i].length()<4) break;
						for(int j=0; j<controlar; j++){
							enviarbala="";
							enviarbala="B"+"/"+PLAYER_NO+"/"+p.getX()+"/"+p.getY()+"/"+p.getAngle()+"/"+1;
							if(i!=j){
							ServidorEnvio2 se2=new ServidorEnvio2(7777);
							se2.enviarTodo2(enviarbala,rudp.direcciones[i]);
							}
						}
					}
				
				}
				
			}
			if (p.getWeapon() == 1 && p.getAmmo() > 0) {
				p.decreaseAmmo();
				Bullet b = new Bullet(this, PLAYER_NO, p.getX(), p.getY(), p.getAngle(), 3);
				bullets.addElement(b);
				new SoundPlayer("Colt45.wav",false,0.8).start();
				if(df.sc==1){
				enviarbala="";
				enviarbala="B"+"/"+PLAYER_NO+"/"+p.getX()+"/"+p.getY()+"/"+p.getAngle()+"/"+3;
				BulletEnvio be=new BulletEnvio(6667,this,rudp);
				be.mandarBala(enviarbala);
				}
				else if(df.sc==0){
				
				
					for(int i=1; i<rudp.direcciones.length; i++){
						if(rudp.direcciones[i]==null || rudp.direcciones[i].length()<4) break;
						for(int j=0; j<controlar; j++){
							enviarbala="";
							enviarbala="B"+"/"+PLAYER_NO+"/"+p.getX()+"/"+p.getY()+"/"+p.getAngle()+"/"+3;
							if(i!=j){
							ServidorEnvio2 se2=new ServidorEnvio2(7777);
							se2.enviarTodo2(enviarbala,rudp.direcciones[i]);
							}
						}
					}
				
				}
			}
			if (p.getWeapon() == 2 && p.getAmmo() > 0) {
				p.decreaseAmmo();
				Rocket r = new Rocket(this, PLAYER_NO, p.getX(), p.getY(), p.getAngle());
				rockets.addElement(r);
				new SoundPlayer("rocket.wav",false,3).start();
				if(df.sc==1){
				enviarbala="";
				enviarbala="R"+"/"+PLAYER_NO+"/"+p.getX()+"/"+p.getY()+"/"+p.getAngle();
				BulletEnvio be=new BulletEnvio(6667,this,rudp);
				be.mandarBala(enviarbala);
				}
				else if(df.sc==0){
					
					for(int i=1; i<rudp.direcciones.length; i++){
						if(rudp.direcciones[i]==null || rudp.direcciones[i].length()<4) break;
						for(int j=0; j<controlar; j++){
							enviarbala="";
							enviarbala="R"+"/"+PLAYER_NO+"/"+p.getX()+"/"+p.getY()+"/"+p.getAngle();
							if(i!=j){
							ServidorEnvio2 se2=new ServidorEnvio2(7777);
							se2.enviarTodo2(enviarbala,rudp.direcciones[i]);
							}
						}
					}
				
				}
				
				
			}
			if (p.getAmmo() <= 0) {
				p.setWeapon(0);
			}
			
		}
	}
	public void mouseReleased(MouseEvent e) {
		wt.endNow();
		Player p = players.elementAt(PLAYER_NO);
		p.setAngle(e.getPoint());
		p.setMouse(e.getPoint());
		int speedMod = timeHeld/3;
		if (speedMod >= 15) { speedMod = 15;}
		if (e.getModifiers() == InputEvent.BUTTON3_MASK && p.getGrenades() > 0) {
			p.changeGrenades(-1);
			Grenade g = new Grenade(this, PLAYER_NO, p.getX(), p.getY(), p.getAngle(), speedMod);
			grenades.addElement(g);
			if(df.sc==1){
				enviargranada="";
				enviargranada="G"+"/"+PLAYER_NO+"/"+p.getX()+"/"+p.getY()+"/"+p.getAngle()+"/"+speedMod;
				BulletEnvio be=new BulletEnvio(6667,this,rudp);
				be.mandarBala(enviargranada);
				}
			else if(df.sc==0){
				
				for(int i=1; i<rudp.direcciones.length; i++){
						if(rudp.direcciones[i]==null || rudp.direcciones[i].length()<4) break;
						for(int j=0; j<controlar; j++){
							enviargranada="";
							enviargranada="G"+"/"+PLAYER_NO+"/"+p.getX()+"/"+p.getY()+"/"+p.getAngle()+"/"+speedMod;
							if(i!=j){
							ServidorEnvio2 se2=new ServidorEnvio2(7777);
							se2.enviarTodo2(enviargranada,rudp.direcciones[i]);
							}
						}
					}

			}
			
		}
	}
	/**Mouse Motion Listener Methods**/
	public void mouseDragged(MouseEvent e) {
		Player p = players.elementAt(PLAYER_NO);
		p.setAngle(e.getPoint());
		p.setMouse(e.getPoint());
	}
	public void mouseMoved(MouseEvent e) {
		Player p = players.elementAt(PLAYER_NO);
		p.setAngle(e.getPoint());
		p.setMouse(e.getPoint());
	}
	/**Key Listener Methods**/
	public void keyTyped(KeyEvent e) {}
	public void keyPressed(KeyEvent e) {
		int k = e.getKeyCode();
		if (k == 87 || k == 38) {dirY = -1;}
		if (k == 83 || k == 40) {dirY = 1;}
		if (k == 68 || k == 39) {dirX = 1;}
		if (k == 65 || k == 37) {dirX = -1;}
		Player p = players.elementAt(PLAYER_NO);
		p.setDirection(dirX, dirY);
	}
	public void keyReleased(KeyEvent e) {
		int k = e.getKeyCode();
		if (k == 87 || k == 38) {dirY = 0;}
		if (k == 83 || k == 40) {dirY = 0;}
		if (k == 68 || k == 39) {dirX = 0;}
		if (k == 65 || k == 37) {dirX = 0;}
		Player p = players.elementAt(PLAYER_NO);
		p.setDirection(dirX, dirY);
	}
}