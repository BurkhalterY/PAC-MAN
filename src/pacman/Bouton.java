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
public class Bouton extends Ghost{
    
    public Bouton(float x, float y, float vitesse, int xScatter, int yScatter) {
        super(x, y, vitesse, xScatter, yScatter, "bouton", 0);
        Frame.setPanMouseListener();
    }
    
    public void setCible(){
        cible = new Tile(Panel.getPlayersTab()[0].getX(), Panel.getPlayersTab()[0].getY(), 0);
    }
    
    public void verifSouris(int xMouse, int yMouse, int width, int height){
        float size;
        int mapWidth = Panel.getMap().getMapWidth();
        int mapHeight = Panel.getMap().getMapHeight();
        
        if(width/mapWidth > height/mapHeight){
            size = (float)height/mapHeight;
        } else {
            size = (float)width/mapWidth;
        }
        
        if(xMouse >= (x - 0.5f)*size && xMouse <= (x + 1.5f)*size && yMouse >= (y - 0.5f)*size && yMouse <= (y + 1.5f)*size){
            Sound.loopElevator();
        }
    }
}
