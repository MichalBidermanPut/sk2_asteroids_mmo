package sk2_asteroids_mmo;

import game_engine.Rectangle;
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
import java.util.Arrays;
import java.util.Iterator;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Board extends JPanel implements ActionListener {

    private static final long serialVersionUID = 1L;
    private Timer timer;
    private final int DELAY = 10;

    private Image background;

    private Client client;

    private boolean isLogin;

    private Craft[] allCrafts;
    private Rectangle[] allAsteroids;
    private Bullet[] allBullets;

    public Board(String username) {
        this.isLogin = false;
        
        allCrafts = new Craft[0];
        allAsteroids = new Rectangle[0];
        allBullets = new Bullet[0];

        initBoard(username);
    }

    private void initBoard(String username) {

        client = new Client("127.0.0.1", 3000);
        if (client.login(username, username.length())) {
            System.out.println("Zalogowano");
            String clientID = null;
            /*while (true) {
                clientID = client.receiveMessage();
                if (clientID != null) {
                    isLogin = true;
                    break;
                }
            }
            byte[] byteAnswer = clientID.getBytes();
            byte[] byteID = new byte[4];
            System.arraycopy(byteAnswer, 0, byteID, 0, 4);
            int id = Integer.parseInt(new String(byteID, 0, byteID.length));
            client.getCraft().setID(id);*/
        } else {
            System.out.println("Błąd logowania");
        }

        addKeyListener(new TAdapter());
        setFocusable(true);
        background = new ImageIcon("space_background.png").getImage();

        timer = new Timer(DELAY, this);
        timer.start();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(background, 0, 0, null);
        //if (isLogin) {
        //doReceive();
        doSend();
        doDrawing(g);
        //}

        Toolkit.getDefaultToolkit().sync();
    }

    private void doReceive() {
        String message = client.receiveMessage();
        byte[] byteMessage = message.getBytes();
        byte[] byteBuffer = new byte[4];
        int len = 0;
        System.arraycopy(byteMessage, len, byteBuffer, 0, byteBuffer.length);
        len += byteBuffer.length;
        int craftsValue = Integer.parseInt(new String(byteBuffer, 0, byteBuffer.length));
        byteBuffer = new byte[4];
        System.arraycopy(byteMessage, len, byteBuffer, 0, byteBuffer.length);
        len += byteBuffer.length;
        int asteroidsValue = Integer.parseInt(new String(byteBuffer, 0, byteBuffer.length));
        byteBuffer = new byte[4];
        System.arraycopy(byteMessage, len, byteBuffer, 0, byteBuffer.length);
        len += byteBuffer.length;
        int bulletsValue = Integer.parseInt(new String(byteBuffer, 0, byteBuffer.length));
        allCrafts = new Craft[craftsValue];
        for (int i = 0; i < allCrafts.length; i++) {
            allCrafts[i] = new Craft();
            byteBuffer = new byte[4];
            System.arraycopy(byteMessage, len, byteBuffer, 0, byteBuffer.length);
            len += byteBuffer.length;
            allCrafts[i].setX(Integer.parseInt(new String(byteBuffer, 0, byteBuffer.length)));
            byteBuffer = new byte[4];
            System.arraycopy(byteMessage, len, byteBuffer, 0, byteBuffer.length);
            len += byteBuffer.length;
            allCrafts[i].setY(Integer.parseInt(new String(byteBuffer, 0, byteBuffer.length)));
            byteBuffer = new byte[4];
            System.arraycopy(byteMessage, len, byteBuffer, 0, byteBuffer.length);
            len += byteBuffer.length;
            allCrafts[i].setID(Integer.parseInt(new String(byteBuffer, 0, byteBuffer.length)));
        }
        allAsteroids = new Rectangle[asteroidsValue];
        for (int i = 0; i < allAsteroids.length; i++) {
            allAsteroids[i] = new Rectangle(0, 0, 0, 0);
            byteBuffer = new byte[4];
            System.arraycopy(byteMessage, len, byteBuffer, 0, byteBuffer.length);
            len += byteBuffer.length;
            allAsteroids[i].setX(Integer.parseInt(new String(byteBuffer, 0, byteBuffer.length)));
            byteBuffer = new byte[4];
            System.arraycopy(byteMessage, len, byteBuffer, 0, byteBuffer.length);
            len += byteBuffer.length;
            allAsteroids[i].setY(Integer.parseInt(new String(byteBuffer, 0, byteBuffer.length)));
            byteBuffer = new byte[4];
            System.arraycopy(byteMessage, len, byteBuffer, 0, byteBuffer.length);
            len += byteBuffer.length;
            allAsteroids[i].setLenX(Integer.parseInt(new String(byteBuffer, 0, byteBuffer.length)));
            allAsteroids[i].setLenY(Integer.parseInt(new String(byteBuffer, 0, byteBuffer.length)));
        }
        allBullets = new Bullet[bulletsValue];
        for (int i = 0; i < allBullets.length; i++) {
            allBullets[i] = new Bullet(0, 0, 0);
            byteBuffer = new byte[4];
            System.arraycopy(byteMessage, len, byteBuffer, 0, byteBuffer.length);
            len += byteBuffer.length;
            allBullets[i].setX(Integer.parseInt(new String(byteBuffer, 0, byteBuffer.length)));
            byteBuffer = new byte[4];
            System.arraycopy(byteMessage, len, byteBuffer, 0, byteBuffer.length);
            len += byteBuffer.length;
            allBullets[i].setY(Integer.parseInt(new String(byteBuffer, 0, byteBuffer.length)));
            byteBuffer = new byte[4];
            System.arraycopy(byteMessage, len, byteBuffer, 0, byteBuffer.length);
            len += byteBuffer.length;
            allBullets[i].setRotation(Integer.parseInt(new String(byteBuffer, 0, byteBuffer.length)));
            byteBuffer = new byte[4];
            System.arraycopy(byteMessage, len, byteBuffer, 0, byteBuffer.length);
            len += byteBuffer.length;
            allBullets[i].setID(Integer.parseInt(new String(byteBuffer, 0, byteBuffer.length)));
            byteBuffer = new byte[8];
            System.arraycopy(byteMessage, len, byteBuffer, 0, byteBuffer.length);
            len += byteBuffer.length;
            allBullets[i].setTime(Long.parseLong(new String(byteBuffer, 0, byteBuffer.length)));
        }
    }

    private void doSend() {
        client.update();
    }

    private void doDrawing(Graphics g) {

        Graphics2D g2d = (Graphics2D) g;

        AffineTransform affineTransform = new AffineTransform();
        affineTransform.rotate(client.getCraft().getRotation(), 6, 6);
        AffineTransformOp op = new AffineTransformOp(affineTransform, AffineTransformOp.TYPE_BILINEAR);
        g2d.drawImage(op.filter(client.getCraft().getImage(), null), (int) client.getCraft().getX(), (int) client.getCraft().getY(), this);
        
        for (int i = 0; i < allCrafts.length; i++) {
           affineTransform.rotate(allCrafts[i].getRotation(), 6, 6); 
           g2d.drawImage(op.filter(allCrafts[i].getImage(), null), (int) allCrafts[i].getX(), (int) allCrafts[i].getY(), this);
        }
        
        for (int i = 0; i < allAsteroids.length; i++) {
            g2d.fillRect((int)allAsteroids[i].getX(), (int)allAsteroids[i].getY(), (int)allAsteroids[i].getLenX(), (int)allAsteroids[i].getLenY());
        }
        
        for (int i = 0; i < allBullets.length; i++) {
            g2d.drawImage(allBullets[i].getImage(), (int) allBullets[i].getX(), (int) allBullets[i].getY(), this);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        client.getCraft().move();
        repaint();
    }

    private class TAdapter extends KeyAdapter {

        @Override
        public void keyReleased(KeyEvent e) {
            client.getCraft().keyReleased(e);
        }

        @Override
        public void keyPressed(KeyEvent e) {
            client.getCraft().keyPressed(e);
        }
    }
}
