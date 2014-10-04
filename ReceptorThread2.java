import java.net.*;
import java.io.*;
import java.util.*;
import java.lang.*;

public class ReceptorThread2 extends Thread {
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
	private int mosy;
	private int mosx;
	private int jugador;
	private double angulo;
	private int velocidad;
	ServidorEnvio2 se2= new ServidorEnvio2(7777);
	

	public ReceptorThread2(int puerto,GamePanel gp, ReceptorUDP rudp){
		this.puerto = puerto;
		this.gp=gp;
		this.rudp=rudp;
		
		try{
			yo = new DatagramSocket(puerto);
		}catch(SocketException e){System.out.println(e.getMessage());}
		
		
		
	}

	
		public void run(){
			while(true){
			
			
			byte[] buffer = new byte[80];
			DatagramPacket paquete = new DatagramPacket(buffer, buffer.length);
			
			try{
				
			
					
					yo.receive(paquete);
		
				
				
			}catch(IOException e){System.out.println(e.getMessage()); }
			
			
			procesaRecepcionDelPaquete(paquete);
			}
		
		}

	private void procesaRecepcionDelPaquete(DatagramPacket paquete){
						String mensaje= new String(paquete.getData());
						String A[]=mensaje.split("/");
						
				
				if(A[0].equals("B")){
		
					jugador=Integer.parseInt(A[1]);
					
					posx=Integer.parseInt(A[2]);
						
					posy=Integer.parseInt(A[3].trim());
					
					angulo=Double.parseDouble(A[4].trim());
					
					velocidad=Integer.parseInt(A[5].trim());
					
					Bullet b= new Bullet(gp,jugador,posx,posy,angulo,velocidad);
					
					gp.bullets.addElement(b);
					
					if(velocidad==1) new SoundPlayer("pistol-01.wav",false,0.8).start();
					else new SoundPlayer("Colt45.wav",false,0.8).start();
					
					
					for(int i=1; i<rudp.direcciones.length; i++){
						if(rudp.direcciones[i]==null || rudp.direcciones[i].length()<4) break;
						for(int j=0; j<gp.controlar; j++){
							envio="";
							envio="B"+"/"+jugador+"/"+posx+"/"+posy+"/"+angulo+"/"+velocidad;
							if(i!=j){
							
							se2.enviarTodo2(envio,rudp.direcciones[i]);
							}
						}
					}
					
					
				
				}
				
				else if(A[0].equals("R")){
					jugador=Integer.parseInt(A[1]);
					
					posx=Integer.parseInt(A[2]);
						
					posy=Integer.parseInt(A[3].trim());
					
					angulo=Double.parseDouble(A[4].trim());
					
					
					Rocket r= new Rocket(gp,jugador,posx,posy,angulo);
					
					gp.rockets.addElement(r);
					
					new SoundPlayer("rocket.wav",false,3).start();
					
					
					for(int i=1; i<rudp.direcciones.length; i++){
						if(rudp.direcciones[i]==null || rudp.direcciones[i].length()<4) break;
						for(int j=0; j<gp.controlar; j++){
							envio="";
							envio="R"+"/"+jugador+"/"+posx+"/"+posy+"/"+angulo;
							if(i!=j){
							
							se2.enviarTodo2(envio,rudp.direcciones[i]);
							}
						}
					}
					
				
				}
				
				else if(A[0].equals("G")){
					jugador=Integer.parseInt(A[1]);
					
					posx=Integer.parseInt(A[2]);
						
					posy=Integer.parseInt(A[3].trim());
					
					angulo=Double.parseDouble(A[4].trim());
					
					velocidad=Integer.parseInt(A[5].trim());
					
					
					Grenade g= new Grenade(gp,jugador,posx,posy,angulo,velocidad);
					
					gp.grenades.addElement(g);
					
					for(int i=1; i<rudp.direcciones.length; i++){
						if(rudp.direcciones[i]==null || rudp.direcciones[i].length()<4) break;
						for(int j=0; j<gp.controlar; j++){
							envio="";
							envio="G"+"/"+jugador+"/"+posx+"/"+posy+"/"+angulo+"/"+velocidad;
							if(i!=j){
							
							se2.enviarTodo2(envio,rudp.direcciones[i]);
							}
						}
					}
					
				
				}
						

	}
	
}