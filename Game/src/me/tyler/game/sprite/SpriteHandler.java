package me.tyler.game.sprite;

import me.tyler.game.GameConstants;
import me.tyler.game.GamePanel;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;

public class SpriteHandler {

    private final GamePanel gamePanel;
    private final List<Sprite> sprites;

    public SpriteHandler(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        this.sprites = new ArrayList<>();
        loadSprites();
        System.out.println(String.format("Loaded %s sprites", sprites.size()));
    }

    private void loadSprites() {
        try {
            BufferedImage spriteImage = ImageIO.read(new File("C:\\Users\\tjsca\\Documents\\cs\\Game\\sprite_sheet.png"));
            for(int x=0; x<spriteImage.getWidth(); x+= GameConstants.TILE_SIZE) {
                for(int y=0; y<spriteImage.getWidth(); y+=GameConstants.TILE_SIZE) {
                    this.sprites.add(
                            new Sprite(
                                    y / GameConstants.TILE_SIZE,
                                    x / GameConstants.TILE_SIZE,
                                    spriteImage.getSubimage(x, y, GameConstants.TILE_SIZE, GameConstants.TILE_SIZE))
                    );
                }
            }
        } catch(IOException ex) {
            ex.printStackTrace();
        }
    }

    public Sprite getSprite(int row, int col) {
        return this.sprites.stream()
                .filter(s -> s.getRow() == row)
                .filter(s -> s.getCol() == col)
                .findFirst().orElse(null);
    }

    public List<Sprite> getSprites() {
        return sprites;
    }
}
