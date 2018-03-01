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
import java.util.Iterator;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Board extends JPanel implements ActionListener {

    private static final long serialVersionUID = 1L;
    private Timer timer;
    private Craft crafts[];
    private final int DELAY = 10;

    private Image background;

    private Client client;

    public Board(String username) {

        initBoard(username);
    }

    private void initBoard(String username) {

        client = new Client("127.0.0.1", 3000, username);
        if (client.login(username, "")) {
            System.out.println("Zalogowano");
        } else {
            System.out.println("Błąd logowania");
        }
        
        crafts = new Craft[100];
        crafts[0] = new Craft(client);

        addKeyListener(new TAdapter());
        setFocusable(true);
        background = new ImageIcon("space_background.png").getImage();

        
        
        // FUNKCJA ODCZYTUJĄCA LICZBĘ STATKÓW I ICH DANE
        
        
        
        
        //crafts[1] = new Craft();
        
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
                Iterator<Bullet> iterator = craft.getActiveBullets().iterator();
                while (iterator.hasNext()) {
                    Bullet bullet = iterator.next();
                    g2d.drawImage(bullet.getImage(), (int) bullet.getX(), (int) bullet.getY(), this);
                    if (bullet.getX() <= 0 || bullet.getX() >= 800 || bullet.getY() <= 0 || bullet.getY() >= 600) {
                        iterator.remove();
                    }
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
