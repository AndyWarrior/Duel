public class PaintThread extends Thread{
	GamePanel g;
	LevelCreator lc;
	int tag;

	public PaintThread(GamePanel g){
		this.g = g;
		tag = 0;
	}
	public PaintThread(LevelCreator lc) {
		this.lc = lc;
		tag = 1;
	}

	public void run(){
		while(true){
			if (tag == 0) {
				g.repaint();
			} else {
				lc.repaint();
			}
			try{
				sleep(20);
			}catch(InterruptedException e){}
		}
	}
}