import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;

public class LevelCreator extends JPanel implements MouseListener, KeyListener{
	int ARENA_X = 1000;
	int ARENA_Y = 600;
	int posX = 0;
	int posY = 0;
	int dirX = 0;
	int dirY = 0;
	int xOff;
	int yOff;
	int type = 0;
	int lastSet = 0;
	String lvlname = "default";
	Point start;
	int[][] SPAWN = {{30,300},{30,30},{940,540},{30,540},{940,30},{500,30},{500,540},{940,300}};
	int[][] WALLPOS = {{0,70,100,20},{400,0,20,100},{400,400,50,50}};
	int[][] ITEMPOS = {{480,380,0},{200,380,1},{800,380,2},{400,200,3}};
	Scanner t = new Scanner(System.in);
	Vector<String> spawns = new Vector<String>();
	Vector<String> walls = new Vector<String>();
	Vector<String> items = new Vector<String>();
	
	public LevelCreator() {
		lvlname = t.nextLine();
		int x = t.nextInt();
		int y = t.nextInt();
		ARENA_X = x;
		ARENA_Y = y;
		setSize(1000,600);		
		addMouseListener(this);
		addKeyListener(this);
		setFocusable(true);
		setBackground(Color.BLACK);
		grabFocus();
		new PaintThread(this).start();
	}
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		moveCamera();
		xOff = (getWidth()/2)-posX;
		yOff = (getHeight()/2)-posY;
		g.setColor(Color.LIGHT_GRAY);
		g.fillRect(xOff,yOff,ARENA_X,ARENA_Y);
		for (int i = 0; i < spawns.size(); i++) {
			String[] data = (spawns.elementAt(i)).split("/");
			int r = 5;
			int[] intData = {Integer.parseInt(data[0]),Integer.parseInt(data[1])};
			g.setColor(Color.WHITE);
			g.fillOval(xOff+intData[0]-r,yOff+intData[1]-r,r*2,r*2);
		}
		for (int i = 0; i < items.size(); i++) {
			String[] data = (items.elementAt(i)).split("/");
			int[] intData = {Integer.parseInt(data[0]),Integer.parseInt(data[1])};
			g.setColor(Color.GREEN);
			g.fillRect(xOff+intData[0],yOff+intData[1],40,40);
			g.setColor(Color.WHITE);
			Font f = new Font("Impact", Font.PLAIN, 36);
			g.setFont(f);
			g.drawString(data[2],xOff+intData[0]+10,yOff+intData[1]+35);
		}
		for (int i = 0; i < walls.size(); i++) {
			String[] data = (walls.elementAt(i)).split("/");
			int[] intData = {Integer.parseInt(data[0]),Integer.parseInt(data[1]),Integer.parseInt(data[2]),Integer.parseInt(data[3])};
			g.setColor(Color.BLUE);
			g.fillRect(xOff+intData[0],yOff+intData[1],intData[2],intData[3]);
		}
	}
	
	public void saveLevel() {
		try {
			System.out.println("creating level...");
			RandomAccessFile newFile = new RandomAccessFile(lvlname+".dat","rw");
			newFile.seek(0);
			System.out.println("saving size...");
			newFile.writeInt(ARENA_X);
			newFile.writeInt(ARENA_Y);
			System.out.println("saving spawn points...");
			newFile.writeInt(SPAWN.length);
			newFile.writeInt(2);
			for (int i = 0; i < SPAWN.length; i++) {
				for (int j = 0; j < 2; j++) {
					newFile.writeInt(SPAWN[i][j]);
				}
			}
			System.out.println("saving wall locations...");
			newFile.writeInt(WALLPOS.length);
			newFile.writeInt(4);
			for (int i = 0; i < WALLPOS.length; i++) {
				for (int j = 0; j < 4; j++) {
					newFile.writeInt(WALLPOS[i][j]);
				}
			}
			System.out.println("saving item locations...");
			newFile.writeInt(ITEMPOS.length);
			newFile.writeInt(3);
			for (int i = 0; i < ITEMPOS.length; i++) {
				for (int j = 0; j < 3; j++) {
					newFile.writeInt(ITEMPOS[i][j]);
				}
			}
			System.out.println("Level Saved: " + lvlname+".dat");
		} catch(Exception e) 
		{ System.out.println(e.getMessage());}
	}
	
	public void settup() {
		System.out.println("setting up spawn locations...");
		int x = spawns.size();
		int y = 2;
		SPAWN = new int[x][y];
		for (int i = 0; i < SPAWN.length; i++) {
			String[] data = (spawns.elementAt(i)).split("/");
			for (int j = 0; j < 2; j++) {
				SPAWN[i][j] = Integer.parseInt(data[j]);
			}
		}
		x = walls.size();
		y = 4;
		System.out.println("setting up wall locations...");
		WALLPOS = new int[x][y];
		for (int i = 0; i < WALLPOS.length; i++) {
			String[] data = (walls.elementAt(i)).split("/");
			for (int j = 0; j < 4; j++) {
				WALLPOS[i][j] = Integer.parseInt(data[j]);
			}
		}
		x = items.size();
		y = 3;
		System.out.println("setting up item locations...");
		ITEMPOS = new int[x][y];
		for (int i = 0; i < ITEMPOS.length; i++) {
			String[] data = (items.elementAt(i)).split("/");
			for (int j = 0; j < 3; j++) {
				ITEMPOS[i][j] = Integer.parseInt(data[j]);
			}
		}
		System.out.println("Level ready");
	}
	
	public void mouseClicked(MouseEvent e) {}
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}
	public void mousePressed(MouseEvent e) {
		start = e.getPoint();
		if (e.getModifiers() == InputEvent.BUTTON2_MASK) {
			String s = "" + (start.x-xOff) + "/" + (start.y-yOff);
			System.out.println("Create a spawn at: " + (start.x-xOff) + ":" + (start.y-yOff));
			spawns.addElement(s);
			lastSet = 1;
		}
		if (e.getModifiers() == InputEvent.BUTTON3_MASK) {
			System.out.println("Create an item type_"+type+" at : " + (start.x-xOff) + ":" + (start.y-yOff));
			String s = "" + (start.x-xOff) + "/" + (start.y-yOff) + "/" + type;
			items.addElement(s);
			lastSet = 2;
		}
	}
	public void mouseReleased(MouseEvent e) {
		Point p = e.getPoint();
		if (e.getModifiers() == InputEvent.BUTTON1_MASK && (p.x-start.x)!=0 && (p.y-start.y)!=0) {
			System.out.println("End a wall at: " + (p.x-start.x) + ":" + (p.y-start.y));
			String s = "" + (start.x-xOff) + "/" + (start.y-yOff) + "/" + (p.x-start.x) + "/" + (p.y-start.y);
			walls.addElement(s);
			lastSet = 0;
		}
	}
	/**Key Listener Methods**/
	public void keyTyped(KeyEvent e) {}
	public void keyPressed(KeyEvent e) {
		int k = e.getKeyCode();
		if (k == 87 || k == 38) {dirY = -1;}
		if (k == 83 || k == 40) {dirY = 1;}
		if (k == 68 || k == 39) {dirX = 1;}
		if (k == 65 || k == 37) {dirX = -1;}
	}
	public void keyReleased(KeyEvent e) {
		int k = e.getKeyCode();
		if (k == 87 || k == 38) {dirY = 0;}
		if (k == 83 || k == 40) {dirY = 0;}
		if (k == 68 || k == 39) {dirX = 0;}
		if (k == 65 || k == 37) {dirX = 0;}
		if (k == 48) { type = 0;}
		if (k == 49) { type = 1;}
		if (k == 50) { type = 2;}
		if (k == 51) { type = 3;}
		if (k == 8) { removeLast();}
		if (k == 112) { settup();}
		if (k == 113) { saveLevel();}
	}
	public void moveCamera() {
		int speed = 10;
		posX += dirX*speed;
		posY += dirY*speed;
	}	
	
	public void removeLast(){
		try {
			if (lastSet == 0) {
				int s = walls.size()-1;
				walls.removeElementAt(s);
			}
			if (lastSet == 1) {
				int s = spawns.size()-1;
				spawns.removeElementAt(s);
			}
			if (lastSet == 2) {
				int s = items.size()-1;
				items.removeElementAt(s);
			}
		} catch (Exception e) {}
	}
} 