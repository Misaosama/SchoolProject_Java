package client.Models;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.awt.geom.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;


/**
 * Class for tank
 * @author formycat
 *
 */
public class Tank extends movingBox implements java.io.Serializable {
	public int health;
	//public float x, y, dx, dy;
	public int size;
	public int speed;
	
	
	public ArrayList<Bullet> newBullets;
	public movingItem box;
	
	private File file;
	private BufferedImage img;
	public int maxHealth;
	public int r,c;
	
	public boolean canFlash = true;
	public boolean canHeal = true;
	
	/**
	 * constructor
	 * @param x initial x position
	 * @param y initial y position
	 * @param size tank size
	 * @param health health
	 * @param whichImage which tank
	 * @param speed speed
	 */
	public Tank(float x, float y, int size, int health, boolean whichImage, int speed) {
		super(x,y);
		this.x = x;
		this.y = y;
		this.size = size;
		this.health = health;
		this.maxHealth = health;
		newBullets = new ArrayList<Bullet>();
		if(whichImage) {
			box = new movingItem(x,y,(float)size,(float)size,
					(float)0.7,(float)0.5,(float)0.6,-1,-1);
		}
		else {
			box = new movingItem(x,y,(float)size,(float)size,
					(float)0.8,(float)0.8,(float)0.8,-1,-1);
		}
		
		this.speed = speed;
		
		
	}
	
	/**
	 * access x
	 * @return x
	 */
	public float getx() {
		return box.x;
	}
	
	/**
	 * access y
	 * @return y
	 */
	public float gety() {
		return box.y;
	}
	
	/**
	 * decrement the health by damage
	 * @param d damage
	 */
	public void beAttacked(int d) {
		health -= d;
		//System.out.println(health);
		if(health <=0 && size != 20) { // do something if dead and not enemiy
			box.x = x;
			box.y = y;
			health = maxHealth;
		}
	}
	
	/**
	 * increment health
	 */
	public void healthKit(){
		health = maxHealth;
	}
	
	/**
	 * increment speed
	 */
	public void accelerate() {
		speed +=4;
	}
	
	/**
	 * decrement speed
	 */
	public void decelerate() {
		
		speed -=4;
		speed = Math.max(speed, 4);
	}
	
	/**
	 * shot interface
	 */
	public void shot() {
		
	}
	
	/**
	 * this is like an factory to create enemy tank
	 * @param x x
	 * @param y y
	 * @param size size
	 * @param health health
	 * @param Boss is the tank the boss
	 * @param i position index
	 * @param j position index
	 * @return the created enemy tank
	 */
	public static enemy createEnemy(float x, float y, int size, int health, boolean Boss, int i, int j) {
		return new enemy(x,y,size,health,Boss,i,j);
	}
	


}

/**
 * enemy tank
 * @author formycat
 *
 */
class enemy extends Tank{
	private boolean boss;
	
	
	/**
	 * constructor
	 * @param x x
	 * @param y y
	 * @param size size
	 * @param health health
	 * @param Boss is the boss
	 * @param i index
	 * @param j index
	 */
	public enemy(float x, float y, int size, int health, boolean Boss, int i, int j) {
		super(x,y,size,health,false,0);
		r = i;
		c = j;
	
		boss = Boss;
		if(Boss) {
			health *= 3;
			box = new movingItem(x,y,(float)size,(float)size,
					(float)1,(float)1,(float)0,-1,-1);
		}
		else {
			box = new movingItem(x,y,(float)size,(float)size,
					(float)0.5,(float)0.5,(float)0,-1,-1);
		}
			
	}
	
	/**
	 * enemy shot bullets
	 */
	public void shot() {
		if(boss) {
			newBullets.add(new Bullet(x+size/2, y+size/2, 0,8, 5));
			newBullets.add(new Bullet(x+size/2, y+size/2, 0,-8, 5));
			newBullets.add(new Bullet(x+size/2, y+size/2, -8,0, 5));
			newBullets.add(new Bullet(x+size/2, y+size/2, 8,0, 5));
			float s = (float) Math.sqrt(8) * 2;
			newBullets.add(new Bullet(x+size/2, y+size/2, s,s, 5));
			newBullets.add(new Bullet(x+size/2, y+size/2, s,-s, 5));
			newBullets.add(new Bullet(x+size/2, y+size/2, -s,s, 5));
			newBullets.add(new Bullet(x+size/2, y+size/2, -s,-s, 5));
		} else {
			newBullets.add(new Bullet(x+size/2, y+size/2, 0,8, 8));
			newBullets.add(new Bullet(x+size/2, y+size/2, 0,-8, 8));
			newBullets.add(new Bullet(x+size/2, y+size/2, -8,0, 8));
			newBullets.add(new Bullet(x+size/2, y+size/2, 8,0, 8));
			float s = (float) Math.sqrt(8) * 2;
			newBullets.add(new Bullet(x+size/2, y+size/2, s,s, 8));
			newBullets.add(new Bullet(x+size/2, y+size/2, s,-s, 8));
			newBullets.add(new Bullet(x+size/2, y+size/2, -s,s, 8));
			newBullets.add(new Bullet(x+size/2, y+size/2, -s,-s, 8));
		}
		

			

	}
	
}




