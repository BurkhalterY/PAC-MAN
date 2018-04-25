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
    protected float x, y, vitesse, vitesseDefaut, xDernierVirage, yDernierVirage;
    protected Direction directionCourente, directionSuivante;
    protected int idSprite = 0, nombreAvancementDepuisDernierVirage = 0;
    protected BufferedImage spriteSheet;
    protected BufferedImage[] sprites;
    protected boolean stop;
    protected static float facteurVitesse = 0.125f;
    
    public Entity(float x, float y, float vitesse, String pictureFile, int rows, int columns){
        this.x = xDernierVirage = x;
        this.y = yDernierVirage = y;
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
        int xa = 0;
        switch (directionCourente) {
            case Gauche:
                xa = (int) Math.ceil(x);
                break;
            case Haut: case Droite: case Bas:
                xa = (int) Math.floor(x);
                break;
            default:
                break;
        }
        return xa;
    }

    /**
     * @return the y
     */
    public int getY() {
        int ya = 0;
        switch (directionCourente) {
            case Haut:
                ya = (int) Math.ceil(y);
                break;
            case Droite: case Gauche: case Bas:
                ya = (int) Math.floor(y);
                break;
            default:
                break;
        }
        return ya;
    }
    
    /**
     * @return the directionCourente
     */
    public Direction getDirectionCourente() {
        return directionCourente;
    }
    
    public boolean collision(Direction direction){
        boolean peutTourner = false;
        float xa, ya;
        xa = x;
        ya = y;
        switch (direction) {
            case Gauche:
                xa = xa-vitesse;
                break;
            case Haut:
                ya = ya-vitesse;
                break;
            case Droite:
                xa = xa+vitesse;
                break;
            case Bas:
                ya = ya+vitesse;
                break;
            default:
                break;
        }
        int xb, yb;
        xb = (int)xa;
        yb = (int)ya;
        switch (directionCourente) {
            case Gauche:
                xb = (int) Math.floor(xa);
                break;
            case Droite:
                xb = (int) Math.ceil(xa);
                break;
            default:
                xb = (int) Math.floor(xa);
                break;
        }
        switch (directionCourente) {
            case Haut:
                yb = (int) Math.floor(ya);
                break;
            case Bas:
                yb = (int) Math.ceil(ya);
                break;
            default:
                yb = (int) Math.ceil(ya);
                break;
        }
        if(Panel.getMap().libreA(xb, yb)){
            /*float min, max, c;
            if(direction == Direction.Gauche || direction == Direction.Droite){
                min = x-vitesse;
                max = x+vitesse;
                c = xDernierVirage+(x - xDernierVirage)*vitesse;
            } else {
                min = y-vitesse;
                max = y+vitesse;
                c = yDernierVirage+(y - yDernierVirage)*vitesse;
            }
            if(c >= min && c <= max){*/
                peutTourner = true;
            //}System.out.println(min +"\t" +c+"\t" + max  );
        }
       
        return peutTourner;
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
                if(collision(Direction.Droite)){
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
        if(direction != null && x >= 0 && x < Panel.getMap().getMapWidth()-1)
        switch (direction) {
            case Gauche: case Droite:
                y = getY();
                xDernierVirage = x;
                yDernierVirage = y;
                directionCourente = directionSuivante;
                break;
            case Haut: case Bas:
                x = getX();
                xDernierVirage = x;
                yDernierVirage = y;
                directionCourente = directionSuivante;
                break;
            default:
                break;
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
