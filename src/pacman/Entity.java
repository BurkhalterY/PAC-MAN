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
    
    
    public Entity(float x, float y, float vitesse){
        this.x = x;
        this.y = y;
        this.vitesse = vitesse;
        directionCourente = Direction.Gauche;
        directionSuivante = Direction.Gauche;
    }
    
    /**
     * @return the x
     */
    public float getX() {
        return x;
    }

    /**
     * @return the y
     */
    public float getY() {
        return y;
    }
    
    /**
     * @return the directionCourente
     */
    public Direction getDirectionCourente() {
        return directionCourente;
    }
    
    public boolean collisionDroite(){
        boolean libre = false;
        if(Singleton.getInstance().getMap().libreA((int) x, (int) y, Direction.Droite) && y == (int)y){
            libre = true;
        }
        return libre;
    }
    
    public boolean collisionGauche(){
        boolean libre = false;
        if(Singleton.getInstance().getMap().libreA((int) Math.ceil(x), (int) y, Direction.Gauche) && y == (int)y){
            libre = true;
        }
        return libre;
    }
    
    public boolean collisionHaut(){
        boolean libre = false;
        if(Singleton.getInstance().getMap().libreA((int) x, (int) Math.ceil(y), Direction.Haut) && x == (int)x){
            libre = true;
        }
        return libre;
    }
    
    public boolean collisionBas(){
        boolean libre = false;
        if(Singleton.getInstance().getMap().libreA((int) x, (int) y, Direction.Bas) && x == (int)x){
            libre = true;
        }
        return libre;
    }
    
    public void avancer(){
        verifDirection();
        
        if(directionCourente == Direction.Gauche){
            if(collisionGauche()){
                x-=vitesse;
            }
        } else if(directionCourente == Direction.Haut){
            if(collisionHaut()){
                y-=vitesse;
            }
        } else if(directionCourente == Direction.Droite){
            if(collisionDroite()){
                x+=vitesse;
            }
        } else if(directionCourente == Direction.Bas){
            if(collisionBas()){
                y+=vitesse;
            }
        }
        
        if(x < 1){
            x = 27;
        } else if(x > 27){
            x = 1;
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
   
}
