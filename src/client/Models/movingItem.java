package client.Models;

public class movingItem extends Item{
	public movingItem(float x, float y, float width, float height
			,float c1, float c2, float c3, int i, int j) {
		super(x, y, width, height, i, j);
		this.c1 = c1; this.c2 = c2; this.c3 = c3;
	}
	
	public float getx() {
		return x;
	}
	
	public float gety() {
		return x;
	}
}

