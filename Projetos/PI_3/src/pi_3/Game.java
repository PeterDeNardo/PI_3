package pi_3;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.Graphics;

import java.lang.Runnable;
import java.lang.Thread;

import javax.swing.JFrame;

import javax.imageio.ImageIO;

import java.io.IOException;
import java.io.File;

public class Game extends JFrame implements Runnable {

    public static int alpha = 0xFFACC58B;

    private Canvas canvas = new Canvas();
    private static RenderHandler renderer;
    
    private SpriteSheet sheet;
    private SpriteSheet playerSheet;
    private SpriteSheet enemySheet;

    private Rectangle testRectangle = new Rectangle(30, 30, 100, 100);

    private Tiles tiles;
    private Map map;
    
    private KeyBoardListener keyListener = new KeyBoardListener(this);
    private MouseEventListener mouseListener = new MouseEventListener(this);
    
    private GameObject objects[];
    
    private Player player;
    private Enemy enemy;
    
    private int xZoom = 3;
    private int yZoom = 3;

    public Game() throws IOException{
        //Make our program shutdown when we exit out.
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Set the position and size of our frame.
        setBounds(0,0, 1000, 800);

        //Put our frame in the center of the screen.
        setLocationRelativeTo(null);

        //Add our graphics compoent
        add(canvas);

        //Make our frame visible.
        setVisible(true);

        //Create our object for buffer strategy.
        canvas.createBufferStrategy(3);

        renderer = new RenderHandler(getWidth(), getHeight());

        //Load Assets;
        File file = new File("Asets.png");
        String path = file.getPath();
        BufferedImage sheetImage = loadImage(path);

        sheet = new SpriteSheet(sheetImage);
        sheet.loadSprites(16, 16);
        
        File file2 = new File("Sprites.png");
        String path2 = file2.getPath();
        BufferedImage playerSheetImage = loadImage(path2);
        
        playerSheet = new SpriteSheet(playerSheetImage);
        playerSheet.loadSprites(16, 16);
        
        File file3 = new File("Sprites.png");
        String path3 = file2.getPath();
        BufferedImage enemySheetImage = loadImage(path2);
        
        enemySheet = new SpriteSheet(enemySheetImage);
        enemySheet.loadSprites(16, 16);
        
        
        //Player Animated Sprites
        AnimatedSprite playerAnimations = new AnimatedSprite(playerSheet, 10);
        AnimatedSprite enemyAnimations = new AnimatedSprite(enemySheet, 10);

        // Load Map and Tiles
        File testFile = new File("./src/pi_3/Tiles.txt");
        if(testFile.createNewFile()){
            System.out.println("yeeey");
        } else {
            System.out.println("Deu certo carregar o Tiles.txt");
        }
        //tiles = new Tiles(new File("Tilses.txt"), sheet);
        tiles = new Tiles(testFile, sheet);

        map = new Map(new File("./src/pi_3/Map.txt"),tiles);
        //Objetos
        objects = new GameObject[2];
        player = new Player(playerAnimations);
        enemy = new Enemy(enemyAnimations);
        objects[0] = player;
        objects[1] = enemy;
        //Add Listeners
        canvas.addKeyListener(keyListener);
        canvas.addFocusListener(keyListener);
        canvas.addMouseListener(mouseListener);
        canvas.addMouseMotionListener(mouseListener);
    }


    public void update(){
        for (int i = 0; i < objects.length; i++) {
            objects[i].update(this);
        }
    }


    private BufferedImage loadImage(String path) {
        try {
            BufferedImage loadedImage = ImageIO.read(Game.class.getResource(path));
            BufferedImage formattedImage = new BufferedImage(loadedImage.getWidth(), loadedImage.getHeight(), BufferedImage.TYPE_INT_RGB);
            formattedImage.getGraphics().drawImage(loadedImage, 0, 0, null);

            return formattedImage;
        }
        catch(IOException exception) {
            exception.printStackTrace();
            return null;
        }
    }

    public void handleCTRL(boolean[] keys) {
        if(keys[KeyEvent.VK_S]){
            map.saveMap();
        }
    }
    
    public void leftClick(int x, int y) {
        x = (int) Math.floor((x + renderer.getCamera().x)/(16.0 * xZoom));
        y = (int) Math.floor((y + renderer.getCamera().y)/(16.0 * yZoom));
        map.setTile(x, y, 2);
    }
    
    public void rightClick(int x, int y) {
        x = (int) Math.floor((x + renderer.getCamera().x)/(16.0 * xZoom));
        y = (int) Math.floor((y + renderer.getCamera().y)/(16.0 * yZoom));
        map.removeTile(x, y);
    }
    
    public void render() {
        BufferStrategy bufferStrategy = canvas.getBufferStrategy();
        Graphics graphics = bufferStrategy.getDrawGraphics();
        super.paint(graphics);
 
        map.render(renderer, xZoom, yZoom);
        
        for (int i = 0; i < objects.length; i++) {
            objects[i].render(renderer, xZoom, yZoom);
        }
  
        renderer.render(graphics);

        graphics.dispose();
        bufferStrategy.show();
        renderer.clear();
    }

    public void run() {
        BufferStrategy bufferStrategy = canvas.getBufferStrategy();
        int i = 0;
        int x = 0;

        long lastTime = System.nanoTime(); //long 2^63
        double nanoSecondConversion = 1000000000.0 / 60; //60 frames per second
        double changeInSeconds = 0;

        while(true) {
            long now = System.nanoTime();

            changeInSeconds += (now - lastTime) / nanoSecondConversion;
            while(changeInSeconds >= 1) {
                    update();
                    changeInSeconds--;
            }
            render();
            lastTime = now;
            
            //Clear the view
            renderer.clear();
        }

    }

    public static void main(String[] args) throws IOException {
        Game game = new Game();
        Thread gameThread = new Thread(game);
        gameThread.start();
    }

    public KeyBoardListener getKeyListener() {
        return keyListener;
    }
    
    public MouseEventListener getMouseListener() {
        return mouseListener;
    }
    
    public static RenderHandler getRender() {
        return renderer;
    }
    
    public int[] getPlayerPositon() {
        int[] pos = {player.getX(), player.getY()};
        return pos;
    }
}