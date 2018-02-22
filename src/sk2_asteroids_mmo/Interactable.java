package sk2_asteroids_mmo;

import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

public abstract class Interactable {

    public abstract void move();

    public abstract double getX();

    public abstract double getY();

    public abstract BufferedImage getImage();
    
    public abstract void keyPressed(KeyEvent e);
    
    public abstract void keyReleased(KeyEvent e);
    
    public abstract String getType();
    
}