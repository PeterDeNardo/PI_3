package pi_3;

import java.awt.Canvas;
import java.awt.Color;
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
	private RenderHandler renderer;
	private BufferedImage testImage;
        
	private Sprite testSprite;
	private SpriteSheet sheet;
        
	private Rectangle testRectangle = new Rectangle(30, 30, 100, 100);
        
        private Tiles tiles;
        private Map map;

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
                
                File testFile = new File("./src/pi_3/Tiles.txt");
                if(testFile.createNewFile()){
                    System.out.println("yeeey");
                } else {
                    System.out.println("Deu certo carregar o Tiles.txt");
            }
                //tiles = new Tiles(new File("Tilses.txt"), sheet);
                tiles = new Tiles(testFile, sheet);
                
                map = new Map(new File("./src/pi_3/Map.txt"),tiles);
                
                //testSprite = sheet.getSprite(1,4);
                
           
		testRectangle.generateGraphics(2, 12234);
	}

	
	public void update(){

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


	public void render() {
			BufferStrategy bufferStrategy = canvas.getBufferStrategy();
			Graphics graphics = bufferStrategy.getDrawGraphics();
			super.paint(graphics);

			//renderer.renderSprite(testSprite, 0, 0, 5, 5);
                        //tiles.RenderTile(1, renderer, 0, 0, 3, 3);
                        map.render(renderer, 3, 3);
			renderer.renderRectangle(testRectangle, 1, 1);
			renderer.render(graphics);

			graphics.dispose();
			bufferStrategy.show();
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
		}

	}

	public static void main(String[] args) throws IOException {
		Game game = new Game();
		Thread gameThread = new Thread(game);
		gameThread.start();
	}

}