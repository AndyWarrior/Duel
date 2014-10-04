import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.net.*;
import java.io.*;

public class Menu extends JPanel implements ActionListener{
	JButton buttonHost;
	JButton buttonJoin;
	JButton buttonCfg;
	JTextField textJoin;
	DuelFrame df;
	
	public Menu(DuelFrame df) {
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
		add(new JLabel());
		add(buttonHost);
		add(new JLabel());
		add(new JLabel("               Enter Host IP:"));
		add(textJoin);
		add(buttonJoin);
		add(new JLabel());
		add(buttonCfg);
		add(new JLabel());
		buttonHost.addActionListener(this);
		buttonJoin.addActionListener(this);
	}
	
	public void actionPerformed(ActionEvent e){
		if (e.getSource() == buttonHost) {
			System.out.println("time to host some stuff");
		}
		if (e.getSource() == buttonJoin) {
			System.out.println("join a game!!!" + textJoin.getText());
		}
		if (e.getSource() == buttonCfg) {
			System.out.println("join a game!!!" + textJoin.getText());
		}
	}
	
}