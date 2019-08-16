package client.Models;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;



/**
 * TimerTask class
 * @author wangj
 *
 */
class Helper extends TimerTask 
{ 
	
    public int TimerCount ; 
    

    public Helper() {
    	TimerCount = 0;
    }
    
    /**
     * Run this thread
     */
    public void run() 
    { 
    	TimerCount ++;

    } 
    
    /**
     * Set seconds
     * @param c seconds
     */
    public void setCount(int c) {
    	TimerCount = c;
    }
} 

public class Simulator {
	
	private static final int MAP_WIDTH = 2000;
	private static final int MAP_HEIGTH = 2000;
	private static final int WALL_SIZE = 20;
	
	private Tank tank;
	//private ArrayList<Item> walls;
	private ArrayList<Item> kits;
	private List<Bullet> bullets;
	private ArrayList<Tank> enemies;
	
	private int[][] map;
	private Timer timer;
	private Helper timertask;
	
	public Simulator(Tank t, int[][] m, 
			ArrayList<Bullet> b, ArrayList<Item> k, ArrayList<Tank> e ) {
		tank = t;
		//bullets = b;
		bullets = t.newBullets;
		map = m;
		kits = k;
		enemies = e;
		
		timer = new Timer();
		timertask = new Helper();
		timer.schedule(timertask, 0, 30);
	}
	
	public boolean update() {
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
			
			if(b.box.x<0 || b.box.x>MAP_WIDTH) {
				itr.remove();
				break;
			}
			if(b.box.y<0 || b.box.y>MAP_HEIGTH) {
				itr.remove();
				break;
			}
			
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

			} else if(map[r][c]==4 || map[r2][c]==4 ||
			          map[r][c2]==4 || map[r2][c2]==4 ||
			          map[r][c]==5 || map[r2][c]==5 ||
			          map[r][c2]==5 || map[r2][c2]==5) {
				Iterator<Tank> eitr = enemies.iterator();
				while(eitr.hasNext()) {
					
					Tank enemy = eitr.next();
					if(enemy.r == r && enemy.c == c || enemy.r == r2 && enemy.c == c ||
						enemy.r == r2 && enemy.c == c2 || enemy.r == r2 && enemy.c == c2	) {
						enemy.beAttacked(10);
						if(enemy.health<=0) {
							eitr.remove();
							map[enemy.r][enemy.c] = 0;
						}
					}
				}
				itr.remove();
			} 
			
		}
		
		
		
		//Check for enemies' bullets
		
		for(Tank e: enemies) {
			if(timertask.TimerCount%40==0) {
				if(Math.abs(row-e.r)<10&&Math.abs(col-e.c)<10) {
					e.shot();
				}
			}				
			Iterator<Bullet> it = e.newBullets.iterator();
			while (it.hasNext()) {
				Bullet b = it.next();
				b.box.x+=b.dx;
				b.box.y+=b.dy;
					
				if(b.box.x<0 || b.box.x>MAP_WIDTH) it.remove();
				if(b.box.y<0 || b.box.y>MAP_HEIGTH) it.remove();
										
				int r = (int)((b.box.y)/WALL_SIZE);
				int c = (int)((b.box.x)/WALL_SIZE);
				int r2 = (int)((b.box.y+b.size)/WALL_SIZE);
				int c2 = (int)((b.box.x+b.size)/WALL_SIZE);
					
				if(r2 > maxRow || c2 > maxRow) {
					if(r2>maxRow+2||c2>maxCol+3) {
						it.remove();
					}
				} else if(map[r][c]==1 || map[r2][c]==1 ||
				          map[r][c2]==1 || map[r2][c2]==1) {
					it.remove();
				} else if (r==row&&c==col || r==row2&&c==col || r==row&&c==col2 || r==row2&&c==col2){
					it.remove();
					//System.out.println("Here -----");
					tank.beAttacked(10);
					
				}
				
			}
				
		}
		
		
		if(row >= map.length-2 && col >= map[0].length-2) {
			return true;
		}
		else {
			return false;
		}
		
		
		
		
		
		
	}

}
