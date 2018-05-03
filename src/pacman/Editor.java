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
import java.awt.event.MouseMotionListener;
import java.io.File;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author BuYa
 */
public class Editor extends JPanel implements ActionListener, MouseMotionListener{
    
    private static Map map;
    private JButton btnOpenFile = new JButton("Ouvrir un fichier");
    private JButton btnSaveFile = new JButton("Sauvegarder");
    private JFileChooser fc = new JFileChooser("res/maps");;
    
    public Editor(int mapWidth, int mapHeight){
        map = new Map(mapWidth, mapHeight);
        fc.setAcceptAllFileFilterUsed(false);
        FileFilter filter = new FileNameExtensionFilter("*.txt", "txt");
        fc.addChoosableFileFilter(filter);
        this.addMouseMotionListener(this);
        btnOpenFile.addActionListener(this);
        btnSaveFile.addActionListener(this);
        this.add(btnOpenFile);
        this.add(btnSaveFile);
    }
    
    public void paintComponent(Graphics g){
        g.setColor(Color.black);
        g.fillRect(0, 0, this.getWidth(), this.getHeight());
        
        map.afficher(g, this.getWidth(), this.getHeight(), true);
        
    }

    @Override
    public void mouseDragged(MouseEvent me) {
        float size;
        int mapWidth = map.getMapWidth();
        int mapHeight = map.getMapHeight();

        if(this.getWidth()/mapWidth > this.getHeight()/mapHeight){
            size = (float)this.getHeight()/mapHeight;
        } else {
            size = (float)this.getWidth()/mapWidth;
        }

        int x = (int)(me.getX()/size);
        int y = (int)(me.getY()/size);
        
        if(SwingUtilities.isLeftMouseButton(me)){
            map.setTile(x, y, 0);
        } else {
            map.setTile(x, y, 1);
        }
        this.repaint();
    }

    @Override
    public void mouseMoved(MouseEvent me) {
        
    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
        if(arg0.getSource() == btnOpenFile){
            int ret = fc.showOpenDialog(this);
            if (ret == JFileChooser.APPROVE_OPTION) {
                File file = fc.getSelectedFile();
                map = new Map(file.getParentFile().getName(), "tileset.png");
                System.out.println(file.getParentFile().getName());
                this.repaint();
            }
        } else if(arg0.getSource() == btnSaveFile){
            
        }
    }
    
}
