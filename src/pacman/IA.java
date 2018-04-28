/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pacman;

/**
 *
 * @author Pascal
 */
public class IA extends Pacman{
    private Direction chemin[] = {
        Direction.Gauche,
        Direction.Bas,
        Direction.Gauche,
        Direction.Bas,
        Direction.Droite,
        Direction.Droite,
        Direction.Droite,
        Direction.Haut
    };
    private int step = 0;
    
    public IA(float x, float y, float vitesse) {
        super(x, y, vitesse);
    }
    
    public void setDirection(Direction direction){
        directionSuivante = chemin[0];
    }
}
