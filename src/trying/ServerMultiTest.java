package trying;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import Connect.ServerTCPConnection;
import View.Box;

public class ServerMultiTest {
	
	public static void main(String[] args) throws ClassNotFoundException {
		
		Box a = new Box((float)10.0, (float)10.0, 30,23, (float)100.0, (float)100.0, (float)1.0, (long)123, 0 );
		Box b = new Box((float)50.0, (float)50.0, 13,30, (float)100.0, (float)100.0, (float)1.0, (long)123, 0 );
        BoxContainer Movings =  new BoxContainer();
        Movings.addBox(a); Movings.addBox(b);
		
		try {
			ServerTCPConnection  tcpS = new ServerTCPConnection();
			tcpS.connect();
//			tcpS.connect();
//			tcpS.send(Movings);
			ServerTCPConnectionHandler ch =  new ServerTCPConnectionHandler(tcpS, Movings);
			Thread sThread = new Thread(ch);
//			sThread.start();
			
			try {
				while(true) {
					tcpS.send(Movings);
					System.out.println("sent");
					int r = tcpS.receive();
					System.out.println(r);
					TimeUnit.MILLISECONDS.sleep(30);
				}
			}catch(IOException | InterruptedException e) {
				return;
			}
			
			

			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}

class ServerTCPConnectionHandler implements Runnable{
	
	private ServerTCPConnection connection_;
	private BoxContainer allMovings_;
	
	public ServerTCPConnectionHandler( ServerTCPConnection connection, BoxContainer bc ) {
		this.connection_ = connection;
		this.allMovings_ = bc;
	}
	
	@Override
	public void run() {
		
		try {
			while(true) {
				int clientResponse = this.connection_.receive();
//				System.out.println("Received");
				if( clientResponse == 1 ) {
					allMovings_.boxes_.get(0).moveDown();
//					tcpS.send(Movings);
//					int re = tcpS.receive();
				}
			}
		}catch (IOException e) {
			// TODO Auto-generated catch block
			return;
		}
		

		
			
		
		
	}
	
}
