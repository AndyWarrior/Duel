import java.util.*;
import java.awt.*;
import javax.swing.*;
import java.io.*;

public class DuelFrame extends JFrame{
	Scanner t = new Scanner(System.in);
	GamePanel gp;
	int w = 1000;
	int h = 600;
	int ARENA_X;
	int ARENA_Y;
	int[][] SPAWN;
	int[][] WALLPOS;
	int[][] ITEMPOS;
	EnviarMensajeUDP eudp=new EnviarMensajeUDP();
	ReceptorUDP rudp =new ReceptorUDP(5555);
	String enviar;
	String dirIpDestino;
	String dirIp2;
	public int sc;
	int m,p;
	String dirIP;
	
	
	//Constructor
	public DuelFrame(){
		System.out.println("Servidor presione 0, Cliente presione 1");
		sc=t.nextInt();
		
		
		if(sc==0){
			System.out.println("Cantidad de jugadores");
			m = t.nextInt();
			System.out.println("Jugador que controlaras");
			p = t.nextInt();
			p=p-1;
			
			
			
			
			String players []= new String [m];
			for(int i=1; i<players.length; i++){
			
			
			rudp.recibirMensaje();
			
			}
			
			
			for(int i=1; i<m; i++){
			enviar=""+m;
			dirIp2=rudp.direcciones[i];
			eudp.enviarMensajee(dirIp2, 5555, enviar);
		}
			
			
	}
		
		else if(sc==1){
			System.out.println("Direccion IP del servidor");
			dirIP = t.nextLine();
			dirIP = t.nextLine();
			rudp.direcciones[0]=dirIP;
			System.out.println("Jugador que controlaras");
			p = t.nextInt();
			p=p-1;
			enviar=""+p;
			eudp.enviarMensajee(dirIP, 5555, enviar);
		
		
			m=rudp.recibirMensaje2();
			
			
		}
		
		System.out.println("Enter Level Name:");
		String l = "kingofthehill";
		readLevel(l);
		System.out.println("Level Start!\n");
		gp = new GamePanel(m,p,ARENA_X,ARENA_Y,SPAWN,WALLPOS,ITEMPOS,rudp,this);
		getContentPane().add(gp);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(w,h);
		setTitle("Duel!");
		setVisible(true);
		setResizable(false);
		
	}
	
	public void readLevel(String lvlname) {
		try {
			System.out.println("Reading level...");
			RandomAccessFile readFile = new RandomAccessFile(lvlname+".dat","rw");
			readFile.seek(0);
			System.out.println("Reading size...");
			ARENA_X = readFile.readInt();
			ARENA_Y = readFile.readInt();
			System.out.println("Getting spawn points...");
			int x = readFile.readInt();
			int y = readFile.readInt();
			SPAWN = new int[x][y];
			for (int i = 0; i<x; i++) {
				for (int j = 0; j<y; j++) {
					SPAWN[i][j] = readFile.readInt();
				}
			}
			System.out.println("Getting wall positions...");
			x = readFile.readInt();
			y = readFile.readInt();
			WALLPOS = new int[x][y];
			for (int i = 0; i<x; i++) {
				for (int j = 0; j<y; j++) {
					WALLPOS[i][j] = readFile.readInt();
				}
			}
			System.out.println("Getting item positions...");
			x = readFile.readInt();
			y = readFile.readInt();
			ITEMPOS = new int[x][y];
			for (int i = 0; i<x; i++) {
				for (int j = 0; j<y; j++) {
					ITEMPOS[i][j] = readFile.readInt();
				}
			}
			System.out.println("Level read succesfully");
		} catch(Exception e) { 
			System.out.println("ERROR: "+e.getMessage());
			System.exit(1);
		}
	}
	
	//Main
	public static void main(String[] args){
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
            		public void run() {
                		new DuelFrame();
            		}
        	});
	}
} 