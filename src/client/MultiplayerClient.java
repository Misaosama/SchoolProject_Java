package client;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import Connect.ClientTCPConnection;
import client.Main;
import client.Models.Item;
import client.Models.ItemContainer;
import client.Models.ItemFactory;

//GUI related
import static org.lwjgl.opengl.GL11.*;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

/**
 * This class is responsible to get data(moving object's position, etc.) from the server 
 * and keep displaying all objects in this game. 
 * @author rongyi
 * @version 1.0
 *
 */
public class MultiplayerClient {
	private static final int DISPLAY_WIDTH = 900;
	private static final int DISPLAY_HEIGTH = 600;
	private static final int FRAMES_PER_SECOND = 30;
	private static final int WALL_SIZE = 15;
	private int portNum_;
	private String ip_;
	private List<Item> walls;
	private int[][] map;
	protected ItemContainer clientMovings_;
	
	/**
	 * 
	 * @param port_num : the server's port number it wants to connect to
	 * @param ip : the ip address of the server. Can be "localhost".
	 */
	public MultiplayerClient( int port_num, String ip ) {
		this.portNum_ = port_num;
		this.ip_ = ip;
		clientMovings_ = new ItemContainer();
		map = Main.readFile(new File("docs/map3.txt")) ;
		walls = new ArrayList<Item>();
		for(int i=0;i<map.length;i++) {
			for(int j=0;j<map[0].length;j++) {
				if(map[i][j]==1) {
					walls.add(ItemFactory.createItem(1, WALL_SIZE*j, WALL_SIZE*i,
							WALL_SIZE, WALL_SIZE,i,j));
				}
			}
		}
	}
	
	/**
	 * Start the display and start getting data from the server. 
	 * @throws IOException
	 */
	public void ClientStart() throws IOException {
		ClientTCPConnection tcpC = new ClientTCPConnection(this.ip_, this.portNum_);
		tcpC.connect();
		//wait until server tells all clients connected:
				System.out.println("Waiting other players...");
				Integer ID = -1;
				
				try {
					ID = tcpC.receiveID();
				} catch (ClassNotFoundException | IOException e1) {
					e1.printStackTrace();
				}
				System.out.println("Receive id as: " + ID);
				
				ClientTCPConnectionHandler ch = new ClientTCPConnectionHandler(tcpC, this.clientMovings_, this );
				Thread cThread = new Thread(ch);
				cThread.start();
				
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
					glClear(GL_COLOR_BUFFER_BIT);//GL_COLOR_BUFFER_BIT: import static org.lwjgl.opengl.GL11.*;
					glTranslatef(0, 0, 0);
					
					this.render();
					
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
							
							if (Keyboard.getEventKey() == Keyboard.KEY_W
									|| Keyboard.getEventKey() == Keyboard.KEY_UP) {
								if (Keyboard.getEventKeyState()) {
									up = true;
								} else {
									up = false;
								}
							}
							if (Keyboard.getEventKey() == Keyboard.KEY_S
									|| Keyboard.getEventKey() == Keyboard.KEY_DOWN) {
								if (Keyboard.getEventKeyState()) {
									down = true;
								} else {
									down = false;
								}
							}
							if (Keyboard.getEventKey() == Keyboard.KEY_D
									|| Keyboard.getEventKey() == Keyboard.KEY_RIGHT) {
								if (Keyboard.getEventKeyState()) {
									right = true;
								} else {
									right = false;
								}
							}
							if (Keyboard.getEventKey() == Keyboard.KEY_A
									|| Keyboard.getEventKey() == Keyboard.KEY_LEFT) {
								if (Keyboard.getEventKeyState()) {
									left = true;
								} else {
									left = false;
								}
							}
							
						}
					}
					
					try {
						if(down) {
							tcpC.send(1);
						}else if (up) {
							tcpC.send(2);
						}
						if(left) {
							tcpC.send(3);
						}else if (right) {
							tcpC.send(4);
						}
					} catch (IOException e) {
						e.printStackTrace();
					}
					Display.update();
					Display.sync(FRAMES_PER_SECOND);
				}
			tcpC.disconnect();
			Display.destroy();
		
	}
	
	/**
	 * The helper function used to display everything in this game.
	 */
	private void render() {
		
		synchronized(this.clientMovings_) {
			for( Item item : this.clientMovings_.items_) {
				drawObject(item);
			}
			
			for(Item wall : walls) {
				drawObject(wall);
			}
		}
		
		
	}
	
	/**
	 * Display an item.
	 * @param box an item to be displayered, everything is displayed as an item type. 
	 */
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

/**
 * 
 * This class is used for multithreading, it keeps receiving data from the server.
 * @author rongyi
 * @version 1.0
 *
 */
class ClientTCPConnectionHandler implements Runnable{
	
	private ClientTCPConnection connection_;
	private ItemContainer allMovingsReceived_;
	private MultiplayerClient cmt_;
	
	public ClientTCPConnectionHandler( ClientTCPConnection connection, 
			ItemContainer allMovingsToReceive, MultiplayerClient cmt ) {
		this.connection_ = connection;
		this.allMovingsReceived_ = allMovingsToReceive;
		this.cmt_ = cmt;
	}
	
	/**
	 * Override function.
	 */
	@Override
	public void run() {
		
		try {
			while(true) {
				ItemContainer receivedMovings = this.connection_.receive();
				this.cmt_.clientMovings_ = receivedMovings;			
			}
				
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
	}
	
}
