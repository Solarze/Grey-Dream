package main;

import java.awt.Image;
import java.awt.Rectangle;

import javax.swing.ImageIcon;
public class Laser {
private String craft1 = "../Weapon";
private double x;
private int random;
private double y;
private int width;
private int height;
private double dir;
private PartShipBody ship;
private PartShipGun gun;
private boolean visible;
private Image image;
private int heat;
private boolean reload;
private int reloadcounter;
public Laser(double x, double y, double direction,PartShipBody shipbody, PartShipGun shipgun) {
	random = (int) (Math.random()*3+1);
    ImageIcon ii = new ImageIcon(this.getClass().getResource(craft1+random+".png"));
	image = ii.getImage();
    width = image.getWidth(null);
    height = image.getHeight(null);
    visible = false;
    this.x = x;
    this.y = y;
    dir = direction;
    ship=shipbody;
    gun=shipgun;
    reloadcounter =0;
}

public double getX() {
    return x;
}

public double getY() {
    return y;
}
public void starx(double xadd){
	x+=xadd;
}
public void stary(double yadd){
	y+=yadd;
}
public void move(){
	x=gun.getmidx()-(width/2)-10;
	y=gun.getmidy()-100;
	dir=ship.getdir();
	if (visible)
		heat+=4;
	if (heat > 1000){
		reload=true;
		reloadcounter=1;
		visible = false;
		heat = 999;
	}
  	if (reload){
      		reloadcounter+=1;
		if (reloadcounter>1000){
			reload=false;
			reloadcounter=0;
			heat = 1;
		}
	} else if (!visible){
		if (heat>0){
			heat-=2;}
	}
			
}
public boolean isVisible() {
    return visible;
}
public int getreload(){
	return reloadcounter;
}
public int getheat(){
	return heat;
}
public boolean getreloading(){
	return reload;
}
public void setVisible(boolean vis){
	if (!reload)
		visible = vis;
}
public void regen(){
	random = (int) (Math.random()*3+1);
    ImageIcon ii = new ImageIcon(this.getClass().getResource(craft1+random+".png"));
	image = ii.getImage();
}
public void setVisible(Boolean visible) {
    this.visible = visible;
}

public Image getImage() {
    return image;
}
public Rectangle getBounds() {
    return new Rectangle((int)x,(int)y, width, height);
}
}
