/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pacman;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.lang.reflect.Constructor;
import javax.swing.JPanel;

/**
 *
 * @author BuYa
 */
public class Panel extends JPanel implements MouseListener{
    
    protected static Map map;
    protected static Tetris tetris = null;
    protected static Player playersTab[];
    protected static Ghost ghostsTab[];
    protected static boolean run = true;
    
    public static void init(String pMap, String pTileset, int nbPlayers, String[] listFantomes){
        map = new Map(pMap, pTileset, true);
        
        if(pMap.equals("TETRIS") || pMap.equals("TETRIS_2")){
            tetris = new Tetris();
        }
        
        playersTab = new Player[nbPlayers];
        for(int i = 0; i < playersTab.length; i++){
            playersTab[i] = new Pacman(Player.getSpawn().getX(), Player.getSpawn().getY(), 0.8f);
        }
        
        float listArguments[][] = {
            {Ghost.getCage().getX()+0.5f, Ghost.getCage().getY(), 0.75f, map.getMapWidth()-3, 0},
            {Ghost.getCage().getX()+0.5f, Ghost.getCage().getY()+3, 0.75f, 2, 0},
            {Ghost.getCage().getX()-1.5f, Ghost.getCage().getY()+3, 0.75f, map.getMapWidth()-1, map.getMapHeight()-1},
            {Ghost.getCage().getX()+2.5f, Ghost.getCage().getY()+3, 0.75f, 0, map.getMapHeight()-1}
        };
        
        ghostsTab = new Ghost[listFantomes.length];
        
        for (int i = 0; i < ghostsTab.length; i++){
            try
            {
                Class classe = Class.forName ("pacman."+listFantomes[i]);
                Constructor c = classe.getConstructor (new Class [] {Float.TYPE, Float.TYPE, Float.TYPE, Integer.TYPE, Integer.TYPE});
                ghostsTab[i] = (Ghost) c.newInstance (new Object [] {listArguments[i%4][0], listArguments[i%4][1], listArguments[i%4][2], (int)listArguments[i%4][3], (int)listArguments[i%4][4]});
            }
            catch (ClassNotFoundException e)
            {
              // La classe n'existe pas
                System.out.println("La classe n'existe pas");
            }
            catch (NoSuchMethodException e)
            {
              // La classe n'a pas le constructeur recherché
                System.out.println("La classe n'a pas le constructeur recherché");
            }
            catch (InstantiationException e)
            {
              // La classe est abstract ou est une interface
                System.out.println("La classe est abstract ou est une interface");
            }
            catch (IllegalAccessException e)
            {
              // La classe n'est pas accessible
                System.out.println("La classe n'est pas accessible");
            }
            catch (java.lang.reflect.InvocationTargetException e)
            {
              // Exception déclenchée si le constructeur invoqué
              // a lui-même déclenché une exception
                System.out.println("Exception déclenchée si le constructeur invoqué\na lui-même déclenché une exception");
            }
            catch (IllegalArgumentException e)
            {
              // Mauvais type de paramètre
              // (Pas obligatoire d'intercepter IllegalArgumentException)
                System.out.println("Mauvais type de paramètre\n(Pas obligatoire d'intercepter IllegalArgumentException)");
            }
        }
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
        if(tetris != null){
            tetris.go();
        }
    }
    
    public void paintComponent(Graphics g){
        g.setColor(Color.black);
        g.fillRect(0, 0, this.getWidth(), this.getHeight());
        
        map.afficher(g, this.getWidth(), this.getHeight(), false);
        for(int i = 0; i < playersTab.length; i++){
            playersTab[i].afficher(g, this.getWidth(), this.getHeight());
        }
        for(int i = 0; i < ghostsTab.length; i++){
            ghostsTab[i].afficher(g, this.getWidth(), this.getHeight());
        }
        if(tetris != null){
            tetris.afficher(g, this.getWidth(), this.getHeight());
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

    /**
     * @return the tetris
     */
    public static Tetris getTetris() {
        return tetris;
    }
    
    @Override
    public void mouseClicked(MouseEvent me) {
        
    }

    @Override
    public void mousePressed(MouseEvent me) {
        for (int i = 0; i < ghostsTab.length; i++){
            if(ghostsTab[i].getClass().getName().equals("pacman.Bouton")){
                ((Bouton)ghostsTab[i]).verifSouris(me.getX(), me.getY(), this.getWidth(), this.getHeight());
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent me) {
        Sound.stopElevator();
    }

    @Override
    public void mouseEntered(MouseEvent me) {
        
    }

    @Override
    public void mouseExited(MouseEvent me) {
        
    }
}
