import java.net.*;
import java.io.*;
import java.util.*;
import java.lang.*;

public class ReceptorUDP {
	DatagramSocket yo;
	DatagramPacket paquete;
	int puerto;
	//Vector<String> direcciones = new Vector<String>();
	public String direcciones [] = new String[8];
	int lugarj;
	int playersito;
	boolean recibio=false;


	public ReceptorUDP(int puerto){
		this.puerto = puerto;
		try{
			yo = new DatagramSocket(puerto);
		}catch(Exception e){}
	}

	
		public void recibirMensaje(){
		
			byte[] buffer = new byte[30];
			paquete = new DatagramPacket(buffer, buffer.length);
			try{
				yo.receive(paquete);
			}catch(Exception e){}
			
			procesaRecepcionDePaquete(paquete);
		
}

	public void procesaRecepcionDePaquete(DatagramPacket paquete){
						InetAddress direccion = null;
						direccion= paquete.getAddress();
						String recibido= new String (direccion.toString());
						String lugar= new String(paquete.getData());
						lugar= lugar.trim();
						try{
						
						lugarj=Integer.parseInt(lugar);
						
						}catch(Exception e){}
						
						recibido=recibido.substring(1);
						direcciones[lugarj]=recibido;
						
						//direcciones.add(recibido);
	}
	
	
		public int recibirMensaje2(){
			byte[] buffer = new byte[30];
			paquete = new DatagramPacket(buffer, buffer.length);
			try{
				yo.receive(paquete);
			}catch(Exception e){}
			
			String nuevo= new String(paquete.getData());
			nuevo=nuevo.trim();
			
			try{
				playersito=Integer.parseInt(nuevo);
			}catch(Exception e){}
		
			return playersito;
}
}