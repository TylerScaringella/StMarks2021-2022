package me.tyler.game.sprite;

import me.tyler.game.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class SpriteHandler {

    private final GamePanel gamePanel;
    private final Map<String, Sprite> sprites;

    public SpriteHandler(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        this.sprites = new HashMap();
        loadSprites();
    }

    private void loadSprites() {
        try {
            BufferedImage spriteImage = ImageIO.read(new File("C:\\Users\\tjsca\\Documents\\cs\\Game\\sprite_sheet.png"));
        } catch(IOException ex) {
            ex.printStackTrace();
        }
    }
}
