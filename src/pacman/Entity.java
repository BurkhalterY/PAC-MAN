/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pacman;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author BuYa
 */
public class Entity {

    public enum Direction{
        Haut,
        Droite,
        Bas,
        Gauche;
    }
    protected float x, y, vitesse, vitesseDefaut;
    protected Direction directionCourente, directionSuivante;
    protected int idSprite = 0;
    protected BufferedImage spriteSheet;
    protected BufferedImage[] sprites;
    protected boolean stop;
    protected static float facteurVitesse = 0.125f;
    
    public Entity(float x, float y, float vitesse, String pictureFile, int rows, int columns){
        this.x = x;
        this.y = y;
        this.vitesse = vitesse*facteurVitesse;
        this.vitesseDefaut = vitesse;
        directionCourente = Direction.Gauche;
        directionSuivante = Direction.Gauche;
        sprites = new BufferedImage[rows * columns];
        try {
            spriteSheet = ImageIO.read(new File("res/"+pictureFile+".png"));
        } catch (IOException ex) {
            Logger.getLogger(Entity.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        for(int i = 0; i < columns; i++) {
            for(int j = 0; j < rows; j++) {
                sprites[(j * columns) + i] = spriteSheet.getSubimage(i * 16, j * 16, 16, 16);
            }
        }
    }
    
    /**
     * @return the x
     */
    public int getX() {
        /*int xa = 0;
        switch (directionCourente) {
            case Gauche:
                xa = (int) Math.ceil(x);
                break;
            case Haut: case Droite: case Bas:
                xa = (int) Math.floor(x);
                break;
            default:
                break;
        }*/
        return Math.round(x);
    }

    /**
     * @return the y
     */
    public int getY() {
        /*int ya = 0;
        switch (directionCourente) {
            case Haut:
                ya = (int) Math.ceil(y);
                break;
            case Droite: case Gauche: case Bas:
                ya = (int) Math.floor(y);
                break;
            default:
                break;
        }*/
        return Math.round(y);
    }
    
    /**
     * @return the directionCourente
     */
    public Direction getDirectionCourente() {
        return directionCourente;
    }
    
    public boolean collision(Direction direction){
        boolean peutTourner = false;
        int xb, yb;
        xb = yb = -1;
        
        switch (direction) {
            case Gauche:
                xb = (int)(x-vitesse);
                yb = Math.round(y);
                break;
            case Haut:
                xb = Math.round(x);
                yb = (int)(y-vitesse);
                break;
            case Droite:
                xb = (int)(x+1+vitesse);
                yb = Math.round(y);
                break;
            case Bas:
                xb = Math.round(x);
                yb = (int)(y+1+vitesse);
                break;
            default:
                break;
        }
        if(Panel.getMap().libreA(xb, yb)){
            peutTourner = true;
        }
        
        byte d1 = 0;
        byte d2 = 0;
        
        if(direction == Direction.Gauche || direction == Direction.Droite){
            d1 = 1;
        } else if(direction == Direction.Haut || direction == Direction.Bas){
            d1 = 2;
        }
        if(directionCourente == Direction.Gauche || directionCourente == Direction.Droite){
            d2 = 1;
        } else if(directionCourente == Direction.Haut || directionCourente == Direction.Bas){
            d2 = 2;
        }
        boolean entreBorne = false;
        
        if(d1 == d2){
            entreBorne = true;
        } else if(d1 == 1 && d2 == 2){
            entreBorne = ((y + vitesse) % 1 < vitesse)||((y - vitesse) % 1 > (1-vitesse))||(int)y == y;
        } else if(d1 == 2 && d2 == 1){
            entreBorne = ((x + vitesse) % 1 < vitesse)||((x - vitesse) % 1 > (1-vitesse))||(int)x == x;
        }
        
        return peutTourner && entreBorne;
    }
    
    public void avancer(){
        if(collision(directionSuivante)){
            setNewDirection(directionSuivante);
        }
        switch (directionCourente) {
            case Gauche:
                if(collision(Direction.Gauche)){
                    x -= vitesse;
                    stop = false;
                } else {
                    stop = true;
                }
                break;
            case Haut:
                if(collision(Direction.Haut)){
                    y -= vitesse;
                    stop = false;
                } else {
                    stop = true;
                }
                break;
            case Droite:
                if(collision(Direction.Droite)){
                    x += vitesse;
                    stop = false;
                } else {
                    stop = true;
                }
                break;
            case Bas:
                if(collision(Direction.Bas)){
                    y += vitesse;
                    stop = false;
                } else {
                    stop = true;
                }
                break;
            default:
                break;
        }
        if(x <= -2){
            x = Panel.getMap().getMapWidth() + 1;
        } else if(x >= Panel.getMap().getMapWidth() + 2){
            x = -1;
        }
    }
    
    public void setNewDirection(Direction direction){
        if(direction != null && x >= 0 && x < Panel.getMap().getMapWidth()-1){
            switch (direction) {
                case Gauche: case Droite:
                    y = Math.round(y);
                    directionCourente = directionSuivante;
                    break;
                case Haut: case Bas:
                    x = Math.round(x);
                    directionCourente = directionSuivante;
                    break;
                default:
                    break;
            }
        }
    }
    
    /**
     * @param vitesse the vitesse to set
     */
    public void setVitesse(float vitesse) {
        this.vitesse = vitesse * facteurVitesse;
    }
    
    /**
     * @return the vitesseDefaut
     */
    public float getVitesseDefaut() {
        return vitesseDefaut;
    }
    
    public void afficher(Graphics g, int width, int height){
        setIdSprite();
        
        double size;
        int mapWidth = Panel.getMap().getMapWidth();
        int mapHeight = Panel.getMap().getMapHeight();
        
        if(width/mapWidth > height/mapHeight){
            size = height/mapHeight;
        } else {
            size = width/mapWidth;
        }
        
        Graphics2D g2d = (Graphics2D)g;
        AffineTransform rotation = new AffineTransform();
        
        rotation.translate((x-0.5)*size, (y-0.5)*size);
        rotation.scale(size/8, size/8);
        g2d.drawImage(sprites[idSprite], rotation, null);
    }
    
    public void setIdSprite(){
        idSprite = 0;
    }
    
}
