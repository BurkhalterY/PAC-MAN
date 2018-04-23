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
public class Pacman extends Player {

    public Pacman(float x, float y, float vitesse){
        super(x, y, vitesse, "pacman", 3, 8);
    }
    
    public void setIdSprite(){
        switch (directionCourente) {
            case Gauche:
                idSprite = 3;
                break;
            case Droite:
                idSprite = 1;
                break;
            case Haut:
                idSprite = 5;
                break;
            case Bas:
                idSprite = 7;
                break;
            default:
                break;
        }
    }
}
