package client.Models;

public class ItemFactory {
	
	public static Item createItem(int id,float x, float y, float w, float h) {
		return new Wall(x,y,w,h);
	}

}
