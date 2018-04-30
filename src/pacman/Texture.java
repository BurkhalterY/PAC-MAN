/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pacman;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 *
 * @author Pascal
 */
public class Texture {
    private static String textureFolder;
    private static int pacman_rows;
    private static int pacman_columns;
    private static int ghosts_rows;
    private static int ghosts_columns;
    private static int pacman_moving_frames;
    private static int ghosts_moving_frames;
    private static boolean pacman_closed_frame;
    private static boolean ghosts_scarred_multiframe;
    private static float ghosts_speed;

    public static void initTexture(String path){
        textureFolder = path;
        try
        {
            File f = new File ("res/"+path+"/texture.txt");
            FileReader fr = new FileReader (f);
            BufferedReader br = new BufferedReader (fr);
            
            try
            {
                String line = br.readLine();
            
                while (line != null)
                {
                    String tableLigne[] = line.split(":");
                    line = br.readLine();
                    
                    if(tableLigne[0].equals("pacman_rows")){
                        pacman_rows = Integer.parseInt(tableLigne[1]);
                    } else if(tableLigne[0].equals("pacman_columns")){
                        pacman_columns = Integer.parseInt(tableLigne[1]);
                    } else if(tableLigne[0].equals("ghosts_rows")){
                        ghosts_rows = Integer.parseInt(tableLigne[1]);
                    } else if(tableLigne[0].equals("ghosts_columns")){
                        ghosts_columns = Integer.parseInt(tableLigne[1]);
                    } else if(tableLigne[0].equals("pacman_moving_frames")){
                        pacman_moving_frames = Integer.parseInt(tableLigne[1]);
                    } else if(tableLigne[0].equals("ghosts_moving_frames")){
                        ghosts_moving_frames = Integer.parseInt(tableLigne[1]);
                    } else if(tableLigne[0].equals("pacman_closed_frame")){
                        pacman_closed_frame = Boolean.parseBoolean(tableLigne[1]);
                    } else if(tableLigne[0].equals("ghosts_scarred_multiframe")){
                        ghosts_scarred_multiframe = Boolean.parseBoolean(tableLigne[1]);
                    } else if(tableLigne[0].equals("ghosts_speed")){
                        ghosts_speed = Float.parseFloat(tableLigne[1]);
                    }
                }
                
                br.close();
                fr.close();
            }
            catch (IOException exception)
            {
                System.out.println ("Erreur lors de la lecture : " + exception.getMessage());
            }
        }
        catch (FileNotFoundException exception)
        {
            System.out.println ("Le fichier n'a pas été trouvé");
        }
    }

    /**
     * @return the pacman_rows
     */
    public static int getPacman_rows() {
        return pacman_rows;
    }

    /**
     * @return the pacman_columns
     */
    public static int getPacman_columns() {
        return pacman_columns;
    }

    /**
     * @return the ghosts_rows
     */
    public static int getGhosts_rows() {
        return ghosts_rows;
    }

    /**
     * @return the ghosts_columns
     */
    public static int getGhosts_columns() {
        return ghosts_columns;
    }

    /**
     * @return the pacman_moving_frames
     */
    public static int getPacman_moving_frames() {
        return pacman_moving_frames;
    }

    /**
     * @return the ghosts_moving_frames
     */
    public static int getGhosts_moving_frames() {
        return ghosts_moving_frames;
    }

    /**
     * @return the pacman_closed_frame
     */
    public static boolean isPacman_closed_frame() {
        return pacman_closed_frame;
    }

    /**
     * @return the ghosts_scarred_multiframe
     */
    public static boolean isGhosts_scarred_multiframe() {
        return ghosts_scarred_multiframe;
    }

    /**
     * @return the ghosts_speed
     */
    public static float getGhosts_speed() {
        return ghosts_speed;
    }

    /**
     * @return the textureFolder
     */
    public static String getTextureFolder() {
        return textureFolder;
    }

    
    
}
