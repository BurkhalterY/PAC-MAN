/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pacman;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import pacman.Entity.Direction;

/**
 *
 * @author Pascal
 */
public class Server extends Panel{
    private static final int port = 999;
    private ServerSocket s;
    private Socket soc;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    
    Server(){
        try {
            s = new ServerSocket(port);
            System.out.println("Socket serveur: " + s);
            
            soc = s.accept();
            System.out.println("Serveur a accepte connexion: " + soc);
            
            out = new ObjectOutputStream(soc.getOutputStream());
            out.flush();
            in = new ObjectInputStream(soc.getInputStream());
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void go(){
        /*Object[] elements = new Object[3];
        elements[0] = map;
        elements[1] = playersTab;
        elements[2] = ghostsTab;*/
        BufferedImage img = new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = img.createGraphics();
        this.printAll(g2d);
        g2d.dispose();
        Panel.getPlayersTab()[1].setDirection(inOut(new ImageIcon(img)));
    }
    
    public Direction inOut(ImageIcon elements){
        Direction direction = null;

        /*try {
            
            out.reset();
            System.out.println("Serveur a cree les flux");
            out.writeObject(g);
            out.flush();
            
            System.out.println("Serveur: donnees emises");
            
            Object objetRecu;
            
            objetRecu = in.readObject();
            direction = (Direction) objetRecu;
            
            System.out.println("Serveur recoit: " + direction);
            
            
            return direction;
            } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            }*/
            
        try {
            ObjectOutputStream oos = new ObjectOutputStream(soc.getOutputStream()); 
            oos.writeObject(elements);
            oos.close();
            
            ObjectInputStream ois = new ObjectInputStream(soc.getInputStream());
            direction = (Direction) ois.readObject(); 
            ois.close();
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return direction;
    }
}
