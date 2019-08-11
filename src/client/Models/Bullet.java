package client.Models;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Bullet extends movingBox{
	public float dx;
	public float dy;
	public float size;

	
	public movingItem box;
	
	public Bullet(float x, float y, float dx, float dy, float size){
		super(x,y);
		this.x = x;
		this.y = y;
		this.dx = dx;
		this.dy = dy;
		this.size = size;
		box = new movingItem(x,y,(float)size,(float)size,
				(float)0,(float)1,(float)1);
	}
}