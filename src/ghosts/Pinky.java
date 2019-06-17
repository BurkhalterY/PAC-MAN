package ghosts;

import engine.Game;
import engine.Ghost;

public class Pinky extends Ghost {

    public Pinky(double x, double y, double speed) {
        super(x, y, speed, "pinky");
    }
    
    @Override
    public void setCible() {
        xCible = Game.getPlayers().get(0).getTileX();
        yCible = Game.getPlayers().get(0).getTileY();
        
        switch(Game.getPlayers().get(0).getDirection()){
            case Up:
                xCible-=4;
                yCible-=4;
                break;
            case Down:
                yCible+=4;
                break;
            case Left:
                xCible-=4;
                break;
            case Right:
                xCible+=4;
                break;
        }
    }
    
}
