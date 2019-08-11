package Connect;


import java.io.*;
import java.net.*;
//import java.util.*;
import trying.BoxContainer;
//import java.nio.charset.*;
import java.io.IOException;

	public class ServerTCPConnection {
		private static final int DEFAULT_PORT = 8188;
		
		private ServerSocket serverSocket;
		private Socket socket;
		private DataInputStream input;
		private ObjectOutputStream output;
		private String GUIMessage_;
		private boolean myTurn_;
//		private InputStream inputStream_;
		private boolean ready_;
		
		//constructor
		public ServerTCPConnection() throws IOException {
			myTurn_ = false;
			ready_ = false;
			this.serverSocket = new ServerSocket(DEFAULT_PORT);
			System.out.println("server created");
//			serverSocket.cl
		}
		
		public void connect() throws IOException, ClassNotFoundException {
			this.socket = this.serverSocket.accept();
			InputStream inStream =  socket.getInputStream();
//			inputStream_ = inStream;
			OutputStream outStream = socket.getOutputStream();
			
			this.input = new DataInputStream(inStream);
			this.output = new ObjectOutputStream(outStream);
			
			GUIMessage_ = "Incoming connection from a client at " + socket.getRemoteSocketAddress().toString() + " accepted.\n" ;
			
		}

		public int receive() throws IOException {
			// TODO Auto-generated method stub
			int signal = this.input.readInt();
//			this.input.
			return signal;
		}
		
		public void send(BoxContainer Movings) throws java.net.SocketException {
			
			try {
				this.output.writeObject(Movings);
				this.output.flush();
				this.output.reset();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		public String getGUIMessage() {
			// TODO Auto-generated method stub
			return this.GUIMessage_;
		}

		public boolean myTurn() {
			// TODO Auto-generated method stub
//			this.myTurn_ = !this.myTurn_;
			return myTurn_;
		}


		public void myTurnNow( boolean turn) {
			// TODO Auto-generated method stub
			this.myTurn_ = turn;
			
		}


		public boolean isConnected() {
			// TODO Auto-generated method stub
			return this.socket.isConnected();
		}

		public boolean readyForAttack() {
			// TODO Auto-generated method stub
			return ready_;
		}

		public void shipPlaced( boolean placed ) {
			this.ready_ = placed;
			if(ready_ == true)
				System.out.println("\nlocal finished placing ships\n");
		}

		public void closeSocket() {
			try {
				this.serverSocket.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		public boolean isClient() {
			// TODO Auto-generated method stub
			return false;
		}
		
	}
	
