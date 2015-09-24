/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package tcc.ifes.edu.br.control;

import java.io.File;
import java.io.IOException;
import org.openimaj.feature.local.list.LocalFeatureList;
import org.openimaj.image.ImageUtilities;
import org.openimaj.image.MBFImage;
import org.openimaj.image.feature.local.engine.asift.ASIFTEngine;
import org.openimaj.image.feature.local.keypoints.Keypoint;
import tcc.ifes.edu.br.dao.ImagemDAO;
import tcc.ifes.edu.br.modelo.Imagem;

/**
 *
 * @author Bassini
 */
public class ImageBusca {

    public void realizarBusca(Imagem entrada) {
        
        try{
            //MBFImage image = ImageUtilities.readMBF( new File(entrada.getPathImagem()) );
            
            ASIFTEngine engine = new ASIFTEngine();
            //LocalFeatureList<Keypoint> imageKeypoints = engine.findKeypoints(image.flatten());
            
            //entrada.setKeypoint(imageKeypoints);
            
            //String vetor = gerarVetorPontos(imageKeypoints);
            //int quantidade = imageKeypoints.size();
            
            ImagemDAO imagemDAO = new ImagemDAO();
            
            //imagemDAO.consultar(vetor, quantidade);
            
    
        }
        catch(Exception e){
            System.out.println("Falha na busca!");
        }
    }
    
    
    private String gerarVetorPontos(LocalFeatureList<Keypoint> lista){
        String vetor = "";
        vetor+="'{";
        System.out.print("'{");
        for(int i=0;i<lista.size();i++){
            
            String vt = gerarVetor(lista.get(i));
            
            System.out.print(vt);
            
            vetor += vt;
            if(i<lista.size()-1){
                vetor += ", ";
                System.out.println(", ");
            }
        
        }
        
        System.out.println("}'");
        vetor+="}'";
        
        return vetor;
    }
    
    private String gerarVetor(Keypoint obj){
        String vetor = "{ ";
        byte[] fv = obj.getFeatureVector().getVector();
                
        for(int i=0;i<fv.length;i++){
            int valor = fv[i];                  
            vetor += valor;

            if(i<fv.length-1){
                vetor += ", ";
            }
        }
        vetor+=" }";
        
        return vetor;
    }
    
    
}
