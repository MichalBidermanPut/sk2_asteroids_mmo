package sk2_asteroids_mmo;

import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class Craft extends Interactable {

    private int dx;
    private int dy;
    private int x;
    private int y;
    private ArrayList<Bullet> bullets = new ArrayList<>();
    private BufferedImage image;
    private double rotation;
    private double drotation;

    public Craft() {

        initCraft();
    }

    private void initCraft() {

        try {
            image = ImageIO.read(new File("craft2.png"));
        } catch (IOException ex) {
            Logger.getLogger(Craft.class.getName()).log(Level.SEVERE, null, ex);
        }
        x = 394;
        y = 294;
        rotation = 0;
    }

    @Override
    public void move() {
        x += dx;
        y += dy;
        rotation += drotation;
        try {
            for (Bullet bullet : bullets) {
                bullet.move();
            }
        } catch (Exception e) {
        }
    }

    @Override
    public double getX() {
        return x;
    }

    @Override
    public double getY() {
        return y;
    }

    @Override
    public BufferedImage getImage() {
        return image;
    }

    @Override
    public void keyPressed(KeyEvent e) {

        int key = e.getKeyCode();

        if (key == KeyEvent.VK_LEFT) {
            dx = -1;
        }

        if (key == KeyEvent.VK_RIGHT) {
            dx = 1;
        }

        if (key == KeyEvent.VK_UP) {
            dy = -1;
        }

        if (key == KeyEvent.VK_DOWN) {
            dy = 1;
        }
        
        if (key == KeyEvent.VK_Z) {
            drotation = 5;
        }
        
        if (key == KeyEvent.VK_X) {
            drotation = -5;
        }

        if (key == KeyEvent.VK_SPACE) {
            Bullet bullet = new Bullet(getX(), getY(), getRotation());
            bullets.add(bullet);
        }

        if (key == KeyEvent.VK_ESCAPE) {
            System.exit(0);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

        int key = e.getKeyCode();

        if (key == KeyEvent.VK_LEFT) {
            dx = 0;
        }

        if (key == KeyEvent.VK_RIGHT) {
            dx = 0;
        }

        if (key == KeyEvent.VK_UP) {
            dy = 0;
        }

        if (key == KeyEvent.VK_DOWN) {
            dy = 0;
        }
        
        if (key == KeyEvent.VK_Z) {
            drotation = 0;
        }
        
        if (key == KeyEvent.VK_X) {
            drotation = 0;
        }
    }

    @Override
    public String getType() {
        return "Craft";
    }

    public ArrayList<Bullet> getBullets() {
        return bullets;
    }
    
    public double getRotation() {
        return Math.toRadians(rotation);
    }
}
