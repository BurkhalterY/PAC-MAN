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
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import pacman.Entity.Direction;

/**
 *
 * @author BuYa
 */
public class Map {
    private Tile map[][];
    private int mapSp[][];
    private BufferedImage tileset;
    private BufferedImage tiles[] = new BufferedImage[6*9];
    private int tilesID = 0;
    private int mapWidth = 28;
    private int mapHeight = 36;
    private int nbBulletTotal = 0;
    private int nbBulletRestantes = 0;
    
    public Map(String path){
        map = new Tile[mapWidth][mapHeight];
        
        try
        {
            File f = new File (path+".txt");
            FileReader fr = new FileReader (f);
            BufferedReader br = new BufferedReader (fr);

            try
            {
                String line = br.readLine();
               
                int y = 0;
                
                while (line != null)
                {
                    String tableLigne[] = line.split("\t");
                    line = br.readLine();
                    
                    for(int x = 0; x < mapWidth; x++){
                        map[x][y] = new Tile(x, y, Integer.parseInt(tableLigne[x]));
                        if(map[x][y].getType() == 45 || map[x][y].getType() == 47){
                            nbBulletTotal++;
                        }
                    }
                    y++;
                }
                br.close();
                fr.close();                
            }
            catch (IOException exception)
            {
                System.out.println ("Erreur lors de la lecture : " + exception.getMessage());
            }
        }
        catch (FileNotFoundException exception)
        {
            System.out.println ("Le fichier n'a pas été trouvé");
        }
        nbBulletRestantes = nbBulletTotal;
        
        mapSp = new int[mapWidth][mapHeight];
        
        try
        {
            File f = new File (path+"_sp.txt");
            FileReader fr = new FileReader (f);
            BufferedReader br = new BufferedReader (fr);

            try
            {
                String line = br.readLine();
               
                int y = 0;
                
                while (line != null)
                {
                    String tableLigne[] = line.split("\t");
                    line = br.readLine();
                    
                    for(int x = 0; x < mapWidth; x++){
                        mapSp[x][y] = Integer.parseInt(tableLigne[x]);
                    }
                    y++;
                }
                br.close();
                fr.close();                
            }
            catch (IOException exception)
            {
                System.out.println ("Erreur lors de la lecture : " + exception.getMessage());
            }
        }
        catch (FileNotFoundException exception)
        {
            System.out.println ("Le fichier n'a pas été trouvé");
        }
        
        try {
            tileset = ImageIO.read(new File("res/tileset1.png"));
        } catch (IOException ex) {
            Logger.getLogger(Map.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        for(int y = 0; y < 6; y++){
            for(int x = 0; x < 9; x++){
                tiles[tilesID] = new BufferedImage(8, 8, BufferedImage.TYPE_INT_RGB);
                tiles[tilesID].getGraphics().drawImage(tileset, 0, 0, 8, 8, x*9, y*9, (x*9)+8, (y*9)+8, null);
                tilesID++;
            }
        }
    }
    
    public boolean libreA(int x, int y, Direction direction){
        boolean libre = true;
        
        int xa = -1;
        int ya = -1;
        
        switch (direction) {
            case Gauche:
                xa = x-1;
                ya = y;
                break;
            case Droite:
                xa = x+1;
                ya = y;
                break;
            case Haut:
                xa = x;
                ya = y-1;
                break;
            case Bas:
                xa = x;
                ya = y+1;
                break;
            default:
                break;
        }
        
        if(xa >= 0 && xa < mapWidth && ya >= 0 && ya < mapHeight){
            if(map[xa][ya].isSolide()){
                libre = false;
            }
        }
        
        return libre;
    }
    
    public boolean mangerGraine(int x, int y){
        boolean puissance = false;
        
        if(x >= 0 && x < mapWidth && y >= 0 && y < mapHeight){
            if(map[x][y].getType() == 45){
                map[x][y].setType(10);
                nbBulletRestantes--;
            }
            if(map[x][y].getType() == 47){
                map[x][y].setType(10);
                puissance = true;
                nbBulletRestantes--;
            }
        }
        
        return puissance;
    }
    
    public int effet(int x, int y){
        int sp = 0;
        
        if(x >= 0 && x < mapWidth && y >= 0 && y < mapHeight){
            sp = mapSp[x][y];
        }
        return sp;
    }
    
    public void afficher(Graphics g, int width, int height){
        
        double size;
        if(width/mapWidth > height/mapHeight){
            size = height/mapHeight;
        } else {
            size = width/mapWidth;
        }
        
        Graphics2D g2d = (Graphics2D)g;

        for(int y=0; y < 36; y++){
            for(int x=0; x < 28; x++){
                AffineTransform rotation = new AffineTransform();
                rotation.translate(map[x][y].getX()*size, map[x][y].getY()*size);
                rotation.scale(size/8, size/8);
                g2d.drawImage(tiles[map[x][y].getType()], rotation, null);
            }
        }
    }
    
    /**
     * @return the mapWidth
     */
    public int getMapWidth() {
        return mapWidth;
    }

    /**
     * @return the mapHeight
     */
    public int getMapHeight() {
        return mapHeight;
    }

    /**
     * @return the nbBulletTotal
     */
    public int getNbBulletTotal() {
        return nbBulletTotal;
    }

    /**
     * @return the nbBulletRestantes
     */
    public int getNbBulletRestantes() {
        return nbBulletRestantes;
    }
    
    public int getNbBulletMangees() {
        return nbBulletTotal-nbBulletRestantes;
    }
}
