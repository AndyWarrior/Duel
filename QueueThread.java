import java.awt.*;
import java.util.*;

public class QueueThread extends Thread{
	GamePanel g;
	int tag;
	int tag2;
	double time;
	

	public QueueThread(GamePanel g, int tag, int tag2, double time){
		this.g = g;
		this.tag = tag;
		this.tag2 = tag2;
		this.time = time;
	}

	public void run(){
		long timer = (long)(time*1000);
		if (tag == 0) {
			Player p = g.players.elementAt(tag2);
			try{
				sleep(timer);
			}catch(InterruptedException e){}
			Random rn = new Random();
			int r = rn.nextInt(g.SPAWN.length);
			p.respawn(g.SPAWN[r][0],g.SPAWN[r][1]);
			System.out.println("Player_" + (tag2+1) +" is spawning at "+g.SPAWN[r][0]+":"+g.SPAWN[r][1]);
		}
		if (tag == 1) {
			Item it = g.items.elementAt(tag2);
			try{
				sleep(timer);
			}catch(InterruptedException e){}
			it.setLocation(g.ITEMPOS[tag2][0],g.ITEMPOS[tag2][1]);
			it.resetState();
			System.out.println("Respawning Item...");
		}
	}
}