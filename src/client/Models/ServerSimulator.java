package client.Models;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


public class ServerSimulator {
	private int[][] map;
	private List<Tank> tanks;
	
	private int MAP_WIDTH ;
	private int MAP_HEIGTH ;
	
	private static final int WALL_SIZE = 20;
	
	
	public ServerSimulator(int[][] m, List<Tank> t) {
		map = m;
		tanks = t;
		MAP_WIDTH = map[0].length*WALL_SIZE;
		
	}
	
	public boolean update() {
		for(Tank tank : tanks) {
			tank.box.x+= tank.dx;
			tank.box.y+=tank.dy;
			//
			
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
			}
			
			
			
			
			
			
			for(Bullet b : tank.newBullets) {
				b.box.x+= b.dx;
				b.box.y+= b.dy;
			}
		}
		
		
		
		
		
		return false;
	}

}
