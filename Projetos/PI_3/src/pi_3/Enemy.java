/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pi_3;

import java.util.ArrayList;   
import java.util.List;

public class Enemy extends Character implements GameObject{
    
    ArrayList<MappedTile> viewRange = new ArrayList<>();
    
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
          System.out.println(getX() + " " + getY());
          i = 0;
        }
    }
    
    public void createRange() {
        int posX = getX();
        int posY = getY();
        
        int mutiplier = 1;
        for(int i = 0; i < 1; i++) {
            mutiplier += 2;
        }
        
        int range = mutiplier;
        posY += ((mutiplier - 1) / 2);
        for(int i = 0; i < range; i++) {
            for(int j = 0; j < range; j++) {
                if (j == 0) {
                    posX -= ((mutiplier - 1) / 2);
                }
                System.out.print(posX + " " + posY + " :: ");
                posX++;
            }
            System.out.println("");
            posX = 0;
            posY--;
        }
        
    }
}
    
 