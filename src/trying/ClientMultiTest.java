package trying;

import java.io.IOException;
import Connect.ClientTCPConnection;
import View.Box;
import client.Models.Bullet;
import client.Models.Item;
import client.Models.ItemContainer;

//GUI related
import static org.lwjgl.opengl.GL11.*;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;


public class ClientMultiTest {
	private static final int DISPLAY_WIDTH = 700;
	private static final int DISPLAY_HEIGTH = 500;
	private static final int FRAMES_PER_SECOND = 30;
	private static final int MAP_WIDTH = 1500;
	private static final int MAP_HEIGTH = 900;
	private static final int SPEED = 4;
	
	protected ItemContainer clientMovings_;
	
	public ClientMultiTest() {
		clientMovings_ = new ItemContainer();
	}
	
	public static void main(String[] args) throws InterruptedException {
		ClientMultiTest mf = new ClientMultiTest();	
		ClientTCPConnection tcpC = new ClientTCPConnection("localhost");
		
		try {
			tcpC.connect();
		} catch (IOException e2) {
			// Connection Failed
			e2.printStackTrace();
			System.exit(0);
		}
		
		//wait until server tells all clients connected:
		System.out.println("Waiting other players...");
		Integer ID = -1;
		
		try {
			ID = tcpC.receiveID();
		} catch (ClassNotFoundException | IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		System.out.println("Receive id as: " + ID);
		
		ClientTCPConnectionHandler ch = new ClientTCPConnectionHandler(tcpC, mf.clientMovings_, mf );
		Thread cThread = new Thread(ch);
		cThread.start();
		
//		cThread.join();
		
		//GUI
		try {
			Display.setDisplayMode(new DisplayMode(DISPLAY_WIDTH, DISPLAY_HEIGTH));
			Display.setResizable(true);
			Display.create();

		} catch (LWJGLException e) {
			e.printStackTrace();
		}
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		glOrtho(0, DISPLAY_WIDTH, DISPLAY_HEIGTH, 0, 1, -1);
		glMatrixMode(GL_MODELVIEW);
		
		boolean up, left, right, down;
		up =left = right = down = false;
		
		while (!Display.isCloseRequested()) {

//			if (Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {
//				break;
//			}
			glClear(GL_COLOR_BUFFER_BIT);//GL_COLOR_BUFFER_BIT: import static org.lwjgl.opengl.GL11.*;
			
//			glTranslatef(-camera.xmov, -camera.ymov, 0);
			glTranslatef(0, 0, 0);
			
			mf.render();
			
			if (Display.isActive()) {
				
				while (Mouse.next()) {
					
					if (Mouse.getEventButtonState() ) {	

						float xmouse = Mouse.getX(); //+ camera.x;
						float ymouse = DISPLAY_HEIGTH - Mouse.getY(); // + camera.y;
						
						try {
							tcpC.send(5);
							tcpC.sendFloat(xmouse);
							tcpC.sendFloat(ymouse);
						} catch (IOException e) {
							e.printStackTrace();
						}						
						
					}
				}
				
				while (Keyboard.next()) {
					
					if (Keyboard.getEventKey() == Keyboard.KEY_S
							|| Keyboard.getEventKey() == Keyboard.KEY_DOWN) {
						if (Keyboard.getEventKeyState()) {
//							character.yVel = 5;
							
//							box.y-=30;
//							System.out.println(mf.box.y);
							down = true;
						} else {
							down = false;
							if (!up) {
//								character.yVel = 0;
//								box.moveDown();
							}
						}
					}else {
//						character.xVel = 0;
//						character.yVel = 0;
					}
					
					
				}
			}
			if(down) {
//				mf.box.moveDown(); 
				try {
//					synchronized(tcpC) {
						tcpC.send(1);
//					}
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			
			Display.update();
			Display.sync(FRAMES_PER_SECOND);
		}
		

		Display.destroy();
		System.exit(0);
		
	}
	
	private void render() {
		
		synchronized(this.clientMovings_) {
			for( Item item : this.clientMovings_.items_) {
				drawObject(item);
			}
		}
		
		
	}
	
	private void drawObject(Item box) {
		glColor3f(box.c1, box.c2, box.c3);
		glBegin(GL_QUADS);
			glVertex2f(box.x, box.y);
			glVertex2f(box.x + box.w, box.y);
			glVertex2f(box.x + box.w, box.y + box.h);
			glVertex2f(box.x, box.y + box.h);
		glEnd();
	}
	
}

class ClientTCPConnectionHandler implements Runnable{
	
	private ClientTCPConnection connection_;
	private ItemContainer allMovingsReceived_;
	private ClientMultiTest cmt_;
	
	public ClientTCPConnectionHandler( ClientTCPConnection connection, 
			ItemContainer allMovingsToReceive, ClientMultiTest cmt ) {
		this.connection_ = connection;
		this.allMovingsReceived_ = allMovingsToReceive;
		this.cmt_ = cmt;
	}
	
	@Override
	public void run() {
		
		try {
//			this.connection_.connect();
			while(true) {
				ItemContainer receivedMovings = this.connection_.receive();
//				synchronized(connection_) {
//					this.connection_.send(-1);
//					System.out.println("sent");
//				}
				
//				synchronized(allMovingsReceived_) {
//					this.allMovingsReceived_.becomeThisBC(receivedMovings); 
//				}
				this.cmt_.clientMovings_ = receivedMovings;
//				System.out.println(receivedMovings.nofBoxes_);
				
			}
			
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		
	}
	
}
