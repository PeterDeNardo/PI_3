/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pi_3;

import java.util.ArrayList;   
import java.util.List;

public class Enemy extends Character implements GameObject{
    
    ArrayList<MappedTile> viewRangeTiles = new ArrayList<>();
    ArrayList<MappedTile> pathTiles = new ArrayList<>();
    ViewRange viewRangeMap;
    
    public Enemy(Sprite sprite) {
        super(sprite);

        if(sprite != null && sprite instanceof AnimatedSprite)
            animatedSprite = (AnimatedSprite) sprite;

        updateDirection();
        characterRectangle = new Rectangle(-90, -150, 20, 26);
        characterRectangle.generateGraphics(3, 0xFF00FF90);
//        startingPoint = characterRectangle.x;
//        endingPoint = startingPoint + 100;
    }
    int i = 1;
    @Override
    public void update(Game game)
    {
        if (i != 0) {
          createRange();
          i = 0;
        }
        detectPlayer(game.getPlayerPositon()[0], game.getPlayerPositon()[1]);
    }
    
    public void createRange() {
        int X = getX();
        int Y = getY();
        
        int mutiplier = 1;
        for(int i = 0; i < 3; i++) {
            mutiplier += 2;
        }
        
        int range = mutiplier;
        int x = X;
        int y = Y;
        int id = 0;
        y += ((mutiplier - 1) / 2);
        for(int i = 0; i < range; i++) {
            for(int j = 0; j < range; j++) {
                if (j == 0) {
                    x -= ((mutiplier - 1) / 2);
                }
                System.out.print(x + " " + y + " :: ");
                MappedTile tile = new MappedTile(id, x, y);
                System.out.print(id + "... |");
                viewRangeTiles.add(tile);
                x++;
                id++;
            }
            System.out.println("");
            x = X;
            y--;
        }
        
        viewRangeMap = new ViewRange(viewRangeTiles);
    }
    
    public void detectPlayer(int playerX, int playerY) {
        for(int i = 0; i < viewRangeTiles.size(); i++) {
            if (viewRangeTiles.get(i).x == playerX && viewRangeTiles.get(i).y == playerY) {
                AEstrela a = new AEstrela();
                pathTiles = (ArrayList<MappedTile>) a.aEstrela(viewRangeTiles.get(25),
                                    viewRangeTiles.get(i),
                                    viewRangeMap);
                
                for(int j = 0; j < pathTiles.size(); j++) {
                    System.out.println(pathTiles.get(j));
                }
            }
        }
    }
}
    
 