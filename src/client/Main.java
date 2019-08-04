package client;

import static org.lwjgl.opengl.GL11.*;

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
