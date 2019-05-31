
package pi_3;

public class Player extends Character implements GameObject{
    int speed = 10;

    //0 - Down, 1 - Left, 2 - Up, 3 Right
    private int direction = 0;
    private Sprite sprite;
    
    
    public Player(Sprite sprite) {
        super(sprite);
        
    }
    
    @Override
    public void update(Game game) {
        KeyBoardListener keyListener = game.getKeyListener();
        
        boolean didMove = false;
        int newDirection = 0;
        
        if(keyListener.up()) {
            newDirection = 2;
            characterRectangle.y -= speed;
            didMove = true;
        }
        if(keyListener.down())  {
            newDirection = 0;
            characterRectangle.y += speed;
            didMove = true;
        }
        if(keyListener.left()) {
            newDirection = 1;
            characterRectangle.x -= speed;
            didMove = true;
        }
        if(keyListener.right()) {
            newDirection = 3;
            characterRectangle.x += speed;
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
            System.out.println(getX() + " " + getY());
            animatedSprite.update(game); 
        }
    }
    
    @Override
    public void updateCamera(Rectangle camera) {
        camera.x = characterRectangle.x - (camera.w / 2);
        camera.y = characterRectangle.y - (camera.h / 2);
    }
}