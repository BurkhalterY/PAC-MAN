package engine;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.Arrays;

public abstract class Ghost extends Entity {
    
    public int xCible, yCible;
    
    public Ghost(double x, double y, double speed, String spritesheetLocation) {
        super(x, y, speed);
        folder = "ghosts";
        this.spritesheetLocation = spritesheetLocation;
        loadSprites();
    }
    
    public abstract void setCible();
    
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
    
    public void afficherDebug(Graphics g, int width, int height){
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
    
}
