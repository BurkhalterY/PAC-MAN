package engine;

import engine.Tile.Type;
import ghosts.*;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import players.Pacman;

public class Map {
    
    private Tile map[][];
    private int mapWidth = 0, mapHeight = 0;
    private int tileWidth = 1, tileHeight = 1;
    
    private BufferedImage tileset;
    private int tilesetRows = 10, tilesetColumns = 8;
    private BufferedImage tiles[] = new BufferedImage[tilesetRows*tilesetColumns];
    
    public Map(String mapFolder, String tilesetPicture){
        //loadTileset("res/tilesets/"+tilesetPicture);
        loadMap("res/levels/"+mapFolder);
        for(int y=0; y < mapHeight; y++){
            for(int x=0; x < mapWidth; x++){
                map[x][y].setImg(calculateTile(x,y));
            }
        }
    }
    
    /*public void loadTileset(String tilesetPicture){
        try {
            tileset = ImageIO.read(new File(tilesetPicture+"/tileset.png"));
        } catch (IOException ex) {
            Logger.getLogger(Map.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        
        tileWidth = tileset.getWidth()/tilesetColumns;
        tileHeight = tileset.getHeight()/tilesetRows;
  
        int tilesID = 0;
        
        for(int y = 0; y < tilesetRows; y++){
            for(int x = 0; x < tilesetColumns; x++){
                tiles[tilesID] = tileset.getSubimage(x * tileWidth, y * tileHeight, tileWidth, tileHeight);
                tilesID++;
            }
        }
        
    }*/
    
    public void loadMap(String mapFolder){
        try
        {
            File f1 = new File (mapFolder+"/map.txt");
            File f2 = new File (mapFolder+"/entities.txt");
            File f3 = new File (mapFolder+"/events.txt");
            FileReader fr1 = new FileReader (f1);
            FileReader fr2 = new FileReader (f2);
            FileReader fr3 = new FileReader (f2);
            BufferedReader br1 = new BufferedReader (fr1);
            BufferedReader br2 = new BufferedReader (fr2);
            BufferedReader br3 = new BufferedReader (fr3);
            FileReader fr0 = new FileReader (f1);
            BufferedReader br0 = new BufferedReader (fr0);
            
            try
            {
            
                int nombreLignes = 0;
                String line = br0.readLine();
                while(line != null){
                    nombreLignes++;
                    line = br0.readLine();
                }

                br0.close();
                fr0.close();

                String line1 = br1.readLine();
                String line2 = br2.readLine();
                String line3 = br3.readLine();

                int y = 0;

                while (line1 != null)
                {
                    String tableLigne1[] = line1.split("\t");
                    String tableLigne2[] = line2.split("\t");
                    String tableLigne3[] = line3.split("\t");
                    line1 = br1.readLine();
                    line2 = br2.readLine();
                    line3 = br3.readLine();

                    if(mapWidth == 0 || mapHeight == 0){
                        mapWidth = tableLigne1.length;
                        mapHeight = nombreLignes;
                        map = new Tile[mapWidth][mapHeight];
                    }

                    for(int x = 0; x < mapWidth; x++){
                        map[x][y] = new Tile(Integer.parseInt(tableLigne1[x]), Integer.parseInt(tableLigne2[x]));
                        int provId = Integer.parseInt(tableLigne3[x]);
                        switch(provId){
                            case 3:
                                Game.getPlayers().add(new Pacman(x, y, 11d/60d));
                                break;
                            case 4:
                                Game.getGhosts().add(new Blinky(x, y, 11d/90d));
                                break;
                            case 5:
                                Game.getGhosts().add(new Inky(x, y, 11d/90d));
                                break;
                            case 6:
                                Game.getGhosts().add(new Pinky(x, y, 11d/90d));
                                break;
                            case 7:
                                Game.getGhosts().add(new Clyde(x, y, 11d/90d));
                                break;
                            case 8:
                                Game.getGhosts().add(new Sue(x, y, 11d/90d));
                                break;
                        }
                    }
                    y++;
                }

                br1.close();
                br2.close();
                br3.close();
                fr1.close();
                fr2.close();
                fr3.close();
                
            } catch (IOException exception) {
                System.out.println ("Erreur lors de la lecture : " + exception.getMessage());
            }
        }
        catch (FileNotFoundException exception)
        {
            System.out.println ("Le fichier n'a pas été trouvé");
        }
        
    }
    
    public BufferedImage calculateTile(int x, int y){
        switch(getTileType(x, y)){
            case Free:
                return changeColor(new BufferedImage(tileWidth, tileHeight, BufferedImage.TYPE_INT_RGB), Color.BLACK, Color.BLACK);
            case Wall:
                return changeColor(new BufferedImage(tileWidth, tileHeight, BufferedImage.TYPE_INT_RGB), Color.BLACK, Color.BLUE);
            case Off_map:
                return changeColor(new BufferedImage(tileWidth, tileHeight, BufferedImage.TYPE_INT_RGB), Color.BLACK, Color.RED);
            case Ghost_house:
                return changeColor(new BufferedImage(tileWidth, tileHeight, BufferedImage.TYPE_INT_RGB), Color.BLACK, Color.ORANGE);
            case Ghost_door:
                return changeColor(new BufferedImage(tileWidth, tileHeight, BufferedImage.TYPE_INT_RGB), Color.BLACK, Color.YELLOW);
            default :
                return changeColor(new BufferedImage(tileWidth, tileHeight, BufferedImage.TYPE_INT_RGB), Color.BLACK, Color.MAGENTA);
        }
    }
    
    /*
    public int autoTilingTileWall(int x, int y){
        int val = 0;
        boolean[] checkCoins = { false, false, false, false };
        
        if(getTileType(x, y-1) == Tile.Type.Wall){
            val += 2;
            checkCoins[0] = true;
        }
        if(getTileType(x-1, y) == Tile.Type.Wall){
            val += 8;
            checkCoins[1] = true;
        }
        if(getTileType(x+1, y) == Tile.Type.Wall){
            val += 16;
            checkCoins[2] = true;
        }
        if(getTileType(x, y+1) == Tile.Type.Wall){
            val += 64;
            checkCoins[3] = true;
        }
        
        if(checkCoins[0] && checkCoins[1] && getTileType(x-1, y-1) == Tile.Type.Wall){
            val += 1;
        }
        if(checkCoins[0] && checkCoins[2] && getTileType(x+1, y-1) == Tile.Type.Wall){
            val += 4;
        }
        if(checkCoins[1] && checkCoins[3] && getTileType(x-1, y+1) == Tile.Type.Wall){
            val += 32;
        }
        if(checkCoins[2] && checkCoins[3] && getTileType(x+1, y+1) == Tile.Type.Wall){
            val += 128;
        }
        
        int[][] remplacement = {{2,1},{8,2},{10,3},{11,4},{16,5},{18,6},{22,7},{24,8},{26,9},{27,10},{30,11},{31,12},{64,13},{66,14},{72,15},{74,16},{75,17},{80,18},{82,19},{86,20},{88,21},{90,22},{91,23},{94,24},{95,25},{104,26},{106,27},{107,28},{120,29},{122,30},{123,31},{126,32},{127,33},{208,34},{210,35},{214,36},{216,37},{218,38},{219,39},{222,40},{223,41},{248,42},{250,43},{251,44},{254,45},{255,46},{0,47}};
        for(int i = 0; i < remplacement.length; i++){
            if(remplacement[i][0] == val){
                val = remplacement[i][1];
            }
        }
        
        return val;
    }
    
    public int autoTilingTileGhostHouse(int x, int y){
        int val = 0;
        
        if(getTileType(x, y-1) == Tile.Type.Ghost_house){
            val += 1;
        }
        if(getTileType(x-1, y) == Tile.Type.Ghost_house){
            val += 2;
        }
        if(getTileType(x+1, y) == Tile.Type.Ghost_house){
            val += 4;
        }
        if(getTileType(x, y+1) == Tile.Type.Ghost_house){
            val += 8;
        }
        return val;
    }
    */
    /*public int adaptToNoEditorMode(int tileIndex){
        int[][] remplacement = {{48,0},{49,1},{50,2},{34,3},{42,4},{26,5},{33,6},{41,7},{69,8},{51,9},{71,10},{52,11},{36,12},{46,13},{28,14},{44,15},{45,16},{68,17},{53,18},{54,19},{55,20},{7,21},{12,22},{4,23},{64,24},{66,25},{76,26},{56,27},{57,28},{60,29},{61,30},{72,31},{73,32},{78,33},{79,34},{70,35},{58,36},{59,37},{62,38},{63,39},{75,40},{74,41},{67,42},{77,43},{66,44}};
        for(int i = 0; i < remplacement.length; i++){
            if(remplacement[i][0] == tileIndex){
                tileIndex = remplacement[i][1];
            }
        }
        return tileIndex;
    }*/
    
    public Tile.Type getTileType(int x, int y){
        if(x >= 0 && x < mapWidth && y >= 0 && y < mapHeight){
            return map[x][y].getMapTile();
        } else {
            return Tile.Type.Off_map;
        }
    }
    
    public void afficher(Graphics g, int width, int height){
        float size;
        if(width/mapWidth > height/mapHeight){
            size = (float)height/mapHeight;
        } else {
            size = (float)width/mapWidth;
        }

        Graphics2D g2d = (Graphics2D)g;
        
        
        for(int x=0; x < mapWidth; x++){
            for(int y=0; y < mapHeight; y++){
                /*AffineTransform transformation = new AffineTransform();
                transformation.translate(x*size, y*size);
                transformation.scale(size/tileWidth, size/tileHeight);
                g2d.drawImage(map[x][y].getImg(), transformation, null);*/
                if(map[x][y].getMapTile() == Type.Free){
                    g.setColor(Color.darkGray);
                } else {
                    g.setColor(Color.orange);
                }
                g.fillRect((int)(x*size), (int)(y*size), (int)(size), (int)(size));
            }
        }
        
    }
    
    public static BufferedImage changeColor(BufferedImage image, Color from, Color to){
        int width = image.getWidth();
        int height = image.getHeight();
        WritableRaster raster = image.getRaster();
        
        for(int x = 0; x < width; x++){
            for(int y = 0; y < width; y++){
                int[] pixel = raster.getPixel(x, y, (int[])null);
                if(pixel[0] == from.getRed() && pixel[1] == from.getGreen() && pixel[2] == from.getBlue()){
                    pixel[0] = to.getRed();
                    pixel[1] = to.getGreen();
                    pixel[2] = to.getBlue();
                    raster.setPixel(x, y, pixel);
                }
            }
        }
        
        return image;
    }

    public int getMapWidth() {
        return mapWidth;
    }

    public int getMapHeight() {
        return mapHeight;
    }
    
    public Tile[][] getMap(){
        return map;
    }
    
    /*public static int moduloNegativ(int a, int b){
        int c = a % b;
        if (c<0) c += b;
        return c;
    }*/
    
}
