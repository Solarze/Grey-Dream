package main;
import java.awt.event.KeyEvent;

public class Keyhandler {
	public PartShipBody shipbody;
	public Keyhandler(PartShipBody ship){
		shipbody=ship;
	}
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_I) {

        }
        if (key == KeyEvent.VK_LEFT) {
            shipbody.leftpressed();        
        }
        if (key == KeyEvent.VK_RIGHT) {
            shipbody.rightpressed();
        }
        if (key == KeyEvent.VK_UP) {
            shipbody.uppressed();
        }
    }
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        
        if (key == KeyEvent.VK_LEFT) {
            shipbody.leftreleased();
        }
        if (key == KeyEvent.VK_RIGHT) {
            shipbody.rightreleased();
        }
        if (key == KeyEvent.VK_UP) {
            shipbody.upreleased();
        }
        if (key == KeyEvent.VK_I) {
        	Board.invbutrel();
        }
        if (key == KeyEvent.VK_P) {
        	if (Board.getpause())
        		Board.setpause(false);
        	else
        		Board.setpause(true);        		
        }
    }
}
