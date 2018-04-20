/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pacman;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;


/**
 *
 * @author BuYa
 */
public class Frame extends JFrame{
    private Panel pan = new Panel();
    private long tempsDebut, tempsFin, dureeBoucle;
    private int fps = 10;

    public Frame(){
        this.setTitle("PAC-MAN");
        this.setSize(500, 700);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(pan);
        this.setVisible(true);
        
        Clavier clav = new Clavier(); 
        addKeyListener(clav);
        
        while(pan.isRun()){
            tempsDebut = System.currentTimeMillis();
            
            pan.go(clav.getDirection());
            
            tempsFin = System.currentTimeMillis();
            dureeBoucle = tempsFin-tempsDebut;

            if(fps-dureeBoucle > 0){
                try {
                    Thread.sleep(fps-dureeBoucle);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Frame.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        
    }
}
