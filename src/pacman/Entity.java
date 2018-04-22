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
    protected Direction directionCourente;
    protected Direction directionSuivante;
    protected BufferedImage img;
    
    public Entity(float x, float y, float vitesse){
        this.x = x;
        this.y = y;
        this.vitesse = vitesse;
        directionCourente = Direction.Gauche;
        directionSuivante = Direction.Gauche;
        try {
            img = ImageIO.read(new File("res/peur.png"));
        } catch (IOException ex) {
            Logger.getLogger(Entity.class.getName()).log(Level.SEVERE, null, ex);
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
                }   break;
            case Haut:
                if(collisionHaut()){
                    y-=vitesse;
                }   break;
            case Droite:
                if(collisionDroite()){
                    x+=vitesse;
                }   break;
            case Bas:
                if(collisionBas()){
                    y+=vitesse;
                }   break;
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
        if(directionSuivante == Direction.Gauche){
            if(collisionGauche()){
                directionCourente = directionSuivante;
            }
        } else  if(directionSuivante == Direction.Haut){
            if(collisionHaut()){
                directionCourente = directionSuivante;
            }
        } else  if(directionSuivante == Direction.Droite){
            if(collisionDroite()){
                directionCourente = directionSuivante;
            }
        } else  if(directionSuivante == Direction.Bas){
            if(collisionBas()){
                directionCourente = directionSuivante;
            }
        }
    }
    
    /**
     * @param vitesse the vitesse to set
     */
    public void setVitesse(float vitesse) {
        this.vitesse = vitesse;
    }
    
    public void afficher(Graphics g, int width, int height){
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
        g2d.drawImage(img, rotation, null);
    }
}
