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
public class Ghost extends Entity{
    
    protected Tile cible;
    protected boolean peur, retour;
    protected BufferedImage img;
    
    public Ghost(float x, float y, float vitesse) {
        super(x, y, vitesse);
        
        try {
            img = ImageIO.read(new File("res/peur.png"));
        } catch (IOException ex) {
            Logger.getLogger(Pacman.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void avancer(){
        setDirection();
        super.avancer();
    }
    
    public void setDirection(){
        Direction directionsPossibles[] = setDirectionsPossibles();
        Direction directionsPreferees[] = new Direction[4];
        
        if (Math.abs(x - cible.getX()) < Math.abs(y - cible.getY())) {
            if(y < cible.getY()) {
                directionsPreferees[0]= Direction.Bas;
                directionsPreferees[3]= Direction.Haut;	
            } else {
                directionsPreferees[0]= Direction.Haut;
                directionsPreferees[3]= Direction.Bas;	
            }

            if(x < cible.getX()) {
                directionsPreferees[1] = Direction.Droite;
                directionsPreferees[2] = Direction.Gauche;
            } else {
                directionsPreferees[1] = Direction.Gauche;
                directionsPreferees[2] = Direction.Droite;
            }
	} else {
            if(x < cible.getX()) {
                directionsPreferees[0]= Direction.Droite;
                directionsPreferees[3]= Direction.Gauche;	
            } else {
                directionsPreferees[0]= Direction.Gauche;
                directionsPreferees[3]= Direction.Droite;	
            }

            if(y < cible.getY()) {
                directionsPreferees[1] = Direction.Bas;
                directionsPreferees[2] = Direction.Haut;
            } else {
                directionsPreferees[1] = Direction.Haut;
                directionsPreferees[2] = Direction.Bas;
            }
        }
        
        Direction newDirection = null;
        
        int i=0;
        while(newDirection == null && i < 4){
            for(int j=0; j < 4; j++){
                if(directionsPreferees[i] == directionsPossibles[j]){
                    newDirection = directionsPreferees[i];
                }
            }
            i++;
        }
        
        directionSuivante = newDirection;
    }
    
    public Direction[] setDirectionsPossibles(){
        
        Direction directionsPossibles[] = new Direction[4];
        int i = 0;
        if(collisionGauche() && directionCourente != Direction.Droite){
            directionsPossibles[i] = Direction.Gauche;
            i++;
        }
        if(collisionHaut() && directionCourente != Direction.Bas){
            directionsPossibles[i] = Direction.Haut;
            i++;
        }
        if(collisionDroite() && directionCourente != Direction.Gauche){
            directionsPossibles[i] = Direction.Droite;
            i++;
        }
        if(collisionBas() && directionCourente != Direction.Haut){
            directionsPossibles[i] = Direction.Bas;
            i++;
        }
        return directionsPossibles;
    }
    
    public void setScatterCible(int xCible, int yCible){
        cible = new Tile(xCible, yCible, 0);
    }
    
    public boolean touherPacman(float xPacman, float yPacman){
        boolean touche = false;
        
        if((int)(x + 0.5f) == (int)(xPacman + 0.5f) && (int)(y + 0.5f) == (int)(yPacman + 0.5f)){
            touche = true;
        }
        
        return touche;
    }
    
    public void afficher(Graphics g, int width, int height){
        double size;
        int mapWidth = Singleton.getInstance().getMap().getMapWidth();
        int mapHeight = Singleton.getInstance().getMap().getMapHeight();
        
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

    /**
     * @param peur the peur to set
     */
    public void setPeur(boolean peur) {
        this.peur = peur;
    }
    
    /**
     * @param retour the peur to set
     */
    public void setRetour(boolean retour) {
        this.retour = retour;
    }
}
