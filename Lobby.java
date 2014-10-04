import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Lobby extends JPanel implements ActionListener{
	JButton buttonBack;
	DuelFrame df;
	
	public Lobby(DuelFrame df) {
		repaint();
		this.df =  df;
		setSize(600,600);
		buttonBack = new JButton("Back");
		add(buttonBack);
		buttonBack.addActionListener(this);
	}
	
	public void actionPerformed(ActionEvent e){
		System.out.println("omfg");
		if (e.getSource() == buttonBack) {
			df.changeTo(0);
		}
	}
	
}