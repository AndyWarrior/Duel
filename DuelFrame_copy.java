import java.util.*;
import java.awt.*;
import javax.swing.*;

public class DuelFrame extends JFrame{
	//Menu m = new Menu(this);
	//Lobby l = new Lobby(this);
	//JPanel emptyCenter = new JPanel();
	GamePanel gp = new GamePanel(2,0);
	int w = 1000;
	int h = 600;
	
	//Constructor
	public DuelFrame(){
		/*
		try {
			// Set System L&F
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {}
		getContentPane().add(emptyCenter, BorderLayout.CENTER);
		getContentPane().add(m, BorderLayout.EAST);
		*/
		getContentPane().add(gp);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(w,h);
		setTitle("Duel!");
		setVisible(true);
		setResizable(false);
	}
	
	/*
	public void changeTo(int x) {
		if (x==0){
			getContentPane().removeAll();
			getContentPane().add(emptyCenter, BorderLayout.CENTER);
			getContentPane().add(m, BorderLayout.EAST);
		}
		else if (x==1){
			getContentPane().removeAll();
			getContentPane().add(l, BorderLayout.CENTER);
			getContentPane().add(emptyCenter, BorderLayout.EAST);
		}
		else if (x==2) {
			System.out.println("Join a game");
		}
		else if (x==3) {
			System.out.println("Configure");
		} else { System.out.println("ERROR");} 
		repaint();
	}
	*/
	
	//Main
	public static void main(String[] args){
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
            		public void run() {
                		new DuelFrame();
            		}
        	});
	}
} 