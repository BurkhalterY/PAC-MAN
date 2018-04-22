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
    
    public enum Etat{
        Attente,
        Normal,
        Scatter,
        Peur,
        Retour,
        AttenteBleu;
    }
    protected Tile cible;
    protected Etat etat;
    protected BufferedImage img;
    protected int xScatter, yScatter;
    protected boolean basAttente, enTrainDeSortir;
    private static boolean scatter;
    private static int phase;
    private static int phases[] = {7, 20, 7, 20, 5, 20, 5};
    private static long start, pauseStart, pauseDuree;
    
    public Ghost(float x, float y, float vitesse, int xScatter, int yScatter) {
        super(x, y, vitesse);
        this.xScatter = xScatter;
        this.yScatter = yScatter;
        etat = Etat.Attente;
        
        try {
            img = ImageIO.read(new File("res/peur.png"));
        } catch (IOException ex) {
            Logger.getLogger(Pacman.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void avancer(){
            
        switch (etat) {
            case Attente:
                if((peutSortir() && y == 17) || enTrainDeSortir){
                    if(x > 13.5f){
                        x-=vitesse;
                    } else if(x < 13.5f){
                        x+=vitesse;
                    } else {
                        sortir();
                    }
                } else {
                    if(basAttente){
                        y+=vitesse;
                        if(y >= 18){
                            basAttente = false;
                        }
                    } else {
                        y-=vitesse;
                        if(y <= 16){
                            basAttente = true;
                        }
                    }
                }
                break;
            case Normal:
                setCible();
                setDirection();
                if(Panel.getMap().effet(getX(), getY()) == 1){
                    vitesse = 0.0625f;
                } else {
                    vitesse = 0.125f;
                }
                super.avancer();
                break;
            case Scatter:
                cible = new Tile(xScatter, yScatter, 0);
                setDirection();
                if(Panel.getMap().effet(getX(), getY()) == 1){
                    vitesse = 0.0625f;
                } else {
                    vitesse = 0.125f;
                }
                super.avancer();
                break;
            case Peur:
                setDirection();
                if(Panel.getMap().effet(getX(), getY()) == 1){
                    vitesse = 0.0625f;
                } else {
                    vitesse = 0.125f;
                }
                super.avancer();
                break;
            case Retour:
                cible = new Tile(13, 14, 0);
                setDirection();
                vitesse = 0.25f;
                super.avancer();
                break;
            case AttenteBleu:
                if((peutSortir() && y == 17) || enTrainDeSortir){
                    if(x > 13.5f){
                        x-=vitesse;
                    } else if(x < 13.5f){
                        x+=vitesse;
                    } else {
                        sortir();
                    }
                } else {
                    if(basAttente){
                        y+=vitesse;
                        if(y == 18){
                            basAttente = false;
                        }
                    } else {
                        y-=vitesse;
                        if(y == 16){
                            basAttente = true;
                        }
                    }
                }
                break;
            default:
                break;
        }
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
        
        if(etat == Etat.Peur){
            for(int i = 0; i < directionsPreferees.length / 2; i++){
                Direction temp = directionsPreferees[i];
                directionsPreferees[i] = directionsPreferees[directionsPreferees.length - i - 1];
                directionsPreferees[directionsPreferees.length - i - 1] = temp;
            }
        }
        
        Direction newDirection = null;
        
        int i=0;
        while(newDirection == null && i < directionsPreferees.length){
            for(int j=0; j < directionsPossibles.length; j++){
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
        if(collisionHaut() && directionCourente != Direction.Bas && Panel.getMap().effet(getX(), getY()) != 2){
            directionsPossibles[i] = Direction.Haut;
            i++;
        }
        if(collisionGauche() && directionCourente != Direction.Droite){
            directionsPossibles[i] = Direction.Gauche;
            i++;
        }
        if(collisionBas() && directionCourente != Direction.Haut){
            directionsPossibles[i] = Direction.Bas;
            i++;
        }
        if(collisionDroite() && directionCourente != Direction.Gauche){
            directionsPossibles[i] = Direction.Droite;
            i++;
        }
        
        return directionsPossibles;
    }
    
    public void setCible(){
        cible = new Tile(0, 0, 0);
    }
    
    public void setCiblePeur(int xPacman, int yPacman){
        cible = new Tile(xPacman, yPacman, 0);
    }
    
    public boolean touherPacman(int xPacman, int yPacman){
        boolean touche = false;
        
        if(getX() == xPacman && getY() == yPacman){
            touche = true;
        }
        
        return touche;
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

    /**
     * @return the etat
     */
    public Etat getEtat() {
        return etat;
    }

    /**
     * @param etat the etat to set
     */
    public void setEtat(Etat etat) {
        this.etat = etat;
    }
    
    public boolean peutSortir(){
        return false;
    }
    
    public void sortir(){
        enTrainDeSortir = true;
        if(y > 14){
            y-=vitesse;
        } else if(y == 14){
            etat = Etat.Scatter;
            enTrainDeSortir = false;
            vitesse = 0.125f;
        }
    }
}
