/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package tcc.ifes.edu.br.modelo;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import org.openimaj.feature.local.list.LocalFeatureList;
import org.openimaj.image.feature.local.keypoints.Keypoint;
import tcc.ifes.edu.br.dao.Model;
import tcc.ifes.edu.br.visao.Resizer;

/**
 *
 * @author Bassini
 */
public class Imagem extends Model{
    private static int LARGURA_ICON= 150, ALTURA_ICON=150;
    private String name;
    private String pathImagem;
    private String pathIcone;
    private LocalFeatureList<Keypoint> keypoint;
    
    public Imagem(){    
        
    }
    
    public Imagem(String name, String path){
        this.name = name;
        this.pathImagem = path.replace("\\" ,"/" );
        this.gerarImagemIcone(LARGURA_ICON, ALTURA_ICON);
        
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPathImagem() {
        return pathImagem;
    }

    public void setPathImagem(String path) {
        this.pathImagem = path.replace("\\" ,"/" );
        this.gerarImagemIcone(LARGURA_ICON, ALTURA_ICON);
    }

    public LocalFeatureList<Keypoint> getKeypoint() {
        return keypoint;
    }

    public void setKeypoint(LocalFeatureList<Keypoint> keypoint) {
        this.keypoint = keypoint;
    }
    
    public void gerarImagemIcone(int novaLargura, int novaAltura){
        try {
            
            String[] split = this.pathImagem.split("/");
            
            String pathIcon = "C:/ImagemTCC/Imagens Temporarias/temp_"+split[split.length-1];
            
            //System.out.println("-->pathIcon: "+pathIcon);
            this.setPathIcone(pathIcon);
            
            BufferedImage imagem = ImageIO.read(new File(this.pathImagem));
            BufferedImage new_img = new BufferedImage(novaLargura, novaAltura, BufferedImage.TYPE_INT_RGB);
            Graphics2D g = new_img.createGraphics();
            g.drawImage(imagem, 0, 0, novaLargura, novaAltura, null);

            ImageIO.write(new_img, "JPG", new File(pathIcon));
            
            
        } catch (IOException ex) {
            Logger.getLogger(Resizer.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    public String getPathIcone(){
        return this.pathIcone;
    }
    
    public void setPathIcone(String pathIcone){
        this.pathIcone = pathIcone;
    }

   
}
