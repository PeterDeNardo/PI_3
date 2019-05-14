/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pi_3;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;


public class RenderHandler {
	private BufferedImage view;
	private int[] pixels;

	public RenderHandler(int width, int height) {
		//Create a BufferedImage that will represent our view.
		view = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

		//Create an array for pixels
		pixels = ((DataBufferInt) view.getRaster().getDataBuffer()).getData();
                
//                for(int heigthIndex = 0; heigthIndex < height; heigthIndex++) {
//                    int randomPixel = (int)(Math.random() * 0xFFFFFF);
//
//                    for(int widthIndex = 0; widthIndex < width; widthIndex++ ){
//                        pixels[heigthIndex*width + widthIndex] = randomPixel;
//                    }
//                }
	}

        // Renderuza o array de pixels na tela
	public void render(Graphics graphics) {
		graphics.drawImage(view, 0, 0, view.getWidth(), view.getHeight(), null);
	}
        
        // Renderiza uma imagem e sua posição na tela
        public void renderImage(BufferedImage image, int xPosition, int yPosition, int xZoom, int yZoom) {
            int[] imagePixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
            
            for(int y = 0; y < image.getHeight(); y++) 
                for(int x = 0; x < image.getWidth(); x++) 
                    for(int yZoomPos = 0; yZoomPos < yZoom; yZoomPos++) 
                        for(int xZoomPos = 0; xZoomPos < xZoom; xZoomPos++) 
                             pixels[((x * xZoom) + xPosition + xZoomPos) + ((y * yZoom) + yPosition + yZoomPos) * view.getWidth()] = imagePixels[x + y * image.getWidth()];
                
            
        }

}