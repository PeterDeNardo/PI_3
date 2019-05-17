package pi_3;

import java.io.File;
import java.io.FileNotFoundException;

import java.util.Scanner;
import java.util.ArrayList;        

public class Map {
    private Tiles tileSet;
    private int fillTileID = -1;
    
    private ArrayList<MappedTile> mappedTiles = new ArrayList<MappedTile>();

    public Map(File mapFile, Tiles tileSet) {
        this.tileSet = tileSet;
        try {
            Scanner scanner = new Scanner(mapFile);
            while(scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if(!line.startsWith("//")) {
                    if(line.contains(":")) {
                        String[] splitString = line.split(":");
                        if(splitString[0].equalsIgnoreCase("Fill")) {
                            fillTileID = Integer.parseInt(splitString[1]);
                            continue;
                        }
                    }
                    
                    String[] splitString = line.split(",");
                    if(splitString.length >= 3) {
                        MappedTile mappedTile = new MappedTile(Integer.parseInt(splitString[0]),
                                                               Integer.parseInt(splitString[1]),
                                                               Integer.parseInt(splitString[2]));
                        mappedTiles.add(mappedTile);
                    }
                }
            }
        } catch (FileNotFoundException e) {
            
        }
    }
    
    public void render(RenderHandler render, int xZoom, int yZoom) {
        int tileWidth = 16 * xZoom;
        int tileHeight = 16 * yZoom;
        
        if(fillTileID >= 0) {
            Rectangle camera = render.getCamera();
            
            for(int y = camera.y - tileHeight - (camera.y % tileHeight); y < camera.y + camera.h; y+= tileHeight){
                for(int x = camera.x - tileWidth - (camera.x % tileWidth); x < camera.x + camera.w; x+= tileWidth){
                    tileSet.RenderTile(fillTileID, render, x, y, xZoom, yZoom);
                }
            }
        }
        for(int tileIndex = 0; tileIndex < mappedTiles.size(); tileIndex++) {
            MappedTile mappedTile = mappedTiles.get(tileIndex);
            tileSet.RenderTile(mappedTile.id, render, mappedTile.x * tileWidth, mappedTile.y * tileHeight, xZoom, yZoom);
        }
    }
    
    class MappedTile {
        public int id, x, y;
        
        public MappedTile(int id, int x, int y) {
            this.id = id;
            this.x = x;
            this.y = y;
        }
    }
    
}
