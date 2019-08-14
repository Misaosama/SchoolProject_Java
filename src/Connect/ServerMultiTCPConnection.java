package Connect;
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;

import trying.BoxContainer;


public class ServerMultiTCPConnection {
	private static final int DEFAULT_PORT = 8189;
	private ServerSocket serverSocket_;
	private List<Socket> sockets_;
	private List<DataInputStream> inputs_;
	private List<ObjectOutputStream> outputs_;
	private int numberOfClientsWanted_;
	private int numberOfActiveClients_;
	private String GUIMessage_;
	private boolean myTurn_;
	private boolean ready_;
	
	public ServerMultiTCPConnection( int numberOfClientsWanted ) throws IOException {
		this.numberOfClientsWanted_ = numberOfClientsWanted;
		this.serverSocket_ = new ServerSocket(DEFAULT_PORT);
		this.sockets_ = new ArrayList<Socket>();
		this.inputs_ = new ArrayList<DataInputStream>();
		this.outputs_ = new ArrayList<ObjectOutputStream>();
		
	}
	
	public void connect() throws IOException {
		for( int i = 0; i < this.numberOfClientsWanted_; i++) {
			this.sockets_.add( this.serverSocket_.accept() );
			this.numberOfActiveClients_++;
			//
			InputStream inStream =  sockets_.get(i).getInputStream();
			OutputStream outStream = sockets_.get(i).getOutputStream();
			this.inputs_.add( new DataInputStream(inStream) );
			this.outputs_.add( new ObjectOutputStream(outStream) );
			GUIMessage_ = "Incoming connection from a client at " + sockets_.get(i).getRemoteSocketAddress().toString() + " accepted.\n" ;
		}
		
	}
	
	public DataInputStream getInputStream(int i) {
		return this.inputs_.get(i);
	}
	
	public ObjectOutputStream getOutputStream(int i) {
		return this.outputs_.get(i);
	}
	
	public int receive(int i) throws IOException {
		int signal = this.inputs_.get(i).readInt();
		return signal;
	}
	
	public void sendToAll(BoxContainer Movings){
		
//		try {
			for( int i = 0; i < this.numberOfActiveClients_; i++) {
				if(!this.sockets_.get(i).isConnected()) {
					System.out.println("disconnected");
					continue;
				}
				if(this.sockets_.get(i).isClosed()) {
					System.out.println("closed");
					continue;
				}
//				this.ready_ = true;
				try {
					this.outputs_.get(i).writeObject(Movings);
					this.outputs_.get(i).flush();
					this.outputs_.get(i).reset();
				} catch (IOException e) {
					// TODO Auto-generated catch block
//					e.printStackTrace();
					System.out.println("client " + i + " disconnected");
					this.oneClientDisconnected(i);
					if(this.numberOfActiveClients_ == 0) {
						System.out.println(0);
//						return;
						System.exit(0);
					}
				}
				
			}	
//		} catch (IOException e) {
//			System.out.println("BBBBBBBBBBBBBBBBBBBB");
//			e.printStackTrace();
//		}
		
	}
	
	public void oneClientDisconnected( int i ) {
		this.inputs_.remove(i);
		this.outputs_.remove(i);
		this.sockets_.remove(i);
		this.numberOfActiveClients_ --;
	}
	
	public int getNofActive() {
		return this.numberOfActiveClients_;
	}
	
	public void closeSocket() {
		try {
			this.serverSocket_.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
}

