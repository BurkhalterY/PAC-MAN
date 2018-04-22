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
    private static Map map = new Map("res/map");
    private static Player playersTab[] = {new Pacman(13.5f, 26, 0.125f)};
    private static Ghost ghostsTab[] = {
        new Blinky(13.5f, 14, 0.09375f, map.getMapWidth()-3, 0),
        new Pinky(13.5f, 17, 0.09375f, 2, 0),
        new Inky(11.5f, 17, 0.09375f, map.getMapWidth(), map.getMapHeight()),
        new Clyde(15.5f, 17, 0.09375f, 0, map.getMapHeight())
    };
    private boolean run;
    
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
        for(int i = 0; i < ghostsTab.length; i++){
            ghostsTab[i].avancer();
        }
        for(int i = 0; i < playersTab.length; i++){
            playersTab[i].avancer();
        }

        repaint();
    }
    
    public void paintComponent(Graphics g){
        map.afficher(g, this.getWidth(), this.getHeight());
        for(int i = 0; i < playersTab.length; i++){
            playersTab[i].afficher(g, this.getWidth(), this.getHeight());
        }
        for(int i = 0; i < ghostsTab.length; i++){
            ghostsTab[i].afficher(g, this.getWidth(), this.getHeight());
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
