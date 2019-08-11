package client.Models;

import java.util.ArrayList;
import java.util.List;

public class Simulator {
	
	private static final int MAP_WIDTH = 2000;
	private static final int MAP_HEIGTH = 2000;
	private static final int WALL_SIZE = 20;
	
	private Tank tank;
	//private ArrayList<Item> walls;
	//private ArrayList<Item> kits;
	private List<Bullet> bullets;
	
	private int[][] map;
	
	public Simulator(Tank t, int[][] m, 
			ArrayList<Bullet> b ) {
		tank = t;
		bullets = b;
		map = m;
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

		}
		
		
	}

}
