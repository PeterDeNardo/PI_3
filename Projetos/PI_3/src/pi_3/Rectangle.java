/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pi_3;

/**
 *
 * @author Fofolho
 */
public class Rectangle {
    
    public int x,y,width,height;
    
    Rectangle(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }
    
    Rectangle() {
        this(0,0,0,0);
    }
}
