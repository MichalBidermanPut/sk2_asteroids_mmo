package sk2_asteroids_mmo;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Board extends JPanel implements ActionListener {

    private static final long serialVersionUID = 1L;
    private Timer timer;
    private Craft crafts[];
    private final int DELAY = 10;

    private Image background;

    public Board() {

        initBoard();
    }

    private void initBoard() {

        addKeyListener(new TAdapter());
        setFocusable(true);
        background = new ImageIcon("space_background.png").getImage();

        crafts = new Craft[100];
        crafts[0] = new Craft();
        crafts[1] = new Craft();
        
        timer = new Timer(DELAY, this);
        timer.start();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(background, 0, 0, null);
        doDrawing(g);

        Toolkit.getDefaultToolkit().sync();
    }

    private void doDrawing(Graphics g) {

        Graphics2D g2d = (Graphics2D) g;

        try {
            for (Craft craft : crafts) {
                AffineTransform affineTransform = new AffineTransform();
                affineTransform.rotate(craft.getRotation(), 6, 6);
                AffineTransformOp op = new AffineTransformOp(affineTransform, AffineTransformOp.TYPE_BILINEAR);
                g2d.drawImage(op.filter(craft.getImage(), null), (int) craft.getX(), (int) craft.getY(), this);
                for (Bullet bullet : craft.getBullets()) {
                    g2d.drawImage(bullet.getImage(), (int) bullet.getX(), (int) bullet.getY(), this);
                }
            }
        } catch (NullPointerException e) {
        }     
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
