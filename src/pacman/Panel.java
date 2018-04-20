/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pacman;

import java.awt.Graphics;
import javax.swing.JPanel;
import pacman.Entity.Direction;

/**
 *
 * @author BuYa
 */
public class Panel extends JPanel{
    private Pacman pacman = new Pacman(13.5f, 26, 0.125f);
    private Blinky blinky = new Blinky(13.5f, 14, 0.125f);
    private Pinky pinky = new Pinky(13.5f, 17, 0.125f);
    private Inky inky = new Inky(11.5f, 17, 0.125f);
    private Clyde clyde = new Clyde(15.5f, 17, 0.125f);
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
                blinky.setPeur(false);
                pinky.setPeur(false);
                inky.setPeur(false);
                clyde.setPeur(false);
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
            blinky.setScatterCible(26, 0);
            pinky.setScatterCible(3, 0);
            inky.setScatterCible(28, 36);
            clyde.setScatterCible(0, 36);
        } else {
            blinky.setCible((int) pacman.getX(), (int) pacman.getY());
            pinky.setCible((int) pacman.getX(), (int) pacman.getY(), pacman.getDirectionCourente());
            inky.setCible((int) pacman.getX(), (int) pacman.getY(), (int) blinky.getX(), (int) blinky.getY(), pacman.getDirectionCourente());
            clyde.setCible((int) pacman.getX(), (int) pacman.getY());
        }
        
        pacman.avancer();
        blinky.avancer();
        pinky.avancer();
        inky.avancer();
        clyde.avancer();
        
        if(Singleton.getInstance().getMap().mangerGraine(pacman.getX(), pacman.getY())){
            peur = true;
            pauseStart = System.currentTimeMillis();
            
            blinky.setPeur(true);
            pinky.setPeur(true);
            inky.setPeur(true);
            clyde.setPeur(true);
        }
        
        if(peur){
            if(blinky.touherPacman(pacman.getX(), pacman.getY())){
                blinky.setRetour(true);
            }
            if(pinky.touherPacman(pacman.getX(), pacman.getY())){
                pinky.setRetour(true);
            }
            if(inky.touherPacman(pacman.getX(), pacman.getY())){
                inky.setRetour(true);
            }
            if(clyde.touherPacman(pacman.getX(), pacman.getY())){
                clyde.setRetour(true);
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
        pacman.afficher(g);
        blinky.afficher(g);
        pinky.afficher(g);
        inky.afficher(g);
        clyde.afficher(g);
    }
}
