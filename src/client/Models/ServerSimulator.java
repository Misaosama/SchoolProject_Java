package client.Models;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


public class ServerSimulator {
	private int[][] map;
	private ArrayList<Tank> tanks;
	
	private static final int MAP_WIDTH = 2000;
	private static final int MAP_HEIGTH = 2000;
	private static final int WALL_SIZE = 20;
	
	
	public ServerSimulator(int[][] m, ArrayList<Tank> t) {
		map = m;
		tanks = t;
		
	}
	
	public boolean update() {
		for(Tank tank : tanks) {
			tank.box.x+= tank.dx;
			tank.box.y+=tank.dy;
			
			
			
			
			
			for(Bullet b : tank.newBullets) {
				b.box.x+= b.dx;
				b.box.y+= b.dy;
			}
		}
		
		
		
		
		
		return false;
	}

}
