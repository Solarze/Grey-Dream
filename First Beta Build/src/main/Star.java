package main;
import java.awt.Image;
import java.awt.Rectangle;
import javax.swing.ImageIcon;


public class Star {
	private int random;
    private String craft1 = "Star.png";
    private String craft2 = "Star2.png";
    private String craft3 = "Star3.png";
    private int x;
    private int y;
    private int width;
    private int height;
    private boolean visible;
    private Image image;
    private int movetime;
    private int Min,Max;
    public Star(int x, int y) {
    	Min = 1;
    	Max = 3;
    	random = Math.round(Min + (int)(Math.random() * ((Max - Min) + 1)));
    	if (random == 1){
        ImageIcon ii = new ImageIcon(this.getClass().getResource(craft1));
    	image = ii.getImage();}
    	else if (random == 2){
    	ImageIcon ii = new ImageIcon(this.getClass().getResource(craft2));
        image = ii.getImage();}	
    	else{
        	ImageIcon ii = new ImageIcon(this.getClass().getResource(craft3));
            image = ii.getImage();}	
        width = image.getWidth(null);
        height = image.getHeight(null);
        visible = true;
        this.x = x;
        this.y = y;
        movetime = 1;
    }


    public void move() {
    	if (movetime == 1){
    		if (x < 0) {
    			x = 400;}
    		x -= 1;
        	movetime=0;}
    	else{
    		movetime=1;
    	}
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean isVisible() {
        return visible;
    }
    
    public void setVisible(Boolean visible) {
        this.visible = visible;
    }
    
    public Image getImage() {
        return image;
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }


}
