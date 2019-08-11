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
	
	public ArrayList<Bullet> newBullets;
	public movingItem box;
	
	private File file;
	private BufferedImage img;
	private int maxHealth;
	
	public Tank(float x, float y, int size, int health, boolean whichImage) {
		super(x,y);
		this.x = x;
		this.y = y;
		this.size = size;
		this.health = health;
		this.maxHealth = health;
		newBullets = new ArrayList<Bullet>();
		if(whichImage) {
			box = new movingItem(x,y,(float)size,(float)size,
					(float)0.5,(float)0.5,(float)0.5);
		}
		else {
			box = new movingItem(x,y,(float)size,(float)size,
					(float)0.8,(float)0.8,(float)0.8);
		}
		
		
	}
	
	public float getx() {
		return box.x;
	}
	
	public float gety() {
		return box.y;
	}
	
	public void healthKit(){
		health = maxHealth;
	}
	

	


}
