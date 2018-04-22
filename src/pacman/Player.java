/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pacman;

import pacman.Ghost.Etat;

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
        if(Panel.getMap().mangerGraine(getX(), getY())){
            for(int i = 0; i < Panel.getGhostsTab().length; i++){
                if(Panel.getGhostsTab()[i].getEtat() == Etat.Attente){
                    Panel.getGhostsTab()[i].setEtat(Etat.AttenteBleu);
                } else {
                    Panel.getGhostsTab()[i].setEtat(Etat.Peur);
                }
            }
        }
    }
}
