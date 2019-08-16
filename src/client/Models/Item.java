package client.Models;

/**
 * All basic items for objects.
 * @author rongyi
 * @version 1.0
 *
 */
public class Item implements java.io.Serializable {
	public float c1,c2,c3;


	
	public float x,y,w,h;
	
	public int r, c;
	
	/**
	 * 
	 * @param x : current position
	 * @param y : current position
	 * @param width : width
	 * @param height : height
	 * @param i : initial position
	 * @param j : initial position
	 */
	public Item(float x, float y, float width, float height, int i, int j ) {
		this.x = x;
		this.y = y;
		w = width;
		h = height;
		r = i;
		c = j;
		c1 = (float)100.0;
		c2 = (float)100.0; 
		c3 = (float)1.0;
	}
	
	

}

class Wall extends Item{
	public Wall(float x, float y, float width, float height, int i, int j) {
		super(x,y,width,height, i, j);
		c1 = (float) 0.9; c2 = (float) 0.9; c3 = (float) 0.9;
	}
}

class mKit extends Item{

	public mKit(float x, float y, float width, float height, int i, int j) {
		super(x, y, width, height, i, j);
		c1 = 1; c2 = 0; c3 = 0;
	}
}

class sKit extends Item{

	public sKit(float x, float y, float width, float height, int i, int j) {
		super(x, y, width, height,i ,j);
		c1 = 0; c2 = (float) 0.9; c3 = 0;
	}
}

