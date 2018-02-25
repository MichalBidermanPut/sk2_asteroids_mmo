package sk2_asteroids_mmo;

import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

public class Craft extends Interactable {

    private int dx;
    private int dy;
    private int x;
    private int y;
    private ArrayList<Bullet> activeBullets = new ArrayList<>();
    private ArrayList<Bullet> newBullets = new ArrayList<>();
    private BufferedImage image;
    private double rotation;
    private double drotation;
    
    private Client client;

    public Craft(Client client) {

        this.client = client;
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
        if ((x + dx) < 0) {
            x = 0;
        } else if ((x + dx) > 788) {
            x = 788;
        } else {
            x += dx;
        }
        if ((y + dy) < 0) {
            y = 0;
        } else if ((y + dy) > 588) {
            y = 588;
        } else {
            y += dy;
        }
        rotation += drotation;
        
        //client.update(x, y, rotation);
        
        // DO ZMIANY !!!!
        try {
            for (Bullet bullet : activeBullets) {
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

        if (key == KeyEvent.VK_X) {
            drotation = 5;
        }

        if (key == KeyEvent.VK_Z) {
            drotation = -5;
        }

        if (key == KeyEvent.VK_SPACE) {
            Bullet bullet = new Bullet(getX(), getY(), getRotation());
            newBullets.add(bullet);
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

        if (key == KeyEvent.VK_X) {
            drotation = 0;
        }

        if (key == KeyEvent.VK_Z) {
            drotation = 0;
        }
    }

    @Override
    public String getType() {
        return "Craft";
    }

    public ArrayList<Bullet> getActiveBullets() {
        return activeBullets;
    }

    public double getRotation() {
        return Math.toRadians(rotation);
    }
}
