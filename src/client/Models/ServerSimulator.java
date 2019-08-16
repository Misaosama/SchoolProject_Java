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
	
	private static final int WALL_SIZE = 15;
	
	
	public ServerSimulator(int[][] m, List<Tank> t) {
		map = m;
		tanks = t;
		MAP_WIDTH = map[0].length*WALL_SIZE;
		MAP_HEIGTH = map.length*WALL_SIZE;
		
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
			//System.out.println(row + " is " +tank.box.y);
			//System.out.println(col + " is " +tank.box.x);
			
			int maxRow = map.length - 1;
			int maxCol = map[0].length - 1;
			if(row2 > maxRow || col2 > maxRow) {
				if(row2>maxRow+2||col2>maxCol+3) {
					tank.box.x -= tank.dx;
					tank.box.y -= tank.dy;
				}
			}else if(map[row][col]==1 || map[row2][col]==1 ||
			          map[row][col2]==1 || map[row2][col2]==1) {
				tank.box.x -= tank.dx;
				tank.box.y -= tank.dy;

			}

			
			
			
			
			
			
			Iterator<Bullet> itr =tank.newBullets.iterator();
			while (itr.hasNext()) {
				Bullet b = itr.next();
				b.box.x+=b.dx;
				b.box.y+=b.dy;
				
				if(b.box.x<0 || b.box.x>MAP_WIDTH) {
					System.out.println("REMOVE-----1");
					itr.remove();
					break;
				}
				if(b.box.y<0 || b.box.y>MAP_HEIGTH) {
					System.out.println(b.box.y);
					itr.remove();
					break;
				}
				
				int r = (int)((b.box.y)/WALL_SIZE);
				int c = (int)((b.box.x)/WALL_SIZE);
				int r2 = (int)((b.box.y+b.size)/WALL_SIZE);
				int c2 = (int)((b.box.x+b.size)/WALL_SIZE);
				
				if(r2 > maxRow || c2 > maxRow) {
					if(r2>maxRow+2||c2>maxCol+3) {
						System.out.println("REMOVE-----3");
						itr.remove();
					}
				} else if(map[r][c]==1 || map[r2][c]==1 ||
				          map[r][c2]==1 || map[r2][c2]==1) { // if bullet hit a wall
					System.out.println("REMOVE-----4");
					itr.remove();

				} else {
					for(Tank e : tanks) {
						if(e==tank) continue; // skip it self
						
						int er = (int)(e.box.y)/WALL_SIZE;
						int ec = (int)(e.box.x)/WALL_SIZE;
						int er2 = (int)((e.box.y+e.size)/WALL_SIZE);
						int ec2 = (int)((e.box.x+e.size)/WALL_SIZE);

						
						if(er == r && ec == c || er == r2 && ec == c ||
								er2== r2 && ec == c2 || er2== r2 && ec2 == c2	) {
								e.beAttacked(10);
								System.out.println("REMOVE-----5");
								itr.remove();
								break;
								
						}
						
						
						
						
						
					}
					
					
					
				}
			}
		}
		
		
		
		
		
		return false;
	}

}
