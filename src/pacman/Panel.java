/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pacman;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JPanel;

/**
 *
 * @author BuYa
 */
public class Panel extends JPanel{
    
    private static Map map;
    private static Player playersTab[];
    private static Ghost ghostsTab[];
    private static boolean run = true;
    
    private long start, pauseStart, pauseDuree;
    
    public static void init(String pMap, String pTileset){
        map = new Map(pMap, pTileset);
        playersTab = new Player[]{new Pacman(Player.getSpawn().getX(), Player.getSpawn().getY(), 0.8f)};
        ghostsTab = new Ghost[]{
            new Blinky(Ghost.getCage().getX()+0.5f, Ghost.getCage().getY(), 0.75f, map.getMapWidth()-3, 0),
            new Pinky(Ghost.getCage().getX()+0.5f, Ghost.getCage().getY()+3, 0.75f, 2, 0),
            new Inky(Ghost.getCage().getX()-1.5f, Ghost.getCage().getY()+3, 0.75f, map.getMapWidth()-1, map.getMapHeight()-1),
            new Clyde(Ghost.getCage().getX()+2.5f, Ghost.getCage().getY()+3, 0.75f, 0, map.getMapHeight()-1)
        };
    }
    
    /**
     * @return the run
     */
    public static boolean isRun() {
        return run;
    }

    /**
     * @param aRun the run to set
     */
    public static void setRun(boolean aRun) {
        run = aRun;
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
        g.setColor(Color.black);
        g.fillRect(0, 0, this.getWidth(), this.getHeight());
        
        map.afficher(g, this.getWidth(), this.getHeight());
        for(int i = 0; i < playersTab.length; i++){
            playersTab[i].afficher(g, this.getWidth(), this.getHeight());
        }
        for(int i = 0; i < ghostsTab.length; i++){
            ghostsTab[i].afficher(g, this.getWidth(), this.getHeight());
        }/*
        for(int i = 0; i < ghostsTab.length; i++){
            ghostsTab[i].afficherTuile(g, this.getWidth(), this.getHeight());
        }*/
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
