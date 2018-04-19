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
public class Singleton {
    private volatile static Singleton single;
    private Map map;

    private Singleton(){
        map = new Map("res/map.txt");
    }

    public static Singleton getInstance(){
        if(single == null){
            synchronized(Singleton.class){
                if(single == null)
                    single = new Singleton();
            }
        }      
        return single;
    }

    public Map getMap(){
        return map;
    }
}
