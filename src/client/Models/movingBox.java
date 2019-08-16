package client.Models;

/**
 * Contain speed and current position of an moving object
 * @author rongyi
 *
 */
public class movingBox {
	public float x,y, dx ,dy;
	
	/**
	 * 
	 * @param a : position
	 * @param b : position
	 */
	public movingBox(float a, float b) {
		x = a;
		y = b;
		dx = 0;
		dy = 0;
	}

}
