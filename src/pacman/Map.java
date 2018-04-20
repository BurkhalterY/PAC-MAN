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
    private BufferedImage tileset;
    private BufferedImage tiles[] = new BufferedImage[48];
    private int tilesID = 0;
    private int mapWidth = 28;
    private int mapHeight = 36;
    
    public Map(String path){
        map = new Tile[mapWidth][mapHeight];
        
        try
        {
            File f = new File (path);
            FileReader fr = new FileReader (f);
            BufferedReader br = new BufferedReader (fr);

            try
            {
                String line = br.readLine();
               
                int y = 0;
                
                while (line != null)
                {
                    String tableLigne[] = line.split(";");
                    line = br.readLine();
                    
                    for(int x = 0; x < mapWidth; x++){
                        map[x][y] = new Tile(x, y, Integer.parseInt(tableLigne[x]));
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
            tileset = ImageIO.read(new File("res/tileset.png"));
        } catch (IOException ex) {
            Logger.getLogger(Map.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        for(int y = 3; y < 6; y++){
            for(int x = 0; x < 16; x++){
                tiles[tilesID] = new BufferedImage(8, 8, BufferedImage.TYPE_INT_RGB);
                tiles[tilesID].getGraphics().drawImage(tileset, 0, 0, 8, 8, x*9, y*9, (x*9)+8, (y*9)+8, null);
                tilesID++;
            }
        }
    }
    
    public boolean libreA(int x, int y, Direction direction){
        boolean libre = true;
        
        switch (direction) {
            case Gauche:
                if(x-1 >= 0 && x-1 < mapWidth && y >= 0 && y < mapHeight){
                    if(map[x-1][y].isSolide()){
                        libre = false;
                    }
                }
                break;
            case Droite:
                if(x+1 >= 0 && x+1 < mapWidth && y >= 0 && y < mapHeight){
                    if(map[x+1][y].isSolide()){
                        libre = false;
                    }
                }
                break;
            case Haut:
                if(x >= 0 && x < mapWidth && y-1 >= 0 && y-1 < mapHeight){
                    if(map[x][y-1].isSolide()){
                        libre = false;
                    }
                }
                break;
            case Bas:
                if(x >= 0 && x < mapWidth && y+1 >= 0 && y+1 < mapHeight){
                    if(map[x][y+1].isSolide()){
                        libre = false;
                    }
                }
                break;
            default:
                break;
        }
        
        return libre;
    }
    
    public boolean mangerGraine(float x, float y){
        boolean puissance = false;
        
        int xa = (int)(x + 0.5f);
        int ya = (int)(y + 0.5f);
        
        if(map[xa][ya].getType() == 45){
            map[xa][ya].setType(44);
        }
        if(map[xa][ya].getType() == 47){
            map[xa][ya].setType(44);
            puissance = true;
        }
        return puissance;
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
}
