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
	private Rectangle camera;
        private int[] pixels;

	public RenderHandler(int width, int height) {
            //Create a BufferedImage that will represent our view.
            view = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

            camera = new Rectangle(0, 0, width, height);

            //Create an array for pixels
            pixels = ((DataBufferInt) view.getRaster().getDataBuffer()).getData();
                
        }
        
        // Renderuza o array de pixels na tela
	public void render(Graphics graphics) {
            graphics.drawImage(view, 0, 0, view.getWidth(), view.getHeight(), null);
	}
        
        // Renderiza uma imagem e sua posição na tela
        public void renderImage(BufferedImage image, int xPosition, int yPosition, int xZoom, int yZoom) {
            int[] imagePixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
            renderArray(imagePixels, image.getWidth(), image.getHeight(), xPosition, yPosition, xZoom, yZoom);
        }
        
        public void renderRectangle(Rectangle rec, int xZoom, int yZoom) {
            int[] recPixels = rec.getPixels();
            if(recPixels != null) {
                renderArray(recPixels, rec.width, rec.height, rec.x, rec.y, xZoom, yZoom);
            }
        }
        
        public void renderArray(int[] renderPixels, int renderWidth, int renderHeight, int xPosition, int yPosition, int xZoom, int yZoom) {
            for(int y = 0; y < renderHeight; y++) 
                for(int x = 0; x < renderWidth; x++) 
                    for(int yZoomPos = 0; yZoomPos < yZoom; yZoomPos++) 
                        for(int xZoomPos = 0; xZoomPos < xZoom; xZoomPos++) 
                             setPixel(renderPixels[x + y * renderWidth], (x*xZoom) + xPosition + xZoomPos, ((y * yZoom) + yPosition + yZoomPos));
  
        }
        
        private void setPixel(int pixel, int x, int y){
            if (x >= camera.x && y >= camera.y && x <= camera.x + camera.width && y <= camera.y + camera.height ) {
                int pixelIndex = x + y * view.getWidth();
                if(pixels.length > pixelIndex)
                    pixels[pixelIndex] = pixel;
           }
        }

}