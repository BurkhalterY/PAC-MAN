/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pacman;

import java.awt.Color;
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

/**
 *
 * @author BuYa
 */
public class Map {
    private Tile map[][];
    private int mapSp[][];
    private BufferedImage tileset;
    private BufferedImage tiles[] = new BufferedImage[6*8];
    private int mapWidth = 0, mapHeight = 0, nbBulletTotal = 0, nbBulletRestantes = 0, tileWidth, tileHeight;
    
    public Map(String mapFolder, String tilesetPicture){
        
        try {
            tileset = ImageIO.read(new File("res/tileset/"+tilesetPicture));
        } catch (IOException ex) {
            Logger.getLogger(Map.class.getName()).log(Level.SEVERE, null, ex);
        }

        tileWidth = tileset.getWidth()/8;
        tileHeight = tileset.getHeight()/6;

        int tilesID = 0;
        for(int y = 0; y < 6; y++){
            for(int x = 0; x < 8; x++){
                tiles[tilesID] = tileset.getSubimage(x * tileWidth, y * tileHeight, tileWidth, tileHeight);
                tilesID++;
            }
        }
        
        try
        {
            File f1 = new File ("res/maps/"+mapFolder+"/map.txt");
            File f2 = new File ("res/maps/"+mapFolder+"/map_sp.txt");
            FileReader fr1 = new FileReader (f1);
            FileReader fr2 = new FileReader (f2);
            BufferedReader br1 = new BufferedReader (fr1);
            BufferedReader br2 = new BufferedReader (fr2);
            FileReader fr3 = new FileReader (f1);
            BufferedReader br3 = new BufferedReader (fr3);
            try
            {
                int nombreLignes = 0;
                String line = br3.readLine();
                while(line != null){
                    nombreLignes++;
                    line = br3.readLine();
                }
                
                br3.close();
                fr3.close();

                String line1 = br1.readLine();
                String line2 = br2.readLine();
                
                int y = 0;
                
                while (line1 != null && line2 != null)
                {
                    String tableLigne1[] = line1.split("\t");
                    String tableLigne2[] = line2.split("\t");
                    line1 = br1.readLine();
                    line2 = br2.readLine();
                    
                    if(mapWidth == 0 || mapHeight == 0){
                        mapWidth = tableLigne1.length;
                        mapHeight = nombreLignes;
                    }
                    
                    if(map == null){
                        map = new Tile[mapWidth][mapHeight];
                        mapSp = new int[mapWidth][mapHeight];
                    }
                    
                    for(int x = 0; x < mapWidth; x++){
                        map[x][y] = new Tile(x, y, Integer.parseInt(tableLigne1[x]));
                        if(map[x][y].getType() == 45 || map[x][y].getType() == 47){
                            nbBulletTotal++;
                        }
                        mapSp[x][y] = Integer.parseInt(tableLigne2[x]);
                        if(mapSp[x][y] == 1){
                            Player.setSpawn(new Tile(x, y));
                            
                        } else if(mapSp[x][y] == 2){
                            Ghost.setCage(new Tile(x, y-1));
                        }
                    }
                    y++;
                }
                
                br1.close();
                br2.close();
                fr1.close();
                fr2.close();
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
        
        for(int x = 0; x < this.getMapWidth(); x++){
            for(int y = 0; y < this.getMapHeight(); y++){
                if(!libreA(x, y)){
                    map[x][y].setImg(tiles[tileIndex(x, y)]);
                }
            }
        }
    }
    
    public int tileIndex(int x, int y){
        
        int north_tile = !libreA(x, y-1) ? 1 : 0;
        int south_tile = !libreA(x, y+1) ? 1 : 0;
        int west_tile = !libreA(x-1, y) ? 1 : 0;
        int east_tile = !libreA(x+1, y) ? 1 : 0;
        
        int north_west_tile = !libreA(x-1, y-1) && west_tile == 1 && north_tile == 1 ? 1 : 0;
        int north_east_tile = !libreA(x+1,y-1) && north_tile == 1 && east_tile == 1 ? 1 : 0;
        int south_west_tile = !libreA(x-1,y+1) && south_tile == 1 && west_tile == 1 ? 1 : 0;
        int south_east_tile = !libreA(x+1,y+1) && south_tile == 1 && east_tile == 1 ? 1 : 0;

        int index = north_west_tile + 2*north_tile + 4*north_east_tile + 8*west_tile + 16*east_tile + 32*south_west_tile + 64*south_tile + 128*south_east_tile;
        
        int redondance[][] = {{2, 1},{8, 2},{10, 3},{11, 4},{16, 5},{18, 6},{22, 7},{24, 8},{26, 9},{27, 10},{30, 11},{31, 12},{64, 13},{66, 14},{72, 15},{74, 16},{75, 17},{80, 18},{82, 19},{86, 20},{88, 21},{90, 22},{91, 23},{94, 24},{95, 25},{104, 26},{106, 27},{107, 28},{120, 29},{122, 30},{123, 31},{126, 32},{127, 33},{208, 34},{210, 35},{214, 36},{216, 37},{218, 38},{219, 39},{222, 40},{223, 41},{248, 42},{250, 43},{251, 44},{254, 45},{255, 46},{0, 47}};     
        
        for(int i = 0; i < redondance.length; i++){
            if(index == redondance[i][0]){
                index = redondance[i][1];
            }
        }
        
        return index;
    }
    
    public boolean libreA(int x, int y){
        boolean libre = true;
        
        if(x >= 0 && x < mapWidth && y >= 0 && y < mapHeight){
            if(map[x][y].isSolide()){
                libre = false;
            }
        }
        
        return libre;
    }
    
    public int mangerGraine(int x, int y){
        int type = 0;
        
        if(x >= 0 && x < mapWidth && y >= 0 && y < mapHeight){
            if(map[x][y].getType() == 45){
                map[x][y].setType(10);
                map[x][y].setImg(tiles[10]);
                nbBulletRestantes--;
                type = 1;
            }
            if(map[x][y].getType() == 47){
                map[x][y].setType(10);
                map[x][y].setImg(tiles[10]);
                nbBulletRestantes--;
                type = 3;
            }
        }
        
        return type;
    }
    
    public int effet(int x, int y){
        int sp = 0;
        
        if(x >= 0 && x < mapWidth && y >= 0 && y < mapHeight){
            sp = mapSp[x][y];
        }
        return sp;
    }
    
    public void afficher(Graphics g, int width, int height){
        float size;
        if(width/mapWidth > height/mapHeight){
            size = (float)height/mapHeight;
        } else {
            size = (float)width/mapWidth;
        }

        Graphics2D g2d = (Graphics2D)g;

        for(int y=0; y < mapHeight; y++){
            for(int x=0; x < mapWidth; x++){
                AffineTransform transformation = new AffineTransform();
                transformation.translate(map[x][y].getX()*size, map[x][y].getY()*size);
                transformation.scale(size/this.tileWidth, size/this.tileHeight);
                g2d.drawImage(map[x][y].getImg(), transformation, null);
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
