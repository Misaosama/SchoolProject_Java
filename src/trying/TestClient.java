package trying;
import java.io.*;
import java.net.*;
import java.util.*;

import View.Box;

import java.nio.charset.*;
import trying.BoxContainer;

//GUI related
import static org.lwjgl.opengl.GL11.*;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

import Connect.ClientTCPConnection;

public class TestClient {
	
	private static final int DISPLAY_WIDTH = 700;
	private static final int DISPLAY_HEIGTH = 500;
	private static final int FRAMES_PER_SECOND = 30;
	private static final int MAP_WIDTH = 1500;
	private static final int MAP_HEIGTH = 900;
	
	protected BoxContainer clientMovings_;
	
	public TestClient() {
		clientMovings_ = new BoxContainer();
	}
	
	public static void main(String[] args) throws InterruptedException {
		TestClient mf = new TestClient();	
//		ClientTCPConnection tcpC = new ClientTCPConnection("localhost");
		DataStreamTestT testThread = new DataStreamTestT();
		Thread t = new Thread(testThread);
		t.start();
		
		
//		ClientTCPConnection ch = new ClientTCPConnection(tcpC, mf.clientMovings_, mf );
//		Thread cThread = new Thread(ch);
//		cThread.start();
		
		
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
//				try {
////					synchronized(tcpC) {
//						tcpC.send(1);
////					}
//					
//				} catch (IOException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
			}
			
			
			Display.update();
			Display.sync(FRAMES_PER_SECOND);
		}
		

		Display.destroy();
		System.exit(0);
		
	}
	
	private void render() {
		
		synchronized(this.clientMovings_) {
			for( Box box : this.clientMovings_.boxes_) {
				drawObject(box);
			}
		}
		
		
	}
	
	private void drawObject(Box box) {
		glColor3f(box.r, box.g, box.b);
		glBegin(GL_QUADS);
			glVertex2f(box.x, box.y);
			glVertex2f(box.x + box.w, box.y);
			glVertex2f(box.x + box.w, box.y + box.h);
			glVertex2f(box.x, box.y + box.h);
		glEnd();
	}
	
	
}

class DataStreamTestT implements Runnable{
	
	public DataStreamTestT() {
		
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			Socket s = new Socket("localhost", 8189);
			InputStream inStream = s.getInputStream();
			DataInputStream di = new DataInputStream(inStream);
			
			while(true) {
				int i = di.readInt();
				System.out.println("Received from server: " + i);
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
//		while
		
	}
	
}

