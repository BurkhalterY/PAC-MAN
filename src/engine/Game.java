package engine;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;


public class Game {
    
    private static final int fps = 60;
    
    private static Map map;
    private static ArrayList<Player> players = new ArrayList<Player>(0);
    private static ArrayList<Ghost> ghosts = new ArrayList<Ghost>(0);
    private static Entity.Direction playerOneDirection = Entity.Direction.Right;
    private static boolean started = false, debug = true;
    private static Timer timer = new Timer();
    private static long tick;
    
    public static void init(){
        map = new Map("PAC-MAN", "PAC-MAN");
        timer.scheduleAtFixedRate(new TimerTask(){
            @Override
            public void run() {
                play();
            }
        }, 1000/fps, 1000/fps);
        started = true;
    }
    
    public static void play(){
        for(int i = 0; i < players.size(); i++){
            players.get(i).move();
        }
        for(int i = 0; i < ghosts.size(); i++){
            ghosts.get(i).move();
        }
        Frame.getFrame().getContentPane().repaint();
        tick++;
    }
    
    public static Map getMap() {
        return map;
    }

    public static ArrayList<Player> getPlayers() {
        return players;
    }

    public static ArrayList<Ghost> getGhosts() {
        return ghosts;
    }
    
    public static long getTick() {
        return tick;
    }
   
    public static boolean isStarted() {
        return started;
    }
    
    public static void addPlayer(Player player){
        players.add(player);
    }
    
    public static void addGhost(Ghost ghost){
        ghosts.add(ghost);
    }

    public static Entity.Direction getPlayerOneDirection() {
        return playerOneDirection;
    }

    public static void setPlayerOneDirection(Entity.Direction aPlayerOneDirection) {
        playerOneDirection = aPlayerOneDirection;
    }

    public static boolean isDebug() {
        return debug;
    }
    
}
