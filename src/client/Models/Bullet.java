package client.Models;

import java.io.Serializable;

/**
 * Bullets class, emitted by tanks.
 * @author rongyi
 *
 */
public class Bullet extends movingBox implements java.io.Serializable{
	public float dx;
	public float dy;
	public float size;

	
	public movingItem box;
	
	/**
	 * 
	 * @param x : current position
	 * @param y : current position
	 * @param dx : speed
	 * @param dy : speed
	 * @param size : size as a square.
	 */
	public Bullet(float x, float y, float dx, float dy, float size){
		super(x,y);
		this.x = x;
		this.y = y;
		this.dx = dx;
		this.dy = dy;
		this.size = size;
		box = new movingItem(x,y,(float)size,(float)size,
				(float)0,(float)1,(float)1,-1,-1);
	}
}