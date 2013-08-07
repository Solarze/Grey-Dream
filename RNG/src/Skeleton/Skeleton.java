package Skeleton;

import javax.swing.JFrame;

public class Skeleton extends JFrame {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public Skeleton() {
        add(new Board());
        setTitle("Skeleton");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(270 , 330);
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);
    }
    public static void main(String[] args) {
        new Skeleton();
    }
}