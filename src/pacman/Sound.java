/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pacman;

import java.applet.Applet;
import java.applet.AudioClip;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Pascal
 */
public class Sound {
    private static AudioClip beginning;
    private static AudioClip chomp;
    private static boolean chompWait;
    private static AudioClip death;
    private static AudioClip eatfruit;
    private static AudioClip eatghost;
    private static AudioClip extrapac;
    private static AudioClip largePelletLoop;
    private static AudioClip intermission;
    private static AudioClip siren;
    
    private static AudioClip elevator;
    
    public static void initSound(String path){
        URL url = null;
        try {
            url = new File("res/sounds/"+path+"/pacman_beginning.wav").toURI().toURL();
        } catch (MalformedURLException ex) {
            Logger.getLogger(Sound.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        beginning = Applet.newAudioClip(url);
        
        try {
            url = new File("res/sounds/"+path+"/pacman_waka_waka.wav").toURI().toURL();
        } catch (MalformedURLException ex) {
            Logger.getLogger(Sound.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        chomp = Applet.newAudioClip(url);
        
        try {
            url = new File("res/sounds/"+path+"/pacman_death.wav").toURI().toURL();
        } catch (MalformedURLException ex) {
            Logger.getLogger(Sound.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        death = Applet.newAudioClip(url);
        
        try {
            url = new File("res/sounds/"+path+"/pacman_eatfruit.wav").toURI().toURL();
        } catch (MalformedURLException ex) {
            Logger.getLogger(Sound.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        eatfruit = Applet.newAudioClip(url);
        
        try {
            url = new File("res/sounds/"+path+"/pacman_eatghost.wav").toURI().toURL();
        } catch (MalformedURLException ex) {
            Logger.getLogger(Sound.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        eatghost = Applet.newAudioClip(url);
        
        try {
            url = new File("res/sounds/"+path+"/pacman_extrapac.wav").toURI().toURL();
        } catch (MalformedURLException ex) {
            Logger.getLogger(Sound.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        extrapac = Applet.newAudioClip(url);
        
        try {
            url = new File("res/sounds/"+path+"/pacman_largePelletLoop.wav").toURI().toURL();
        } catch (MalformedURLException ex) {
            Logger.getLogger(Sound.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        largePelletLoop = Applet.newAudioClip(url);
        
        try {
            url = new File("res/sounds/"+path+"/pacman_intermission.wav").toURI().toURL();
        } catch (MalformedURLException ex) {
            Logger.getLogger(Sound.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        intermission = Applet.newAudioClip(url);
        
        try {
            url = new File("res/sounds/"+path+"/pacman_siren.wav").toURI().toURL();
        } catch (MalformedURLException ex) {
            Logger.getLogger(Sound.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        siren = Applet.newAudioClip(url);
        
        try {
            url = new File("res/sounds/"+path+"/elevator.wav").toURI().toURL();
        } catch (MalformedURLException ex) {
            Logger.getLogger(Sound.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        elevator = Applet.newAudioClip(url);
    }
    
    public static void playBeginning(){
        beginning.play();
    }
    
    public static void loopChomp(){
        chomp.loop();
    }
    
    public static void stopChomp(){
        chomp.stop();
    }
    
    public static void waitChomp(){
        try {
            chomp.wait();
            chompWait = true;
        } catch (InterruptedException ex) {
            Logger.getLogger(Sound.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void notifyChomp(){
        chomp.notify();
        chompWait = false;
    }
    
    /**
     * @return the chompWait
     */
    public static boolean isChompWait() {
        return chompWait;
    }
        
    public static void playDeath(){
        death.play();
    }
    
    public static void playEatfruit(){
        eatfruit.play();
    }
    
    public static void playEatghost(){
        eatghost.play();
    }
    
    public static void playExtrapac(){
        extrapac.play();
    }
    
    public static void playIntermission(){
        intermission.play();
    }
    
    public static void loopSiren(){
        siren.loop();
    }
    
    public static void stopSiren(){
        siren.stop();
    }

    
    public static void loopElevator(){
        elevator.loop();
    }
    
    public static void stopElevator(){
        elevator.stop();
    }
}
