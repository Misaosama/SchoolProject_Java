package Connect;
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;

import client.Models.ItemContainer;

/**
 * This class is used to group all server's network resources together.
 * @author rongyi
 * @version 1.0
 *
 */
public class ServerMultiTCPConnection {
	private int DEFAULT_PORT = 8188;
	private ServerSocket serverSocket_;
	private List<Socket> sockets_;
	private List<DataInputStream> inputs_;
	private List<ObjectOutputStream> outputs_;
	private boolean clientDisconnected_ [];
	private int numberOfClientsWanted_;
	private int numberOfActiveClients_;
	private String GUIMessage_;
	private boolean myTurn_;
	private boolean ready_;
	
	/**
	 * Constructs a instance of this class given number of players and a port number.
	 * @param numberOfClientsWanted : the number of players in this game.
	 * @param portNumber : Port number this server will use
	 * @throws IOException: network related.
	 */
	public ServerMultiTCPConnection( int numberOfClientsWanted, int portNumber ) throws IOException {
		this.DEFAULT_PORT = portNumber;
		this.numberOfClientsWanted_ = numberOfClientsWanted;
		clientDisconnected_ = new boolean[numberOfClientsWanted_];
		this.serverSocket_ = new ServerSocket(DEFAULT_PORT);
		this.sockets_ = new ArrayList<Socket>();
		this.inputs_ = new ArrayList<DataInputStream>();
		this.outputs_ = new ArrayList<ObjectOutputStream>();
		
	}
	
	/**
	 * Wait for all clients to connect.
	 * @throws IOException If connection fails
	 */
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
		//send ID to all clients:
		for( int i = 0; i < this.numberOfClientsWanted_; i++) {
			this.outputs_.get(i).writeObject(new Integer(i));
		}
	}
	
	/**
	 * 
	 * @param i the index of a inputStream, each inputStream corresponds to a client.
	 * @return the input stream so that other classes can use it to read data from clients.
	 */
	public DataInputStream getInputStream(int i) {
		return this.inputs_.get(i);
	}
	
	/**
	 * 
	 * @param i the index of a outputStream, each outputStream corresponds to a client.
	 * @return the outputStream so that other classes can use it to send data to clients.
	 */
	public ObjectOutputStream getOutputStream(int i) {
		return this.outputs_.get(i);
	}
	
	/**
	 * 
	 * @param i the index of the client to read data from. 
	 * @return the integer read from a given client
	 * @throws IOException usually when the i-th client disconnects.
	 */
	public int receive(int i) throws IOException {
		int signal = this.inputs_.get(i).readInt();
		return signal;
	}
	
	/**
	 * Send the same data to all clients.
	 * @param Movings, an ItemContainer containing all display related data for all movable
	 * objects in this game.
	 */
	public void sendToAll(ItemContainer Movings){
		
			for( int i = 0; i < this.numberOfClientsWanted_; i++) {
				if( this.clientDisconnected_[i] ) {
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
		
	}
	
	//just mark the client as disconnected, so that the list of streams is untouched,
	//otherwise have to update clients' ids.
	/**
	 * Marks a client as disconnected after it disconnected.
	 * @param i the index of the client just disconnected from server.
	 */
	public void oneClientDisconnected( int i ) {
//		this.inputs_.remove(i);
//		this.outputs_.remove(i);
//		this.sockets_.remove(i);
		this.clientDisconnected_[i] = true;
//		this.sockets_.get(i).close();//necessary?
		this.numberOfActiveClients_ --;
	}
	
	/**
	 * 
	 * @return the number of currently active clients 
	 */
	public int getNofActive() {
		return this.numberOfActiveClients_;
	}
	
	/**
	 * close socket and realise resouces.
	 */
	public void closeSocket() {
		try {
			this.serverSocket_.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
}

