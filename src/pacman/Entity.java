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
    
    public Entity(float x, float y, float vitesse, String pictureFile, int rows, int columns){
        this.x = x;
        this.y = y;
        this.vitesse = vitesse;
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
            case Haut:
                xa = (int) x;
                break;
            case Droite:
                xa = (int) x;
                break;
            case Bas:
                xa = (int) x;
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
            case Gauche:
                ya = (int) y;
                break;
            case Haut:
                ya = (int) Math.ceil(y);
                break;
            case Droite:
                ya = (int) y;
                break;
            case Bas:
                ya = (int) y;
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
    
    public boolean collisionDroite(){
        boolean libre = false;
        if(Panel.getMap().libreA((int) x, (int) y, Direction.Droite) && y == (int)y){
            libre = true;
        }
        return libre;
    }
    
    public boolean collisionGauche(){
        boolean libre = false;
        if(Panel.getMap().libreA((int) Math.ceil(x), (int) y, Direction.Gauche) && y == (int)y){
            libre = true;
        }
        return libre;
    }
    
    public boolean collisionHaut(){
        boolean libre = false;
        if(Panel.getMap().libreA((int) x, (int) Math.ceil(y), Direction.Haut) && x == (int)x){
            libre = true;
        }
        return libre;
    }
    
    public boolean collisionBas(){
        boolean libre = false;
        if(Panel.getMap().libreA((int) x, (int) y, Direction.Bas) && x == (int)x){
            libre = true;
        }
        return libre;
    }
    
    public void avancer(){
        if(x > 0 && x < Panel.getMap().getMapWidth()){    
            verifDirection();
        }
        switch (directionCourente) {
            case Gauche:
                if(collisionGauche()){
                    x-=vitesse;
                    stop = false;
                } else {
                    stop = true;
                }
                break;
            case Haut:
                if(collisionHaut()){
                    y-=vitesse;
                    stop = false;
                } else {
                    stop = true;
                }
                break;
            case Droite:
                if(collisionDroite()){
                    x+=vitesse;
                    stop = false;
                } else {
                    stop = true;
                }
                break;
            case Bas:
                if(collisionBas()){
                    y+=vitesse;
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
    
    public void verifDirection(){
        switch (directionSuivante) {
            case Gauche:
                if(collisionGauche()){
                    directionCourente = directionSuivante;
                }
                break;
            case Haut:
                if(collisionHaut()){
                    directionCourente = directionSuivante;
                }
                break;
            case Droite:
                if(collisionDroite()){
                    directionCourente = directionSuivante;
                }
                break;
            case Bas:
                if(collisionBas()){
                    directionCourente = directionSuivante;
                }
                break;
            default:
                break;
        }
    }
    
    /**
     * @param vitesse the vitesse to set
     */
    public void setVitesse(float vitesse) {
        this.vitesse = vitesse;
        x = vitesseDefaut * ((x+vitesseDefaut/2)/vitesseDefaut);
        y = vitesseDefaut * ((y+vitesseDefaut/2)/vitesseDefaut);
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
