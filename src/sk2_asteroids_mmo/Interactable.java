package sk2_asteroids_mmo;

import java.awt.Image;
import java.awt.event.KeyEvent;

public abstract class Interactable {

    public abstract void move();

    public abstract int getX();

    public abstract int getY();

    public abstract Image getImage();
    
    public abstract void keyPressed(KeyEvent e);
    
    public abstract void keyReleased(KeyEvent e);
    
    public abstract String getType();
    
}