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
    protected Tile cible = new Tile(0, 0, 0);
    protected Etat etat;
    protected int xScatter, yScatter;
    protected boolean basAttente, enTrainDeSortir, dejaManger;
    protected BufferedImage cibles, cibleImg;
    private static boolean scatter = true, peur;
    private static int phase;
    private static int phases[] = {7, 20, 7, 20, 5, 20, 5};
    private static long start = 0, pauseStart = 0, pauseDuree = 0, pausePrevu = 0;
    private static Tile cage;

    public Ghost(float x, float y, float vitesse, int xScatter, int yScatter, String pictureFile, int rows, int columns, int numero) {
        super(x, y, vitesse, pictureFile, rows, columns);
        this.xScatter = xScatter;
        this.yScatter = yScatter;
        etat = Etat.Attente;
        
        BufferedImage[] prov = sprites;
        sprites = new BufferedImage[rows * columns * 2];
        
        for(int i = 0; i < prov.length; i++){
            sprites[i] = prov[i];
        }
        
        try {
            spriteSheet = ImageIO.read(new File("res/peur.png"));
        } catch (IOException ex) {
            Logger.getLogger(Entity.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        for(int i = 0; i < columns; i++) {
            for(int j = 0; j < rows; j++) {
                sprites[(j * columns) + i + prov.length] = spriteSheet.getSubimage(i * 16, j * 16, 16, 16);
            }
        }
        
        try {
            cibles = ImageIO.read(new File("res/cibles3.png"));
        } catch (IOException ex) {
            Logger.getLogger(Entity.class.getName()).log(Level.SEVERE, null, ex);
        }
        cibleImg = cibles.getSubimage(numero*8, 0, 8, 8);
    }
    
    public static void calculEtatGlobal(){
        if(peur){
            if(Frame.getMs() - pauseStart >= pausePrevu){
                peur = false;
                pauseDuree = Frame.getMs()-pauseStart;
                pausePrevu = 0;
            }
        } else {
            if(phase >= phases.length){
                scatter = false;
            } else {
                if(Frame.getMs() - start - pauseDuree >= phases[phase]*1000){
                    scatter = !scatter;
                    //System.out.println(scatter);
                    for(int i = 0; i < Panel.getGhostsTab().length; i++){
                        Panel.getGhostsTab()[i].inverserDirection();
                    }
                    phase++;
                    start = Frame.getMs();
                    pauseDuree = 0;
                }
            }
        }
    }
    
    public void avancer(){
        if(!peur){
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
                if(dejaManger){
                    setVitesse(vitesseDefaut);
                } else {
                    setVitesse(vitesseDefaut/2);
                }
                if(x > cage.getX()+0.5f+vitesse){
                    x-=vitesse;
                    directionCourente = Direction.Gauche;
                } else if(x < cage.getX()+0.5f-vitesse){
                    x+=vitesse;
                    directionCourente = Direction.Droite;
                } else {
                    basAttente = false;
                    enTrainDeSortir = true;
                    sortir();
                }
            } else {
                if(etat == Etat.AttenteBleu){
                    setVitesse(vitesseDefaut/2);
                } else {
                    setVitesse(vitesseDefaut);
                }
                if(basAttente){
                    y+=vitesse;
                    directionCourente = Direction.Bas;
                    if(y >= cage.getY()+3.5f){
                        basAttente = false;
                    }
                } else {
                    y-=vitesse;
                    directionCourente = Direction.Haut;
                    if(y <= cage.getY()+2.5f){
                        basAttente = true;
                    }
                }
            }
        } else {
            float vitesseMode = vitesseDefaut;
            
            if(etat == Etat.Scatter){
                cible = new Tile(xScatter, yScatter, 0);
            } else if(etat == Etat.Peur){
                cible = new Tile(Panel.getPlayersTab()[0].getX(), Panel.getPlayersTab()[0].getY(), 0);
                vitesseMode = vitesseDefaut/2;
            } else if(etat == Etat.Normal){
                setCible();
            } else if(etat == Etat.Retour){
                cible = cage;
                vitesseMode = vitesseDefaut*2;
                if(Math.round(x-0.5f) == cage.getX() && Math.round(y) == cage.getY()){
                    basAttente = true;
                }
            }
            
            setVitesse(vitesseMode);
            
            if(Panel.getMap().effet(Math.round(x), Math.round(y)) == 3){
                vitesse /= 2;
            }
            
            if(basAttente){
                if(y < cible.getY()+3 && !enTrainDeSortir){
                    y += vitesse;
                } else {
                    etat = Etat.Attente;
                    enTrainDeSortir = true;
                    sortir();
                }
            } else {
                setDirection();
                super.avancer();
            }
                        
            if(touchePacman()){
                if(etat == Etat.Peur || etat == Etat.AttenteBleu){
                    etat = Etat.Retour;
                    dejaManger = true;
                } else if(etat == Etat.Normal || etat == Etat.Scatter){
                    Panel.setRun(false);
                }
            }
        }
    }
    
    public Direction[] setDirectionsPreferees(){
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
        
        return directionsPreferees;
    }
    
    public void setDirection(){
        Direction directionsPossibles[] = setDirectionsPossibles();
        Direction directionsPreferees[] = setDirectionsPreferees();
        
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
        
        if(newDirection == null){
            Direction directionRestante = setDirectionRestante();
            int j=0;
            while(newDirection == null && j < directionsPreferees.length){
                if(directionsPreferees[j] == directionRestante){
                    newDirection = directionRestante;
                }
                j++;
            }
        }
        
        directionSuivante = newDirection;
    }
    
    public Direction[] setDirectionsPossibles(){
        Direction directionsPossibles[] = new Direction[4];
        
        int i = 0;
        if(collision(Direction.Haut) && directionCourente != Direction.Bas && Panel.getMap().effet(Math.round(x), Math.round(y)) != 4){
            directionsPossibles[i] = Direction.Haut;
            i++;
        }
        if(collision(Direction.Gauche) && directionCourente != Direction.Droite){
            directionsPossibles[i] = Direction.Gauche;
            i++;
        }
        if(collision(Direction.Bas) && directionCourente != Direction.Haut){
            directionsPossibles[i] = Direction.Bas;
            i++;
        }
        if(collision(Direction.Droite) && directionCourente != Direction.Gauche){
            directionsPossibles[i] = Direction.Droite;
        }
        
        return directionsPossibles;
    }
    
    public Direction setDirectionRestante(){
        Direction directionRestante = null;
        
        switch (directionCourente) {
            case Gauche:
                directionRestante = Direction.Droite;
                break;
            case Droite:
                directionRestante = Direction.Gauche;
                break;
            case Haut:
                directionRestante = Direction.Bas;
                break;
            case Bas:
                directionRestante = Direction.Haut;
                break;
            default:
                break;
        }
        
        return directionRestante;
    }
    
    public void setCible(){
        cible = new Tile(0, 0, 0);
    }
    
    public void setCiblePeur(int xPacman, int yPacman){
        cible = new Tile(xPacman, yPacman, 0);
    }
    
    public boolean touchePacman(){
        boolean touche = false;
        
        if(getX() == Panel.getPlayersTab()[0].getX() && getY() == Panel.getPlayersTab()[0].getY()){
            touche = true;
        }
        
        return touche;
    }
    
    public void setIdSprite(){
        if(etat == Etat.Peur || etat == Etat.AttenteBleu){
            idSprite = 8;
            if(Frame.getMs() - pauseStart >= pausePrevu-2000 && (Frame.getMs() - pauseStart) % 500 < 250){
                idSprite += 2;
            }
        } else {
            switch (directionCourente) {
                case Droite:
                    idSprite = 0;
                    break;
                case Gauche:
                    idSprite = 2;
                    break;
                case Haut:
                    idSprite = 4;
                    break;
                case Bas:
                    idSprite = 6;
                    break;
                default:
                    break;
            }
        }
        if(etat == Etat.Retour){
            idSprite /= 2;
            idSprite += 12;
        } else if (Frame.getTicksTotal() % (1/vitesse) >= 0.5f/vitesse){
            idSprite++;
        }
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
            if(Panel.getGhostsTab()[i].etat == Etat.Normal || Panel.getGhostsTab()[i].etat == Etat.Scatter){
                Panel.getGhostsTab()[i].etat = Etat.Peur;
            } else if(Panel.getGhostsTab()[i].etat == Etat.Attente){
                Panel.getGhostsTab()[i].etat = Etat.AttenteBleu;
            }
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
            directionCourente = Direction.Haut;
        } else if(y == cage.getY()){
            enTrainDeSortir = false;
            basAttente = false;
            if(etat == Etat.AttenteBleu){
                etat = Etat.Peur;
            } else {
                if(scatter){
                    etat = Etat.Scatter;
                } else {
                    etat = Etat.Normal;
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
    
    public void afficherTuile(Graphics g, int width, int height){
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
        
        rotation.translate(cible.getX()*size, cible.getY()*size);
        rotation.scale(size/8, size/8);
        g2d.drawImage(cibleImg, rotation, null);
    }

    /**
     * @param aCage the cage to set
     */
    public static void setCage(Tile aCage) {
        cage = aCage;
    }
    
    /**
     * @return the cage
     */
    public static Tile getCage() {
        return cage;
    }
}
