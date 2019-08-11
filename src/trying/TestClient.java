package trying;
import java.io.*;
import java.net.*;
import java.util.*;

import View.Box;

import java.nio.charset.*;
import trying.BoxContainer;

public class TestClient {
	
	public static void main (String[] args) throws UnknownHostException, IOException, ClassNotFoundException {
		
		String host_ = "localhost";
		int DEFAULT_PORT = 8189;
		Socket socket = new Socket(host_, DEFAULT_PORT);
		InputStream inStream = socket.getInputStream();
		OutputStream outStream = socket.getOutputStream();
		
//		Scanner input = new Scanner(inStream);
//		PrintWriter output = new PrintWriter(new OutputStreamWriter(outStream, StandardCharsets.UTF_8), true /*autoFlush */);
//		
//		
//		String message = input.nextLine();
//		System.out.println(String.format("Message %s received.\n", message));
//		
//		for( int i = 0; i < 9; i++) {
//			output.println(i);
//			message = input.nextLine();
//			System.out.println(String.format("Message %s received.\n", message));
//		}
//		
//		output.println("BYE");
		DataOutputStream  output = new DataOutputStream (outStream);
		
		try(	
//				ObjectOutputStream oos = new ObjectOutputStream(outStream);
				ObjectInputStream ois = new ObjectInputStream(inStream);) {
			Box a = null;
			System.out.println("Waiting for input");
			a = (Box) ois.readObject(); 
			
			System.out.println("Object has been deserialized "); 
	        System.out.println("width = " + a.w); 
	        System.out.println("height = " + a.h);
	        
			
			output.writeInt(1);
//			oos.writeObject(a);
//			oos.flush();
			System.out.println("Client Sent int 1");
			
			BoxContainer bc1 = (BoxContainer) ois.readObject();
			System.out.println("Object has been deserialized "); 
	        
			for ( int i = 0; i < bc1.nofBoxes_; i++) {
				System.out.println("width = " + bc1.boxes_.get(i).w); 
		        System.out.println("height = " + bc1.boxes_.get(i).h);
			}
	        
		}catch (IOException e) {
			e.printStackTrace();
		}
		

		
	}
	
	
}

//class BoxContainer{
//	public List<Box> boxes_;
//	public int nofBoxes_;
//	
//	public BoxContainer() {
//		
//	}
//	
//	public void addBox( Box b1) {
//		this.boxes_.add(b1);
//	}
//	
//}
