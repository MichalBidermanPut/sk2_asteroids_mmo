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
public class Rectangle {

    public Rectangle(double x, double y, double lenx, double leny) {
        this.x = x;
        this.y = y;
        this.lenx = lenx;
        this.leny = leny;
    }
    
    private double x;
    private double y;
    private double lenx;
    private double leny;
    
    public boolean colides(Rectangle that){
        if(this.maxX() < that.minX()) return false;
        if(this.maxY() < that.minY()) return false;
        if(this.minX() > that.maxX()) return false;
        if(this.minY() > that.maxY()) return false;
        return true;
    }

    public double minX() {
        return x-lenx/2;
    }

    public double maxX() {
        return x+lenx/2;
    }

    public double minY() {
        return y-leny/2;
    }

    public double maxY() {
        return y+leny/2;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getLenX() {
        return lenx;
    }

    public void setLenX(double lenx) {
        this.lenx = lenx;
    }

    public double getLenY() {
        return leny;
    }

    public void setLenY(double leny) {
        this.leny = leny;
    }
    
}
