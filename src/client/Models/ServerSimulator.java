package client.Models;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


public class ServerSimulator {
	private int[][] map;
	private ArrayList<Tank> tanks;
	
	public ServerSimulator(int[][] m, ArrayList<Tank> t) {
		map = m;
		tanks = t;
		
	}
	
	public boolean update() {
		for(Tank tank : tanks) {
			tank.box.x+= tank.dx;
			tank.box.y+=tank.dy;
		}
		
		
		
		
		
		return false;
	}

}
