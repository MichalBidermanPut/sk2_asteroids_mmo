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
public class Craft implements Interactable{
    private double x,y,dx,dy;
    private final double lx=1,ly=1;
    @Override
    public boolean collision() {
        return false;
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
    
}
