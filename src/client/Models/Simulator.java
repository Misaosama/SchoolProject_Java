package client.Models;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Simulator {
	
	private static final int MAP_WIDTH = 2000;
	private static final int MAP_HEIGTH = 2000;
	private static final int WALL_SIZE = 20;
	
	private Tank tank;
	//private ArrayList<Item> walls;
	private ArrayList<Item> kits;
	private List<Bullet> bullets;
	
	private int[][] map;
	
	public Simulator(Tank t, int[][] m, 
			ArrayList<Bullet> b, ArrayList<Item> k ) {
		tank = t;
		bullets = b;
		map = m;
		kits = k;
	}
	
	public void update() {
		tank.box.x += tank.dx;
		tank.box.y += tank.dy;
		
		//Check if the tank is out the map
		if(tank.box.x<0 || tank.box.x>MAP_WIDTH) tank.box.x-=tank.dx;
		if(tank.box.y<0 || tank.box.y>MAP_HEIGTH) tank.box.y-=tank.dy;
		
		int row = (int)(tank.box.y)/WALL_SIZE;
		int col = (int)(tank.box.x)/WALL_SIZE;
		int row2 = (int)(tank.box.y+tank.size)/WALL_SIZE;
		int col2 = (int)(tank.box.x+tank.size)/WALL_SIZE;
		
		int maxRow = map.length - 1;
		int maxCol = map[0].length - 1;
		if(row2 > maxRow || col2 > maxRow) {
			if(row2>maxRow+2||col2>maxCol+3) {
				tank.box.x -= tank.dx;
				tank.box.y -= tank.dy;
			}
		} else if(map[row][col]==1 || map[row2][col]==1 ||
		          map[row][col2]==1 || map[row2][col2]==1) {
			tank.box.x -= tank.dx;
			tank.box.y -= tank.dy;
			if(tank.speed>6) tank.decelerate();

		} else if (map[row][col]==2 || map[row2][col]==2 ||
		          map[row][col2]==2 || map[row2][col2]==2) {
			// When get a healthKit, add health and remove its kit
			tank.healthKit();
			map[row][col]= 0; map[row2][col]=0; map[row][col2]=0; map[row2][col2]=0;
			Iterator<Item> itr = kits.iterator();
			while(itr.hasNext()) {
				Item kit = itr.next();
				if(kit.r == row || kit.r == row2) {
					itr.remove();
				}
			}
			
			
		} else if (map[row][col]==3 || map[row2][col]==3 ||
		          map[row][col2]==3 || map[row2][col2]==3) { 
			//When get a speedKit, increase speed and remove that kit
			tank.accelerate();
			map[row][col]= 0; map[row2][col]=0; map[row][col2]=0; map[row2][col2]=0;
			Iterator<Item> itr = kits.iterator();
			while(itr.hasNext()) {
				Item kit = itr.next();
				if(kit.r == row || kit.r == row2) {
					itr.remove();
				}
			}
		} 
		
		//check for bullet 
		Iterator<Bullet> itr = bullets.iterator();
		while (itr.hasNext()) {
			Bullet b = itr.next();
			b.box.x+=b.dx;
			b.box.y+=b.dy;
			
			if(b.box.x<0 || b.box.x>MAP_WIDTH) itr.remove();
			if(b.box.y<0 || b.box.y>MAP_HEIGTH) itr.remove();
			
			int r = (int)((b.box.y)/WALL_SIZE);
			int c = (int)((b.box.x)/WALL_SIZE);
			int r2 = (int)((b.box.y+b.size)/WALL_SIZE);
			int c2 = (int)((b.box.x+b.size)/WALL_SIZE);
			
			if(r2 > maxRow || c2 > maxRow) {
				if(r2>maxRow+2||c2>maxCol+3) {
					itr.remove();
				}
			} else if(map[r][c]==1 || map[r2][c]==1 ||
			          map[r][c2]==1 || map[r2][c2]==1) {
				itr.remove();

			}
			
			
		}
		
		
		
	}

}
