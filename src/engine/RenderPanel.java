package engine;

import java.awt.Graphics;
import javax.swing.JPanel;

public class RenderPanel extends JPanel {
    
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        int width = (int)this.getVisibleRect().width;
        int height = (int)this.getVisibleRect().height;
        
        g.clearRect(0, 0, this.getWidth(), this.getHeight());
        
        if(Game.isStarted()){
            Game.getMap().afficher(g, width, height);
            
            for(int i = 0; i < Game.getPlayers().size(); i++){
                Game.getPlayers().get(i).afficher(g, width, height);
            }
            for(int i = 0; i < Game.getGhosts().size(); i++){
                Game.getGhosts().get(i).afficher(g, width, height);
                if(Game.isDebug()){
                    Game.getGhosts().get(i).afficherDebug(g, width, height);
                }
            }
        }
    }
}
