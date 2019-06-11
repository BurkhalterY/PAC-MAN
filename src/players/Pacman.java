package players;

import engine.Game;
import engine.Player;

public class Pacman extends Player{
    public Pacman(double x, double y, double speed) {
        super(x, y, speed);
    }

    @Override
    public void updateNextDirection() {
        nextDirection = Game.getPlayerOneDirection();
    }

    @Override
    public void loadSprites() {
        
    }
}
