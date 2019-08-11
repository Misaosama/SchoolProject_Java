package View;

import static org.lwjgl.opengl.GL11.*;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;





public class MainFrame {
	private static final int DISPLAY_WIDTH = 700;
	private static final int DISPLAY_HEIGTH = 500;
	private static final int FRAMES_PER_SECOND = 30;
	private static final int MAP_WIDTH = 1500;
	private static final int MAP_HEIGTH = 900;
	Box box = new Box((float)10.0, (float)10.0, 30,30, (float)100.0, (float)100.0, (float)1.0, (long)123, 0 );
	
	public static void main(String[] args) {
		MainFrame mf = new MainFrame();
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
		
		
		System.out.println();
		Box box = new Box((float)1.0, (float)1.0, 30,30, (float)100.0, (float)100.0, (float)1.0, (long)123, 0 );
//		float x = (float) 1.0;
//		box = new Box( x, float y, int width, int height, float r, float g, float b, long id, int xp)
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
//			System.out.println("rendered");
			//
			if (Display.isActive()) {
				while (Keyboard.next()) {
					
					if (Keyboard.getEventKey() == Keyboard.KEY_S
							|| Keyboard.getEventKey() == Keyboard.KEY_DOWN) {
						if (Keyboard.getEventKeyState()) {
//							character.yVel = 5;
							
//							box.y-=30;
							System.out.println(mf.box.y);
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
				mf.box.moveDown(); 
			}
			
			Display.update();
			Display.sync(FRAMES_PER_SECOND);
		}
		

		Display.destroy();
		System.exit(0);
		
	}
	
	private void render() {
		
		glColor3f(box.r, box.g, box.b);
		glBegin(GL_QUADS);
			glVertex2f(box.x, box.y);
			glVertex2f(box.x + box.w, box.y);
			glVertex2f(box.x + box.w, box.y + box.h);
			glVertex2f(box.x, box.y + box.h);
		glEnd();
	}
	
}
