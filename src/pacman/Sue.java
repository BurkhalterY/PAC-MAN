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
public class Sue extends Ghost{
    
    public Sue(float x, float y, float vitesse, int xScatter, int yScatter) {
        super(x, y, vitesse, xScatter, yScatter, "sue", 0);
    }
    
    public void setCible(){
        Direction directionsPossiblesNull[] = setDirectionsPossibles();
        
        int i = -1;
        do{
            i++;
        } while(directionsPossiblesNull[i] != null);
        
        Direction directionsPossiblesW[] = new Direction[i];
        
        for(int j = 0; j < directionsPossiblesW.length; j++){
            directionsPossiblesW[j] = directionsPossiblesNull[j];
        }
        
        int rnd = (int)(Math.random()*i);
        
        Direction directionsRandom = directionsPossiblesW[rnd];
        
        int xSue = Math.round(x);
        int ySue = Math.round(y);
        
        switch (directionsRandom) {
            case Gauche:
                xSue -= 1;
                break;
            case Droite:
                xSue += 1;
                break;
            case Haut:
                ySue -= 1;
                break;
            case Bas:
                ySue += 1;
                break;
            default:
                break;
        }
        
        cible = new Tile(xSue, ySue, 0);
    }
    
    public void setCibleScatter(){
        setCible();
    }
    
    public boolean peutSortir(){
        boolean sortir = false;
        if(Panel.getMap().getNbBulletMangees() > Panel.getMap().getNbBulletTotal()/3){
            sortir = true;
        }
        return sortir;
    }
}
