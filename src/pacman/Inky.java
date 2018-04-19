/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pacman;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
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
    private BufferedImage inky;
    
    public Inky(float x, float y, float vitesse) {
        super(x, y, vitesse);
        
        try {
            inky = ImageIO.read(new File("res/inky.png"));
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
    
    public void afficher(Graphics g){    
        Graphics2D g2d = (Graphics2D)g;
        AffineTransform rotation = new AffineTransform();
        
        rotation.translate(x*8-4, y*8-4);
        g2d.drawImage(inky, rotation, null);
        
        super.afficher(g);
    }
}
