package client.Models;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Bullet extends movingBox{
	
	public float x;
	public float y;
	public float k;
	public float c;
	public float pn;
	
	
	public Bullet(float x, float y, float k, float c, float pn){
		super(x,y);
		this.x = x;
		this.y = y;
		this.k = k;
		this.c = c;
		this.pn = pn;
	}
}