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
public class Snake extends Ghost{
    private int longueur, pz;
    private Queue blocsSerpent[], apple;
    private BufferedImage tete, queue, pomme;
    
    public Snake(float x, float y, float vitesse, int xScatter, int yScatter) {
        super(x, y, 1, xScatter, yScatter, "snake", 3);
        
        BufferedImage spriteSheet = null;
        try {
            spriteSheet = ImageIO.read(new File("res/textures_pack/"+Texture.getTextureFolder()+"/snake.png"));
        } catch (IOException ex) {
            Logger.getLogger(Entity.class.getName()).log(Level.SEVERE, null, ex);
            try {
                spriteSheet = ImageIO.read(new File("res/textures_pack/original/snake.png"));
            } catch (IOException ex1) {
                Logger.getLogger(Entity.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }
        
        spriteWidth *= (Texture.getGhosts_columns()/3f);
        spriteHeight *= (Texture.getGhosts_rows()/1f);
        tete = spriteSheet.getSubimage(0, 0, spriteWidth, spriteHeight);
        queue = spriteSheet.getSubimage(spriteWidth, 0, spriteWidth, spriteHeight);
        pomme = spriteSheet.getSubimage(spriteWidth*2, 0, spriteWidth, spriteHeight);
        
        blocsSerpent = new Queue[Panel.getMap().getMapWidth()*Panel.getMap().getMapHeight()];
        blocsSerpent[0] = new Queue(Ghost.getCage().getX()+1, Ghost.getCage().getY(), 2);
        blocsSerpent[1] = new Queue(Ghost.getCage().getX()+1, Ghost.getCage().getY()+1, 2);
        blocsSerpent[2] = new Queue(Ghost.getCage().getX()+1, Ghost.getCage().getY()+2, 2);
        
        for (int i=3; i < blocsSerpent.length; i++){
            blocsSerpent[i] = new Queue(0, 0, 0);
        }
        
        longueur = 2;
        
        spawnPomme();
    }
    
    public void avancer(){
        if(pz == 10){
            pz = 0;
            for(int i=longueur; i >= 0; i--){
                blocsSerpent[i+1].setX(blocsSerpent[i].getX());
                blocsSerpent[i+1].setY(blocsSerpent[i].getY());
            }

            setCible();
            setDirection();

            for(int i=longueur; i >= 0; i--){
                blocsSerpent[i+1].setX(blocsSerpent[i].getX());
                blocsSerpent[i+1].setY(blocsSerpent[i].getY());
            }

            if(collision(directionSuivante)){
                setNewDirection(directionSuivante);
            }
            switch (directionCourente)
            {
                case Gauche:
                    blocsSerpent[0].removeX();
                    break;
                case Haut:
                    blocsSerpent[0].removeY();
                    break;
                case Droite:
                    blocsSerpent[0].addX();
                    break;
                case Bas:
                    blocsSerpent[0].addY();
                    break;
                default:
                    break;
            }
            x = blocsSerpent[0].getX();
            y = blocsSerpent[0].getY();

            if(touchePacman()){
                Frame.stop();
            }
            
            if(blocsSerpent[0].getX() == apple.getX() && blocsSerpent[0].getY() == apple.getY()){
                manger();
            }

            if(seTouche() > 0){
                
                for(int i = seTouche()+1; i <= longueur; i++){
                    blocsSerpent[i] = new Queue(0, 0, 0);
                }
                longueur = seTouche();
            }
            
            if(blocsSerpent[0].getX() < 0){
            blocsSerpent[0].setX(Panel.getMap().getMapWidth());
            }
            if(blocsSerpent[0].getY() < 0){
                blocsSerpent[0].setY(Panel.getMap().getMapHeight());
            }
            if(blocsSerpent[0].getX() > Panel.getMap().getMapWidth()){
                blocsSerpent[0].setX(0);
            }
            if(blocsSerpent[0].getY() > Panel.getMap().getMapHeight()){
                blocsSerpent[0].setY(0);
            }
        }
        
        pz++;
    }
    
    public void spawnPomme(){
        boolean suite = false;
        do{
            apple = new Queue((int)(Math.random() * Panel.getMap().getMapWidth()), (int)(Math.random() * Panel.getMap().getMapHeight()), 1);
            suite = (Panel.getMap().libreA(apple.getX(), apple.getY()))
                && (apple.getX() < Ghost.getCage().getX()-3 || apple.getY() > Ghost.getCage().getY()+4)
                && (apple.getY() < Ghost.getCage().getY() || apple.getY() > Ghost.getCage().getY()+5);
        } while (!suite);
        
    }
    
    public void setCible(){
        cible = new Tile(apple.getX(), apple.getY(), 0);
    }
    
    public boolean peutSortir(){
        return true;
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
        
        for(int i = longueur; i >= 0; i--){
            AffineTransform transformation = new AffineTransform();
        
            transformation.translate((blocsSerpent[i].getX()-0.5)*size, (blocsSerpent[i].getY()-0.5)*size);
            transformation.scale(size/(spriteWidth/2), size/(spriteHeight/2));
            if(i == 0){
                g2d.drawImage(tete, transformation, null);
            } else {
                g2d.drawImage(queue, transformation, null);
            }
        }
        AffineTransform transformation = new AffineTransform();
        
        transformation.translate((apple.getX()-0.5)*size, (apple.getY()-0.5)*size);
        transformation.scale(size/(spriteWidth/2), size/(spriteHeight/2));
        g2d.drawImage(pomme, transformation, null);
        
    }
    
    public void inverserDirection(){
        
    }
    
    public void manger(){
        longueur++;
        blocsSerpent[longueur] = new Queue(blocsSerpent[longueur-1].getX(), blocsSerpent[longueur-1].getY(), 3);
        longueur++;
        blocsSerpent[longueur] = new Queue(blocsSerpent[longueur-1].getX(), blocsSerpent[longueur-1].getY(), 3);
        spawnPomme();
    }
    
    public boolean touchePacman(){
        boolean touche = false;
        
        for(int i = 0; i <= longueur; i++){
            if(blocsSerpent[i].getX() == Panel.getPlayersTab()[0].getX() && blocsSerpent[i].getY() == Panel.getPlayersTab()[0].getY()){
                touche = true;
            }
        }
        
        return touche;
    }
    
    public int seTouche(){
        int touche = 0;
        
        for(int i = 1; i <= longueur; i++){
            if(blocsSerpent[i].getX() == blocsSerpent[0].getX() && blocsSerpent[i].getY() == blocsSerpent[0].getY()){
                touche = i;
            }
        }
        
        return touche;
    }
}
