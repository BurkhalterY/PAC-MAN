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
        super(x, y, vitesse, "pacman");
    }
    
    public void setIdSprite(){
        if(!stop){
            switch (directionCourente) {
                case Droite:
                    idSprite = Texture.getPacman_moving_frames()*0;
                    break;
                case Gauche:
                    idSprite = Texture.getPacman_moving_frames()*1;
                    break;
                case Haut:
                    idSprite = Texture.getPacman_moving_frames()*2;
                    break;
                case Bas:
                    idSprite = Texture.getPacman_moving_frames()*3;
                    break;
                default:
                    break;
            }
            
            for(int i = 0; i < Texture.getPacman_moving_frames()*2; i++){
                if(Frame.getTicksTotal() % (Texture.getPacman_moving_frames()/vitesse) >= i*(Texture.getPacman_moving_frames()/vitesse)/(Texture.getPacman_moving_frames()*2)
                && Frame.getTicksTotal() % (Texture.getPacman_moving_frames()/vitesse) < (i+1)*(Texture.getPacman_moving_frames()/vitesse)/(Texture.getPacman_moving_frames()*2)){
                    if(Texture.isPacman_closed_frame() && i == Texture.getPacman_moving_frames()*2-1){
                        idSprite = Texture.getPacman_moving_frames()*4;
                    } else {
                        idSprite += (i%Texture.getPacman_moving_frames());
                    }
                }
            }
            
        } else {
            if(Texture.isPacman_closed_frame() && idSprite == Texture.getPacman_moving_frames()*4){
                switch (directionCourente) {
                    case Droite:
                        idSprite = Texture.getPacman_moving_frames()*0;
                        break;
                    case Gauche:
                        idSprite = Texture.getPacman_moving_frames()*1;
                        break;
                    case Haut:
                        idSprite = Texture.getPacman_moving_frames()*2;
                        break;
                    case Bas:
                        idSprite = Texture.getPacman_moving_frames()*3;
                        break;
                    default:
                        break;
                }
            }
        }
        
    }
}
