/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pacman;

import java.awt.Graphics;
import javax.swing.JPanel;

/**
 *
 * @author BuYa
 */
public class Panel extends JPanel{
    private static Map map = new Map("res/map");
    private static Player playersTab[] = {new Pacman(13.5f, 26, 0.8f)};
    private static Ghost ghostsTab[] = {
        new Blinky(13.5f, 14, 0.75f, map.getMapWidth()-3, 0),
        new Pinky(13.5f, 17, 0.75f, 2, 0),
        new Inky(11.5f, 17, 0.75f, map.getMapWidth()-1, map.getMapHeight()),
        new Clyde(15.5f, 17, 0.75f, 0, map.getMapHeight())
    };
    private boolean run = true;
    
    private long start, pauseStart, pauseDuree;
    
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
    
    public void go(){
        Ghost.calculEtatGlobal();
        for(int i = 0; i < playersTab.length; i++){
            playersTab[i].avancer();
        }
        for(int i = 0; i < ghostsTab.length; i++){
            ghostsTab[i].avancer();
        }
    }
    
    public void paintComponent(Graphics g){
        map.afficher(g, this.getWidth(), this.getHeight());
        for(int i = 0; i < playersTab.length; i++){
            playersTab[i].afficher(g, this.getWidth(), this.getHeight());
        }
        for(int i = 0; i < ghostsTab.length; i++){
            ghostsTab[i].afficher(g, this.getWidth(), this.getHeight());
        }
        for(int i = 0; i < ghostsTab.length; i++){
            ghostsTab[i].afficherTuile(g, this.getWidth(), this.getHeight());
        }
    }

    /**
     * @return the playersTab
     */
    public static Player[] getPlayersTab() {
        return playersTab;
    }

    /**
     * @return the ghostsTab
     */
    public static Ghost[] getGhostsTab() {
        return ghostsTab;
    }
    
    public static Map getMap(){
        return map;
    }
}
