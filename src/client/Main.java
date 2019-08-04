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
import client.Models.Item;
import client.Models.ItemFactory;

public class Main {
	private static final int DISPLAY_WIDTH = 700;
	private static final int DISPLAY_HEIGTH = 500;

	private static final int MAP_WIDTH = 1500;
	private static final int MAP_HEIGTH = 900;

	private static final int FRAMES_PER_SECOND = 30;

	static long ID = -1; // we get ID from the server side
	
	private List<Item> items;
	
	public static void main(String[] args) {
		
		int[][] map = readFile(new File("docs/map3.txt")) ;
		
		if(map != null) {
			for(int i = 0; i< map.length; i++) {
				for(int j = 0; j< map[i].length; j++) {
					System.out.print(map[i][j]);
				}
				System.out.println("");
			}
		}else {
			System.out.println("Error");
		}
		
		Main main = new Main();
		main.initOpenGl();
		//main.init();
		main.start();
	}

	public Main(){
		generateItems();
	}
	
	// use this function to create the walls
	private void generateItems() {
		items = new ArrayList<Item>();
		for(int i=0;i<10;i++) {
			items.add(ItemFactory.createItem(0,20*i,20*i,20,20));
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
                		}else {
                			temp[j] = 1;
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
		for(Item w : items) {
			drawWall(w);
		}

		
	}
	
	public void drawWall(Item wall) {
		glColor3f(wall.c1, wall.c2, wall.c3);
		glBegin(GL_QUADS);
			glVertex2f(wall.x, wall.y);
			glVertex2f(wall.x + wall.w, wall.y);
			glVertex2f(wall.x + wall.w, wall.y + wall.h);
			glVertex2f(wall.x, wall.y + wall.h);
		glEnd();
	}

}
