public class WeaponThread extends Thread{
	GamePanel g;
	int timeHeld = 0;
	boolean cont = true;

	public WeaponThread(GamePanel g){
		this.g = g;
	}

	public void run(){
		while(cont){
			timeHeld++;
			try{
				sleep(20);
			}catch(InterruptedException e){}
		}
	}
	
	public void endNow() {
		cont = false;
		g.timeHeld = timeHeld;
	}
}