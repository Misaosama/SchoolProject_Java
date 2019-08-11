package client;

import static org.lwjgl.opengl.GL11.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

import client.Models.Tank;
import client.Models.movingBox;
import client.Models.Bullet;
import client.Models.Item;
import client.Models.ItemFactory;

public class Main {
	private static final int DISPLAY_WIDTH = 700;
	private static final int DISPLAY_HEIGTH = 500;

	private static final int MAP_WIDTH = 1500;
	private static final int MAP_HEIGTH = 900;

	private static final int FRAMES_PER_SECOND = 30;
	
	private static final int WALL_SIZE = 20;
	private static final int TANK_SIZE = 5;
	
	

	static long ID = -1; // we get ID from the server side
	
	private List<Item> walls;
	private List<Item> kits;
	private int[][] map;
	
	private Camera camera;
	private Tank tank;
	private ArrayList<Bullet> bullets;
	
	public static void main(String[] args) {
		
		Main main = new Main();
		main.initOpenGl();
		main.init();
		main.start();
	}

	public Main(){
		map = readFile(new File("docs/map2.txt")) ;
		generateItems();
	}
	
	// use this function to create the walls
	private void generateItems() {
		
		
		
		walls = new ArrayList<Item>();
		for(int i=0;i<map.length;i++) {
			for(int j=0;j<map[0].length;j++) {
				if(map[i][j]==1) {
					walls.add(ItemFactory.createItem(1, WALL_SIZE*j, WALL_SIZE*i,
							WALL_SIZE, WALL_SIZE));
				}
			}
		}
		
		kits = new ArrayList<Item>();
		for(int i=0;i<map.length;i++) {
			for(int j=0;j<map[0].length;j++) {
				if(map[i][j]==2||map[i][j]==3) {
					kits.add(ItemFactory.createItem(map[i][j], 
							WALL_SIZE*j, WALL_SIZE*i,
							WALL_SIZE, WALL_SIZE));
				}
			}
		}
	}
	
    
	
	
	/** Initializing OpenGL functions */
	private void initOpenGl() {

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
	}
	
	/** Setting up screen, establishing connections (TCP, UPD) with server, etc. */
	private void init() {

//		connections = new TcpConnection(this, server_ip, server_port_tcp);
//
//		if ((ID = connections.getIdFromServer()) == -1) {
//			System.err.println("cant get id for char");
//		}
		
//		obstacles = connections.getMapDetails();

		tank = new Tank(0,WALL_SIZE,TANK_SIZE,TANK_SIZE,true);
		bullets = new ArrayList<Bullet>();
		camera = new Camera(0, 0);
		ArrayList<movingBox> movingObjects = new ArrayList<movingBox>();

//		new Thread(new UdpConnection(this, connections, client_port_udp)).start();
	}
	
	/** Game loop */
	private void start() {

		while (!Display.isCloseRequested()) {

			glClear(GL_COLOR_BUFFER_BIT);

			/*
			if (Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {
				closingOperations();
			}

			handlingEvents();
			sendCharacter();
			update();
			*/
			render();
			

			Display.update();
			Display.sync(FRAMES_PER_SECOND);
		}
		//closingOperations();
	}
	
	/** Rendering obstacles, players and bullets */
	private void render() {
		for(Item w : walls) {
			drawItem(w);
		}
		
		for(Item k : kits) {
			drawItem(k);
		}

		
	}
	
	public void drawItem(Item wall) {
		glColor3f(wall.c1, wall.c2, wall.c3);
		glBegin(GL_QUADS);
			glVertex2f(wall.x, wall.y);
			glVertex2f(wall.x + wall.w, wall.y);
			glVertex2f(wall.x + wall.w, wall.y + wall.h);
			glVertex2f(wall.x, wall.y + wall.h);
		glEnd();
	}
	
	
	
	
	/**
	 * Camera shows map regarding main character's position
	 */
	private class Camera {

		private float x;
		private float y;

		private float xmov;
		private float ymov;

		Camera(float x, float y) {

			this.x = x;
			this.y = y;
			xmov = 0;
			ymov = 0;
		}

		private void update(Tank character) {

			float xnew = character.x, ynew = character.y;
			float xCam = Math.min(Math.max(0, (xnew + character.size / 2) - DISPLAY_WIDTH / 2),
					MAP_WIDTH - DISPLAY_WIDTH);
			float yCam = Math.min(Math.max(0, (ynew + character.size / 2) - DISPLAY_HEIGTH / 2),
					MAP_HEIGTH - DISPLAY_HEIGTH);

			xmov = xCam - x;
			x = xCam;

			ymov = yCam - y;
			y = yCam;
		}
	}
	
	
	
	
	
	/**
     * construct and initialize a ROW row and COL columns GRID
     * with all integer 0
     * @param input_file the input file we are going to read
     * @return return a string message of error or success
     */
    public static int[][] readFile(File input_file){
    	int [][] map;
        try {
            BufferedReader br = new BufferedReader(new FileReader(input_file));
            String size;
            size = br.readLine();
            int seperate = size.indexOf(',');
            if(seperate<1) {
                return null;
            }
            String row_st = size.substring(0, seperate);
            String col_st = size.substring(seperate + 2);
            //check if row_st is number
            for (int i = row_st.length();--i>=0;){
                if (!Character.isDigit(row_st.charAt(i))){
                    return null;
                }
            }
            //check if col_st is number
            for (int i = col_st.length();--i>=0;){
                if (!Character.isDigit(col_st.charAt(i))){
                    return null;
                }
            }
            int row = Integer.valueOf(row_st);
            int col = Integer.valueOf(col_st);
            if((row < 3)||(col < 3)) {
                return null;
            }
            map = new int [row][col];
            for(int i = 0; i < row; i++) {
            	for(int j = 0; j < col; j++) {
            		map[i][j] = 0;
            	}
            }
            
            //read the grid from file
            String st;
            for(int i = 0; i < row; i++) {
                if ((st = br.readLine()) == null) {
                    return null;
                }
                String[] this_line = new String[col];
                this_line = st.split(",");
                int[] temp = new int[this_line.length];
                
                for(int j = 0; j< this_line.length; j++) {
                	this_line[j] = this_line[j].trim();
                	try {
                		if(Integer.valueOf(this_line[j])==0) {
                			temp[j] = 0;
                		}else if(Integer.valueOf(this_line[j])==1) {
                			temp[j] = 1;
                		}else if(Integer.valueOf(this_line[j])==2) {
                			temp[j] = 2;
                		}else if(Integer.valueOf(this_line[j])==3) {
                			temp[j] = 3;
                		}else if(Integer.valueOf(this_line[j])==4) {
                			temp[j] = 4;
                		}else if(Integer.valueOf(this_line[j])==5) {
                			temp[j] = 5;
                		}
                	}catch(Exception e) {
                		return null;
                		
                	}
                }

                map[i] = temp;
            }
            br.close();
            
        } catch (Exception e) {
            return null;
        }
        return map;
    }

}
