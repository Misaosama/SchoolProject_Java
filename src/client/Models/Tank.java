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



public class Tank extends movingBox {
	public int health;
	//public float x, y, dx, dy;
	public int size;
	public int speed;
	
	public ArrayList<Bullet> newBullets;
	public movingItem box;
	
	private File file;
	private BufferedImage img;
	private int maxHealth;
	public int r,c;
	
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
					(float)0.5,(float)0.5,(float)0.5,-1,-1);
		}
		else {
			box = new movingItem(x,y,(float)size,(float)size,
					(float)0.8,(float)0.8,(float)0.8,-1,-1);
		}
		
		this.speed = speed;
		
		
	}
	
	public float getx() {
		return box.x;
	}
	
	public float gety() {
		return box.y;
	}
	
	public void beAttacked(int d) {
		health -= d;
		if(health <=0) { // do something if dead
			
		}
	}
	
	public void healthKit(){
		health = maxHealth;
	}
	
	public void accelerate() {
		speed +=4;
	}
	
	public void decelerate() {
		
		speed -=4;
		speed = Math.max(speed, 4);
	}
	
	public void shot() {
		
	}
	
	public static enemy createEnemy(float x, float y, int size, int health, boolean Boss, int i, int j) {
		return new enemy(x,y,size,health,Boss,i,j);
	}
	


}

class enemy extends Tank{
	private boolean boss;
	
	
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
	
	public void shot() {
		newBullets.add(new Bullet(x+size/2, y+size/2, 0,8, 5));
		newBullets.add(new Bullet(x+size/2, y+size/2, 0,-8, 5));
		newBullets.add(new Bullet(x+size/2, y+size/2, -8,0, 5));
		newBullets.add(new Bullet(x+size/2, y+size/2, 8,0, 5));
			float s = (float) Math.sqrt(8) * 2;
			newBullets.add(new Bullet(x+size/2, y+size/2, s,s, 5));
			newBullets.add(new Bullet(x+size/2, y+size/2, s,-s, 5));
			newBullets.add(new Bullet(x+size/2, y+size/2, -s,s, 5));
			newBullets.add(new Bullet(x+size/2, y+size/2, -s,-s, 5));

			

	}
	
}




