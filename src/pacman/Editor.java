/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pacman;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFormattedTextField;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

/**
 *
 * @author BuYa
 */
public class Editor extends JPanel implements ActionListener, MouseListener, MouseMotionListener{
    
    private static Map map;
    private JButton btnOpenFile = new JButton("Ouvrir un fichier");
    private JButton btnSaveFile = new JButton("Sauvegarder");
    private JCheckBox quad = new JCheckBox("Quadrillage", true);
    private JFormattedTextField textLargeur = new JFormattedTextField(NumberFormat.getIntegerInstance());
    private JFormattedTextField textHauteur = new JFormattedTextField(NumberFormat.getIntegerInstance());
    private JButton btnApplicate = new JButton("Appliquer");
    private JFileChooser fc = new JFileChooser("res/maps");
    //Tiles
    private JButton btnMur = new JButton("Mur", new ImageIcon("res/editor/icons/iconMur.png"));
    private JButton btnExt = new JButton("Extérieure");
    private JButton btnGhostsHouse = new JButton("Maison fantômes", new ImageIcon("res/editor/icons/iconGhostHouse.png"));
    //Bullets
    private JButton btnBullets = new JButton("Pac-Gomme", new ImageIcon("res/editor/icons/iconBullet.png"));
    private JButton btnSuperBullets = new JButton("Super Pac-Gomme", new ImageIcon("res/editor/icons/iconSuperBullet.png"));
    //Events
    private JButton btnSpawn = new JButton("Spawn");
    private JButton btnGhostRalenti = new JButton("Ralentissement des fantômes");
    private JButton btnGhostUpBlock = new JButton("Bloquer les fantômes vers le haut");
    
    private int tileType = 0, tileX = 0, tileY = 0, margeRight = 300, spType = 0;
    private float size = 0;
    private boolean spModif = false;
    
    public Editor(){
        map = new Map();
        fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
        
        btnGhostsHouse.setVerticalTextPosition(SwingConstants.BOTTOM); 
        btnGhostsHouse.setHorizontalTextPosition(SwingConstants.CENTER); 
        
        textLargeur.setText(Integer.toString(map.getMapWidth()));
        textHauteur.setText(Integer.toString(map.getMapHeight()));
        
        btnOpenFile.addActionListener(this);
        btnSaveFile.addActionListener(this);
        quad.addActionListener(this);
        btnApplicate.addActionListener(this);
        btnMur.addActionListener(this);
        btnExt.addActionListener(this);
        btnGhostsHouse.addActionListener(this);
        btnBullets.addActionListener(this);
        btnSuperBullets.addActionListener(this);
        btnSpawn.addActionListener(this);
        btnGhostRalenti.addActionListener(this);
        btnGhostUpBlock.addActionListener(this);
        this.add(btnOpenFile);
        this.add(btnSaveFile);
        this.add(quad);
        this.add(btnApplicate);
        this.add(btnMur);
        this.add(btnExt);
        this.add(btnGhostsHouse);
        this.add(btnBullets);
        this.add(btnSuperBullets);
        this.add(btnSpawn);
        this.add(btnGhostRalenti);
        this.add(btnGhostUpBlock);
        this.add(textLargeur);
        this.add(textHauteur);
    }
    
    public void paintComponent(Graphics g){
        g.setColor(Color.black);
        g.fillRect(0, 0, this.getWidth(), this.getHeight());
        
        int y = 20;
        btnOpenFile.setBounds(this.getWidth()-margeRight+(margeRight/4), y, margeRight/2, 25);
        y += 30;
        btnSaveFile.setBounds(this.getWidth()-margeRight+(margeRight/4), y, margeRight/2, 25);
        y += 30;
        quad.setBounds(this.getWidth()-margeRight+(margeRight/4), y, margeRight/2, 25);
        y += 30;
        btnMur.setBounds(this.getWidth()-margeRight+(margeRight/4), y, margeRight/2, 25);
        y += 30;
        btnExt.setBounds(this.getWidth()-margeRight+(margeRight/4), y, margeRight/2, 25);
        y += 30;
        btnGhostsHouse.setBounds(this.getWidth()-margeRight+(margeRight/4), y, margeRight/2, 75);
        y += 80;
        btnBullets.setBounds(this.getWidth()-margeRight+(margeRight/4), y, margeRight/2, 25);
        y += 30;
        btnSuperBullets.setBounds(this.getWidth()-margeRight+(margeRight/4), y, margeRight/2, 25);
        y += 30;
        btnSpawn.setBounds(this.getWidth()-margeRight+(margeRight/4), y, margeRight/2, 25);
        y += 30;
        btnGhostRalenti.setBounds(this.getWidth()-margeRight+(margeRight/4), y, margeRight/2, 25);
        y += 30;
        btnGhostUpBlock.setBounds(this.getWidth()-margeRight+(margeRight/4), y, margeRight/2, 25);
        y += 30;
        textLargeur.setBounds(this.getWidth()-margeRight+(margeRight/4), y, margeRight/4, 25);
        textHauteur.setBounds(this.getWidth()-margeRight+(margeRight/2), y, margeRight/4, 25);
        y += 30;
        btnApplicate.setBounds(this.getWidth()-margeRight+(margeRight/4), y, margeRight/2, 25);
        
        map.afficher(g, this.getWidth()-margeRight, this.getHeight(), quad.isSelected());
        
        g.setColor(new Color(255, 0, 0, 128));
        g.fillRect((int)(tileX*size), (int)(tileY*size), (int)(size), (int)(size));
        
        if(tileType == 3){
            if(tileX+8 > map.getMapWidth() || tileY+5 > map.getMapHeight()){
                g.setColor(new Color(255, 0, 0, 128));
                g.fillRect((int)(tileX*size), (int)(tileY*size), (int)(size*8), (int)(size*5));
            } else {
                g.setColor(new Color(0, 0, 255, 128));
                g.fillRect((int)(tileX*size), (int)(tileY*size), (int)(size*8), (int)(size*5));
            }
        }
        
    }
    
    
    @Override
    public void mouseClicked(MouseEvent me) {
        int mapWidth = map.getMapWidth();
        int mapHeight = map.getMapHeight();

        if((this.getWidth()-margeRight)/mapWidth > this.getHeight()/mapHeight){
            size = (float)this.getHeight()/mapHeight;
        } else {
            size = (float)(this.getWidth()-margeRight)/mapWidth;
        }

        tileX = (int)(me.getX()/size);
        tileY = (int)(me.getY()/size);
        
        if(SwingUtilities.isLeftMouseButton(me)){
            if(spModif){
                map.setSp(tileX, tileY, spType);
            } else {
                if(tileType == 3){
                    if(tileX+8 <= map.getMapWidth() && tileY+5 <= map.getMapHeight()){
                        map.setTile(tileX, tileY, tileType);
                    }
                    tileType = 0;
                } else {
                    map.setTile(tileX, tileY, tileType);
                }      
            }
        } else if(SwingUtilities.isRightMouseButton(me)) {
            if(spModif){
                map.setSp(tileX, tileY, 0);
            } else {
                map.setTile(tileX, tileY, 1);
                if(tileType == 3){
                    tileType = 0;
                }
            }
        } else if(SwingUtilities.isMiddleMouseButton(me)) {
            tileType = map.getMap()[tileX][tileY].getType();
        }
        this.repaint();
    }

    @Override
    public void mousePressed(MouseEvent me) {
        
    }

    @Override
    public void mouseReleased(MouseEvent me) {
        
    }

    @Override
    public void mouseEntered(MouseEvent me) {
        
    }

    @Override
    public void mouseExited(MouseEvent me) {
        
    }

    @Override
    public void mouseDragged(MouseEvent me) {
        
        int mapWidth = map.getMapWidth();
        int mapHeight = map.getMapHeight();

        if((this.getWidth()-margeRight)/mapWidth > this.getHeight()/mapHeight){
            size = (float)this.getHeight()/mapHeight;
        } else {
            size = (float)(this.getWidth()-margeRight)/mapWidth;
        }

        tileX = (int)(me.getX()/size);
        tileY = (int)(me.getY()/size);
        
        if(SwingUtilities.isLeftMouseButton(me)){
            if(spModif){
                map.setSp(tileX, tileY, spType);
            } else {
                if(tileType == 3){
                    if(tileX+8 <= map.getMapWidth() && tileY+5 <= map.getMapHeight()){
                        map.setTile(tileX, tileY, tileType);
                    }
                    tileType = 0;
                } else {
                    map.setTile(tileX, tileY, tileType);
                }
            }
        } else if(SwingUtilities.isRightMouseButton(me)) {
            if(spModif){
                map.setSp(tileX, tileY, 0);
            } else {
                map.setTile(tileX, tileY, 1);
                if(tileType == 3){
                    tileType = 0;
                }
            }
        }
        this.repaint();
    }

    @Override
    public void mouseMoved(MouseEvent me) {
        
        int mapWidth = map.getMapWidth();
        int mapHeight = map.getMapHeight();

        if((this.getWidth()-margeRight)/mapWidth > this.getHeight()/mapHeight){
            size = (float)this.getHeight()/mapHeight;
        } else {
            size = (float)(this.getWidth()-margeRight)/mapWidth;
        }

        tileX = (int)(me.getX()/size);
        tileY = (int)(me.getY()/size);
        this.repaint();
    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
        if(arg0.getSource() == btnOpenFile){
            int ret = fc.showOpenDialog(this);
            if (ret == JFileChooser.APPROVE_OPTION) {
                File file = fc.getSelectedFile();
                map = new Map(file.getName(), false);
                this.repaint();
            }
        } else if(arg0.getSource() == btnSaveFile){
            if(map.mapValide()){
                int ret = fc.showSaveDialog(this);
                if (ret == JFileChooser.APPROVE_OPTION) {
                    FileWriter fstream = null, fstream2 = null;
                    try {
                        File file = new File(fc.getSelectedFile().getPath()+"/map.txt");
                        File file2 = new File(fc.getSelectedFile().getPath()+"/map_sp.txt");
                        fstream = new FileWriter(file);
                        fstream2 = new FileWriter(file2);
                        BufferedWriter out = new BufferedWriter(fstream);
                        BufferedWriter out2 = new BufferedWriter(fstream2);
                        for(int y = 0; y < map.getMapHeight(); y++){
                            for(int x = 0; x < map.getMapWidth(); x++){

                                out.write(Integer.toString(map.getMap()[x][y].getType()));
                                out2.write(Integer.toString(map.getMapSp()[x][y]));
                                if(x+1 < map.getMapWidth()){
                                    out.write("\t");
                                    out2.write("\t");
                                }
                            }
                            out.write("\n");
                            out2.write("\n");
                        }
                        out.close();
                        out2.close();
                    } catch (IOException ex) {
                        Logger.getLogger(Editor.class.getName()).log(Level.SEVERE, null, ex);
                    } finally {
                        try {
                            fstream.close();
                            fstream2.close();
                        } catch (IOException ex) {
                            Logger.getLogger(Editor.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            } else {
                JOptionPane.showMessageDialog(null, "Vous ne pouvez pas enregistrer tant\nqu'il reste des tuiles grises", "", JOptionPane.WARNING_MESSAGE);
            }
        } else if(arg0.getSource() == quad){
            repaint();
        } else if(arg0.getSource() == btnMur){
            tileType = 0;
            spModif = false;
        } else if(arg0.getSource() == btnExt){
            tileType = 2;
            spModif = false;
        } else if(arg0.getSource() == btnGhostsHouse){
            tileType = 3;
            spModif = false;
        } else if(arg0.getSource() == btnBullets){
            tileType = 4;
            spModif = false;
        } else if(arg0.getSource() == btnSuperBullets){
            tileType = 5;
            spModif = false;
        } else if(arg0.getSource() == btnSpawn){
            spType = 1;
            spModif = true;
        } else if(arg0.getSource() == btnGhostRalenti){
            spType = 2;
            spModif = true;
        } else if(arg0.getSource() == btnGhostUpBlock){
            spType = 3;
            spModif = true;
        } else if(arg0.getSource() == btnApplicate){
            map.setNewSize(Integer.parseInt(textLargeur.getText()), Integer.parseInt(textHauteur.getText()));
            textLargeur.setText(Integer.toString(map.getMapWidth()));
            textHauteur.setText(Integer.toString(map.getMapHeight()));
        }
    }
    
}
