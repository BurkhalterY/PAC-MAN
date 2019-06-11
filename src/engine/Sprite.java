package engine;

import java.awt.image.BufferedImage;

public class Sprite {
    private BufferedImage[] spritesheet;
    private int fps = 10;
    
    public Sprite(BufferedImage[] spritesheet){
        this.spritesheet = spritesheet;
        this.fps = fps;
    }
    
    public BufferedImage render(){
        return spritesheet[(int)(Game.getTick()/fps)%spritesheet.length];
    }
}
