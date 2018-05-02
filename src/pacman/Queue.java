/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pacman;

/**
 *
 * @author BuYa
 */
public class Queue {
    
    protected int x, y, type;
    /*  0 = vide
    1 = pomme
    2 = tête
    3 = corps */
    
    public int getX() {
        return x;
    }
    
    public int getY() {
        return y;
    }
    
    public int getType() {
        return type;
    }
    
    public void setX(int x) {
        this.x = x;
    }
    
    public void setY(int y) {
        this.y = y;
    }
    
    public void addX() {
        x++;
    }
    
    public void addY() {
        y++;
    }
    
    public void removeX() {
        x--;
    }
    
    public void removeY() {
        y--;
    }
    
    public Queue(int x, int y, int type){
        this.x = x;
        this.y = y;
        this.type = type;
    }
    
}
