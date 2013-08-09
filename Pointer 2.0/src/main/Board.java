package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.PointerInfo;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter; // Get keyboard events
import java.awt.event.KeyEvent; // Get keyboard info
import java.awt.event.MouseEvent; // Get mouse events
import java.awt.event.MouseListener; // Get mouse info
import java.awt.MouseInfo;    // get mouse x and y
import java.io.*;
import java.util.ArrayList;
import java.awt.Rectangle;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;
public class Board extends JPanel implements ActionListener { 
	/**
	 * 
	 */
	private static final long serialVersionUID = -8692123294390921211L;
	private Timer timer;
    private String heatimagestring = "../Heatimage.png";//The Pipes in the bottom left corner
    private Image heatimage;
    private String heatfillstr = "../Fillimg.png";//The Pipe's Fill in the bottom left corner
    private Image heatfillimage;
    private String heatovrstr = "../Heatoverlay.png";//The Pipe's Fill in the bottom left corner
    private Image heatovrimage;
    private String tls = "../Topright.png";//The Top right's cover gui element
    private Image tli;
    private int heatimageheight;
    private int heatovrheight;
	public static boolean INV_Screen_Shown;
	public Keyhandler keyhandler;
	public PartShipBody shipbody;
	public static PartShipGun shipgun;
	public static PartShipCockpit shipcockpit;
	public static PartShipEngine shipengine;
	public static Laser laser;  // Your ship's laser on the screen
	public ReloadText reload; // Reloading Text on the screen
	public boolean loaded;
	public static boolean paused;
	public final int Blocksize=45;
	public static int Screensizeh;  
	public static int Screensizev;
	Font bigfont = new Font("Haettenschweiler", Font.PLAIN, 36);
	Font mediumfont  = new Font("Haettenschweiler", Font.PLAIN, 26);
	Font smallfont = new Font("Haettenschweiler", Font.PLAIN, 16);
	public static double camx;
	public static double camy;
    private static ArrayList<Star> stars;
    private static ArrayList<Planet> planets;
    private static ArrayList<Asteroid> asteroids;
    private static boolean ismenu;
    private double asteroidrotate;
    private boolean collider;
    private String questtext;
    private int points;
    
    // Main Menu Stuff
    // Play Button
    private static int pbuttx;
	private static int pbutty;
	private static int pbutheight;
	private static int pbutwidth;
    private String pbutstrs = "../MenuMainPlayButtonSelect.png";//The button's image selected
    private String pbutstr = "../MenuMainPlayButtonUp.png";//The button's image selected
    private Image pbutimg;
    private Image pbutimgs;
    private boolean pbutispressed;
    // Exit button
    private String ebutstrs = "../MenuMainExitButtonSelect.png";//The button's image selected
    private String ebutstr = "../MenuMainExitButtonUp.png";//The button's image selected
    private Image ebutimg;
    private Image ebutimgs;
    private static int ebuttx;
	private static int ebutty;
	private static int ebutheight;
	private static int ebutwidth;
    private boolean ebutispressed;
    // Hovering Title Text
    private String mm = "../MenuMainText.png";//The button's image selected
    private Image mi;
    
    // Escape menu stuff
    private static boolean isescmenu;
    public enum quests{
    	Letter, Color, Coord;
    }
    private char lchar;
    private int qx;
    private int qy;
    private String qcol;
    private quests quest;
	public Board(){
        Toolkit tk = Toolkit.getDefaultToolkit(); 
    	Screensizeh= ((int) tk.getScreenSize().getWidth());  
    	Screensizev = ((int) tk.getScreenSize().getHeight());
    	
		// load the main menu
    	
    	// load the play button
	    ImageIcon pbt = new ImageIcon(this.getClass().getResource(pbutstr));
	    pbutimg = pbt.getImage(); // load the pipes in bottom left corner
	    ImageIcon pbti = new ImageIcon(this.getClass().getResource(pbutstrs));
		pbutimgs = pbti.getImage(); // load the play button not selected
		pbutheight=pbutimg.getHeight(null);
		pbutwidth=pbutimg.getWidth(null);
		pbuttx = Screensizeh/2-pbutwidth+100;
		pbutty = Screensizev/2-pbutheight;
		ismenu=true;
		
		// load the exit button
	    ImageIcon ebt = new ImageIcon(this.getClass().getResource(ebutstr));
	    ebutimg = ebt.getImage(); 					// load the exit button
	    ImageIcon ebti = new ImageIcon(this.getClass().getResource(ebutstrs));
		ebutimgs = ebti.getImage(); 				// load the exit button when hovered over
		ebutheight=ebutimg.getHeight(null);
		ebutwidth=ebutimg.getWidth(null);
		ebuttx = Screensizeh/2-ebutwidth+100;
		ebutty = Screensizev/2-ebutheight+100;
													//load a big title screen image
	    ImageIcon mj = new ImageIcon(this.getClass().getResource(mm));
		mi = mj.getImage(); 						// load the pipes in bottom left corner
		
		
		//game stuff
	    ImageIcon heatimageicon = new ImageIcon(this.getClass().getResource(heatimagestring));
		heatimage = heatimageicon.getImage(); 			// load the Red bar in bottom left corner
	    ImageIcon tlii = new ImageIcon(this.getClass().getResource(tls));
		tli = tlii.getImage(); 							// load the bar in the top right corner
		heatimageheight=heatimage.getHeight(null);
	    ImageIcon heatfillicon = new ImageIcon(this.getClass().getResource(heatfillstr));
		heatfillimage = heatfillicon.getImage(); 		// load the heat fill image in bottom left corner
	    ImageIcon heatovericon = new ImageIcon(this.getClass().getResource(heatovrstr));
		heatovrimage = heatovericon.getImage(); 		// load the heat's overlay in bottom left corner
		heatovrheight=heatovrimage.getHeight(null);
		setFocusable(true);
        setBackground(Color.BLACK);
        setDoubleBuffered(true);
        addKeyListener(new TAdapter());
        addMouseListener(new Mousey());
        initStars();
        initAsteroids();
        try {
			initPlanets();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        INV_Screen_Shown = true;
        invbutrel();
        shipbody = new PartShipBody(Screensizeh/2-50,(Screensizev/2)-70,2*Math.PI);
        shipgun = new PartShipGun(100,100,2*Math.PI,shipbody);
        shipengine = new PartShipEngine(100,100,2*Math.PI,shipbody);
        shipcockpit = new PartShipCockpit(100,100,2*Math.PI,shipbody);
        laser = new Laser(100,100,shipbody,shipgun);
        reload = new ReloadText(800,100,laser);
        keyhandler = new Keyhandler(shipbody,laser);
        questtext = "This is the first quest. Find a planet beginning with the letter a.";
        quest = quests.Letter;
        lchar='a';
        qx=0;
        qy=0;
        qcol="Red";
        timer = new Timer(5, this);
        timer.start();
	}
    public void initPlanets() throws FileNotFoundException {
        planets = new ArrayList<Planet>();
    	BufferedReader br;
			br = new BufferedReader(new FileReader("Star Names.txt"));
    	String line;
    	try {
			while ((line = br.readLine()) != null) { // try to read the line. if its not null, read the line and add 1 to the line number
	        	planets.add(new Planet(Math.random()*35000-17500, Math.random()*35000-17500,line));
			}
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    	try {
			br.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    public void initAsteroids(){
        asteroids = new ArrayList<Asteroid>();
        for (int i=0; i<70; i++ ) {
        	asteroids.add(new Asteroid(Math.random()*Screensizeh*10, Math.random()*Screensizev*10));
        }
    }
    public void initStars() {
        stars = new ArrayList<Star>();
        for (int i=0; i<70; i++ ) {
        	stars.add(new Star(Math.random()*Screensizeh, Math.random()*Screensizev));
        }
    }
public int getscreensizeh(){
	return Screensizeh;
}
public int getscreensizev(){
	return Screensizev;
}
@Override
public void actionPerformed(ActionEvent e) {
	// TODO Auto-generated method stub
	repaint();
	if (!paused){
		if (!INV_Screen_Shown){
			if (!ismenu){
				if (!isescmenu){
					shipbody.move();
					shipgun.move();
					shipengine.move();
					laser.move();
					shipcockpit.move();
					reload.update();
					for(int x=1;x<70;x++){
			        	Star a = stars.get(x);
			        	a.move();
					}
					collisionchecker();
					if (collider){
						
					}
					asteroidrotate +=0.01;
				}else {
					mouseticker();
				}
			} else {
				mouseticker();
			}
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
public static void setcx(double d, double diff){
	camx=d;
    for (int i=1; i<70; i++ ) {
    	Star a = stars.get(i);
    	if (a.getslowstar())
    		a.starx(diff/2); // add half the distance the ship moved in x so further away stars go slower
    }
}
public static void regenship(){
	shipengine.regen();
	shipcockpit.regen();
	shipgun.regen();
	laser.regen();
}
public static void setcy(double y,  double diff){
	camy=y;
    for (int i=1; i<70; i++ ) {
    	Star a = stars.get(i);
        if (a.getslowstar())
        	a.stary(diff/2); // add half the distance the ship moved in y so further away stars go slower
    }
}
public static double getcx(){
	return camx;
}
public static double getcy(){
	return camy;	
}
@Override
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
	else if (isescmenu){
		if (ebutispressed){
			g.drawImage(ebutimgs, ebuttx, ebutty, this);
		} else {
			g.drawImage(ebutimg, ebuttx, ebutty, this);
		}
	}
	else if (ismenu) {
        setBackground(Color.BLACK);
		if (pbutispressed){
			g.drawImage(pbutimgs, pbuttx, pbutty, this);
		} else {
			g.drawImage(pbutimg, pbuttx, pbutty, this);
		}
		if (ebutispressed){
			g.drawImage(ebutimgs, ebuttx, ebutty, this);
		} else {
			g.drawImage(ebutimg, ebuttx, ebutty, this);
		}
		g.drawImage(mi, 400,180, this);
	}
	else{
        setBackground(Color.BLACK);
        for (int i=1; i<70; i++ ) {
        	Star a = stars.get(i);
        	g.drawImage(a.getImage(),(int)(a.getX()-camx),(int)(a.getY()-camy),this);
        }
        for (int i=0; i<70; i++ ) {
        	Asteroid a = asteroids.get(i);
        	if (a.isVisible()){
	    		g2d.rotate(asteroidrotate, a.getmidx()-camx, a.getmidy()-camy);
	    		g.drawImage(a.getImage(),(int)(a.getX()-camx),(int)(a.getY()-camy),this);
	    		g2d.rotate(-asteroidrotate, a.getmidx()-camx, a.getmidy()-camy);}
        }
        for (int i=0;i<planets.size();i++){
        	Planet a = planets.get(i);
        	g.drawImage(a.getImage(),(int)(a.getX()-camx),(int)(a.getY()-camy),this);
    		g2d.setColor(Color.WHITE);
        	g2d.drawString("Name: "+a.getname(),(int)(a.getX()-camx),(int)(a.getY()-camy));
        }
		g2d.rotate(shipbody.getdir(), shipbody.getmidx(), shipbody.getmidy());
		g.drawImage(shipbody.getimage(), (int) shipbody.getX(),(int) shipbody.getY(), this);
		g2d.rotate(-shipbody.getdir(), shipbody.getmidx(), shipbody.getmidy());
		g2d.rotate(shipgun.getdir(),shipbody.getmidx(),shipbody.getmidy());
		g.drawImage(shipgun.getimage(), (int) shipgun.getX(),(int) shipgun.getY(), this);
		g.drawImage(shipengine.getimage(), (int) shipengine.getX(),(int) shipengine.getY(), this);
		g.drawImage(shipcockpit.getimage(), (int) shipcockpit.getX(),(int) shipcockpit.getY(), this);
		if (laser.isVisible())
			g.drawImage(laser.getImage(), (int) laser.getX(),(int) laser.getY(),112,2000, this);
		g2d.rotate(-shipgun.getdir(),shipbody.getmidx(),shipbody.getmidy());
		// End of game objects
		
		// Start of Gui ETC
        g.drawImage(heatimage, 0,Screensizev-heatimageheight, this); 
        g.drawImage(heatfillimage,10,Screensizev-10-((laser.getheat()-laser.getreload())/10),30,((laser.getheat()-laser.getreload())/10),this);
        g.drawImage(heatovrimage, 0,Screensizev-heatovrheight, this);
        // reload box
        g.drawImage(tli, Screensizeh-tli.getWidth(null), 0, this);
        if (reload.getvisible())
        	g.drawImage(reload.getimage(), (int) reload.getX()-40,(int) reload.getY()+20, this);
        // Start of debug info
        g2d.setFont(mediumfont);
		g2d.setColor(Color.WHITE);
        g2d.drawString("Dir: "+shipbody.getdir(), 5, 20);
        g2d.drawString("Heat: "+laser.getheat(), 5, 40);
        g2d.drawString("Reload Timer: "+laser.getreload(), 5, 60);
        g2d.drawString("Ship X Position: "+camx, 5, 80);
        g2d.drawString("Ship Y Position: "+-camy, 5, 100);
        g2d.drawString("Ship Speed: "+shipbody.getspeed(), 5, 120);
        g2d.drawString("Reloading?: "+laser.getreloading(), 5, 140);
        g2d.drawString("Colliding?: "+collider, 5, 160);
        g2d.setFont(bigfont);
        g2d.drawString("Points: "+points, 5, 195);
        g2d.setFont(mediumfont);
        g2d.drawString("Quest Text: "+questtext, 5, 230);
        // end of gui
        // end of painting
        
	}
    Toolkit.getDefaultToolkit().sync();
    g.dispose();
    g2d.dispose();
}


public void collisionchecker(){
	Rectangle r1 = shipbody.getBounds();
	Boolean collide;
	collide = false;
	for (int x=1;x<planets.size();x++){
		Planet a = planets.get(x);
		Rectangle r2 = a.getBounds();
		if (r1.intersects(r2)){
			collide =true;
			if (quest == quests.Color){
				if (qcol == "Red"){
					if (a.getrandom() == 1 || a.getrandom() == 9){
						points++;
						newquest();
					}
				}
				if (qcol == "Blue"){
					if (a.getrandom() == 5){
						points++;
						newquest();
					}
				}
				if (qcol == "Green"){
					if (a.getrandom() == 2 || a.getrandom() == 6 || a.getrandom() == 7){
						points++;
						newquest();
					}
				}
			} else if (quest == quests.Letter){
				char bqb = a.getname().toLowerCase().charAt(0);
				if (bqb==lchar){
					points++;
					newquest();
				}
			}
		}
	}

	
	collider = collide;
	r1=laser.getBounds();
	for (int x=1;x<asteroids.size();x++){
		Rectangle r2 = asteroids.get(x).getBounds();
		if (r1.intersects(r2)){
			asteroids.get(x).explode();
		}
	}
	if (quest == quests.Coord){
		if (camx < 420+ qx && camx > qx -420 && camy < -qy+420 && camy > -qy -420 ){
			points++;
			newquest();
		}
	}
	
}
public void newquest(){
	int random = (int)(Math.random()*3)+1;
	if (random == 1){
		quest = quests.Color;
		random = (int)(Math.random()*3)+1;
		if (random == 1){
			qcol = "Red";
		} else if (random == 2){
			qcol = "Blue";
		}else if (random == 3){
			qcol = "Green";
		}
		questtext = "Your new mission is to find a "+ qcol.toLowerCase() + " planet. Good luck!";
	} else if (random == 2){
		int randomx = (int)(Math.random()*10000)-5000;
		int randomy = (int)(Math.random()*10000)-5000;
		quest = quests.Coord;
		qx=randomx;
		qy=randomy;
		questtext = "Your new mission is to go to the coordinates X: "+ qx + " , Y: " + qy + " . Good luck!";
	}else if (random == 3){
		quest = quests.Letter;
		random = (int)(Math.random()*3)+1;
		if (random == 1){
			lchar = 'a';
		} else if (random == 2){
			lchar = 'd';
		}else if (random == 3){
			lchar = 'm';
		}
		questtext = "Your new mission is to find a planet with a name beginning with the letter '"+ lchar + "'. Good luck!";
	}
}

public static void mousepressed(){
	if (isescmenu){
		PointerInfo r = MouseInfo.getPointerInfo();
        int mouseY = r.getLocation().y;
        int mouseX = r.getLocation().x;  
        if (mouseX<ebuttx+ebutwidth && mouseX>ebuttx &&
        		mouseY<ebutty+ebutheight && mouseY>ebutty){
        	System.exit(0);
        } else {
        	// do nothing - yet
        }
	}
	if (ismenu){
		PointerInfo r = MouseInfo.getPointerInfo();
        int mouseY = r.getLocation().y;
        int mouseX = r.getLocation().x;  
        if (mouseX<ebuttx+ebutwidth && mouseX>ebuttx &&
        		mouseY<ebutty+ebutheight && mouseY>ebutty){
        	System.exit(0);
        } else {
        	// do nothing - yet
        }
        if (mouseX<pbuttx+pbutwidth && mouseX>pbuttx &&
        		mouseY<pbutty+pbutheight && mouseY>pbutty){
        	ismenu = false;
        } else {
        	// do nothing - yet
        }
	}
}
public void mouseticker(){
	if (isescmenu){
		PointerInfo r = MouseInfo.getPointerInfo();
        int mouseY = r.getLocation().y;
        int mouseX = r.getLocation().x;  
        if (mouseX<ebuttx+ebutwidth && mouseX>ebuttx &&
        		mouseY<ebutty+ebutheight && mouseY>ebutty){
        	pbutispressed = true;
        } else {
        	pbutispressed = false;
        }
	}
	else if (ismenu){
		PointerInfo r = MouseInfo.getPointerInfo(); // Checks if mouse is over a button
        int mouseY = r.getLocation().y;
        int mouseX = r.getLocation().x;  
        // Check Play button
        if (mouseX<pbuttx+pbutwidth && mouseX>pbuttx &&
        		mouseY<pbutty+pbutheight && mouseY>pbutty){
        	pbutispressed = true;
        } else {
        	pbutispressed = false;
        }
        // Check Exit Button
        if (mouseX<ebuttx+ebutwidth && mouseX>ebuttx &&
        		mouseY<ebutty+ebutheight && mouseY>ebutty){
        	ebutispressed = true;
        } else {
        	ebutispressed = false;
        }
	}
}
public static void escpress(){
	isescmenu = !isescmenu;
}

private class TAdapter extends KeyAdapter {

    @Override
	public void keyReleased(KeyEvent e) {
    	keyhandler.keyReleased(e);
    }

    @Override
	public void keyPressed(KeyEvent e) {
    	keyhandler.keyPressed(e);
    }
}
private static class Mousey implements MouseListener {
	private static boolean mouseDown = false;
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
	    if (e.getButton() == MouseEvent.BUTTON1) {
	        mouseDown = true;
	    }
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
	    if (e.getButton() == MouseEvent.BUTTON1) {
	        mouseDown = true;
	        Board.mousepressed();
	    }
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
	    if (e.getButton() == MouseEvent.BUTTON1) {
	        mouseDown = false;
	    }
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	private boolean isRunning = false;
	private synchronized boolean checkAndMark() {
		if (isRunning) return false;
		isRunning = true;
 	   return true;
	}
}
}
