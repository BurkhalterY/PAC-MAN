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
public class Clyde extends Ghost{
    
    public Clyde(float x, float y, float vitesse, int xScatter, int yScatter) {
        super(x, y, vitesse, xScatter, yScatter, "clyde", 1, 8);
    }
    
    public void setCible(){
        int xPacman = Panel.getPlayersTab()[0].getX();
        int yPacman = Panel.getPlayersTab()[0].getY();
        
        if(Math.sqrt(Math.pow(x - xPacman, 2) + Math.pow(y - yPacman, 2)) > 8){
            cible = new Tile(xPacman, yPacman, 0);
        } else {
            cible = new Tile(xScatter, yScatter, 0);
        }
    }
    
    public boolean peutSortir(){
        boolean sortir = false;
        if(Panel.getMap().getNbBulletMangees() > Panel.getMap().getNbBulletTotal()/3){
            sortir = true;
        }
        return sortir;
    }
}
