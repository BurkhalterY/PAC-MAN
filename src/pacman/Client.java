/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pacman;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import pacman.Entity.Direction;

/**
 *
 * @author Pascal
 */
public class Client extends Panel{
    private static String serverName;
    private static int serverPort = 999;
    private Socket socket;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private ImageIcon elements /*= new Object[3]*/;
    private BufferedImage bi;
     
    public Client(String serverName) {
        this.serverName = serverName;
        try {
            socket = new Socket(serverName, serverPort);
            System.out.println("Socket client: " + socket);
            
            out = new ObjectOutputStream(socket.getOutputStream());
            out.flush();
            in = new ObjectInputStream(socket.getInputStream());
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void go(){
        elements = inOut(Clavier.getDirection2());
        bi = new BufferedImage(elements.getIconWidth(), elements.getIconHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics g = bi.createGraphics();
        elements.paintIcon(null, g, 0,0);
        g.dispose();
        /*map = (Map) elements[0];
        playersTab = (Player[]) elements[1];
        ghostsTab = (Ghost[]) elements[2];*/
    }
    
    public void paintComponent(Graphics g){
        g.drawImage(bi, 0, 0, this);
    }
    
    public ImageIcon inOut(Direction direction){
        ImageIcon tableauRecu = null;
        /*try {
            out.reset();
            System.out.println("Client a cree les flux");
            
            out.writeObject(direction);
            out.flush();
            
            System.out.println("Client: donnees emises");
            
            Object objetRecu;
            
            objetRecu = in.readObject();
            tableauRecu = (Graphics) objetRecu;
            
            System.out.println("Client recoit: " + tableauRecu);
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }*/
        try {
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream()); 
            oos.writeObject(direction);
            oos.close();
            
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            tableauRecu = (ImageIcon) ois.readObject(); 
            ois.close();
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
        return tableauRecu;
    }
    
}
