package client.Models;

public abstract class Item {
	public float c1,c2,c3;


	
	public float x,y,w,h;
	
	public Item(float x, float y, float width, float height) {
		this.x = x;
		this.y = y;
		w = width;
		h = height;
	}
	
	

}

class Wall extends Item{
	public Wall(float x, float y, float width, float height) {
		super(x,y,width,height);
		c1 = (float) 0.9; c2 = (float) 0.9; c3 = (float) 0.9;
	}
}

class mKit extends Item{

	public mKit(float x, float y, float width, float height) {
		super(x, y, width, height);
		c1 = 1; c2 = 0; c3 = 0;
	}
}

class sKit extends Item{

	public sKit(float x, float y, float width, float height) {
		super(x, y, width, height);
		c1 = 0; c2 = (float) 0.9; c3 = 0;
	}
}

class movingItem extends Item{
	public movingItem(float x, float y, float width, float height
			,float c1, float c2, float c3) {
		super(x, y, width, height);
		this.c1 = c1; this.c2 = c2; this.c3 = c3;
	}
}
