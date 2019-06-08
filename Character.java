package pi_3;

public class Character {
    public Rectangle characterRectangle;
    int speed = 10;

    //0 - Down, 1 - Left, 2 - Up, 3 Right
    private int direction = 0;
    private Sprite sprite;
    public AnimatedSprite animatedSprite = null;
    
    
    public Character(Sprite sprite) {
        
        this.sprite = sprite;
        
        if(sprite instanceof AnimatedSprite) {
            animatedSprite = (AnimatedSprite) sprite;
        }
        
        updateDirection();
        characterRectangle = new Rectangle(32, 16, 16, 32);
        characterRectangle.generateGraphics(3, 0xFF00FF90);
    }
    
    public void updateDirection() {
        if (animatedSprite != null) {
            animatedSprite.setAnimationRange(direction * 2, (direction * 3 + 2));
        }
    }

    //Call every time physically possible.
    public void render(RenderHandler render, int xZoom, int yZoom) {
        if(animatedSprite != null) {
            render.renderSprite(animatedSprite, characterRectangle.x, characterRectangle.y, xZoom, yZoom);
        } else if(sprite != null) {
            render.renderSprite(sprite, characterRectangle.x, characterRectangle.y, xZoom, yZoom);
        } else {
            render.renderRectangle(characterRectangle, xZoom, yZoom);
        }
    }
    
    public void update(Game game) {
    }
    
    public void updateCamera(Rectangle camera) {
    }
    
    public int getX() {
        return ((characterRectangle.x / (16*3 )));
    }
    
    public int getY(){
        return ((characterRectangle.y / (16*3 )));
    }
    
    
    
}