import java.net.*;
import java.io.*;
import java.util.*;

public class ClientesEnvio extends Thread{
	DatagramSocket yo;
	DatagramPacket paquete;
	int puerto;
	String dirIP;
	int puertoDest=5555;
	String mensaje;
	GamePanel gp;
	ReceptorUDP rudp;
	DuelFrame df;

	
	public ClientesEnvio (int puerto, GamePanel gp, ReceptorUDP rudp, DuelFrame df) {
		this.puerto=puerto;
		this.gp=gp;
		this.rudp=rudp;
		this.df=df;
		dirIP=gp.ipaddress;
	
	}
	
	public void run(){
		InetAddress direccion = null;
		DatagramSocket yo = null;
		DatagramPacket paquete;
		while (true){
		
			mensaje="";
			mensaje=gp.players.elementAt(gp.number).getX()+"/"+gp.players.elementAt(gp.number).getY()+"/"+gp.players.elementAt(gp.number).getMousex()+"/"+gp.players.elementAt(gp.number).getMousey();
			
	
		
		byte[] buffer= new byte[80];

		try{
	        	direccion = InetAddress.getByName(dirIP);
		}catch(UnknownHostException e){
	        	System.out.println("ERROR: "+e.getMessage());
		}		
	     	
	        	yo = rudp.yo;
	    	
		buffer = mensaje.getBytes();
		paquete = new DatagramPacket(buffer, buffer.length, direccion, puerto);
		try{
	        	yo.send(paquete);
		}catch(IOException e){
		        System.out.println("ERROR: "+e.getMessage());
		}
		
	
			try{
				Thread.sleep(20);
			}catch(Exception e){}
	
}

}


	

}