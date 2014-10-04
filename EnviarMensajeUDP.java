import java.net.*;
import java.io.*;
import java.util.*;
import java.lang.*;


public class EnviarMensajeUDP {


public EnviarMensajeUDP(){}

public void enviarMensajee(String dirDestino, int puertito, String mensaje){
		
		InetAddress direccion = null;
		int puerto;
		DatagramSocket yo = null;
		DatagramPacket paquete;
		byte[] buffer;

		try{
	        	direccion = InetAddress.getByName(dirDestino);
		}catch(UnknownHostException e){
	        	System.out.println("ERROR: "+e.getMessage());
		}	
		puerto = puertito;
	     	try{
	        	yo = new DatagramSocket();
	    	}catch(SocketException e){
	        	System.out.println("ERROR: "+e.getMessage());
	    	}
		buffer = mensaje.getBytes();
		paquete = new DatagramPacket(buffer, buffer.length, direccion, puerto);
		try{
	        	yo.send(paquete);
		}catch(IOException e){
		        System.out.println("ERROR: "+e.getMessage());
		}		
	}
	
	public void enviarMensaje2(String dirDestino, int puertito, String mensaje){
		
		
		InetAddress direccion = null;
		int puerto;
		DatagramSocket yo = null;
		DatagramPacket paquete;
		byte[] buffer;

		try{
	        	direccion = InetAddress.getByName(dirDestino);
		}catch(UnknownHostException e){
				System.out.println("DAMN1");
	        	System.out.println("ERROR: "+e.getMessage());
		}	
		puerto = puertito;
	     	try{
	        	yo = new DatagramSocket();
	    	}catch(SocketException e){
				System.out.println("DAMN2");
	        	System.out.println("ERROR: "+e.getMessage());
	    	}
		buffer = mensaje.getBytes();
		paquete = new DatagramPacket(buffer, buffer.length, direccion, puerto);
		try{
	        	yo.send(paquete);
		}catch(IOException e){
				System.out.println("DAMN3");
		        System.out.println("ERROR: "+e.getMessage());
		}		
	}
	
}