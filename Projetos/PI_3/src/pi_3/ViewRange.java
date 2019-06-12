/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pi_3;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author peterdenardo
 */
public class ViewRange {
    private static int linhas = 3;
    private static int colunas = 3;
    private static int width = Game.renderer.getCamera().w;
    private static int height = Game.renderer.getCamera().h;
    private static ArrayList<MappedTile> mappedTiles = new ArrayList<MappedTile>();
    private static MappedTile[][] nodes = new MappedTile[linhas][colunas];
    
    public ViewRange(ArrayList<MappedTile> mapArray) {
        this.mappedTiles = mapArray;
        int cont = 0;
        for(int i = 0; i < linhas; i++) {
            for(int j = 0; j < colunas; j++) {
                mappedTiles.get(cont).setId(cont);
                System.out.println("ÓOOO : " + mappedTiles.get(cont).id);
                nodes[i][j] = mappedTiles.get(0);
                cont++;
            }
        }
        configuraTiles();
    }
    
    public final List<MappedTile> findPath(MappedTile s, MappedTile g)
	{
		// If our start position is the same as our goal position ...
            System.out.println(5);
		if (s.x == g.x && s.y == g.y)
		{
                    System.out.println(7);
			// Return an empty path, because we don't need to move at all.
			return new LinkedList<MappedTile>();
		}

		// The set of nodes already visited.
		List<MappedTile> openList = new LinkedList<MappedTile>();
		// The set of currently discovered nodes still to be visited.
		List<MappedTile> closedList = new LinkedList<MappedTile>();

		// Add starting node to open list.
//                for(int i = 0; i < linhas; i++) {
//                    for(int j = 0; j < colunas; j++) {
//                        System.out.print(mappedTiles.get(cont));
//                        cont++;
//                    }
//                }
		openList.add(s);

		// This loop will be broken as soon as the current node position is
		// equal to the goal position.
		while (true)
		{
			// Gets node with the lowest F score from open list.
			MappedTile current = lowestFInList(openList);
			// Remove current node from open list.
			openList.remove(current);
			// Add current node to closed list.
			closedList.add(current);

			// If the current node position is equal to the goal position ...
			if ((current.getX() == g.x) && (current.getY() == g.y))
			{
				// Return a LinkedList containing all of the visited nodes.
				return calcPath(s, current);
			}

			List<MappedTile> adjacentNodes = getAdjacent(current, closedList);
                        System.out.print(adjacentNodes.size());
			for (int i = 0; i < adjacentNodes.size(); i++)
			{
				// If node is not in the open list ...
				if (!openList.contains(adjacentNodes.get(i)))
				{
					// Set current node as parent for this node.
					adjacentNodes.get(i).setParent(current);
					// Set H costs of this node (estimated costs to goal).
					adjacentNodes.get(i).setH(g);
					// Set G costs of this node (costs from start to this node).
					adjacentNodes.get(i).setG(current);
					// Add node to openList.
					openList.add(adjacentNodes.get(i));
				}
				// Else if the node is in the open list and the G score from
				// current node is cheaper than previous costs ...
				else if (adjacentNodes.get(i).getG() > adjacentNodes.get(i).calculateG(current))
				{
					// Set current node as parent for this node.
					adjacentNodes.get(i).setParent(current);
					// Set G costs of this node (costs from start to this node).
					adjacentNodes.get(i).setG(current);
				}
			}

			// If no path exists ...
			if (openList.isEmpty())
			{
				// Return an empty list.
				return new LinkedList<MappedTile>();
			}
			// But if it does, continue the loop.
		}
	}
    
    private MappedTile lowestFInList(List<MappedTile> list)
	{
		MappedTile cheapest = list.get(0);
		for (int i = 0; i < list.size(); i++)
		{
			if (list.get(i).getF() < cheapest.getF())
			{
				cheapest = list.get(i);
			}
		}
		return cheapest;
	}
    
    private List<MappedTile> getAdjacent(MappedTile node, List<MappedTile> closedList)
	{
		List<MappedTile> adjacentNodes = new LinkedList<MappedTile>();
		int x = node.getX();
		int y = node.getY();

		//MappedTile adjacent;
                
                for(int i = 0; i < mappedTiles.size(); i++) {
                    adjacentNodes.addAll(acharCantos(mappedTiles.get(i)));
                    adjacentNodes.addAll(acharOrtogonais(mappedTiles.get(i)));
                }
//
//		// Check left node
//		if (x > 0)
//		{
//			adjacent = getNode(x - 1, y);
//			if (adjacent != null && adjacent.getBloqueado() && !closedList.contains(adjacent))
//			{
//				adjacentNodes.add(adjacent);
//			}
//		}
//
//		// Check right node
//		if (x < width)
//		{
//			adjacent = getNode(x + 1, y);
//			if (adjacent != null && adjacent.getBloqueado() && !closedList.contains(adjacent))
//			{
//				adjacentNodes.add(adjacent);
//			}
//		}
//
//		// Check top node
//		if (y > 0)
//		{
//			adjacent = this.getNode(x, y - 1);
//			if (adjacent != null && adjacent.getBloqueado() && !closedList.contains(adjacent))
//			{
//				adjacentNodes.add(adjacent);
//			}
//		}
//
//		// Check bottom node
//		if (y < height)
//		{
//			adjacent = this.getNode(x, y + 1);
//			if (adjacent != null && adjacent.getBloqueado() && !closedList.contains(adjacent))
//			{
//				adjacentNodes.add(adjacent);
//			}
//		}
		return adjacentNodes;
	}
    
    public MappedTile getNode(int x, int y)
	{
            for(int i = 0; i < mappedTiles.size(); i++) {
		if (mappedTiles.get(i).x == x && mappedTiles.get(i).y == y)
		{
			return mappedTiles.get(i);
		}
            }
        return null;
	}
    
    private List<MappedTile> calcPath(MappedTile start, MappedTile goal)
	{
		LinkedList<MappedTile> path = new LinkedList<MappedTile>();

		MappedTile node = goal;
		boolean done = false;
		while (!done)
		{
			path.addFirst(node);
			node = node.getParent();
			if (node.equals(start))
			{
				done = true;
			}
		}
		return path;
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
