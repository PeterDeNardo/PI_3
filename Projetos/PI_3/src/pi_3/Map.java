package pi_3;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

import java.util.Scanner;
import java.util.ArrayList;   
import java.util.List;
import java.util.HashMap;
import java.util.Collections;
import java.util.Comparator;

public class Map {
    private Tiles tileSet;
    private int fillTileID = -1;
    private File mapFile;
    
    private static int linhas = 5;
    private static int colunas = 6;
    private static ArrayList<MappedTile> mappedTiles = new ArrayList<MappedTile>();
    private HashMap<Integer, String> comments = new HashMap<Integer, String>();

    public Map(File mapFile, Tiles tileSet) {
        this.tileSet = tileSet;
        this.mapFile = mapFile;
        
        try {
            Scanner scanner = new Scanner(mapFile);
            int currentLine = 0;
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
                        MappedTile mappedTile = new MappedTile(/*mappedTiles.size() +1,*/
                                                               Integer.parseInt(splitString[0]),
                                                               Integer.parseInt(splitString[1]),
                                                               Integer.parseInt(splitString[2]));
                        mappedTiles.add(mappedTile);
                    }
                }
                else {
                    comments.put(currentLine, line);
                }
                currentLine++;
            }
        } catch (FileNotFoundException e) {
            
        }
        
        Collections.sort(mappedTiles, new Comparator() {
            public int compare(Object o1, Object o2) {
                MappedTile t1 = (MappedTile) o1;
                MappedTile t2 = (MappedTile) o2;
                return t1.y < t2.y ? -1 : (t1.y > t2.y ? +1 : 0);
            }
        });
        
        ArrayList<MappedTile> orgTiles = new ArrayList<MappedTile>(); 
        ArrayList<MappedTile> cloneTiles = new ArrayList<MappedTile>(mappedTiles); 
        mappedTiles.clear();
        
        int cont = 0;
        
        for (int i = 0; i < cloneTiles.size(); i++) {
            cont++;
            orgTiles.add(cloneTiles.get(i));
            if (cont == 6) {
                Collections.sort(orgTiles, new Comparator() {
                    public int compare(Object o1, Object o2) {
                        MappedTile t1 = (MappedTile) o1;
                        MappedTile t2 = (MappedTile) o2;
                        return t1.x < t2.x ? -1 : (t1.x > t2.x ? +1 : 0);
                    }
                });
                mappedTiles.addAll(orgTiles);
                orgTiles.clear();
                cont = 0;
            }
        }

        int id = 1;
        for (MappedTile e : mappedTiles){
            e.setId(id);
            id++;
            System.out.println(mappedTiles.size() + " :: y :: " + e.y + " :: x :: " + e.x +" :: "+ e.id);
        } 
        
        //configuraMapa();
    }
    
    public void setTile(int tileX, int tileY, int tileID) {
        boolean foundTile = false;
        //int id = mappedTiles.size()  + 1;
        for(int i = 0; i < mappedTiles.size(); i++) {
            MappedTile mappedTile = mappedTiles.get(i);
            if(mappedTile.x == tileX && mappedTile.y == tileY) {
                mappedTile.tileId = tileID;
                break;
            }
        }
        
        if(!foundTile) {
            mappedTiles.add(new MappedTile(tileID, tileX, tileY));
        }
    }
    
    public void removeTile(int tileX, int tileY) {
        for (int i = 0; i < mappedTiles.size(); i++) {
            MappedTile mappedTile = mappedTiles.get(i);
            if (mappedTile.x == tileX && mappedTile.y == tileY) {
                mappedTiles.remove(i);
            }
        }
    }
    
    public void saveMap() {
        try {
            int currentLine = 0;
            if(mapFile.exists()) {
                mapFile.delete();
            }
            mapFile.createNewFile();
            
            PrintWriter printWriter = new PrintWriter(mapFile);
            
            if(fillTileID >= 0) {
                if (comments.containsKey(currentLine)) {
                    printWriter.println(comments.get(currentLine));
                    currentLine++;
                }
                printWriter.println("Fill:" + fillTileID);
            }
            
            for(int i = 0; i < mappedTiles.size(); i++) {
                if(comments.containsKey(currentLine)) {
                    printWriter.println(comments.get(currentLine));
                }
                MappedTile tile = mappedTiles.get(i);
                printWriter.println(tile.id + "," + tile.x + "," + tile.y);
                currentLine++;
            }
            printWriter.close();
            
        } catch (java.io.IOException e) {
            e.printStackTrace();
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
            tileSet.RenderTile(mappedTile.tileId, render, mappedTile.x * tileWidth, mappedTile.y * tileHeight, xZoom, yZoom);
        }
    }
    
    public static void configuraMapa()
    {
        for(MappedTile no: mappedTiles)
        {
            System.out.println(no.getId());
            //no.vizinhos.addAll(acharCantos(no));
            no.vizinhos.addAll(acharOrtogonais(no));
        }
    }
    
    public static List<MappedTile> acharCantos(MappedTile no)
    {
        int id = no.getId();
        List<MappedTile> list = new ArrayList();
        
        //calcular linha
        int linhaDoNo = (no.getId()/linhas)+1;
        System.out.println((no.getId()/linhas)+1);
        //calcula coluna
        int colunaDoNo = (no.getId()%colunas)+1;
            
        //pega canto superior esquerda
        if (linhaDoNo > 1 && colunaDoNo > 1) {
            list.add(mappedTiles.get((id - colunas) - 1));
        }
        System.out.println((id - colunas) - 1);
        //pega canto superior direita
        if (linhaDoNo > 1 && colunaDoNo < colunas) {
            list.add(mappedTiles.get((id - colunas) + 1));
        }
        
        //pegar canto infoerior esquerdo
        if (linhaDoNo < mappedTiles.size() / linhas && colunaDoNo > 1) {
            list.add(mappedTiles.get((id + colunas) - 1));
        }
        //pegar canto inferior direito
        if (linhaDoNo < mappedTiles.size() / linhas && colunaDoNo < colunas) {
            list.add(mappedTiles.get((id + colunas) + 1));
        }

        return list;
    }
    
    public static List<MappedTile> acharOrtogonais (MappedTile no)
    {   
        //calcular linha
        int linhaDoNo = (no.getId()/linhas)+1;
        //calcula coluna
        int colunaDoNo = (no.getId()%colunas)+1;
        List<MappedTile> list = new ArrayList();
        int id = no.getId();
        //pegar vizinho esquerdo
        if (colunaDoNo > 1) {
            list.add(mappedTiles.get(id - 1));
        }
        //pegar vizinho direito
       	if (colunaDoNo < colunas) {
            list.add(mappedTiles.get(id + 1));
        }
        //pegar vizinho cima
        if (linhaDoNo > 1) {
            list.add(mappedTiles.get((id - linhas)+1));
        }
        //pegar vizinho baixo
        if (linhaDoNo < mappedTiles.size()/linhas - 1) {
            System.out.println(mappedTiles.size() +" "+ linhas);
            System.out.println(linhaDoNo +" "+  mappedTiles.size()/linhas);
            list.add(mappedTiles.get(id + colunas));
        }
        
        return list;
    }
    
    public int getColunas() {
        return colunas;
    }
    
    public int getLinhas() {
        return linhas;
    }
    
    public ArrayList<MappedTile> getMap() {
        return this.mappedTiles;
    }
}
