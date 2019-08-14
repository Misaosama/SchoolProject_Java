package trying;

import java.io.*;
import java.net.*;
import java.util.*;

import View.Box;

import java.nio.charset.*;

public class TestServer {
	
	public static void main(String[] args) throws IOException, ClassNotFoundException {
		int port = 8189;
		BoxContainer bc = new BoxContainer();
		bc.addBox(new Box((float)10.0, (float)10.0, 30,23, (float)100.0, (float)100.0, (float)1.0, (long)123, 0 ) );
		
		
		try (ServerSocket s = new ServerSocket(port)){
			try (Socket incoming = s.accept()){
				System.out.printf("Incoming connection from a client at %s.\n",
						incoming.getRemoteSocketAddress().toString());
					InputStream inStream =  incoming.getInputStream();
					OutputStream outStream = incoming.getOutputStream();
					
//					try (Scanner in = new Scanner(inStream)){
//						
//						PrintWriter out = new PrintWriter(new 
//								OutputStreamWriter(outStream, StandardCharsets.UTF_8), true /*autoFlush */);
//							out.println("Enter any command or BYE to exit");
//							
//							boolean done = false;
//							while (!done && in.hasNextLine()) {
//								String line = in.nextLine();
//								System.out.printf("Command received from the client: %s\n", line);
//								line = line.toLowerCase();
//								out.println("Echo: " + line);
//								System.out.printf("Response sent to the client: %s\n", line);
//								if (line.trim().equals("bye")) done = true;
//							}
//					}
					
					DataInputStream input = new DataInputStream (inStream);
//					DataOutputStream output = new DataOutputStream(outStream);
//					System.out.println("Serializing");
					try( 	
//							ObjectInputStream ois = new ObjectInputStream(incoming.getInputStream());
							ObjectOutputStream oos = new ObjectOutputStream(incoming.getOutputStream()) ) 
										{
						
//						Box a = new Box((float)10.0, (float)10.0, 30,30, (float)100.0, (float)100.0, (float)1.0, (long)123, 0 );
//						oos.writeObject(a);
//						oos.flush();
//			            System.out.println("Object has been serialized");
			            
			            while(true) {
			            	oos.writeObject(bc);
			            	int a = input.readInt();
			            }
			            
//			            Box b = (Box)ois.readObject();
//			            System.out.println("Object has been deserialized "); 
//				        System.out.println("b(float) = " + b.b); 
//				        System.out.println("id = " + b.id);
				        
//			            Box b = new Box((float)10.0, (float)10.0, 30,30, (float)100.0, (float)100.0, (float)1.0, (long)123, 0 );
//				        BoxContainer bc =  new BoxContainer();
//				        bc.addBox(a); bc.addBox(b);
//			            oos.writeObject(bc);
						
					}catch (IOException e) {
						e.printStackTrace();
					}

					
			}
		}
	}
	


}

class ServerTcp implements Runnable{
	
	private BoxContainer allMovingObjects_;
	
	public ServerTcp( BoxContainer bc ) {
		this.allMovingObjects_ = bc;
		this.allMovingObjects_.addBox(new Box((float)10.0, (float)10.0, 30,23, (float)100.0, (float)100.0, (float)1.0, (long)123, 0 ) );
	}

	@Override
	public void run() {
		
	}
	
}

