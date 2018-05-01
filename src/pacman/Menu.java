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
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author BuYa
 */
public class Menu extends JPanel implements ActionListener, Runnable{
    
    private int idMenu = 0;
    private BufferedImage logo;
    
    //0
    private JButton btnPlay;
    private JLabel labelTexturePack = new JLabel("Textures Pack :");
    private JComboBox choixTexturePack;
    private JLabel labelMap = new JLabel("Map :");
    private JComboBox choixMap;
    private JLabel labelTileset = new JLabel("Tileset :");
    private JComboBox choixTileset;
    
    private JLabel labelFantome1 = new JLabel("Fantôme 1:");
    private JLabel labelFantome2 = new JLabel("Fantôme 2:");
    private JLabel labelFantome3 = new JLabel("Fantôme 3:");
    private JLabel labelFantome4 = new JLabel("Fantôme 4:");
    private JComboBox[] choixFantome = new JComboBox[4];
    
    //1
    private JButton btnReprendre;

    public void selectionMenu(){ //0

        File repTexturePack = new File("res/textures_pack");
        File[] filesTexturePack = repTexturePack.listFiles();
        String[] listTexturePack = new String[filesTexturePack.length];
        for (int i = 0; i < filesTexturePack.length; i++){
            listTexturePack[i] = filesTexturePack[i].getName();
        }
        choixTexturePack = new JComboBox(listTexturePack);
        

        File repMap = new File("res/maps");
        File[] filesMap = repMap.listFiles();
        String[] listMap = new String[filesMap.length];
        for (int i = 0; i < filesMap.length; i++){
            listMap[i] = filesMap[i].getName();
        }
        choixMap = new JComboBox(listMap);
        
        
        File repTileset = new File("res/tileset");
        File[] filesTileset = repTileset.listFiles();
        String[] listTileset = new String[filesTileset.length];
        for (int i = 0; i < filesTileset.length; i++){
            listTileset[i] = filesTileset[i].getName();
        }
        choixTileset = new JComboBox(listTileset);
        
        
        for(int i = 0; i < choixFantome.length; i++){
            choixFantome[i] = new JComboBox(new String[]{"Blinky", "Pinky", "Inky", "Clyde", "Sue", "Bouton"});
            choixFantome[i].setSelectedIndex(i);
        }
        
        try {
            logo = ImageIO.read(new File("res/logo.png"));
        } catch (IOException ex) {
            Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        btnPlay = new JButton("1 Player");
        btnPlay.addActionListener(this);
        
        this.add(btnPlay);
        this.add(labelTexturePack);
        this.add(choixTexturePack);
        this.add(labelMap);
        this.add(choixMap);
        this.add(labelTileset);
        this.add(choixTileset);
        this.add(labelFantome1);
        this.add(labelFantome2);
        this.add(labelFantome3);
        this.add(labelFantome4);
        for(int i = 0; i < choixFantome.length; i++){
            this.add(choixFantome[i]);
        }
        this.setLayout(null);
    }
    
    public void pauseMenu(){ //10

        try {
            logo = ImageIO.read(new File("res/logo.png"));
        } catch (IOException ex) {
            Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
        }

        btnReprendre = new JButton("Reprendre");

        btnReprendre.addActionListener(this);

        this.add(btnReprendre);
        this.setLayout(null);
    }

    public void paintComponent(Graphics g){
        if(idMenu == 0){
            drawSelectionMenu();
        } else if(idMenu == 10){
            drawPauseMenu();
        }

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
    }
    
    public void drawSelectionMenu(){
        btnPlay.setBounds(this.getWidth()/3, (this.getHeight()-25)/2, this.getWidth()/3, 25);
            
        labelMap.setForeground(Color.white);
        labelMap.setBounds(0, (int) ((this.getHeight()-25)/1.5f)-50, this.getWidth()/3, 25);
        choixMap.setBounds(0, (int) ((this.getHeight()-25)/1.5f)-25, this.getWidth()/3, 25);

        labelTexturePack.setForeground(Color.white);
        labelTexturePack.setBounds(this.getWidth()/3, (int) ((this.getHeight()-25)/1.5f)-25, this.getWidth()/3, 25);
        choixTexturePack.setBounds(this.getWidth()/3, (int) ((this.getHeight()-25)/1.5f), this.getWidth()/3, 25);

        labelTileset.setForeground(Color.white);
        labelTileset.setBounds(this.getWidth()-this.getWidth()/3, (int) ((this.getHeight()-25)/1.5f), this.getWidth()/3, 25);
        choixTileset.setBounds(this.getWidth()-this.getWidth()/3, (int) ((this.getHeight()-25)/1.5f)+25, this.getWidth()/3, 25);
        
        labelFantome1.setForeground(Color.red);
        labelFantome1.setBounds(0, this.getHeight()-50, this.getWidth()/4, 25);
        
        labelFantome2.setForeground(Color.pink);
        labelFantome2.setBounds(this.getWidth()/4, this.getHeight()-50, this.getWidth()/4, 25);
        
        labelFantome3.setForeground(Color.cyan);
        labelFantome3.setBounds(this.getWidth()/4*2, this.getHeight()-50, this.getWidth()/4, 25);
        
        labelFantome4.setForeground(Color.orange);
        labelFantome4.setBounds(this.getWidth()/4*3, this.getHeight()-50, this.getWidth()/4, 25);
        
        for(int i = 0; i < choixFantome.length; i++){
            choixFantome[i].setBounds(this.getWidth()/4*i, this.getHeight()-25, this.getWidth()/4, 25);
        }
    }
    
    public void drawPauseMenu(){
        btnReprendre.setBounds(this.getWidth()/3, (this.getHeight()-25)/2, this.getWidth()/3, 25);
    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
        if(arg0.getSource() == btnPlay){
            Frame.start();
            this.removeAll();
            pauseMenu();
            idMenu = 10;
        } else if(arg0.getSource() == btnReprendre){
            Frame.setPause();
        }
    }

    @Override
    public void run() {
        Frame.go();
    }
    
    public String getTexturePack(){
        return choixTexturePack.getSelectedItem().toString();
    }
    
    public String getMap(){
        return choixMap.getSelectedItem().toString();
    }
    
    public String getTileset(){
        return choixTileset.getSelectedItem().toString();
    }
    
    public String[] getListFantomes(){
        String[] listFantomes = new String[choixFantome.length];
        for (int i = 0; i < listFantomes.length; i++){
            listFantomes[i] = choixFantome[i].getSelectedItem().toString();
        }
        return listFantomes;
    }
}
