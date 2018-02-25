/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk2_asteroids_mmo;

import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author patry
 */
public class Bullet extends Interactable {

    private double x;
    private double y;
    private double rotation;
    private BufferedImage image;

    public Bullet(double x, double y, double rotation) {
        this.x = x;
        this.y = y;
        this.rotation = rotation;
        try {
            image = ImageIO.read(new File("craft.png"));
        } catch (IOException ex) {
            Logger.getLogger(Bullet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void move() {
        y -= 10 * Math.cos(rotation);
        x += 10 * Math.sin(rotation);
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
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public String getType() {
        return "Bullet";
    }
    
    public double getRotation() {
        return rotation;
    }

}
