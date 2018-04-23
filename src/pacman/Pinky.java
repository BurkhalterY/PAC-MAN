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
public class Pinky extends Ghost{
    
    public Pinky(float x, float y, float vitesse, int xScatter, int yScatter) {
        super(x, y, vitesse, xScatter, yScatter, "pinky", 1, 8);
        basAttente = true;
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
