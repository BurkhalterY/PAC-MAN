/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pacman;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 *
 * @author BuYa
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        File dir = new File ("C:/xampp/htdocs/test");
        dir.mkdirs();
        copier(new File("res/test/com.php"), new File("C:/xampp/htdocs/test/com.php"));
        copier(new File("res/test/com_post.php"), new File("C:/xampp/htdocs/test/com_post.php"));
        Frame fen = new Frame();
    }
    
    public static boolean copier(File source, File dest) { 
        try (InputStream sourceFile = new java.io.FileInputStream(source);  
                OutputStream destinationFile = new FileOutputStream(dest)) { 
            // Lecture par segment de 0.5Mo  
            byte buffer[] = new byte[512 * 1024]; 
            int nbLecture; 
            while ((nbLecture = sourceFile.read(buffer)) != -1){ 
                destinationFile.write(buffer, 0, nbLecture); 
            } 
        } catch (IOException e){ 
            e.printStackTrace(); 
            return false; // Erreur 
        } 
        return true; // Résultat OK   
    }
}
