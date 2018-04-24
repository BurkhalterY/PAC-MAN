/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pacman;

import javax.swing.JFrame;


/**
 *
 * @author BuYa
 */
public class Frame extends JFrame{
    
    private Panel pan = new Panel();
    private static long ms = 0;
    private static int ticksTotal = 0;

    public Frame(){
        this.setTitle("PAC-MAN");
        this.setSize(500, 700);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(pan);
        this.setVisible(true);
        
        Clavier clav = new Clavier(); 
        addKeyListener(clav);
        
        long startTime = System.currentTimeMillis();
        long initialTime = System.nanoTime();
        int UPS = 60;
        final double timeU = 1000000000 / UPS;
        int FPS = 60;
        final double timeF = 1000000000 / FPS;
        double deltaU = 0, deltaF = 0;
        int frames = 0, ticks = 0;
        long timer = System.currentTimeMillis();

        while (pan.isRun()) {

            long currentTime = System.nanoTime();
            deltaU += (currentTime - initialTime) / timeU;
            deltaF += (currentTime - initialTime) / timeF;
            initialTime = currentTime;

            if (deltaU >= 1) {
                pan.go();
                ticks++;
                ticksTotal++;
                deltaU--;
            }

            if (deltaF >= 1) {
                pan.repaint();
                frames++;
                deltaF--;
            }

            if (System.currentTimeMillis() - timer > 1000) {
                frames = 0;
                ticks = 0;
                timer = System.currentTimeMillis();
            }
            
            ms = System.currentTimeMillis() - startTime;
        }
    }

    /**
     * @return the ms
     */
    public static long getMs() {
        return ms;
    }
    
    /**
     * @return the ticksTotal
     */
    public static int getTicksTotal() {
        return ticksTotal;
    }
}
