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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author BuYa
 */
public class Editor extends JPanel implements ActionListener, MouseListener, MouseMotionListener{
    
    private static Map map;
    private JButton btnOpenFile = new JButton("Ouvrir un fichier");
    private JButton btnSaveFile = new JButton("Sauvegarder");
    private JFileChooser fc = new JFileChooser("res/maps");
    private JButton btnMur = new JButton("Mur");
    private JButton btnExt = new JButton("Extérieure");
    private JButton btnGhostsHouse = new JButton("Maison fantômes", new ImageIcon("res/iconGhostHouse.png"));
    private JButton btnBullets = new JButton("Pac-Gomme");
    private JButton btnSuperBullets = new JButton("Super Pac-Gomme");
    
    private int tileType = 0, tileX = 0, tileY = 0, margeRight = 300;
    private float size = 0;
    
    public Editor(int mapWidth, int mapHeight){
        map = new Map(mapWidth, mapHeight);
        fc.setAcceptAllFileFilterUsed(false);
        FileFilter filter = new FileNameExtensionFilter("*.txt", "txt");
        fc.addChoosableFileFilter(filter);
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
        
        btnGhostsHouse.setVerticalTextPosition(SwingConstants.BOTTOM); 
        btnGhostsHouse.setHorizontalTextPosition(SwingConstants.CENTER); 
        
        btnOpenFile.addActionListener(this);
        btnSaveFile.addActionListener(this);
        btnMur.addActionListener(this);
        btnExt.addActionListener(this);
        btnGhostsHouse.addActionListener(this);
        btnBullets.addActionListener(this);
        btnSuperBullets.addActionListener(this);
        this.add(btnOpenFile);
        this.add(btnSaveFile);
        this.add(btnMur);
        this.add(btnExt);
        this.add(btnGhostsHouse);
        this.add(btnBullets);
        this.add(btnSuperBullets);
    }
    
    public void paintComponent(Graphics g){
        g.setColor(Color.black);
        g.fillRect(0, 0, this.getWidth(), this.getHeight());
        
        int y = 20;
        btnOpenFile.setBounds(this.getWidth()-margeRight+(margeRight/4), y, margeRight/2, 25);
        y += 30;
        btnSaveFile.setBounds(this.getWidth()-margeRight+(margeRight/4), y, margeRight/2, 25);
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
        
        map.afficher(g, this.getWidth()-margeRight, this.getHeight(), true);
        
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
            if(tileType == 3){
                if(tileX+8 <= map.getMapWidth() && tileY+5 <= map.getMapHeight()){
                    map.setTile(tileX, tileY, tileType);
                }
                tileType = 0;
            } else {
                map.setTile(tileX, tileY, tileType);
            }      
        } else if(SwingUtilities.isRightMouseButton(me)) {
            map.setTile(tileX, tileY, 1);
            if(tileType == 3){
                tileType = 0;
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
            if(tileType == 3){
                if(tileX+8 <= map.getMapWidth() && tileY+5 <= map.getMapHeight()){
                    map.setTile(tileX, tileY, tileType);
                }
                tileType = 0;
            } else {
                map.setTile(tileX, tileY, tileType);
            }
        } else if(SwingUtilities.isRightMouseButton(me)) {
            map.setTile(tileX, tileY, 1);
            if(tileType == 3){
                tileType = 0;
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
                map = new Map(file.getParentFile().getName(), "tileset.png");
                this.repaint();
            }
        } else if(arg0.getSource() == btnSaveFile){
            int ret = fc.showSaveDialog(this);
            if (ret == JFileChooser.APPROVE_OPTION) {
                FileWriter fstream = null;
                try {
                    File file = fc.getSelectedFile();
                    fstream = new FileWriter(file);
                    BufferedWriter out = new BufferedWriter(fstream);
                    for(int y = 0; y < map.getMapHeight(); y++){
                        for(int x = 0; x < map.getMapWidth(); x++){
                            out.write(Integer.toString(map.getMap()[x][y].getType()));
                            if(x+1 < map.getMapWidth()){
                                out.write("\t");
                            }
                        }
                        out.write("\n");
                    }
                    out.close();
                } catch (IOException ex) {
                    Logger.getLogger(Editor.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        fstream.close();
                    } catch (IOException ex) {
                        Logger.getLogger(Editor.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        } else if(arg0.getSource() == btnMur){
            tileType = 0;
        } else if(arg0.getSource() == btnExt){
            tileType = 2;
        } else if(arg0.getSource() == btnGhostsHouse){
            tileType = 3;
        } else if(arg0.getSource() == btnBullets){
            tileType = 4;
        } else if(arg0.getSource() == btnSuperBullets){
            tileType = 5;
        }
    }
    
}
