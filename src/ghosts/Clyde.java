package ghosts;

import engine.Game;
import engine.Ghost;

public class Clyde extends Ghost {

    public Clyde(double x, double y, double speed) {
        super(x, y, speed, "clyde");
    }
    
    @Override
    public void setCible() {
        int xPacman = Game.getPlayers().get(0).getTileX();
        int yPacman = Game.getPlayers().get(0).getTileY();
        
        if(Math.sqrt(Math.pow(getTileX() - xPacman, 2) + Math.pow(getTileY() - yPacman, 2)) > 8){
            xCible = xPacman;
            yCible = yPacman;
        } else {
            xCible = 0;
            yCible = 0;
        }
    }
    
}
