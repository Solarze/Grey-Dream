package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.Timer;


public class Board extends JPanel implements ActionListener {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Timer timer;
	private Timer endtimer;
    private Craft craft;
    private ArrayList<Alien> aliens;
    private ArrayList<Star> stars;
    public boolean ingame;
    private boolean crash;
    private int B_WIDTH;
    private int B_HEIGHT;

    private int[][] pos = { 
        {2380, 29}, {2500, 59}, {1380, 89},
        {780, 109}, {580, 139}, {680, 239}, 
        {790, 259}, {760, 50}, {790, 150},
        {980, 209}, {560, 45}, {510, 70},
        {930, 159}, {590, 80}, {530, 60},
        {940, 59}, {990, 30}, {920, 200},
        {900, 259}, {660, 50}, {540, 90},
        {810, 220}, {860, 20}, {740, 180},
        {820, 128}, {490, 170}, {700, 30}
     };
    
    private int[][] spos = { 
            {200, 150}, {20, 240}, {1380, 50},
            {780, 67}, {580, 122}, {680, 239}, 
            {160, 45}, {760, 137}, {30, 150},
            {980, 26}, {560, 105}, {510, 2},
            {200, 180}, {590, 245}, {530, 60},
            {940, 108}, {380, 64}, {920, 200},
            {100, 240}, {660, 83}, {540, 7},
            {810, 70}, {860, 72}, {410, 180},
            {420, 45}, {490, 28}, {700, 30}
         };

    public Board() {
    	
    	crash = false;
        addKeyListener(new TAdapter());
        setFocusable(true);
        setBackground(Color.BLACK);
        setDoubleBuffered(true);
        ingame = true;

        setSize(400, 300);

        craft = new Craft();

        initAliens();
        initStars();
        timer = new Timer(5, this);
        endtimer = new Timer(5, this);
        timer.start();
    }

    public void addNotify() {
        super.addNotify();
        B_WIDTH = getWidth();
        B_HEIGHT = getHeight();   
    }

    public void initAliens() {
        aliens = new ArrayList<Alien>();

        for (int i=0; i<pos.length; i++ ) {
            aliens.add(new Alien(pos[i][0], pos[i][1]));
        }
    }
    
    public void initStars() {
        stars = new ArrayList<Star>();

        for (int i=0; i<spos.length; i++ ) {
            stars.add(new Star(spos[i][0], spos[i][1]));
        }
    }


    public void paint(Graphics g) {
        super.paint(g);
        
        if (!ingame && crash==false)
        {
        	 String msg = "You Win!";
             Font small = new Font("Helvetica", Font.BOLD, 14);
             FontMetrics metr = this.getFontMetrics(small);

             g.setColor(Color.white);
             g.setFont(small);
             g.drawString(msg, (B_WIDTH - metr.stringWidth(msg)) / 2,
                          B_HEIGHT / 2);
        }
        else if (ingame) {

            Graphics2D g2d = (Graphics2D)g;

            if (craft.isVisible())
                g2d.drawImage(craft.getImage(), craft.getX(), craft.getY(),
                              this);

            ArrayList<?> ms = craft.getMissiles();

            for (int i = 0; i < ms.size(); i++) {
                Missile m = (Missile)ms.get(i);
                g2d.drawImage(m.getImage(), m.getX(), m.getY(), this);
            }
            for (int i = 0; i < stars.size(); i++) {
                Star a = (Star)stars.get(i);
                		if (a.isVisible())
                    g2d.drawImage(a.getImage(), a.getX(), a.getY(), this);
            }
            
            for (int i = 0; i < aliens.size(); i++) {
                Alien a = (Alien)aliens.get(i);
                if (a.isVisible() && a.isExploding())
                    g2d.drawImage(a.getImageexplotion(), a.getX(), a.getY(), this);
                else if (a.isVisible())
                    g2d.drawImage(a.getImage(), a.getX(), a.getY(), this);
            }
            


            g2d.setColor(Color.WHITE);
            g2d.drawString("Aliens left: " + aliens.size(), 5, 15);


        } 
        else {
            String msg = "Game Over";
            Font small = new Font("Helvetica", Font.BOLD, 14);
            FontMetrics metr = this.getFontMetrics(small);

            g.setColor(Color.white);
            g.setFont(small);
            g.drawString(msg, (B_WIDTH - metr.stringWidth(msg)) / 2,
                         B_HEIGHT / 2);
        }

        Toolkit.getDefaultToolkit().sync();
        g.dispose();
    }


    public void actionPerformed(ActionEvent e) {

        if (aliens.size()==0) {
            ingame = false;
            endtimer.start();
        }

        ArrayList<?> ms = craft.getMissiles();

        for (int i = 0; i < ms.size(); i++) {
            Missile m = (Missile) ms.get(i);
            if (m.isVisible()) 
                m.move();
            else ms.remove(i);
        }

        for (int i = 0; i < aliens.size(); i++) {
            Alien a = (Alien) aliens.get(i);
            if (a.isVisible()) 
                a.move();
            else aliens.remove(i);
            if (a.isExploding()) {
                a.changeexplodetimer();
            }
            if (a.isexpltmr()){
            	a.setVisible(false);
            }
        }
        for (int t = 0; t < stars.size(); t++) {
                Star s = (Star) stars.get(t);
                if (s.isVisible()) 
                    s.move();
                else stars.remove(t);
        }
        craft.move();
        checkCollisions();
        repaint();  
    }

    public void checkCollisions() {

        Rectangle r3 = craft.getBounds();

        for (int j = 0; j<aliens.size(); j++) {
            Alien a = (Alien) aliens.get(j);
            Rectangle r2 = a.getBounds();

            if (r3.intersects(r2)) {
                craft.setVisible(false);
                a.setVisible(false);
                crash = true;
                ingame = false;
                endtimer.start();
            }
        }

        ArrayList<?> ms = craft.getMissiles();

        for (int i = 0; i < ms.size(); i++) {
            Missile m = (Missile) ms.get(i);

            Rectangle r1 = m.getBounds();

            for (int j = 0; j<aliens.size(); j++) {
                Alien a = (Alien) aliens.get(j);
                Rectangle r2 = a.getBounds();

                if (r1.intersects(r2)) {
                    m.setVisible(false);
                    a.setExploding(true);
                    a.setExpltmr(5);
                }
            }
        }
    }
    public boolean isover(){
    	return ingame;
    }

    private class TAdapter extends KeyAdapter {

        public void keyReleased(KeyEvent e) {
            craft.keyReleased(e);
        }

        public void keyPressed(KeyEvent e) {
            craft.keyPressed(e);
        }
    }
}
