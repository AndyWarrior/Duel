import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Menu extends JPanel implements ActionListener{
	JButton buttonHost;
	JButton buttonJoin;
	JButton buttonCfg;
	JTextField textJoin;
	DuelFrame df;
	
	public Menu(DuelFrame df) {
		repaint();
		this.df =  df;
		setLayout(new GridLayout(0,1));
		setSize(200,300);
		buttonHost = new JButton("Host Game");
		buttonJoin = new JButton("Join Game");
		buttonCfg = new JButton("Configure");
		textJoin = new JTextField(15);
		for (int i = 0; i < 10; i++) {
			add(new JLabel());
		}
		add(buttonHost);
		add(new JLabel());
		add(new JLabel("               Enter Host IP:"));
		add(textJoin);
		add(buttonJoin);
		add(new JLabel());
		add(buttonCfg);
		buttonHost.addActionListener(this);
		buttonJoin.addActionListener(this);
		buttonCfg.addActionListener(this);
	}
	
	public void actionPerformed(ActionEvent e){
		if (e.getSource() == buttonHost) {
			df.changeTo(1);
		}
		if (e.getSource() == buttonJoin) {
			df.changeTo(2);
		}
		if (e.getSource() == buttonCfg) {
			df.changeTo(3);
		}
	}
	
}