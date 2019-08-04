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

public class Main {
	private static final int DISPLAY_WIDTH = 700;
	private static final int DISPLAY_HEIGTH = 500;

	private static final int MAP_WIDTH = 1500;
	private static final int MAP_HEIGTH = 900;

	private static final int FRAMES_PER_SECOND = 30;

	static long ID = -1; // we get ID from the server side
	
	public static void main(String[] args) {
		
		
		
		Main main = new Main();
		main.initOpenGl();
		//main.init();
		main.start();
	}

	public Main(){
		
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
			render();
			*/

			Display.update();
			Display.sync(FRAMES_PER_SECOND);
		}
		//closingOperations();
	}

}
