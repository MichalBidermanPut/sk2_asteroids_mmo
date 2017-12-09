package sk2_asteroids_mmo;


import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JPanel;
import javax.swing.Timer;


public class Board extends JPanel implements ActionListener {

	private static final long serialVersionUID = 1L;
	private Timer timer;
    private Interactable crafts[];
    private final int DELAY = 10;

    public Board() {

        initBoard();
    }
    
    private void initBoard() {
        
        addKeyListener(new TAdapter());
        setFocusable(true);
        setBackground(Color.BLACK);

        crafts = new Craft[100];
        crafts[0] = new Craft();
        crafts[1] = new Craft();

        timer = new Timer(DELAY, this);
        timer.start();        
    }


    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        doDrawing(g);

        Toolkit.getDefaultToolkit().sync();
    }

    private void doDrawing(Graphics g) {
        
        Graphics2D g2d = (Graphics2D) g;
        
        
        g2d.drawImage(crafts[1].getImage(), crafts[1].getX(), crafts[1].getY(), this);
        
        g2d.drawImage(crafts[0].getImage(), crafts[0].getX(), crafts[0].getY(), this);        
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        
    	crafts[0].move();
        repaint();  
    }

    private class TAdapter extends KeyAdapter {

        @Override
        public void keyReleased(KeyEvent e) {
        	crafts[0].keyReleased(e);
        }

        @Override
        public void keyPressed(KeyEvent e) {
        	crafts[0].keyPressed(e);
        }
    }
}