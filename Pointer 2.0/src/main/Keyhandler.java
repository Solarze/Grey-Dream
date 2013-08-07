package main;
import java.awt.event.KeyEvent;

public class Keyhandler {
	public PartShipBody shipbody;
	public Laser laser;
	public Keyhandler(PartShipBody ship, Laser weapon){
		shipbody=ship;
		laser = weapon;
	}
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_SPACE) {
        	laser.setVisible(true);
        }
        if (key == KeyEvent.VK_R){
        	Board.regenship();
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
        if (key == KeyEvent.VK_ESCAPE) {
            Board.escpress();
        }
    }
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_SPACE) {
        	laser.setVisible(false);
        }
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