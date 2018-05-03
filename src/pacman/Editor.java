/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pacman;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

/**
 *
 * @author BuYa
 */
public class Editor extends JPanel implements MouseMotionListener{
    
    private static Map map;
    private JComboBox selectionTile = new JComboBox(new String[]{"Passage", "Mur simple", "Mur double"});
    
    public Editor(int mapWidth, int mapHeight){
        map = new Map(mapWidth, mapHeight);
        this.addMouseMotionListener(this);
        this.add(selectionTile);
    }
    
    public void paintComponent(Graphics g){
        g.setColor(Color.black);
        g.fillRect(0, 0, this.getWidth(), this.getHeight());
        
        map.afficher(g, this.getWidth(), this.getHeight());
    }

    @Override
    public void mouseDragged(MouseEvent me) {
        int size;
        int mapWidth = map.getMapWidth();
        int mapHeight = map.getMapHeight();

        if(this.getWidth()/mapWidth > this.getHeight()/mapHeight){
            size = this.getHeight()/mapHeight;
        } else {
            size = this.getWidth()/mapWidth;
        }

        int x = me.getX()/size;
        int y = me.getY()/size;
        
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
    
}
