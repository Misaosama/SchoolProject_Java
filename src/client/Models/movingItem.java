package client.Models;

/**
 * Represents all movable objects in this game, with position data, size data,
 * and color values.
 * @author rongyi
 *
 */
public class movingItem extends Item{
	/**
	 * 
	 * @param x : current position
	 * @param y : current position
	 * @param width: width
	 * @param height : height
	 * @param c1 : color
	 * @param c2 : color
	 * @param c3 : color
	 * @param i : initial position
	 * @param j : initial position
	 */
	public movingItem(float x, float y, float width, float height
			,float c1, float c2, float c3, int i, int j) {
		super(x, y, width, height, i, j);
		this.c1 = c1; this.c2 = c2; this.c3 = c3;
	}
	
	/**
	 * 
	 * @return current x value
	 */
	public float getx() {
		return x;
	}
	
	/**
	 * 
	 * @return curret y value.
	 */
	public float gety() {
		return y;
	}
}

