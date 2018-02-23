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
public class CraftS implements InteractableS{
    private int id;
    private double x,y,dx,dy;
    private final double lx=1,ly=1;
    @Override
    public boolean collision(InteractableS other_one) {
        return this.getBounds().colides(other_one.getBounds());
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle(x,y,lx,ly);
    }

    @Override
    public double getDX() {
        return dx;
    }

    @Override
    public double getDY() {
        return dy;
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
    public void setDX(double dx) {
        this.dx=dx;
    }

    @Override
    public void setDY(double dy) {
        this.dy=dy;
    }

    @Override
    public void setX(double x) {
        this.x=x;
    }

    @Override
    public void setY(double y) {
        this.y=y;
    }

    @Override
    public int getID() {
        return id;
    }

    @Override
    public void setID(int id) {
        this.id = id;
    }
    
}
