/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pacman;

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
    private static boolean scatter = true, peur;
    private static int phase;
    private static int phases[] = {7, 20, 7, 20, 5, 20, 5};
    private static long start = 0, pauseStart = 0, pauseDuree = 0, pausePrevu = 0;
    private static Tile cage = new Tile(13, 14, 0);

    public Ghost(float x, float y, float vitesse, int xScatter, int yScatter, String pictureFile, int rows, int columns) {
        super(x, y, vitesse, pictureFile, rows, columns);
        this.xScatter = xScatter;
        this.yScatter = yScatter;
        etat = Etat.Attente;
        
        try {
            img = ImageIO.read(new File("res/peur.png"));
        } catch (IOException ex) {
            Logger.getLogger(Pacman.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void calculEtatGlobal(){
        if(peur){
            if(Frame.getMs() - pauseStart >= pausePrevu){
                peur = false;
                pauseDuree = Frame.getMs()-pauseStart;
                pausePrevu = 0;
            }
        } else {
            if(Frame.getMs() - start - pauseDuree >= phases[phase]*1000){
                scatter = !scatter;
                for(int i = 0; i < Panel.getGhostsTab().length; i++){
                    Panel.getGhostsTab()[i].inverserDirection();
                }
                phase++;
                start = Frame.getMs();
                pauseDuree = 0;
            }
        }
    }
    
    public void avancer(){
        if(peur){
            if(etat == Etat.Attente || etat == Etat.AttenteBleu){
                etat = Etat.AttenteBleu;
            } else if(etat == Etat.Normal || etat == Etat.Scatter){
                etat = Etat.Peur;
            }
        } else {
            if(scatter){
                if(etat == Etat.Normal || etat == Etat.Peur){
                    etat = Etat.Scatter;
                } else if(etat == Etat.AttenteBleu){
                    etat = Etat.Attente;
                }
            } else {
                if(etat == Etat.Scatter || etat == Etat.Peur){
                    etat = Etat.Normal;
                } else if(etat == Etat.AttenteBleu){
                    etat = Etat.Attente;
                }
            }
        }
        
        if(etat == Etat.Attente || etat == Etat.AttenteBleu){
            if((peutSortir() && y == cage.getY()+3) || enTrainDeSortir){
                vitesse = vitesseDefaut/2;
                if(x > cage.getX()+0.5f){
                    x-=vitesse;
                } else if(x < cage.getX()+0.5f){
                    x+=vitesse;
                } else {
                    enTrainDeSortir = true;
                    sortir();
                }
            } else {
                if(basAttente){
                    y+=vitesse;
                    if(y >= cage.getY()+3.5f){
                        basAttente = false;
                    }
                } else {
                    y-=vitesse;
                    if(y <= cage.getY()+2.5f){
                        basAttente = true;
                    }
                }
            }
        } else {
            if(scatter){
                cible = new Tile(xScatter, yScatter, 0);
            } else {
                setCible();
            }
            setDirection();
            if(Panel.getMap().effet(getX(), getY()) == 1){
                vitesse = vitesseDefaut/2;
            } else {
                vitesse = vitesseDefaut;
            }
            super.avancer();
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
    
    public void setIdSprite(){
        idSprite = 0;
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
    
    /**
     * @param aPeur the peur to set
     */
    public static void setPeurTrue() {
        peur = true;
        for(int i = 0; i < Panel.getGhostsTab().length; i++){
            Panel.getGhostsTab()[i].inverserDirection();
        }
        pausePrevu += 6000;
        pauseStart = Frame.getMs();
    }
    
    public boolean peutSortir(){
        return false;
    }
    
    public void sortir(){
        if(y > cage.getY()){
            y-=vitesse;
        } else if(y == cage.getY()){
            enTrainDeSortir = false;
            if(peur){
                etat = Etat.Peur;
                vitesse = vitesseDefaut/2;
            } else {
                if(scatter){
                    etat = Etat.Scatter;
                    vitesse = vitesseDefaut;
                } else {
                    etat = Etat.Normal;
                    vitesse = vitesseDefaut;
                }
            }
        }
    }

    public void inverserDirection(){
        switch (directionCourente) {
            case Gauche:
                directionCourente = Direction.Droite;
                break;
            case Haut:
                directionCourente = Direction.Bas;
                break;
            case Droite:
                directionCourente = Direction.Gauche;
                break;
            case Bas:
                directionCourente = Direction.Haut;
                break;
            default:
                break;
        }
    }
}
