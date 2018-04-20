/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pacman;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author BuYa
 */
public class Inky extends Ghost{
    
    public Inky(float x, float y, float vitesse, int xScatter, int yScatter) {
        super(x, y, vitesse, xScatter, yScatter);
        
        try {
            img = ImageIO.read(new File("res/inky.png"));
        } catch (IOException ex) {
            Logger.getLogger(Pacman.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public void setCible(int xPacman, int yPacman, int xBlinky, int yBlinky, Direction direction){
        switch (directionCourente) {
            case Gauche:
                xPacman -= 2;
                break;
            case Droite:
                xPacman += 2;
                break;
            case Haut:
                yPacman -= 2;
                break;
            case Bas:
                yPacman += 2;
                break;
            default:
                break;
        }
        
        cible = new Tile(xPacman + (xBlinky - xPacman), yPacman + (yBlinky - yPacman), 0);
    }
}
