
package pi_3;

public class Player implements GameObject{
    Rectangle playerRectangle;
    int speed = 10;

    //0 - Down, 1 - Left, 2 - Up, 3 Right
    private int direction = 0;
    private Sprite sprite;
    private AnimatedSprite animatedSprite = null;
    
    public Player(Sprite sprite) {
        
        this.sprite = sprite;
        
        if(sprite instanceof AnimatedSprite) {
            animatedSprite = (AnimatedSprite) sprite;
        }
        
        updateDirection();
        playerRectangle = new Rectangle(32, 16, 16, 32);
        playerRectangle.generateGraphics(3, 0xFF00FF90);
    }
    
    private void updateDirection() {
        if (animatedSprite != null) {
            animatedSprite.setAnimationRange(direction * 2, (direction * 3 + 2));
        }
    }

    //Call every time physically possible.
    public void render(RenderHandler render, int xZoom, int yZoom) {
        if(animatedSprite != null) {
            render.renderSprite(animatedSprite, playerRectangle.x, playerRectangle.y, xZoom, yZoom);
        } else if(sprite != null) {
            render.renderSprite(sprite, playerRectangle.x, playerRectangle.y, xZoom, yZoom);
        } else {
            render.renderRectangle(playerRectangle, xZoom, yZoom);
        }
    }
    
    public void update(Game game) {
        KeyBoardListener keyListener = game.getKeyListener();
        
        boolean didMove = false;
        int newDirection = 0;
        
        if(keyListener.up()) {
            newDirection = 2;
            playerRectangle.y -= speed;
            didMove = true;
        }
        if(keyListener.down())  {
            newDirection = 0;
            playerRectangle.y += speed;
            didMove = true;
        }
        if(keyListener.left()) {
            newDirection = 1;
            playerRectangle.x -= speed;
            didMove = true;
        }
        if(keyListener.right()) {
            newDirection = 3;
            playerRectangle.x += speed;
            didMove = true;
        }
        
        if (newDirection != direction) {
            direction = newDirection;
            updateDirection();
        }
        
        
        if(!didMove) {
            animatedSprite.reset();
        }
        updateCamera(game.getRender().getCamera());
        
        if (didMove) {
            animatedSprite.update(game); 
        }
    }
    
    public void updateCamera(Rectangle camera) {
        camera.x = playerRectangle.x - (camera.w / 2);
        camera.y = playerRectangle.y - (camera.h / 2);
    }
}