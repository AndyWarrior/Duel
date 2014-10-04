import java.net.*;
import java.io.*;
import java.util.*;

public class ServidorEnvio2 extends Thread{
	DatagramSocket yo;
	DatagramPacket paquete;
	int puerto;
	String dirIP;
	int puertoDest=7777;
	GamePanel gp;
	ReceptorUDP rudp;
	DuelFrame df;
	String mensaje;

	
	public ServidorEnvio2 (int puerto) {
		this.puerto=puerto;
		this.rudp=rudp;
	
	}
	
	public void enviarTodo2(String mensaje, String dirIP){
		this.dirIP=dirIP;
		this.mensaje=mensaje;
		InetAddress direccion = null;
		DatagramSocket yo = null;
		DatagramPacket paquete;
		
		
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