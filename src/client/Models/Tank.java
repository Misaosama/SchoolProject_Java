package client.Models;

import java.awt.*;
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
	
	public List<Bullet> newBullets;
	
	private File file;
	private BufferedImage img;
	
	public Tank(float x, float y, int size, int health, boolean whichImage) {
		super(x,y);
		this.x = x;
		this.y = y;
		this.size = size;
		this.health = health;
		
	}
	

	


}
