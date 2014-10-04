import java.net.*;
import java.io.*;
import java.util.*;
import java.lang.*;

public class ReceptorClienteThread extends Thread {
	DatagramSocket yo;
	int puerto;
	int lugarj;
	int playersito;
	boolean bandera=true;;
	GamePanel gp;
	ReceptorUDP rudp;
	private int index;
	private int posx;
	private int posy;
	private int mosx;
	private int mosy;

	public ReceptorClienteThread(int puerto,GamePanel gp, ReceptorUDP rudp){
		this.puerto = puerto;
		this.gp=gp;
		this.rudp=rudp;
		yo = rudp.yo;
		
	}

	
		public void run(){
			while(true){
			
			yo=rudp.yo;
			byte[] buffer = new byte[80];
			DatagramPacket paquete = new DatagramPacket(buffer, buffer.length);
			
			try{
				
			
					
					yo.receive(paquete);
	
				
				
			}catch(IOException e){System.out.println(e.getMessage()); }
			
			procesaRecepcionDelPaquete(paquete);
			}
		
		}

	public void procesaRecepcionDelPaquete(DatagramPacket paquete){
						
						
						String mensaje= new String(paquete.getData());
						String A[]=mensaje.split("/");
						
						
						
						posx=Integer.parseInt(A[0].trim());
						posy=Integer.parseInt(A[1].trim());
						mosx=Integer.parseInt(A[2].trim());
						mosy=Integer.parseInt(A[3].trim());
						index=Integer.parseInt(A[4].trim());
						
						
						
						
						gp.players.elementAt(index).setX(posx);
						gp.players.elementAt(index).setY(posy);
						gp.players.elementAt(index).setAngulito(mosx,mosy);
						
						
						gp.players.elementAt(index).setMousex(mosx);
						gp.players.elementAt(index).setMousey(mosy);
						
						
						
						
						
						
						
						
	}
	
}