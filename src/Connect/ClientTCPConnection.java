package Connect;

import java.io.*;
import java.net.*;
//import java.util.*;

import client.Main;
import client.Models.ItemContainer;
import client.Models.Tank;

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
	private String host_;
	private int DEFAULT_PORT;
	
	/**
	 * 
	 * @param host : a string containing host's address or name the user wants to connect to
	 */
	public ClientTCPConnection(String host, int portnum) {
		DEFAULT_PORT = portnum;
		host_ = host;
//		map = Main.readFile(new File("docs/map3.txt")) ;
	}
	

	
	/**
	 * connect to a server. 
	 * @throws IOException if conneciton fails
	 * @throws java.net.ConnectException a specific situation needs to be handled.
	 */
	public void connect() throws IOException,java.net.ConnectException {
		// TODO Auto-generated method stub
		this.socket = new Socket(host_, DEFAULT_PORT);
		InputStream inStream = socket.getInputStream();
//		inputStream_ = inStream;
		OutputStream outStream = socket.getOutputStream();
		this.input = new ObjectInputStream(inStream);
//		this.output = new PrintWriter(new OutputStreamWriter(outStream, StandardCharsets.UTF_8), true /*autoFlush */);
		this.output = new DataOutputStream(outStream);
	}

	 /**
	  * get data from server
	  * @return an ItemContainer object just received from server.
	  * @throws ClassNotFoundException : if de-serialization fails.
	  * @throws IOException if connection fails.
	  */
	public ItemContainer receive() throws ClassNotFoundException, IOException {
		// TODO Auto-generated method stub
//		this.input.e
		ItemContainer allMovings = (ItemContainer)this.input.readObject();
//		log.info(String.format("Message %s received.\n", message));
		return allMovings;
	}
	
	/**
	 * 
	 * @return the Integer received from server(for client's id)
	 * @throws ClassNotFoundException : has to through for de-serialization
	 * @throws IOException: connection problems
	 */
	public Integer receiveID() throws ClassNotFoundException, IOException {
		Integer id = (Integer)this.input.readObject();
		return id;
	}

	/**
	 * send client input signal to server 
	 * @param signal : the player input which needs to be sent to the server.
	 * @throws IOException: send fails because of network issues.
	 */
	public void send(int signal) throws IOException {
		// TODO Auto-generated method stub
		this.output.writeInt(signal);
		output.flush();
	}
	
	/**
	 * send floats to the server(mouse position)
	 * @param f: a float used to represent a mouse position.
	 * @throws IOException: send fails because of network issues.
	 */
	public void sendFloat(float f) throws IOException {
		this.output.writeFloat(f);
		output.flush();
	}
	
	public void disconnect() throws IOException {
		this.socket.close();
	}
}

