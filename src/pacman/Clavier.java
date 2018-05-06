/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pacman;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import pacman.Entity.Direction;

/**
 *
 * @author BuYa
 */
public class Clavier extends KeyAdapter{
    
    private static Direction direction, direction2;

    public static Direction getDirection() {
        return direction;
    }
    
    public static Direction getDirection2() {
        return direction2;
    }
    
    public void keyPressed(KeyEvent e) {

        if(e.getKeyCode() == KeyEvent.VK_LEFT) {
            direction = Direction.Gauche;
        }

        if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
            direction = Direction.Droite;
        } 
        
        if(e.getKeyCode() == KeyEvent.VK_UP) {
            direction = Direction.Haut;
        }

        if(e.getKeyCode() == KeyEvent.VK_DOWN) {
            direction = Direction.Bas;
        }
        
        if(e.getKeyCode()== KeyEvent.VK_A) {
            Panel.getTetris().setDirection(Direction.Gauche);
        }

        if(e.getKeyCode()== KeyEvent.VK_D) {
            Panel.getTetris().setDirection(Direction.Droite);
        }

        if(e.getKeyCode()== KeyEvent.VK_S) {
            Panel.getTetris().setDirection(Direction.Bas);
        }
        
        if(e.getKeyCode()== KeyEvent.VK_SPACE) {
            Panel.getTetris().rotation();
        }
        
        Panel.getPlayersTab()[0].setDirection(direction);
        
        if(e.getKeyCode() == KeyEvent.VK_ESCAPE){
            Frame.setPause();
        }
    }
}
