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
public class Pacman extends Player {

    public Pacman(float x, float y, float vitesse){
        super(x, y, vitesse, "pacman", 3, 8);
    }
    
    public void setIdSprite(){
        if(!stop){
            switch (directionCourente) {
                case Droite:
                    idSprite = 0;
                    break;
                case Gauche:
                    idSprite = 2;
                    break;
                case Haut:
                    idSprite = 4;
                    break;
                case Bas:
                    idSprite = 6;
                    break;
                default:
                    break;
            }

            if((Frame.getTicksTotal() % (2/vitesse) >= 0.5f/vitesse && Frame.getTicksTotal() % (2/vitesse) < 1/vitesse) || Frame.getTicksTotal() % (2/vitesse) >= 1.5f/vitesse){
                idSprite++;
            } else if(Frame.getTicksTotal() % (2/vitesse) >= 1/vitesse && Frame.getTicksTotal() % (2/vitesse) < 1.5f/vitesse){
                idSprite = 8;
            }
        } else {
            if(idSprite == 8){
                switch (directionCourente) {
                    case Droite:
                        idSprite = 0;
                        break;
                    case Gauche:
                        idSprite = 2;
                        break;
                    case Haut:
                        idSprite = 4;
                        break;
                    case Bas:
                        idSprite = 6;
                        break;
                    default:
                        break;
                }
            }
        }
        
    }
}
