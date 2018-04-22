/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pacman;

import java.awt.Graphics;
import javax.swing.JPanel;
import pacman.Entity.Direction;
import pacman.Ghost.Etat;

/**
 *
 * @author BuYa
 */
public class Panel extends JPanel{
    private Pacman pacman = new Pacman(13.5f, 26, 0.125f);
    private Blinky blinky = new Blinky(13.5f, 14, 0.125f, Singleton.getInstance().getMap().getMapWidth()-3, 0);
    private Pinky pinky = new Pinky(13.5f, 17, 0.125f, 2, 0);
    private Inky inky = new Inky(11.5f, 17, 0.125f, Singleton.getInstance().getMap().getMapWidth(), Singleton.getInstance().getMap().getMapHeight());
    private Clyde clyde = new Clyde(15.5f, 17, 0.125f, 0, Singleton.getInstance().getMap().getMapHeight());
    private boolean run, scatter, peur;
    private int phase;
    private int phases[] = {7, 20, 7, 20, 5, 20, 5};
    
    private long start, pauseStart, pauseDuree;
    
    public Panel(){
        run = true;
        scatter = true;
        start = System.currentTimeMillis();
        phase = 0;
    }

    /**
     * @return the run
     */
    public boolean isRun() {
        return run;
    }

    /**
     * @param run the run to set
     */
    public void setRun(boolean run) {
        this.run = run;
    }
    
    public void go(Direction direction){
        
        if(peur){
            if(System.currentTimeMillis() - pauseStart >= 6000){
                pauseDuree = System.currentTimeMillis() - pauseStart;
                
                peur = false;
                blinky.setEtat(Etat.Normal);
                pinky.setEtat(Etat.Normal);
                inky.setEtat(Etat.Normal);
                clyde.setEtat(Etat.Normal);
            }
        } else {
            if(phase >= phases.length){
                scatter = false;
            } else {
                if(System.currentTimeMillis() - start - pauseDuree >= phases[phase]*1000){
                    scatter = !scatter;
                    start = System.currentTimeMillis();
                    phase++;
                }
            }
        }

        pacman.setDirection(direction);
        
        if(scatter || peur){
            blinky.setScatterCible();
            pinky.setScatterCible();
            inky.setScatterCible();
            clyde.setScatterCible();
        } else {
            blinky.setCible(pacman.getX(), pacman.getY());
            pinky.setCible(pacman.getX(), pacman.getY(), pacman.getDirectionCourente());
            inky.setCible(pacman.getX(), pacman.getY(), blinky.getX(), blinky.getY(), pacman.getDirectionCourente());
            clyde.setCible(pacman.getX(), pacman.getY());
        }
        
        pacman.avancer();
        blinky.avancer();
        pinky.avancer();
        inky.avancer();
        clyde.avancer();
        
        if(Singleton.getInstance().getMap().mangerGraine(pacman.getX(), pacman.getY())){
            peur = true;
            pauseStart = System.currentTimeMillis();
            
            blinky.setEtat(Etat.Peur);
            pinky.setEtat(Etat.Peur);
            inky.setEtat(Etat.Peur);
            clyde.setEtat(Etat.Peur);
        }
        
        if(peur){
            if(blinky.touherPacman(pacman.getX(), pacman.getY())){
                blinky.setEtat(Etat.Retour);
            }
            if(pinky.touherPacman(pacman.getX(), pacman.getY())){
                pinky.setEtat(Etat.Retour);
            }
            if(inky.touherPacman(pacman.getX(), pacman.getY())){
                inky.setEtat(Etat.Retour);
            }
            if(clyde.touherPacman(pacman.getX(), pacman.getY())){
                clyde.setEtat(Etat.Retour);
            }
        } else {
            if(blinky.touherPacman(pacman.getX(), pacman.getY())
            || pinky.touherPacman(pacman.getX(), pacman.getY())
            || inky.touherPacman(pacman.getX(), pacman.getY())
            || clyde.touherPacman(pacman.getX(), pacman.getY())){
                run = false;
            }
        }
        
        repaint();
    }
    
    public void paintComponent(Graphics g){
        Singleton.getInstance().getMap().afficher(g, this.getWidth(), this.getHeight());
        pacman.afficher(g, this.getWidth(), this.getHeight());
        blinky.afficher(g, this.getWidth(), this.getHeight());
        pinky.afficher(g, this.getWidth(), this.getHeight());
        inky.afficher(g, this.getWidth(), this.getHeight());
        clyde.afficher(g, this.getWidth(), this.getHeight());
    }
}
