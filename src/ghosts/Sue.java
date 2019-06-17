package ghosts;

import engine.Game;
import engine.Ghost;
import java.util.Random;

public class Sue extends Ghost {

    public Sue(double x, double y, double speed) {
        super(x, y, speed, "sue");
    }
    
    @Override
    public void setCible() {
        xCible = this.getTileX();
        yCible = this.getTileY();
        
        Random randomGenerator = new Random();
	int random = randomGenerator.nextInt(4);
        System.out.println(random);
        
        switch(random){
            case 0:
                xCible++;
                break;
            case 1:
                xCible--;
                break;
            case 2:
                yCible++;
                break;
            case 3:
                yCible--;
                break;
        }
    }
    
}
