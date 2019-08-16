package Server;

import java.io.DataInputStream;
import java.io.File;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import Connect.ServerMultiTCPConnection;
import client.Main;
import client.Models.Bullet;
//import Connect.ServerTCPConnection;
import client.Models.Item;
import client.Models.ItemContainer;
import client.Models.ServerSimulator;
import client.Models.Tank;

public class ServerStarter{
	
	private static final int WALL_SIZE = 15;
	private static final int TANK_SIZE = 15;
	
	private static final int SPEED = 4;
	
	private int numberOfClients_;
	private int portNumber_;
	private ItemContainer AllMovings_;
	private List<Tank> players_;
	private int[][] map;
	private ServerSimulator simulator_;
	
	public ServerStarter( int numberOfClients, int portNumber ) {
		this.numberOfClients_ = numberOfClients;
		this.portNumber_ = portNumber;
		this.AllMovings_ = new ItemContainer();
		players_ = new ArrayList<Tank>();
		map = Main.readFile(new File("docs/map3.txt")) ;
//		int[][]m = null;
		simulator_ = new ServerSimulator(map, players_  ); 
	}
	
	public void startServer() {
		
		try {
			ServerMultiTCPConnection  tcpSM = new ServerMultiTCPConnection(numberOfClients_,
					this.portNumber_);
			System.out.println("Server created, waiting for connections");
			//wait for all connects
			tcpSM.connect();
			System.out.println("All connected");
			
			//Generate tanks
			for ( int i = 0 ; i < numberOfClients_; i++) {
				if( i == 1) {
					Tank tank = new Tank(36*WALL_SIZE,36*WALL_SIZE,TANK_SIZE,TANK_SIZE,true,SPEED);
					this.players_.add(tank);
					continue;
				}
				Tank tank =	new Tank(2*WALL_SIZE,2*WALL_SIZE,TANK_SIZE,TANK_SIZE,true,SPEED);
				this.players_.add(tank);
			}
			
			for ( int i = 0; i < numberOfClients_; i++) {
				ClientListener ch =  new ClientListener(i,tcpSM.getInputStream(i),tcpSM.getOutputStream(i), 
						this.AllMovings_, this.players_);
				Thread sThread = new Thread(ch);
				sThread.start();
			}
			
			
			try {
				while(true) {
					//simulate part.
					simulator_.update();
					//
					for( Tank tank: this.players_) {
						tank.dx = 0;
						tank.dy = 0;
					}
					
					//add all tanks and bullets
					for( Tank player: this.players_) {
						this.AllMovings_.addItem(player.box);
						for( Bullet b : player.newBullets ) {
							this.AllMovings_.addItem( b.box );
						}
					}
					
					try {
						tcpSM.sendToAll(this.AllMovings_);
					}catch(Exception e) {
						return;
					}
					
//					int rp = 0;
					this.AllMovings_.clearContainer();
					
					TimeUnit.MILLISECONDS.sleep(10);
				}
			}catch( InterruptedException e) {
				return;
			}

			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public static void main(String[] args) throws ClassNotFoundException, UnknownHostException {
		
        int nofClients = 2;
		ServerStarter ss = new ServerStarter(nofClients, 8189);
		InetAddress localHostAddress = InetAddress.getLocalHost();
		System.out.println( localHostAddress );
		ss.startServer();
		
	}
}

class ClientListener implements Runnable{
	private static final int SPEED = 4;
	private DataInputStream input_;
	private ObjectOutputStream output_;
	private int clientIndex_;
	private List<Tank> tanks_;
	private ItemContainer allMovings_;
	
	public ClientListener(int i, DataInputStream input, ObjectOutputStream output, 
			ItemContainer allMovings, List<Tank> tanks) {
		this.tanks_ = tanks;
		this.clientIndex_ = i;
		this.input_ = input;
		this.output_ = output;
		this.allMovings_ = allMovings;
	}
	
	
	@Override
	public void run() {
		
		try {
			while(true) {
				int clientResponse = this.input_.readInt();
				System.out.println(String.format("%d Received %d", this.clientIndex_,clientResponse));
				if( clientResponse == 1 ) {
					synchronized(allMovings_) {
//						this.tanks_.get(clientIndex_).box.y += 2.0;
						this.tanks_.get(clientIndex_).dy += 4.0;
					}
					
//					tcpS.send(Movings);
//					int re = tcpS.receive();
				}else if( clientResponse == 2 ) {
//					this.tanks_.get(clientIndex_).box.y -= 2.0;
					this.tanks_.get(clientIndex_).dy -= 4.0;
				}else if( clientResponse == 3 ) {
//					this.tanks_.get(clientIndex_).box.x -= 2.0;
					this.tanks_.get(clientIndex_).dx -= 4.0;
				}else if( clientResponse == 4 ) {
//					this.tanks_.get(clientIndex_).box.x += 2.0;
					this.tanks_.get(clientIndex_).dx += 4.0;
				}
				else if ( clientResponse == 5 ) {
					float[] p = new float[2];
					for ( int i = 0; i < 2; i++) {
						p[i] = this.input_.readFloat();
					}
					float xmouse = p[0];
					float ymouse = p[1];
					Tank tank = this.tanks_.get(clientIndex_);
					float xmain = tank.getx() + tank.size / 2;
					float ymain = tank.gety() + tank.size / 2;
					float k = (ymain - ymouse) / (xmain - xmouse);
					float dx = (float) Math.sqrt(4*SPEED*SPEED /(1+k*k));
					if(xmouse<xmain) dx=-dx;
					float dy = k*dx;
					float size = 5;
					tank.newBullets.add(new Bullet(xmain, ymain, dx/2,dy/2, size));
				}
				
			}
		}catch (IOException e) {
			return;
		}
		
	}
	
}
	


