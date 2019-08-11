package View;

//import javax.xml.bind.annotation.XmlAccessType;
//import javax.xml.bind.annotation.XmlAccessorType;
//import javax.xml.bind.annotation.XmlRootElement;

//@XmlRootElement
//@XmlAccessorType(XmlAccessType.FIELD)
public class Box implements java.io.Serializable {
	
	public float x;
	public float y;
	public int w;
	public int h;
	
	public int xp;
	
	public float r;
	public float g;
	public float b;
	
	public long id;
	
	public Box(){}

	public Box(float x, float y, int width, int height, float r, float g, float b, long id, int xp) {
		this.r = r;
		this.b = b;
		this.g = g;

		this.x = x;
		this.y = y;
		this.w = width;
		this.h = height;
		
		this.id= id;
		this.xp = xp;
	}
	public void  moveDown() {
		this.y += 10.0;
	}
	
	public void becomeBox(Box b1) {
		this.r = b1.r;
		this.b = b1.b;
		this.g = b1.g;

		this.x = b1.x;
		this.y = b1.y;
		this.w = b1.w;
		this.h = b1.h;
		
		this.id= b1.id;
		this.xp = b1.xp;
	}
	
	
}