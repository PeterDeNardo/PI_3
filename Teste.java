/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pi_3;

/**
 *
 * @author peterdenardo
 */
public class Teste {
    public static void main(String[] args) {
        int X = -41;
        int Y = -3;
        
        int mutiplier = 1;
        for(int i = 0; i < 3; i++) {
            mutiplier += 2;
        }
        
        int range = mutiplier;
        int x = X;
        int y = Y;
        y += ((mutiplier - 1) / 2);
        for(int i = 0; i < range; i++) {
            for(int j = 0; j < range; j++) {
                if (j == 0) {
                    x -= ((mutiplier - 1) / 2);
                }
                System.out.print(x + " " + y + " :: ");
                x++;
            }
            System.out.println("");
            x = X;
            y--;
            
        }
        
        
    }
}
