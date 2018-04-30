/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pacman;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 *
 * @author BuYa
 */
public class Menu extends JPanel implements ActionListener, Runnable{
    
    private int idMenu = 0;
    private BufferedImage logo;
    private JButton btnPlay;

    public void selectionMenu(){ //0

        File rep = new File("res/textures_pack");
        File[] files = rep.listFiles();
        
        try {
            logo = ImageIO.read(new File("res/logo.png"));
        } catch (IOException ex) {
            Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
        }

        btnPlay = new JButton("1 Player");

        btnPlay.addActionListener(this);

        this.add(btnPlay);
        this.setLayout(null);  
    }
    
    public void pauseMenu(){ //10

        try {
            logo = ImageIO.read(new File("res/logo.png"));
        } catch (IOException ex) {
            Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
        }

        btnPlay = new JButton("Reprendre");

        btnPlay.addActionListener(this);

        this.add(btnPlay);
        this.setLayout(null);  
    }

    public void paintComponent(Graphics g){
        if(idMenu == 0){
            btnPlay.setBounds(this.getWidth()/3, (this.getHeight()-25)/2, this.getWidth()/3, 25);

            float size;
            if(this.getWidth()/logo.getWidth() > this.getHeight()/logo.getHeight()){
                size = (float)this.getHeight()/logo.getHeight();
            } else {
                size = (float)this.getWidth()/logo.getWidth();
            }

            Graphics2D g2d = (Graphics2D)g;

            g.setColor(Color.black);
            g.fillRect(0, 0, this.getWidth(), this.getHeight());

            AffineTransform transformation = new AffineTransform();
            transformation.scale(size, size);

            g2d.drawImage(logo, transformation, null);
        } else if(idMenu == 10){
            
        }
        
    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
        if(arg0.getSource() == btnPlay){
            Frame.setPause();
        }
    }

    @Override
    public void run() {
        Frame.go();
    }
}
