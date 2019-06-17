package engine;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import org.json.JSONArray;
import org.json.JSONObject;

public abstract class Entity {
    
    public enum Direction{
        Up,
        Down,
        Left,
        Right;
    }
    protected double x, y, speed;
    protected Direction currentDirection = Direction.Right, nextDirection = Direction.Right;
    protected boolean stop = false;
    protected String spritesheetLocation, currentSprite = "";
    protected HashMap<String, Sprite> sprites = new HashMap<String, Sprite>();
    protected int spriteWidth = 8, spriteHeight = 8;
    
    protected String folder = "entities";
    
    public Entity(double x, double y, double speed) {
        this.x = x;
        this.y = y;
        this.speed = speed;
    }
    
    public void loadSprites(){
        try {
            
            String fileStr = new String(Files.readAllBytes(Paths.get("res/textures_pack/Original/"+folder+"/"+spritesheetLocation+"/"+spritesheetLocation+".json")), "UTF-8");
            JSONArray json = new JSONObject(fileStr).getJSONArray("sprites");
            for(int j = 0; j < json.length(); j++){
                
                String name = json.getJSONArray(j).getString(0);
                int value = json.getJSONArray(j).getInt(1);
                
                BufferedImage spritesheet = ImageIO.read(new File("res/textures_pack/Original/"+folder+"/"+spritesheetLocation+"/"+name+".png"));
                BufferedImage[] sprite = new BufferedImage[value];
                for(int i = 0; i < value; i++){
                    int width = spritesheet.getWidth()/value;
                    int height = spritesheet.getHeight();
                    sprite[i] = spritesheet.getSubimage(i * width, 0, width, height);
                }
                sprites.put(name, new Sprite(sprite));
                
            }
        
        } catch (IOException ex) {
            Logger.getLogger(Ghost.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void afficher(Graphics g, int width, int height){
        int mapWidth = Game.getMap().getMapWidth();
        int mapHeight = Game.getMap().getMapHeight();
        
        float size;
        if(width/mapWidth > height/mapHeight){
            size = (float)height/mapHeight;
        } else {
            size = (float)width/mapWidth;
        }
        
        Graphics2D g2d = (Graphics2D)g;
        
        AffineTransform transformation = new AffineTransform();
        transformation.translate((x-0.5)*size, (y-0.5)*size);
        transformation.scale(size/spriteWidth, size/spriteHeight);
        g2d.drawImage(sprites.getOrDefault(currentSprite, new Sprite(new BufferedImage[1])).render(), transformation, null);
    }
    
    public void move(){
        
        calculateNextDirection();
        
        double xFut = x;
        double yFut = y;
        
        switch(currentDirection){
            case Up:
                yFut -= speed;
                currentSprite = "up";
                break;
            case Down:
                yFut += speed;
                currentSprite = "down";
                break;
            case Left:
                xFut -= speed;
                currentSprite = "left";
                break;
            case Right:
                xFut += speed;
                currentSprite = "right";
                break;
        }
        
        if(collision(currentDirection)){
            x = (int)(xFut+0.5d);
            y = (int)(yFut+0.5d);
        } else {
            x = xFut;
            y = yFut;
        }
        
        switch(currentDirection){
            case Up: case Down:
                x = (int)(xFut+0.5d);
                break;
            case Left: case Right:
                y = (int)(yFut+0.5d);
                break;
        }
        
        if(x >= Game.getMap().getMapWidth()+1){
            x = -1;
        } else if(x <= -2){
            x = Game.getMap().getMapWidth();
        }
        if(y >= Game.getMap().getMapHeight()+1){
            y = -1;
        } else if(y <= -2){
            y = Game.getMap().getMapHeight();
        }
        
    }
    
    public boolean collision(Direction direction){
        
        double x4checkA = x;
        double y4checkA = y;
        
        double xFut = x;
        double yFut = y;
        
        boolean collision = true;
        
        try{
            switch(direction){
                case Up:
                    yFut -= speed;
                    y4checkA = yFut;
                    collision = !(Game.getMap().getMap()[(int)(x4checkA+speed/2d)][(int)y4checkA].isPassable() && Game.getMap().getMap()[(int)(x4checkA+1-speed/2d)][(int)y4checkA].isPassable());
                    break;
                case Down:
                    yFut += speed;
                    y4checkA = yFut+1;
                    collision = !(Game.getMap().getMap()[(int)(x4checkA+speed/2d)][(int)y4checkA].isPassable() && Game.getMap().getMap()[(int)(x4checkA+1-speed/2d)][(int)y4checkA].isPassable());
                    break;
                case Left:
                    xFut -= speed;
                    x4checkA = xFut;
                    collision = !(Game.getMap().getMap()[(int)x4checkA][(int)(y4checkA+speed/2d)].isPassable() && Game.getMap().getMap()[(int)x4checkA][(int)(y4checkA+1-speed/2d)].isPassable());
                    break;
                case Right:
                    xFut += speed;
                    x4checkA = xFut+1;
                    collision = !(Game.getMap().getMap()[(int)x4checkA][(int)(y4checkA+speed/2d)].isPassable() && Game.getMap().getMap()[(int)x4checkA][(int)(y4checkA+1-speed/2d)].isPassable());
                    break;
            }
        } catch(ArrayIndexOutOfBoundsException e){
            collision = false;
        }

        return collision;
    }

    public void calculateNextDirection(){
        updateNextDirection();

        if(!collision(nextDirection) && x >= 0 & x <= Game.getMap().getMapWidth()-1 && y >= 0 & y <= Game.getMap().getMapHeight()-1){
            currentDirection = nextDirection;
        }
    }
    
    public int getTileX(){
        return (int)(x+0.5d);
    }
    
    public int getTileY(){
        return (int)(y+0.5d);
    }
    
    public Direction getDirection(){
        return currentDirection;
    }
    
    public static Direction inverseDirection(Direction direction){
        switch(direction){
            case Up:
                return Direction.Down;
            case Down:
                return Direction.Up;
            case Left:
                return Direction.Right;
            case Right:
                return Direction.Left;
            default:
                return null;
        }
    }
    
    public static double calculateDistanceBetweenTiles(int x1, int y1, int x2, int y2){
        return Math.sqrt(Math.pow(x2-x1, 2) + Math.pow(y2-y1, 2));
    }
    
    public abstract void updateNextDirection();
}
