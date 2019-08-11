package client.Models;

public abstract class movingBox {
	public float x,y, dx ,dy;
	
	public movingBox(float a, float b) {
		x = a;
		y = b;
		dx = 0;
		dy = 0;
	}

}
