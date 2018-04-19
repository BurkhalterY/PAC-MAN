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
    
    private Direction direction;

    public Direction getDirection() {
        return direction;
    }
    
    public void keyPressed(KeyEvent e) {

        if(e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode()== KeyEvent.VK_A) {
            direction = Direction.Gauche;
        }

        if(e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode()== KeyEvent.VK_D) {
            direction = Direction.Droite;
        } 
        
        if(e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode()== KeyEvent.VK_W) {
            direction = Direction.Haut;
        }

        if(e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode()== KeyEvent.VK_S) {
            direction = Direction.Bas;
        }

    }
}
