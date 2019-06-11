package engine;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public abstract class Ghost extends Entity {
    
    public int xCible, yCible;
    
    public Ghost(double x, double y, double speed, String spritesheetLocation) {
        super(x, y, speed);
        this.spritesheetLocation = spritesheetLocation;
        loadSprites();
    }
    
    public void loadSprites(){
        try {
            JSONObject json = (JSONObject)new JSONParser().parse(new FileReader("res/textures_pack/Original/Ghosts/"+spritesheetLocation+"/"+spritesheetLocation+".json"));
            int up = ((Long)json.get("up")).intValue();
            int down = ((Long)json.get("down")).intValue();
            int left = ((Long)json.get("left")).intValue();
            int right = ((Long)json.get("right")).intValue();
            
            BufferedImage spritesheetUp = ImageIO.read(new File("res/textures_pack/Original/Ghosts/"+spritesheetLocation+"/up.png"));
            BufferedImage[] spriteUp = new BufferedImage[up];
            for(int i = 0; i < up; i++){
                int width = spritesheetUp.getWidth()/up;
                int height = spritesheetUp.getHeight();
                spriteUp[i] = spritesheetUp.getSubimage(i * width, 0, width, height);
            }
            sprites.put("up", new Sprite(spriteUp));
            
            BufferedImage spritesheetDown = ImageIO.read(new File("res/textures_pack/Original/Ghosts/"+spritesheetLocation+"/down.png"));
            BufferedImage[] spriteDown = new BufferedImage[down];
            for(int i = 0; i < down; i++){
                int width = spritesheetDown.getWidth()/down;
                int height = spritesheetDown.getHeight();
                spriteDown[i] = spritesheetDown.getSubimage(i * width, 0, width, height);
            }
            sprites.put("down", new Sprite(spriteDown));
            
            BufferedImage spritesheetLeft = ImageIO.read(new File("res/textures_pack/Original/Ghosts/"+spritesheetLocation+"/left.png"));
            BufferedImage[] spriteLeft = new BufferedImage[left];
            for(int i = 0; i < left; i++){
                int width = spritesheetLeft.getWidth()/left;
                int height = spritesheetLeft.getHeight();
                spriteLeft[i] = spritesheetLeft.getSubimage(i * width, 0, width, height);
            }
            sprites.put("left", new Sprite(spriteLeft));
            
            BufferedImage spritesheetRight = ImageIO.read(new File("res/textures_pack/Original/Ghosts/"+spritesheetLocation+"/right.png"));
            BufferedImage[] spriteRight = new BufferedImage[right];
            for(int i = 0; i < right; i++){
                int width = spritesheetRight.getWidth()/right;
                int height = spritesheetRight.getHeight();
                spriteRight[i] = spritesheetRight.getSubimage(i * width, 0, width, height);
            }
            sprites.put("right", new Sprite(spriteRight));
        
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Ghost.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Ghost.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(Ghost.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public abstract void setCible();
    
    @Override
    public void move(){
        super.move();
        switch(currentDirection){
            case Up:
                currentSprite = "up";
                break;
            case Down:
                currentSprite = "down";
                break;
            case Left:
                currentSprite = "left";
                break;
            case Right:
                currentSprite = "right";
                break;
        }
    }
    
    @Override
    public void updateNextDirection() {
        setCible();
        DirectionDistance[] directionsPossibles = new DirectionDistance[4];
        directionsPossibles[0] = new DirectionDistance(Entity.calculateDistanceBetweenTiles(getTileX(), getTileY()-1, xCible, yCible), Direction.Up);
        directionsPossibles[1] = new DirectionDistance(Entity.calculateDistanceBetweenTiles(getTileX()-1, getTileY(), xCible, yCible), Direction.Left);
        directionsPossibles[2] = new DirectionDistance(Entity.calculateDistanceBetweenTiles(getTileX(), getTileY()+1, xCible, yCible), Direction.Down);
        directionsPossibles[3] = new DirectionDistance(Entity.calculateDistanceBetweenTiles(getTileX()+1, getTileY(), xCible, yCible), Direction.Right);
        Arrays.sort(directionsPossibles);
        
        for(DirectionDistance direction : directionsPossibles){
            if(!collision(direction.getDirection()) && currentDirection != inverseDirection(direction.getDirection())){
                nextDirection = direction.getDirection();
                break;
            }
        }
        
    }
    
    class DirectionDistance implements Comparable<DirectionDistance> {
        private double distance;
        private Direction direction;
        
        public DirectionDistance(double distance, Direction direction) {
            this.distance = distance;
            this.direction = direction;
        }

        public double getDistance() {
            return distance;
        }

        public void setDistance(double distance) {
            this.distance = distance;
        }

        public Direction getDirection() {
            return direction;
        }

        public void setDirection(Direction direction) {
            this.direction = direction;
        }
        
        @Override
        public int compareTo(DirectionDistance o) {
            return (int)(this.distance - o.distance);
        }
    }
    
    /*public Direction[] calculateDirectionsPossibles(){
        ArrayList<Direction> list = new ArrayList<>();
        if(!collision(Direction.Up) && currentDirection != Direction.Down)
            list.add(Direction.Up);
        if(!collision(Direction.Left) && currentDirection != Direction.Right)
            list.add(Direction.Left);
        if(!collision(Direction.Down) && currentDirection != Direction.Up)
            list.add(Direction.Down);
        if(!collision(Direction.Right) && currentDirection != Direction.Left)
            list.add(Direction.Right);
        return list.toArray(new Direction[list.size()]);
    }*/
}
