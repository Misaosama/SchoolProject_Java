package client.Models;

/**
 * to generate Wall, mKit or sKit.
 * @author rongyi
 *
 */
public class ItemFactory {
	
	public static Item createItem(int id,float x, float y, float w, float h, int i, int j) {
		if(id == 1) return new Wall(x,y,w,h,i,j);
		else if(id == 2) return new mKit(x,y,w,h,i,j);
		else return new sKit(x,y,w,h,i,j);
		
	}

}
