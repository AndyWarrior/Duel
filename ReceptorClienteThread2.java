import java.net.*;
import java.io.*;
import java.util.*;
import java.lang.*;

public class ReceptorClienteThread2 extends Thread {
	DatagramSocket yo;
	int puerto;
	int lugarj;
	int playersito;
	boolean bandera=true;;
	GamePanel gp;
	private int index;
	private int posx;
	private int posy;
	private int mosx;
	private int mosy;
	int jugador;
	int velocidad;
	double angulo;
	ReceptorThread2 rt2;

	public ReceptorClienteThread2(int puerto,GamePanel gp){
		this.puerto = puerto;
		this.gp=gp;
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
						
						}
						
						else if(A[0].equals("R")){
							
							jugador=Integer.parseInt(A[1]);
							
							posx=Integer.parseInt(A[2]);
								
							posy=Integer.parseInt(A[3].trim());
							
							angulo=Double.parseDouble(A[4].trim());
							
							
							Rocket r= new Rocket(gp,jugador,posx,posy,angulo);
							
							gp.rockets.addElement(r);
							
							new SoundPlayer("rocket.wav",false,3).start();
				
						}
						
						if(A[0].equals("G")){
							
							jugador=Integer.parseInt(A[1]);
							
							posx=Integer.parseInt(A[2]);
								
							posy=Integer.parseInt(A[3].trim());
							
							angulo=Double.parseDouble(A[4].trim());
							
							velocidad=Integer.parseInt(A[5].trim());
							
							Grenade g= new Grenade(gp,jugador,posx,posy,angulo,velocidad);
							
							gp.grenades.addElement(g);
						
						}
						
						
						
						
	}
	
}