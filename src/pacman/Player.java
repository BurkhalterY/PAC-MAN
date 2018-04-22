/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pacman;

/**
 *
 * @author BuYa
 */
public class Player extends Entity{

    public Player(float x, float y, float vitesse) {
        super(x, y, vitesse);
    }
    
    public void setDirection(Direction direction){
        directionSuivante = direction;
    }
    
    public void avancer(){
        super.avancer();
    }
}
