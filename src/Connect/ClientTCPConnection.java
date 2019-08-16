package Connect;

import java.io.*;
import java.net.*;
//import java.util.*;

import client.Main;
import client.Models.ItemContainer;
import client.Models.Tank;
import trying.BoxContainer;

//import java.nio.charset.*;

/**
 * 
 * @author Rongyi Zhang
 * @version 1.0
 * The class is responsible for setting up socket to communicate with server, if the local
 * is in client mode.
 *
 */
public class ClientTCPConnection {
//	private static final int DEFAULT_PORT = 8189;
	
	private Socket socket;
	private ObjectInputStream input;
	private DataOutputStream output;
	private String GUIMessage_;
	private String host_;
	private boolean myTurn_;
//	private InputStream inputStream_;
	private boolean ready_;
	private int[][] map;
	private int DEFAULT_PORT;
	
	/**
	 * 
	 * @param host : a string containing host's address or name the user wants to connect to
	 */
	public ClientTCPConnection(String host, int portnum) {
		DEFAULT_PORT = portnum;
		host_ = host;
		myTurn_ = true;
		this.ready_ = false;
		map = Main.readFile(new File("docs/map3.txt")) ;
	}
	
	/**
	 * Java doc for overridden functions is in the interface file.
	 */
	
	 
	public void connect() throws IOException {
		// TODO Auto-generated method stub
		this.socket = new Socket(host_, DEFAULT_PORT);
		InputStream inStream = socket.getInputStream();
//		inputStream_ = inStream;
		OutputStream outStream = socket.getOutputStream();
		this.input = new ObjectInputStream(inStream);
//		this.output = new PrintWriter(new OutputStreamWriter(outStream, StandardCharsets.UTF_8), true /*autoFlush */);
		this.output = new DataOutputStream(outStream);
		GUIMessage_ = "Incoming connection from a client at " + socket.getRemoteSocketAddress().toString() + " accepted.\n" ;
	}

	 
	public ItemContainer receive() throws ClassNotFoundException, IOException {
		// TODO Auto-generated method stub
//		this.input.e
		ItemContainer allMovings = (ItemContainer)this.input.readObject();
//		log.info(String.format("Message %s received.\n", message));
		return allMovings;
	}
	
	public Integer receiveID() throws ClassNotFoundException, IOException {
		Integer id = (Integer)this.input.readObject();
		return id;
	}

	 
	public void send(int signal) throws IOException {
		// TODO Auto-generated method stub
		this.output.writeInt(signal);
		output.flush();
	}
	
	public void sendFloat(float f) throws IOException {
		this.output.writeFloat(f);
		output.flush();
	}

	 
	public String getGUIMessage() {
		// TODO Auto-generated method stub
		return this.GUIMessage_;
	}
	
	public boolean isConnectionClosed() {
		return this.socket.isClosed();
	}

	 
	public boolean myTurn() {
		return this.myTurn_;
	}


	public void myTurnNow( boolean turn) {
		// TODO Auto-generated method stub
		this.myTurn_ = turn;
		
	}


	 
	public boolean isConnected() {
		// TODO Auto-generated method stub
		return this.socket.isConnected();
	}

	 
	/**
	 *
	 */
	public boolean readyForAttack() {
		return ready_;
	}
	
	/**
	 * set whether local finished placing ships.
	 */
	 
	public void shipPlaced(boolean placed) {
		this.ready_ = placed;
//		if(ready_ == true)
//			System.out.println("\nlocal finished placing ships\n");
		
	}
	
	/**
	 * do nothing here because this class is a client. adding it just to implement 
	 * IConnectable interface. Server class will close server socket so that it free the resources.
	 */
	 
	public void closeSocket() {
		
	}
	
	/**
	 * @return whether this is a client.
	 */
	
	public boolean isClient() {
		// TODO Auto-generated method stub
		return true;
	}

}

