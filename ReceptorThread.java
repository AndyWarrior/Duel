import java.net.*;
import java.io.*;
import java.util.*;
import java.lang.*;

public class ReceptorThread extends Thread {
	DatagramSocket yo;
	int puerto;
	int lugarj;
	int playersito;
	boolean recibio=false;
	GamePanel gp;
	ReceptorUDP rudp;
	private int index=0;
	private int posx;
	private int posy;
	String envio;
	ServidorEnvio se= new ServidorEnvio(5555);
	private int mosy;
	private int mosx;
	private int jugador;
	private double angulo;
	private int velocidad;
	

	public ReceptorThread(int puerto,GamePanel gp, ReceptorUDP rudp){
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
						try{
						InetAddress direccion = null;
						direccion= paquete.getAddress();
						String recibido= new String (direccion.toString());
						recibido=recibido.substring(1);
						
					
						for(int i=0; i<rudp.direcciones.length; i++){
							if(recibido.equals(rudp.direcciones[i])){
								index=i;
								break;
							}
						}
						}catch(Exception e){}
						String mensaje= new String(paquete.getData());
						String A[]=mensaje.split("/");
						if(!A[0].equals("B")){
						
						try{
						
						posx=Integer.parseInt(A[0]);
						
						posy=Integer.parseInt(A[1].trim());
						
						mosx=Integer.parseInt(A[2].trim());
						
						mosy=Integer.parseInt(A[3].trim());
						
						
						
						gp.players.elementAt(index).setX(posx);
						gp.players.elementAt(index).setY(posy);
						gp.players.elementAt(index).setAngulito(mosx,mosy);
						
						gp.players.elementAt(index).setMousex(mosx);
						gp.players.elementAt(index).setMousey(mosy);
						
						}catch(Exception e){}
						
					
					for(int i=1; i<rudp.direcciones.length; i++){
						if(rudp.direcciones[i]==null || rudp.direcciones[i].length()<4) break;
						for(int j=0; j<gp.controlar; j++){
							envio="";
							envio=gp.players.elementAt(j).getX()+"/"+gp.players.elementAt(j).getY()+"/"+gp.players.elementAt(j).getMousex()+"/"+gp.players.elementAt(j).getMousey()+"/"+j;
							if(i!=j){
							se.enviarTodo(envio,rudp.direcciones[i],rudp);
							}
						}
					}
				}
				
			
						

	}
	
}