package Models;

import java.awt.*;
import java.awt.geom.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;

import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.paint.ImagePattern;


class testComponent extends JComponent{
	public void paintComponent(Graphics g) {
		Tank t = new Tank(0,0,50,50,true);
		Graphics2D g2d = (Graphics2D) g;
		try {
			t.draw(g2d);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

public class Tank {
	int health;
	double x, y;
	int size;
	File file;
	BufferedImage img;
	
	public Tank(double x, double y, int size, int health, boolean whichImage) {
		this.x = x;
		this.y = y;
		this.size = size;
		this.health = health;
		img = new BufferedImage(size, size, BufferedImage.TYPE_INT_BGR);
		if(whichImage) file = new File("tank01.jpg");
		else file = new File("tank02.jpg");
	}
	
	public void draw(Graphics2D g) throws IOException {
		g = img.createGraphics();
		ImageIO.write(img, "png", file);
	}
	
	
	
	
	
	public static void main(String args[]) {
		Tank t = new Tank(0,0,50,50,true);
		
		JFrame jf = new JFrame();
		jf.setTitle("Test for gridPanel");
		jf.setSize(1200,1200);
		jf.setVisible(true);
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		Container contain = jf.getContentPane();
		contain.add(new testComponent());
	}
	

}
