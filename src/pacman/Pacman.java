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
public class Pacman extends Player {
    private BufferedImage pacman;

    public Pacman(float x, float y, float vitesse){
        super(x, y, vitesse);
        
        try {
            pacman = ImageIO.read(new File("res/pacman.png"));
        } catch (IOException ex) {
            Logger.getLogger(Pacman.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public void afficher(Graphics g, int width, int height){
        double size;
        int mapWidth = Panel.getMap().getMapWidth();
        int mapHeight = Panel.getMap().getMapHeight();
        
        if(width/mapWidth > height/mapHeight){
            size = height/mapHeight;
        } else {
            size = width/mapWidth;
        }
        
        Graphics2D g2d = (Graphics2D)g;
        AffineTransform rotation = new AffineTransform();
        int a = 0;
        switch (directionCourente) {
            case Gauche:
                a = 180;
                break;
            case Droite:
                a = 0;
                break;
            case Haut:
                a = -90;
                break;
            case Bas:
                a = 90;
                break;
            default:
                break;
        }
        rotation.translate((x-0.5)*size, (y-0.5)*size);
        rotation.scale(size/8, size/8);
        rotation.rotate(Math.toRadians(a), 8, 8);
        g2d.drawImage(pacman, rotation, null);
    }
}
