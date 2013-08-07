package main;

import java.awt.Image;
import java.awt.Rectangle;

import javax.swing.ImageIcon;

public class PartShipBody {
    private String craft1 = "../Ship Body.png";
    private double x;
    private double y;
    private int width;
    private int height;
    private double dir;
    private Image image;
    private boolean leftpressed;
    private boolean rightpressed;
    private boolean moving;
    private boolean thrusting;
    private double speed;
    private final int maxspeed = 2;
    public PartShipBody(int x, int y, double direction) {
        ImageIcon ii = new ImageIcon(this.getClass().getResource(craft1));
    	image = ii.getImage();    
        width = image.getWidth(null);
        height = image.getHeight(null);
        this.x = x;
        this.y = y;
        dir = direction;
    }
    public Image getimage(){
    	return image;
    }
    public void move(){
    	if ((speed > 0))
    		moving = true;
    	else 
    		moving = false;
    	if (leftpressed){
    		dir = dir - 0.02;
    		if (dir<0)
    			dir+=2*Math.PI;
    	}
    	if (rightpressed){
    		dir = dir + 0.02;
			if (dir>2*Math.PI)
				dir-=2*Math.PI;}
    	if (thrusting){
    		if (speed<maxspeed){
    			speed = speed +1;
    			}
    		}else{
    			if (speed>0)
    				speed = speed - 0.02;
    			if (speed<0)
    				speed=0;
    		}
    		
    		
    	if (moving)
    		x=(x+(speed*Math.cos(dir+(Math.PI/2))));
    		y=(y+(speed*Math.sin(dir+(Math.PI/2))));
    }
    public double getX() {
        return x;
    }
    public Rectangle getBounds() {
        return new Rectangle((int) x,(int) y, width, height);
    }
    public void setVisible(Boolean visible) {
    }
    public void leftpressed()
    {
    	leftpressed=true;
    }
    public void uppressed()
    {
    	thrusting=true;
    }
    public void upreleased()
    {
    	thrusting=false;
    }
    public int getmidx(){
        return(int)  x+(width/2);
    }
    public int getmidy(){
        return(int)  y+(height/2);
    }
    
    public void leftreleased()
    {
    	leftpressed=false;
    }
    public void rightpressed()
    {
    	rightpressed=true;
    }
    public void rightreleased()
    {
    	rightpressed=false;    
    }

    public double getY() {
        return y;
    }
    public double getdir(){
    	return dir;
    }
    public boolean getthrusting(){
    	return thrusting;
    }
}
