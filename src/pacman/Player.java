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

    public Player(float x, float y, float vitesse, String pictureFile, int rows, int columns) {
        super(x, y, vitesse, pictureFile, rows, columns);
    }
    
    public void setDirection(Direction direction){
        directionSuivante = direction;
    }
    
    public void avancer(){
        super.avancer();
        if(Panel.getMap().mangerGraine(getX(), getY())){
            Ghost.setPeurTrue();
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
