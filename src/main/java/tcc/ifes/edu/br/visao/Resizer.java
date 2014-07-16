/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package tcc.ifes.edu.br.visao;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;

/**
 *
 * @author Bassini
 */
public class Resizer {
    
    public static ImageIcon redimensionar(Image image, int width, int height, boolean aumentar){
        // Calculos necessários para manter as proporções da imagem, conhecido
        // como "aspect ratio"
        double imageWidth = (double) image.getWidth(null);
        double imageHeight = (double) image.getHeight(null);
        
        double thumbRatio = (double) width / (double) height;
        double imageRatio = imageWidth / imageHeight;
        
        if( imageWidth > width || imageHeight > height || aumentar){
            if (thumbRatio < imageRatio) {
                height = (int) (width / imageRatio);
            } else {
                width = (int) (height * imageRatio);
            }
        }
        else{
            width = image.getWidth(null);
            height = image.getHeight(null);
        }
        
        // Fim do cálculo
        
        BufferedImage thumbImage = new BufferedImage(width, height,
                BufferedImage.TYPE_INT_RGB);
        
        Graphics2D graphics2D = thumbImage.createGraphics();
        
        graphics2D.drawImage(image, 0, 0, width, height, null);
        return (new ImageIcon((Image)thumbImage));
    }

    
}
