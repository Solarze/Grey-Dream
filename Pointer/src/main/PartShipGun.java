package main;

import java.awt.Image;
import java.awt.Rectangle;

import javax.swing.ImageIcon;

public class PartShipGun {
    private String craft1 = "../Ship Guns 1.png";
    private double x;
    private double y;
    private int width;
    private int height;
    private double dir;
    private PartShipBody ship;
    private Image image;
    private int lengthfrom;
    public PartShipGun(int x, int y, double direction,PartShipBody shipbody) {
        ImageIcon ii = new ImageIcon(this.getClass().getResource(craft1));
    	image = ii.getImage();    
    	lengthfrom = (int) (Math.random()*(80-1))-40;
        width = image.getWidth(null);
        height = image.getHeight(null);
        this.x = x-(width*2);
        this.y = y;
        dir = direction;
        ship=shipbody;
    }
    public Image getimage(){
    	return image;
    }
    public void move(){
    	x=ship.getmidx()-(width/2);
    	y=ship.getmidy()+lengthfrom;
    	dir=ship.getdir();
    }
    public double getX() {
        return x;
    }
    public Rectangle getBounds() {
        return new Rectangle((int) x,(int) y, width, height);
    }
    public int getmidx(){
        return(int)  x+(width/2);
    }
    public int getmidy(){
        return(int)  y+(height/2);
    }
    public double getY() {
        return y;
    }
    public double getdir(){
    	return dir;
    }
    public int getlengthfrom(){
    	return lengthfrom;
    }

}
