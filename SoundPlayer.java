import java.io.*;
import javax.sound.sampled.*;

public class SoundPlayer extends Thread{
	String url;
	boolean loop;
	double length;

	public SoundPlayer(String url, boolean loop, double length){
		this.url = url;
		this.loop = loop;
		this.length = length;
	}
	
	public void run(){
		do {
			try {
			Clip clip = AudioSystem.getClip();
			AudioInputStream inputStream = AudioSystem.getAudioInputStream(DuelFrame.class.getResourceAsStream("" + url));
			clip.open(inputStream);
			clip.start(); 
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
			try {
				long timer = (long)(length*1000);
				Thread.sleep(timer);
			} catch (Exception e) {}
		} while(loop);
	}

}