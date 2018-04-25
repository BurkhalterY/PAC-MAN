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
    protected float x;
    protected float y;
    protected float vitesse;
    protected float vitesseDefaut;
    protected Direction directionCourente;
    protected Direction directionSuivante;
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
    
    public float collision(Direction direction){
        float xa, ya;
        if(direction == directionCourente){
            xa = x;
            ya = y;
        } else {
            xa = getX();
            ya = getY();
        }
        int xb, yb;
        switch (direction) {
            case Gauche:
                xb = (int)Math.floor(xa-vitesse);
                yb = (int)ya;
                break;
            case Haut:
                xb = (int)xa;
                yb = (int)Math.floor(ya-vitesse);
                break;
            case Droite:
                xb = (int)Math.ceil(xa+vitesse);
                yb = (int)ya;
                break;
            case Bas:
                xb = (int)xa;
                yb = (int)Math.ceil(ya+vitesse);
                break;
            default:
                xb = -1;
                yb = -1;
                break;
        }
        
        float distance = 0;
        if(Panel.getMap().libreA(xb, yb)){
            distance = vitesse;
        } else {
            for(float i = 0; i < vitesse; i += 0.1f){
                switch (direction) {
                    case Gauche:
                        xb = (int)Math.floor(xa-i);
                        break;
                    case Haut:
                        yb = (int)Math.floor(ya-i);
                        break;
                    case Droite:
                        xb = (int)Math.ceil(xa+i);
                        break;
                    case Bas:
                        yb = (int)Math.ceil(ya+i);
                        break;
                    default:
                        break;
                }
                if(Panel.getMap().libreA(xb, yb)){
                    distance = i;
                }
            }
            if(distance == 0 && direction == directionCourente){
                switch (direction) {
                    case Gauche:
                        x = (float)Math.floor(x);
                        break;
                    case Haut:
                        y = (float)Math.floor(y);
                        break;
                    case Droite:
                        x = (float)Math.ceil(x);
                        break;
                    case Bas:
                        y = (float)Math.ceil(y);
                        break;
                    default:
                        break;
                }
            }
        }
        return distance;
    }
    
    public void avancer(){
        if(verifDirection(directionSuivante)){
            setNewDirection(directionSuivante);
        }
        float distance = collision(directionCourente);
        switch (directionCourente) {
            case Gauche:
                if(distance > 0 && y == (int)y){
                    x -= distance;
                    stop = false;
                } else {
                    stop = true;
                }
                break;
            case Haut:
                if(distance > 0 && x == (int)x){
                    y -= distance;
                    stop = false;
                } else {
                    stop = true;
                }
                break;
            case Droite:
                if(distance > 0 && y == (int)y){
                    x += distance;
                    stop = false;
                } else {
                    stop = true;
                }
                break;
            case Bas:
                if(distance > 0 && x == (int)x){
                    y += distance;
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
    
    public boolean verifDirection(Direction directionAVerifier){
        boolean peutTourner = false;
        
        switch (directionAVerifier) {
            case Gauche:
                if(collision(Direction.Gauche) > 0){
                    if(directionCourente == Direction.Haut){
                        if(y % 1 >= 1-facteurVitesse || (int)y == y){
                            peutTourner = true;
                        }
                    } else if(directionCourente == Direction.Bas){
                        if(y % 1 < facteurVitesse){
                            peutTourner = true;
                        }
                    } else {
                        peutTourner = true;
                    }
                }
                break;
            case Haut:
                if(collision(Direction.Haut) > 0){
                    if(directionCourente == Direction.Gauche){
                        if(x % 1 >= 1-facteurVitesse || (int)x == x){
                            peutTourner = true;
                        }
                    } else if(directionCourente == Direction.Droite){
                        if(x % 1 < facteurVitesse){
                            peutTourner = true;
                        }
                    } else {
                        peutTourner = true;
                    }
                }
                break;
            case Droite:
                if(collision(Direction.Droite) > 0){
                    if(directionCourente == Direction.Haut){
                        if(y % 1 >= -1 || (int)y == y){
                            peutTourner = true;
                        }
                    } else if(directionCourente == Direction.Bas){
                        if(y % 1 < facteurVitesse){
                            peutTourner = true;
                        }
                    } else {
                        peutTourner = true;
                    }
                }
                break;
            case Bas:
                if(collision(Direction.Bas) > 0){
                    if(directionCourente == Direction.Gauche){
                        if(x % 1 >= 1-facteurVitesse || (int)x == x){
                            peutTourner = true;
                        }
                    } else if(directionCourente == Direction.Droite){
                        if(x % 1 < facteurVitesse){
                            peutTourner = true;
                        }
                    } else {
                        peutTourner = true;
                    }
                }
                break;
            default:
                break;
        }
        return peutTourner;
    }
    
    public void setNewDirection(Direction direction){
        if(direction != null && x >= 0 && x < Panel.getMap().getMapWidth()-1)
        switch (direction) {
            case Gauche: case Droite:
                y = getY();
                directionCourente = directionSuivante;
                break;
            case Haut: case Bas:
                x = getX();
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
