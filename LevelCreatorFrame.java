import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
	
public class LevelCreatorFrame extends JFrame {
	LevelCreator lc = new LevelCreator();
	
	//Constructor
	public LevelCreatorFrame() {
		setTitle("Level Creator");
		setVisible(true);
		setResizable(false);
		setSize(1000,600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().add(lc);
	}
	
	//Main
	public static void main(String[] args){
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
            		public void run() {
                		new LevelCreatorFrame();
            		}
        	});
	}

}