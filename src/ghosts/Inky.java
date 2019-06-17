package ghosts;

import engine.Game;
import engine.Ghost;

public class Inky extends Ghost {

    public Inky(double x, double y, double speed) {
        super(x, y, speed, "inky");
    }
    
    @Override
    public void setCible() {
        int xPacman = Game.getPlayers().get(0).getTileX();
        int yPacman = Game.getPlayers().get(0).getTileY();
        Direction direction = Game.getPlayers().get(0).getDirection();
        
        int xBlinky = Game.getGhosts().get(0).getTileX();
        int yBlinky = Game.getGhosts().get(0).getTileY();
        
        switch (direction) {
            case Left:
                xPacman -= 2;
                break;
            case Right:
                xPacman += 2;
                break;
            case Up:
                xPacman -= 2;
                yPacman -= 2;
                break;
            case Down:
                yPacman += 2;
                break;
            default:
                break;
        }

        xCible = 2* xPacman - xBlinky;
        yCible = 2* yPacman - yBlinky;
    }
    
}
