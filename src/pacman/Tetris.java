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
import pacman.Entity.Direction;

/**
 *
 * @author Pascal
 */
public class Tetris {
    private Tile[] forme, copyForme;
    private int pz, tetrisRows = 1, tetrisColumns = 7, tetrisWidth, tetrisHeight;
    private BufferedImage tetriset, tetrisheet[] = new BufferedImage[tetrisColumns*tetrisRows];
    
    public Tetris(){
        try {
            tetriset = ImageIO.read(new File("res/textures_pack/original/tetris.png"));
        } catch (IOException ex) {
            Logger.getLogger(Tetris.class.getName()).log(Level.SEVERE, null, ex);
        }
        tetrisWidth = tetriset.getWidth()/tetrisColumns;
        tetrisHeight = tetriset.getHeight()/tetrisRows;
        int tilesID = 0;
        for(int y = 0; y < tetrisRows; y++){
            for(int x = 0; x < tetrisColumns; x++){
                tetrisheet[tilesID] = tetriset.getSubimage(x * tetrisWidth, y * tetrisHeight, tetrisWidth, tetrisHeight);
                tilesID++;
            }
        }
        int idForme = (int)(Math.random()*7);
        forme = randomForme(idForme);
        int xa = (Panel.getMap().getMapWidth()-2)/2+1;
        for(int i = 0; i < forme.length; i++){
            forme[i].setX(forme[i].getX()+xa);
            forme[i].setY(forme[i].getY()+2);
            forme[i].setImg(tetrisheet[idForme]);
        }
        copyForme = randomForme(idForme);
    }
    
    public void go(){
        if(pz >= 10){
            pz = 0;
            if(!contactSol()){
                descendre();
                System.out.println(copyForme[2].getY());
            } else {
                for(int i = 0; i < forme.length; i++){
                    //Panel.getMap().setTile(forme[i].getX(), forme[i].getY(), 0);
                    Panel.getMap().getMap()[forme[i].getX()][forme[i].getY()] = forme[i];
                }
                verifiLigne();
                int idForme = (int)(Math.random()*7);
                forme = randomForme(idForme);
                int xa = (Panel.getMap().getMapWidth()-2)/2+1;
                for(int i = 0; i < forme.length; i++){
                    forme[i].setX(forme[i].getX()+xa);
                    forme[i].setY(forme[i].getY()+2);
                    forme[i].setImg(tetrisheet[idForme]);
                }
                copyForme = randomForme(idForme);
            }
        }        
        pz++;
    }
    
    public Tile[] randomForme(int idForme){
        Tile[] forme = new Tile[4];

        switch (idForme) {
            case 0:
                forme[0] = new Tile(-1, -1, 0);
                forme[1] = new Tile(0, -1, 0);
                forme[2] = new Tile(1, -1, 0);
                forme[3] = new Tile(2, -1, 0);
                break;
            case 1:
                forme[0] = new Tile(-1, -1, 0);
                forme[1] = new Tile(0, -1, 0);
                forme[2] = new Tile(-1, 0, 0);
                forme[3] = new Tile(0, 0, 0);
                break;
            case 2:
                forme[0] = new Tile(-1, -1, 0);
                forme[1] = new Tile(0, -1, 0);
                forme[2] = new Tile(1, -1, 0);
                forme[3] = new Tile(0, 0, 0);
                break;
            case 3:
                forme[0] = new Tile(-1, -1, 0);
                forme[1] = new Tile(0, -1, 0);
                forme[2] = new Tile(1, -1, 0);
                forme[3] = new Tile(-1, 0, 0);
                break;
            case 4:
                forme[0] = new Tile(-1, -1, 0);
                forme[1] = new Tile(0, -1, 0);
                forme[2] = new Tile(1, -1, 0);
                forme[3] = new Tile(1, 0, 0);
                break;
            case 5:
                forme[0] = new Tile(-1, -1, 0);
                forme[1] = new Tile(0, -1, 0);
                forme[2] = new Tile(0, 0, 0);
                forme[3] = new Tile(1, 0, 0);
                break;
            case 6:
                forme[0] = new Tile(0, -1, 0);
                forme[1] = new Tile(1, -1, 0);
                forme[2] = new Tile(-1, 0, 0);
                forme[3] = new Tile(0, 0, 0);
                break;
            default:
                break;
        }
        
        return forme;
    }
    
    public void descendre(){
        for(int i = 0; i < forme.length; i++){
            forme[i].setY(forme[i].getY()+1);
        }
    }
    
    public void aDroite(){
        if(!contactMurDroite()){
            for(int i = 0; i < forme.length; i++){
                forme[i].setX(forme[i].getX()+1);
            }
        }
    }
    
    public void aGauche(){
        if(!contactMurGauche()){
            for(int i = 0; i < forme.length; i++){
                forme[i].setX(forme[i].getX()-1);
            }
        }
    }
    
    public boolean contactSol(){
        boolean touche = false;
        for(int i = 0; i < forme.length; i++){
            if(Panel.getMap().dansLaMap(forme[i].getX(), forme[i].getY()+1)){
                if(Panel.getMap().getMap()[forme[i].getX()][forme[i].getY()+1].getType() == 0){
                    touche = true;
                }
            }
        }
        return touche;
    }
    
    public boolean contactMurGauche(){
        boolean touche = false;
        for(int i = 0; i < forme.length; i++){
            if(Panel.getMap().dansLaMap(forme[i].getX()-1, forme[i].getY())){
                if(Panel.getMap().getMap()[forme[i].getX()-1][forme[i].getY()].getType() == 0){
                    touche = true;
                }
            }
        }
        return touche;
    }
    
    public boolean contactMurDroite(){
        boolean touche = false;
        for(int i = 0; i < forme.length; i++){
            if(Panel.getMap().dansLaMap(forme[i].getX()+1, forme[i].getY())){
                if(Panel.getMap().getMap()[forme[i].getX()+1][forme[i].getY()].getType() == 0){
                    touche = true;
                }
            }
        }
        return touche;
    }
    
    public void rotation(){
        int prevX = forme[0].getX();
        int prevY = forme[0].getY();
        for(int i = 0; i < forme.length; i++){
            int x = copyForme[i].getY();
            int y = -copyForme[i].getX();
            copyForme[i].setX(x);
            copyForme[i].setY(y);
            forme[i].setX(prevX+x);
            forme[i].setY(prevY+y);
        }
    }
    
    public void verifiLigne(){
        int ligneVaincus = 0;
        int noLigne = 0;
        for(int y = 1; y < Panel.getMap().getMapHeight()-1; y++){
            boolean ligne = true;
            for(int x = 1; x < Panel.getMap().getMapWidth()-1; x++){
                if(Panel.getMap().getMap()[x][y].getType() != 0){
                    ligne = false;
                }
            }
            if(ligne == true){
                ligneVaincus++;
                if(noLigne == 0){
                    noLigne = y;
                }
                for(int x = 1; x < Panel.getMap().getMapWidth()-1; x++){
                    //Panel.getMap().setTile(x, y, 1);
                    Panel.getMap().getMap()[x][y] = new Tile(x, y, 1);
                }
            }
        }
        for(int y = noLigne; y > 1; y--){
            for(int x = 1; x < Panel.getMap().getMapWidth()-1; x++){
                //Panel.getMap().setTile(x, y, Panel.getMap().getMap()[x][y-ligneVaincus].getType());
                Panel.getMap().getMap()[x][y] = Panel.getMap().getMap()[x][y-ligneVaincus];
            }
        }
    }
    
    public void setDirection(Direction direction){
        if(direction == Direction.Droite){
            aDroite();
        } else if(direction == Direction.Gauche){
            aGauche();
        } else if(direction == Direction.Bas){
            pz += 10;
        }
    }
    
    public void afficher(Graphics g, int width, int height){
        float size;
        if(width/Panel.getMap().getMapWidth() > height/Panel.getMap().getMapHeight()){
            size = (float)height/Panel.getMap().getMapHeight();
        } else {
            size = (float)width/Panel.getMap().getMapWidth();
        }

        Graphics2D g2d = (Graphics2D)g;
        for(int i = 0; i < forme.length; i++){
            AffineTransform transformation = new AffineTransform();
            transformation.translate(forme[i].getX()*size, forme[i].getY()*size);
            transformation.scale(size/tetrisWidth, size/tetrisHeight);
            g2d.drawImage(forme[i].getImg(), transformation, null);
        }
    }
}
