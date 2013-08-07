package main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JPanel;
import javax.swing.Timer;
public class Board extends JPanel implements ActionListener { 
	/**
	 * 
	 */
	private static final long serialVersionUID = -8692123294390921211L;
	private Timer timer;
	public static boolean INV_Screen_Shown;
	public Keyhandler keyhandler;
	public PartShipBody shipbody;
	public PartShipGun shipgun;
	public PartShipEngine shipengine;
	public boolean loaded;
	public static boolean paused;
	public final int Blocksize=45;
	public final int Screensizeh=810;
	public final int Screensizev=428;
	public Board(){
        setFocusable(true);
        setBackground(Color.BLACK);
        setDoubleBuffered(true);
        addKeyListener(new TAdapter());
        INV_Screen_Shown = true;
        invbutrel();
        shipbody = new PartShipBody(100,100,2*Math.PI);
        shipgun = new PartShipGun(100,100,2*Math.PI,shipbody);
        shipengine = new PartShipEngine(100,100,2*Math.PI,shipbody);
        keyhandler = new Keyhandler(shipbody);
        timer = new Timer(5, this);
        timer.start();
	}
@Override
public void actionPerformed(ActionEvent e) {
	// TODO Auto-generated method stub
	repaint();
	if (!paused){
		if (!INV_Screen_Shown){
			shipbody.move();
			shipgun.move();
			shipengine.move();
		}
	}
}
public static boolean getpause(){
	return paused;
}
public static void setpause(Boolean as){
	paused = as; 
}
public static void invbutrel(){
	INV_Screen_Shown = !INV_Screen_Shown;
}
public void paint(Graphics g){
    super.paint(g);
    Graphics2D g2d = (Graphics2D)g;
    g.setColor(Color.black);
	if (INV_Screen_Shown){
        setBackground(Color.WHITE);
		for (int x=1;x*Blocksize<Screensizeh;x++)
			g.drawLine(Blocksize*x,0,Blocksize*x,Screensizev);
		for (int x=1;x*Blocksize<Screensizev;x++)
			g.drawLine(0,Blocksize*x,Screensizeh,Blocksize*x);
	}
	else{
        setBackground(Color.BLACK);
		g2d.setColor(Color.WHITE);
        g2d.drawString("Dir:"+shipbody.getdir(), 5, 15);
        g2d.drawString("Gun Lengthfrom:"+shipgun.getlengthfrom(), 5, 30);
        g2d.drawString("Engine Lengthfrom:"+shipengine.getlengthfrom(), 5, 45);
		g2d.rotate(shipbody.getdir(), shipbody.getmidx(), shipbody.getmidy());
		g.drawImage(shipbody.getimage(), (int) shipbody.getX(),(int) shipbody.getY(), this);
		g2d.rotate(-shipbody.getdir(), shipbody.getmidx(), shipbody.getmidy());
		g2d.rotate(shipgun.getdir(),(int)  shipbody.getmidx(),(int)  shipbody.getmidy());
		g.drawImage(shipgun.getimage(), (int) shipgun.getX(),(int) shipgun.getY(), this);
		g.drawImage(shipengine.getimage(), (int) shipengine.getX(),(int) shipengine.getY(), this);
		g2d.rotate(-shipgun.getdir(),(int)  shipbody.getmidx(),(int)  shipbody.getmidy());
		g2d.rotate(shipengine.getdir(),(int)  shipbody.getmidx(),(int)  shipbody.getmidy());
		g.drawImage(shipengine.getimage(), (int) shipengine.getX(),(int) shipengine.getY(), this);
	}
}
private class TAdapter extends KeyAdapter {

    public void keyReleased(KeyEvent e) {
    	keyhandler.keyReleased(e);
    }

    public void keyPressed(KeyEvent e) {
    	keyhandler.keyPressed(e);
    }
}
}
