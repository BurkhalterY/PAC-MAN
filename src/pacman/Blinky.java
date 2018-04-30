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
public class Blinky extends Ghost{

    public Blinky(float x, float y, float vitesse, int xScatter, int yScatter) {
        super(x, y, vitesse, xScatter, yScatter, "blinky", 0);
        etat = Etat.Scatter;
    }
    
    public void setCible(){
        cible = new Tile(Panel.getPlayersTab()[0].getX(), Panel.getPlayersTab()[0].getY(), 0);
    }
}
