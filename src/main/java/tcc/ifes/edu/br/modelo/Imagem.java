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
import javax.swing.Icon;
import javax.swing.ImageIcon;
import org.openimaj.feature.local.list.LocalFeatureList;
import org.openimaj.image.feature.dense.gradient.dsift.ByteDSIFTKeypoint;
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
    private File arquivo;
    private File icone;
    private LocalFeatureList<Keypoint> keypointASIFT;
    private LocalFeatureList<Keypoint> keypointSIFT;
    private LocalFeatureList<ByteDSIFTKeypoint> keypointDSIFT;
    
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    

    public void gerarImagemIcone(){
        this.gerarImagemIcone(LARGURA_ICON, ALTURA_ICON);
    }
    
    private void gerarImagemIcone(int novaLargura, int novaAltura){
        try {
            
            String caminho = this.arquivo.getAbsolutePath().replace("\\", "/");
            System.out.println("caminho: "+caminho);
            String[] split = caminho.split("/");
            
            String caminhoIcone = "";
                       
            for(int i=0;i<split.length-1;i++){
                caminhoIcone += split[i]+"/";
            }
            caminhoIcone = caminhoIcone+"icone_"+split[split.length-1];
            System.out.println("caminho icone: "+caminhoIcone);
            
            BufferedImage imagem = ImageIO.read(arquivo);
            BufferedImage new_img = new BufferedImage(novaLargura, novaAltura, BufferedImage.TYPE_INT_RGB);
            Graphics2D g = new_img.createGraphics();
            g.drawImage(imagem, 0, 0, novaLargura, novaAltura, null);

            ImageIO.write(new_img, "JPG", new File(caminhoIcone));
            
            this.icone = new File(caminhoIcone);
            
        } catch (IOException ex) {
            Logger.getLogger(Resizer.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    

    public LocalFeatureList<Keypoint> getKeypointASIFT() {
        return keypointASIFT;
    }

    public void setKeypointASIFT(LocalFeatureList<Keypoint> keypointASIFT) {
        this.keypointASIFT = keypointASIFT;
    }

    public LocalFeatureList<Keypoint> getKeypointSIFT() {
        return keypointSIFT;
    }

    public void setKeypointSIFT(LocalFeatureList<Keypoint> keypointSIFT) {
        this.keypointSIFT = keypointSIFT;
    }

    public LocalFeatureList<ByteDSIFTKeypoint> getKeypointDSIFT() {
        return keypointDSIFT;
    }

    public void setKeypointDSIFT(LocalFeatureList<ByteDSIFTKeypoint> keypointDSIFT) {
        this.keypointDSIFT = keypointDSIFT;
    }

    public File getArquivo() {
        return arquivo;
    }

    public void setArquivo(File arquivo) {
        this.arquivo = arquivo;
    }

    public File getIcone() {
        return icone;
    }

    public void setIcone(File icone) {
        this.icone = icone;
    }
    
    

   
}
