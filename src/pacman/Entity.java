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
    protected BufferedImage[] sprites;
    protected boolean stop;
    protected static float facteurVitesse = 0.155f;
    
    public Entity(float x, float y, float vitesse, String pictureFile, int rows, int columns){
        this.x = x;
        this.y = y;
        this.vitesse = vitesse*facteurVitesse;
        this.vitesseDefaut = vitesse;
        directionCourente = Direction.Gauche;
        directionSuivante = Direction.Gauche;
        sprites = new BufferedImage[rows * columns];
        BufferedImage spriteSheet = null;
        try {
            spriteSheet = ImageIO.read(new File("res/textures_pack/"+Texture.getTextureFolder()+"/"+pictureFile+".png"));
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
        return Math.round(x);
    }

    /**
     * @return the y
     */
    public int getY() {
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
        
        boolean d1 = false, d2 = false;
        
        if(direction == Direction.Gauche || direction == Direction.Droite){
            d1 = false;
        } else if(direction == Direction.Haut || direction == Direction.Bas){
            d1 = true;
        }
        if(directionCourente == Direction.Gauche || directionCourente == Direction.Droite){
            d2 = false;
        } else if(directionCourente == Direction.Haut || directionCourente == Direction.Bas){
            d2 = true;
        }
        boolean entreBorne = false;
        
        if(d1 == d2){
            entreBorne = true;
        } else if(d1 && !d2){
            if(directionCourente == Direction.Gauche){
                entreBorne = ((x - vitesse) % 1 > (1-vitesse)) || (int)x == x;
            } else if(directionCourente == Direction.Droite){
                entreBorne = ((x + vitesse) % 1 < vitesse) || (int)x == x;
            }
        } else if(!d1 && d2){
            if(directionCourente == Direction.Haut){
                entreBorne = ((y - vitesse) % 1 > (1-vitesse)) || (int)y == y;
            } else if(directionCourente == Direction.Bas){
                entreBorne = ((y + vitesse) % 1 < vitesse) || (int)y == y;
            }
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
                    x = Math.round(x);
                    stop = true;
                }
                break;
            case Haut:
                if(collision(Direction.Haut)){
                    y -= vitesse;
                    stop = false;
                } else {
                    y = Math.round(y);
                    stop = true;
                }
                break;
            case Droite:
                if(collision(Direction.Droite)){
                    x += vitesse;
                    stop = false;
                } else {
                    x = Math.round(x);
                    stop = true;
                }
                break;
            case Bas:
                if(collision(Direction.Bas)){
                    y += vitesse;
                    stop = false;
                } else {
                    y = Math.round(y);
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
        if(y <= -2){
            y = Panel.getMap().getMapHeight()+ 1;
        } else if(y >= Panel.getMap().getMapHeight() + 2){
            y = -1;
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
        
        float size;
        int mapWidth = Panel.getMap().getMapWidth();
        int mapHeight = Panel.getMap().getMapHeight();
        
        if(width/mapWidth > height/mapHeight){
            size = (float)height/mapHeight;
        } else {
            size = (float)width/mapWidth;
        }
        
        Graphics2D g2d = (Graphics2D)g;
        AffineTransform transformation = new AffineTransform();
        
        transformation.translate((x-0.5)*size, (y-0.5)*size);
        transformation.scale(size/8, size/8);
        g2d.drawImage(sprites[idSprite], transformation, null);
    }
    
    public void setIdSprite(){
        idSprite = 0;
    }
    
}
