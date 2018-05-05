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
    protected Tile map[][];
    protected int mapSp[][];
    protected BufferedImage editorset;
    protected int editorsetRows = 10, editorsetColumns = 8;
    protected BufferedImage tileset;
    protected int tilesetRows = 6, tilesetColumns = 9;
    protected BufferedImage tiles[] = new BufferedImage[editorsetColumns*editorsetRows];
    protected BufferedImage bulletset;
    protected int bulletsetRows = 1, bulletsetColumns = 4;
    protected BufferedImage bullets[] = new BufferedImage[bulletsetColumns*bulletsetRows];
    protected BufferedImage effetset;
    protected int effetsetRows = 1, effetsetColumns = 4;
    protected BufferedImage effets[] = new BufferedImage[effetsetColumns*effetsetRows];
    protected int mapWidth = 0, mapHeight = 0, nbBulletTotal = 0, nbBulletRestantes = 0;
    protected int editorWidth, editorHeight, tileWidth, tileHeight, bulletWidth, bulletHeight, effetWidth, effetHeight;
    protected int cageX = -8, cageY = -5, spawnX = 13, spawnY = 26;
    
    public Map(String mapFolder, String tilesetPicture, boolean basicTileset){
        readMap("res/maps/"+mapFolder, "res/tileset/"+tilesetPicture, basicTileset);
    }
    
    public Map(String mapFolder, boolean basicTileset){
        readMap("res/maps/"+mapFolder, "res/editor", basicTileset);
    }
    
    public Map(){
        readMap("res/editor", "res/editor", false);
    }
    
    public void readMap(String mapFolder, String tilesetPicture, boolean basicTileset){
        
        try {
            editorset = ImageIO.read(new File("res/editor/tileset.png"));
            tileset = ImageIO.read(new File(tilesetPicture+"/tileset.png"));
            bulletset = ImageIO.read(new File(tilesetPicture+"/bullets.png"));
            effetset = ImageIO.read(new File("res/editor/effets.png"));
        } catch (IOException ex) {
            Logger.getLogger(Map.class.getName()).log(Level.SEVERE, null, ex);
        }

        editorWidth = editorset.getWidth()/editorsetColumns;
        editorHeight = editorset.getHeight()/editorsetRows;
                
        int tilesID = 0;
        for(int y = 0; y < editorsetRows; y++){
            for(int x = 0; x < editorsetColumns; x++){
                tiles[tilesID] = editorset.getSubimage(x * editorWidth, y * editorHeight, editorWidth, editorHeight);
                tilesID++;
            }
        }
        
        if(basicTileset){
            int tilesetCorrespondance[] = {
                48, 49, 50, 34, 42, 26, 33, 41, 69,
                51,  0, 52, 36, 46, 28, 44, 45, 68,
                53, 54, 55,  7, 12,  4, 64, 77, 65,
                56, 57, 60, 61, 72, 73, 79, 70, 78,
                58, 59, 62, 63, 75, 74, 67, 76, 66,
                71
            };
            tileWidth = tileset.getWidth()/tilesetColumns;
            tileHeight = tileset.getHeight()/tilesetRows;
            
            for(int i = 0; i < tilesetCorrespondance.length; i++){
                int x = i % tilesetColumns;
                int y = i / tilesetColumns;
                tiles[tilesetCorrespondance[i]] = tileset.getSubimage(x * tileWidth, y * tileHeight, tileWidth, tileHeight);
            }
        } else {
            tileWidth = editorWidth;
            tileHeight = editorHeight;
        }
        bulletWidth = bulletset.getWidth()/bulletsetColumns;
        bulletHeight = bulletset.getHeight()/bulletsetRows;
        
        tilesID = 0;
        for(int y = 0; y < bulletsetRows; y++){
            for(int x = 0; x < bulletsetColumns; x++){
                bullets[tilesID] = bulletset.getSubimage(x * bulletWidth, y * bulletHeight, bulletWidth, bulletHeight);
                tilesID++;
            }
        }
        effetWidth = effetset.getWidth()/effetsetColumns;
        effetHeight = effetset.getHeight()/effetsetRows;
        
        tilesID = 0;
        for(int y = 0; y < effetsetRows; y++){
            for(int x = 0; x < effetsetColumns; x++){
                effets[tilesID] = effetset.getSubimage(x * effetWidth, y * effetHeight, effetWidth, effetHeight);
                tilesID++;
            }
        }
        
        try
        {
            File f1 = new File (mapFolder+"/map.txt");
            File f2 = new File (mapFolder+"/map_sp.txt");
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
                        if(map[x][y].getType() == 3 && cageX < 0 && cageY < 0){
                            cageX = x;
                            cageY = y;
                            Ghost.setCage(new Tile(x+3, y-1));
                        }
                        if(map[x][y].getType() >= 4){
                            nbBulletTotal++;
                        }
                        mapSp[x][y] = Integer.parseInt(tableLigne2[x]);
                        
                        if(mapSp[x][y] == 1){
                            Player.setSpawn(new Tile(x, y));
                            
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
                setTile(x, y, map[x][y].getType());
            }
        }
    }
    
    public int tileIndex(int x, int y){
        
        int north_tile = isTypeN(x, y-1, 0, 2) ? 1 : 0;
        int south_tile = isTypeN(x, y+1, 0, 2) ? 1 : 0;
        int west_tile = isTypeN(x-1, y, 0, 2) ? 1 : 0;
        int east_tile = isTypeN(x+1, y, 0, 2) ? 1 : 0;
        
        int north_west_tile = isTypeN(x-1, y-1, 0, 2) && west_tile == 1 && north_tile == 1 ? 1 : 0;
        int north_east_tile = isTypeN(x+1,y-1, 0, 2) && north_tile == 1 && east_tile == 1 ? 1 : 0;
        int south_west_tile = isTypeN(x-1,y+1, 0, 2) && south_tile == 1 && west_tile == 1 ? 1 : 0;
        int south_east_tile = isTypeN(x+1,y+1, 0, 2) && south_tile == 1 && east_tile == 1 ? 1 : 0;

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
    
    public boolean solideA(int x, int y){
        boolean libre = false;
        
        if(x >= 0 && x < mapWidth && y >= 0 && y < mapHeight){
            if(map[x][y].isSolide()){
                libre = true;
            }
        } else {
            libre = true;
        }
        
        return libre;
    }
    
    public boolean isTypeN(int x, int y, int type){
        boolean libre = false;
        
        if(x >= 0 && x < mapWidth && y >= 0 && y < mapHeight){
            if(map[x][y].getType() == type){
                libre = true;
            }
        } else {
            libre = true;
        }
        
        return libre;
    }
    
    public boolean isTypeN(int x, int y, int type, int type2){
        boolean libre = false;
        
        if(x >= 0 && x < mapWidth && y >= 0 && y < mapHeight){
            if(map[x][y].getType() == type || map[x][y].getType() == type2){
                libre = true;
            }
        } else {
            libre = true;
        }
        
        return libre;
    }

    
    public boolean dansLaMap(int x, int y){
        return x >= 0 && x < mapWidth && y >= 0 && y < mapHeight;
    }
    
    public int mangerGraine(int x, int y){
        int type = 0;
        
        if(x >= 0 && x < mapWidth && y >= 0 && y < mapHeight){
            if(map[x][y].getType() == 4){
                setTile(x, y, 1);
                nbBulletRestantes--;
                type = 1;
            }
            if(map[x][y].getType() == 5){
                setTile(x, y, 1);
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
    
    public void afficher(Graphics g, int width, int height, boolean quad){
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
                transformation.translate(x*size, y*size);
                transformation.scale(size/tileWidth, size/tileHeight);
                g2d.drawImage(map[x][y].getImg(), transformation, null);
            }
        }
        
        if(quad){
            
            g2d.setColor(Color.white);
            for(int i = 0; i <= mapWidth; i++){
                g2d.drawLine((int)(size*i), 0, (int)(size*i), (int)(size*mapHeight));
            }

            for(int i = 0; i <= mapHeight; i++){
                g2d.drawLine(0, (int)(size*i), (int)(size*mapWidth), (int)(size*i));
            }
            
            for(int y=0; y < mapHeight; y++){
                for(int x=0; x < mapWidth; x++){
                    AffineTransform transformation = new AffineTransform();
                    transformation.translate(x*size, y*size);
                    transformation.scale(size/tileWidth, size/tileHeight);
                    g2d.drawImage(effets[mapSp[x][y]], transformation, null);
                }
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
    
    public void setTile(int x, int y, int type){
        if(x >= 0 && x < mapWidth && y >= 0 && y < mapHeight){
            if(map[x][y].getType() == 0 || map[x][y].getType() == 2 || map[x][y].getType() == 3){
                mapSp[x][y] = 0;
            }
            if(map[x][y].getType() != 3){
                map[x][y].setType(type);
                if(type == 3){
                    for(int j = cageY; j < cageY+5; j++){
                        for(int i = cageX; i < cageX+8; i++){
                            if(dansLaMap(i, j)){
                                map[i][j].setType(0);
                            }
                        }
                    }
                    cageX = x;
                    cageY = y;
                    for(int i = 0; i < this.getMapWidth(); i++){
                        for(int j = 0; j < this.getMapHeight(); j++){
                            setTile(i, j, map[i][j].getType());
                        }
                    }
                } else {
                    for(int i = x-1; i <= x+1; i++){
                        for(int j = y-1; j <= y+1 ; j++){
                            if(dansLaMap(i, j)){
                                switch (map[i][j].getType()) {
                                    case 0:
                                        int index = tileIndex(i, j);
                                        if(index == 28){
                                            if(isTypeN(i-1, j, 2)){
                                                index = 51;
                                            }
                                        }
                                        if(index == 36){
                                            if(isTypeN(i+1, j, 2)){
                                                index = 52;
                                            }
                                        }
                                        if(index == 12){
                                            if(isTypeN(i, j-1, 2)){
                                                index = 49;
                                            }
                                        }
                                        if(index == 42){
                                            if(isTypeN(i, j+1, 2)){
                                                index = 54;
                                            }
                                        }
                                        //----------------------0
                                        if(index == 33){
                                            if(isTypeN(i-1, j, 2)){
                                                if(isTypeN(i, j-1, 2)){
                                                    index = 48;
                                                } else if(!solideA(i+1, j+1)){
                                                    index = 56;
                                                }
                                            } else if(isTypeN(i, j-1, 2)){
                                                index = 60;
                                            }
                                        }
                                        if(index == 41){
                                            if(isTypeN(i+1, j, 2)){
                                                if(isTypeN(i, j-1, 2)){
                                                    index = 50;
                                                } else if(!solideA(i-1, j+1)){
                                                    index = 57;
                                                }
                                            } else if(isTypeN(i, j-1, 2)){
                                                index = 61;
                                            }
                                        }
                                        if(index == 44){
                                            if(isTypeN(i-1, j, 2)){
                                                if(isTypeN(i, j+1, 2)){
                                                    index = 53; 
                                                } else if(!solideA(i+1, j-1)){
                                                    index = 58;
                                                }
                                            } else if(isTypeN(i, j+1, 2)){
                                                index = 62;
                                            }
                                        }
                                        if(index == 45){
                                            if(isTypeN(i+1, j, 2)){
                                                if(isTypeN(i, j+1, 2)){
                                                    index = 55;
                                                } else if(!solideA(i-1, j-1)){
                                                    index = 59;
                                                }
                                            } else if(isTypeN(i, j+1, 2)){
                                                index = 63;
                                            }
                                        }
                                        //----------------------0
                                        if(index == 4){
                                            if(isTypeN(i-1, j-1, 2)){
                                                index = 74;
                                            }
                                        }
                                        if(index == 7){
                                            if(isTypeN(i+1, j-1, 2)){
                                                index = 75;
                                            }
                                        }
                                        if(index == 26){
                                            if(isTypeN(i-1, j+1, 2)){
                                                index = 73;
                                            }
                                        }
                                        if(index == 34){
                                            if(isTypeN(i+1, j+1, 2)){
                                                index = 72;
                                            }
                                        }
                                        
                                        map[i][j].setImg(tiles[index]);
                                        
                                        int listeConformes[] = {0, 4, 7, 12, 26, 28, 33, 34, 36, 41, 42, 44, 45, 46, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, 62, 63, 64, 65, 66, 67, 68, 69, 70, 73, 74, 75, 76};
                                        
                                        boolean conforme = false;
                                        for(int k = 0; k < listeConformes.length; k++){
                                            if(listeConformes[k] == index){
                                                conforme = true;
                                            }
                                        }
                                        map[i][j].setConforme(conforme);
                                        
                                        break;
                                    case 1:
                                        map[i][j].setImg(tiles[0]);
                                        break;
                                    case 2:
                                        map[i][j].setImg(tiles[71]);
                                        break;
                                    case 4:
                                        map[i][j].setImg(bullets[0]);
                                        break;
                                    case 5:
                                        map[i][j].setImg(bullets[2]);
                                        break;
                                    default:
                                        break;
                                }
                                if(map[x][y].getType() != 0){
                                    map[i][j].setConforme(true);
                                }
                            }
                        }
                    }
                }
                int cageTiles[] = {
                    64, 77, 69, 70, 70, 68, 77, 65,
                    79,  0,  0,  0,  0,  0,  0, 78,
                    79,  0,  0,  0,  0,  0,  0, 78,
                    79,  0,  0,  0,  0,  0,  0, 78,
                    67, 76, 76, 76, 76, 76, 76, 66
                };
                int indexCage = 0;

                for(int j = cageY; j < cageY+5; j++){
                    for(int i = cageX; i < cageX+8; i++){
                        if(dansLaMap(i, j)){
                            map[i][j].setType(3);
                            map[i][j].setImg(tiles[cageTiles[indexCage]]);
                        }
                        indexCage++;
                    }
                }
            }
        }
    }
    
    public void setSp(int x, int y, int type){
        if(x >= 0 && x < mapWidth && y >= 0 && y < mapHeight && libreA(x, y)){
            if(type == 1){
                mapSp[spawnX][spawnY] = 0;
                spawnX = x;
                spawnY = y;
            }
            mapSp[x][y] = type;
        }
    }
    
    /**
     * @return the tileWidth
     */
    public int getTileWidth() {
        return tileWidth;
    }

    /**
     * @return the tileHeight
     */
    public int getTileHeight() {
        return tileHeight;
    }
    
    /**
     * @return the map
     */
    public Tile[][] getMap() {
        return map;
    }
    
    /**
     * @return the mapSp
     */
    public int[][] getMapSp() {
        return mapSp;
    }
    
    public void setNewSize(int newWidth, int newHeight){
        if(newWidth > 0 && newHeight > 0){
            Tile newMap[][] = new Tile[newWidth][newHeight];
            int newMapSp[][] = new int[newWidth][newHeight];

            for(int i = 0; i < newWidth; i++){
                for(int j = 0; j < newHeight; j++){
                    if(dansLaMap(i, j)){
                        newMap[i][j] = new Tile(i, j, map[i][j].getType());
                        newMapSp[i][j] = mapSp[i][j];
                    } else {
                        newMap[i][j] = new Tile(i, j, 1);
                        newMapSp[i][j] = 0;
                    }
                }
            }
            map = newMap;
            mapSp = newMapSp;
            mapWidth = newWidth;
            mapHeight = newHeight;
            for(int x = 0; x < this.getMapWidth(); x++){
                for(int y = 0; y < this.getMapHeight(); y++){
                    setTile(x, y, map[x][y].getType());
                }
            }
        }
    }
    
    public boolean mapValide(){
        boolean valide = true;
        for(int x = 0; x < this.getMapWidth(); x++){
            for(int y = 0; y < this.getMapHeight(); y++){
                if(!map[x][y].isConforme()){
                    valide = false;
                }
            }
        }
        return valide;
    }
}
