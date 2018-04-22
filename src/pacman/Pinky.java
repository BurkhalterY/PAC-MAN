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
public class Pinky extends Ghost{
    
    public Pinky(float x, float y, float vitesse, int xScatter, int yScatter) {
        super(x, y, vitesse, xScatter, yScatter);
        basAttente = true;
        
        try {
            img = ImageIO.read(new File("res/pinky.png"));
        } catch (IOException ex) {
            Logger.getLogger(Pinky.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public void setCible(){
        int xPacman = Panel.getPlayersTab()[0].getX();
        int yPacman = Panel.getPlayersTab()[0].getY();
        Direction direction = Panel.getPlayersTab()[0].getDirectionCourente();
        switch (direction) {
            case Gauche:
                xPacman -= 4;
                break;
            case Droite:
                xPacman += 4;
                break;
            case Haut:
                yPacman -= 4;
                break;
            case Bas:
                yPacman += 4;
                break;
            default:
                break;
        }
        
        cible = new Tile(xPacman, yPacman, 0);
    }
    
    public boolean peutSortir(){
        return true;
    }
}
