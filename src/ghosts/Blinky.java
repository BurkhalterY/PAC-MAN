package ghosts;

import engine.Game;
import engine.Ghost;

public class Blinky extends Ghost {

    public Blinky(double x, double y, double speed) {
        super(x, y, speed, "blinky");
    }
    
    @Override
    public void setCible() {
        xCible = Game.getPlayers().get(0).getTileX();
        yCible = Game.getPlayers().get(0).getTileY();
    }
    
}
