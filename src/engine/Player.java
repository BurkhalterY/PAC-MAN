package engine;

public abstract class Player extends Entity {
    public Player(double x, double y, double speed, String spritesheetLocation) {
        super(x, y, speed);
        folder = "players";
        this.spritesheetLocation = spritesheetLocation;
        loadSprites();
    }
    

}
