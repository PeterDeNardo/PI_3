/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pi_3;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author peterdenardo
 */
public class ViewRange {
    private static int linhas = 7;
    private static int colunas = 7;
    private static ArrayList<MappedTile> mappedTiles = new ArrayList<MappedTile>();
    
    public ViewRange(ArrayList<MappedTile> mapArray) {
        this.mappedTiles = mapArray;
        configuraTiles();
    }
    
    public static void configuraTiles(){
        for(int i = 0; i < mappedTiles.size() -1; i++ ) {
            mappedTiles.get(i).vizinhos.addAll(acharOrtogonais(mappedTiles.get(i)));
            mappedTiles.get(i).vizinhos.addAll(acharCantos(mappedTiles.get(i)));
        }
    }
    
    public static List<MappedTile> acharCantos(MappedTile no){
        int id = no.getTileId();
        List<MappedTile> list = new ArrayList();
        
        //calcular linha
        int linhaDoNo = (no.getTileId()/linhas)+1;
        //calcula coluna
        int colunaDoNo = (no.getTileId()%colunas)+1;
            
        //pega canto superior esquerda
        if (linhaDoNo > 1 && colunaDoNo > 1) {
            list.add(mappedTiles.get((id - colunas) - 1));
        }
        
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
        
//        for(int i = 0; i < list.size(); i++ ) {
//            System.out.print(list.get(i).tileId + " ");
//        }
//        System.out.println();

        return list;
    }
    
    public static List<MappedTile> acharOrtogonais (MappedTile no)
    {   
        //calcular linha
        int linhaDoNo = (no.getTileId()/linhas)+1;
        //calcula coluna
        int colunaDoNo = (no.getTileId()%colunas)+1;
        List<MappedTile> list = new ArrayList();
        int id = no.getTileId();
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
        if (linhaDoNo < mappedTiles.size()/linhas) {
            list.add(mappedTiles.get(id + colunas));
        }
        
//        for(int i = 0; i < list.size(); i++ ) {
//            System.out.print(list.get(i).tileId + " ");
//        }
//        System.out.println();
        
        return list;
    }
    
    public int getColunas() {
        return colunas;
    }
    
    public int getLinhas() {
        return linhas;
    }
    
    public ArrayList<MappedTile> getMap() {
        return mappedTiles;
    }
}
