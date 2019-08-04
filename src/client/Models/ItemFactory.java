package client.Models;

public class ItemFactory {
	
	public static Item createItem(int id,float x, float y, float w, float h) {
		if(id == 1) return new Wall(x,y,w,h);
		else if(id == 2) return new mKit(x,y,w,h);
		else return new sKit(x,y,w,h);
		
	}

}
