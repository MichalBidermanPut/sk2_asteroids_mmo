/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game_engine;

/**
 *
 * @author szmii
 */
public interface InteractableS {
    public boolean collision(InteractableS other_one);
    public Rectangle getBounds();
    public double getDX();
    public double getDY();
    public double getX();
    public double getY();
    public void setDX(double dx);
    public void setDY(double dy);
    public void setX(double x);
    public void setY(double y);
    public int getID();
    public void setID(int id);
}
