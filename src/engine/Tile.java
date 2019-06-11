package engine;

import java.awt.image.BufferedImage;

public class Tile {
    
    public static final byte up = 1;
    public static final byte left = 2;
    public static final byte down = 4;
    public static final byte right = 8;
    
    public enum Type{
        Free,
        Wall,
        Off_map,
        Ghost_house,
        Ghost_door
    }
    
    public enum Event{
        Nothing,
        Slowing_down,
        Prohibition_up,
        Prohibition_down,
        Prohibition_left,
        Prohibition_right
    }
    
    private short opensDirections = 0;
    
    private Type mapTile;
    private Event eventType;
    private BufferedImage img;
    
    public Tile(int mapTile, int eventType){
        switch(mapTile){
            case 0:
                this.mapTile = Type.Free;
                break;
            case 1:
                this.mapTile = Type.Wall;
                break;
            case 2:
                this.mapTile = Type.Ghost_house;
                break;
            case 3:
                this.mapTile = Type.Ghost_door;
                break;
        }
        switch(mapTile){
            case 0:
                this.eventType = Event.Nothing;
                break;
            case 1:
                this.eventType = Event.Slowing_down;
                break;
            case 2:
                this.eventType = Event.Prohibition_up;
                break;
            case 3:
                this.eventType = Event.Prohibition_down;
                break;
            case 4:
                this.eventType = Event.Prohibition_left;
                break;
            case 5:
                this.eventType = Event.Prohibition_right;
                break;
        }
    }
    
    public boolean isPassable(){
        return mapTile == Type.Free;
    }
    
    public Type getMapTile(){
        return mapTile;
    }

    public BufferedImage getImg(){
        return img;
    }
    
    public void setImg(BufferedImage img){
        this.img = img;
    }
    
    /*public void addOpenDirection(byte added){
        opensDirections |= added;
    }
    
    public void removeOpenDirection(byte removed){
        opensDirections &= ~removed;
    }
    
    public boolean checkOpenDirection(byte direction){
        return (opensDirections & direction) > 0;
    }*/
    
}
