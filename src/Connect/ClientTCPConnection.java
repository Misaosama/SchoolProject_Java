package Connect;

import java.io.*;
import java.net.*;
//import java.util.*;

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
	private static final int DEFAULT_PORT = 8188;
	
	private Socket socket;
	private ObjectInputStream input;
	private DataOutputStream output;
	private String GUIMessage_;
	private String host_;
	private boolean myTurn_;
//	private InputStream inputStream_;
	private boolean ready_;
	
	/**
	 * 
	 * @param host : a string containing host's address or name the user wants to connect to
	 */
	public ClientTCPConnection(String host) {
		host_ = host;
		myTurn_ = true;
		this.ready_ = false;
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

	 
	public BoxContainer receive() throws ClassNotFoundException, IOException {
		// TODO Auto-generated method stub
//		this.input.e
		BoxContainer allMovings = (BoxContainer)this.input.readObject();
//		log.info(String.format("Message %s received.\n", message));
		return allMovings;
	}
	

	 
	public void send(int signal) throws IOException {
		// TODO Auto-generated method stub
		this.output.writeInt(signal);;
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

