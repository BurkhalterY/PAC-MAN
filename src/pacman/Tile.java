/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pacman;

import java.awt.Color;
import java.awt.image.BufferedImage;

/**
 *
 * @author BuYa
 */
public class Tile {
    
    private int x;
    private int y;
    private int type;
    private boolean solide;
    private BufferedImage img;
    
    public Tile(int x, int y){
        this.x = x;
        this.y = y;
    }
    
    public Tile(int x, int y, int type){
        this.x = x;
        this.y = y;
        this.type = type;
        if(type == 10 || type == 45 || type == 46 || type == 47){
            this.solide = false;
        } else {
            this.solide = true;
        }
        this.img = img;
    }
    
    /**
     * @return the x
     */
    public int getX() {
        return x;
    }

    /**
     * @return the y
     */
    public int getY() {
        return y;
    }

    /**
     * @return the type
     */
    public int getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(int type) {
        this.type = type;
    }

    /**
     * @return the solide
     */
    public boolean isSolide() {
        return solide;
    }

    /**
     * @return the img
     */
    public BufferedImage getImg() {
        return img;
    }

    /**
     * @param img the img to set
     */
    public void setImg(BufferedImage img) {
        this.img = img;
    }
    
    
}
