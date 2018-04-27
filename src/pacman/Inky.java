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
public class Inky extends Ghost{
    
    public Inky(float x, float y, float vitesse, int xScatter, int yScatter) {
        super(x, y, vitesse, xScatter, yScatter, "inky", 1, 8, 2);
    }
    
    public void setCible(){
        int xPacman = Panel.getPlayersTab()[0].getX();
        int yPacman = Panel.getPlayersTab()[0].getY();
        Direction direction = Panel.getPlayersTab()[0].getDirectionCourente();
        
        int xBlinky = Panel.getGhostsTab()[0].getX();
        int yBlinky = Panel.getGhostsTab()[0].getY();
        
        switch (direction) {
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
        
        cible = new Tile(xPacman - (xBlinky - xPacman), yPacman - (yBlinky - yPacman), 0);
    }
    
    public boolean peutSortir(){
        boolean sortir = false;
        if(Panel.getMap().getNbBulletMangees() >= 30){
            sortir = true;
        }
        return sortir;
    }
}
