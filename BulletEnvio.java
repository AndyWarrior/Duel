import java.net.*;
import java.io.*;
import java.util.*;

public class BulletEnvio extends Thread{
	DatagramSocket yo;
	DatagramPacket paquete;
	int puerto;
	String dirIP;
	int puertoDest=7777;
	String mensaje;
	GamePanel gp;
	ReceptorUDP rudp;
	DuelFrame df;
	Bullet bul;

	
	public BulletEnvio (int puerto, GamePanel gp, ReceptorUDP rudp) {
		this.puerto=puerto;
		this.gp=gp;
		this.rudp=rudp;
		this.bul=bul;
		dirIP=gp.ipaddress;
	
	}
	
	public void mandarBala(String mensaje){
		InetAddress direccion = null;
		DatagramSocket yo = null;
		DatagramPacket paquete;
		this.mensaje=mensaje;
		
	
	
		
		byte[] buffer= new byte[80];
		

		try{
	        	direccion = InetAddress.getByName(dirIP);
		}catch(UnknownHostException e){
	        	System.out.println("ERROR: "+e.getMessage());
		}		
	     	try{
	        	yo = new DatagramSocket();
	    	}catch(SocketException e){System.out.println(e.getMessage());}
		buffer = mensaje.getBytes();
		paquete = new DatagramPacket(buffer, buffer.length, direccion, puerto);
		try{
	        	yo.send(paquete);
			
		
		}catch(IOException e){
		        System.out.println("ERROR: "+e.getMessage());
		}
		
	
		
	
}

}

