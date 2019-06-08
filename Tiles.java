
package pi_3;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.ArrayList;

public class Tiles {
    private SpriteSheet spriteSheet;
    private ArrayList<Tile> tileList = new ArrayList<Tile>();
    
    public Tiles(File tilesFile, SpriteSheet spriteSheet ) {
        this.spriteSheet = spriteSheet;
        try {
            Scanner scanner = new Scanner(tilesFile);
            while(scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (!line.startsWith("//")) {
                    String[] splitString = line.split("-");
                    String tileName = splitString[0];
                    int spriteX = Integer.parseInt(splitString[1]);
                    int spriteY = Integer.parseInt(splitString[2]);
                    Tile tile = new Tile(tileName, spriteSheet.getSprite(spriteX, spriteY));
                    tileList.add(tile);
                }
            }
        } catch(FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    
    public void RenderTile(int tileID, RenderHandler render, int posX, int posY, int xZoom, int yZoom) {
        if (tileID >= 0 && tileList.size() > tileID) {
            render.renderSprite(tileList.get(tileID).sprite, posX, posY, xZoom, yZoom);
        } else {
            System.out.println("TileID: " +tileID+ " fora do range: " + tileList.size() );
        }
    }
    
    class Tile {
        public String tileName;
        public Sprite sprite;
        public Tile(String tileName, Sprite sprite){
            this.tileName = tileName;
            this.sprite = sprite;
        }
    }        
}
