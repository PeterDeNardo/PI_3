/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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

public class Game extends JFrame implements Runnable {
	private Canvas canvas = new Canvas();
	private RenderHandler renderer;
        private BufferedImage testImage;

	public Game() {
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

                testImage = loadImage("Asets.png");
                
	}

	
	public void update() {

	}

        private BufferedImage loadImage(String path) {
            try {
                BufferedImage loadedImage = ImageIO.read(Game.class.getResource(path));
                BufferedImage formattedImage = new BufferedImage(loadedImage.getWidth(), loadedImage.getHeight(), BufferedImage.TYPE_INT_RGB);
                formattedImage.getGraphics().drawImage(loadedImage, 0, 0, null);
                
                return loadedImage;
            } 
            catch(IOException e) {
                e.printStackTrace();
                return null;
            }
        }

	public void render() {
            BufferStrategy bufferStrategy = canvas.getBufferStrategy();
            Graphics graphics = bufferStrategy.getDrawGraphics();
            super.paint(graphics);

            renderer.renderImage(testImage, 0, 0, 16, 16);
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

	public static void main(String[] args) {
		Game game = new Game();
		Thread gameThread = new Thread(game);
		gameThread.start();
	}

}