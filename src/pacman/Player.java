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
    private static Tile spawn;
    private int tempsArret = 0;

    public Player(float x, float y, float vitesse, String pictureFile, int rows, int columns) {
        super(x, y, vitesse, pictureFile, rows, columns);
    }
    
    public void setDirection(Direction direction){
        directionSuivante = direction;
    }
    
    public void avancer(){
        float vitesseMode = vitesseDefaut;
        if(Ghost.isPeur()){
            vitesseMode = vitesseDefaut+(1-vitesseDefaut)/2;
        }
        setVitesse(vitesseMode);
        
        tempsArret += Panel.getMap().mangerGraine(getX(), getY());
        if(tempsArret == 0){
            super.avancer();
        } else if(tempsArret == 3){
            Ghost.setPeurTrue();
            tempsArret--;
        } else {
            tempsArret--;
        }
    }
    
    /**
     * @param aSpawn the cage to set
     */
    public static void setSpawn(Tile aSpawn) {
        spawn = aSpawn;
    }
    
    /**
     * @return the cage
     */
    public static Tile getSpawn() {
        return spawn;
    }
}
