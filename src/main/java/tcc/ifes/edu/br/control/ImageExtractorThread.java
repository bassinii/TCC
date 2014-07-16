/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package tcc.ifes.edu.br.control;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;
import javax.swing.JProgressBar;
import org.apache.commons.math.util.MultidimensionalCounter;
import static org.netlib.lapack.Dlacon.i;
import org.openimaj.feature.local.list.LocalFeatureList;
import org.openimaj.image.ImageUtilities;
import org.openimaj.image.MBFImage;
import org.openimaj.image.feature.local.engine.asift.ASIFTEngine;
import org.openimaj.image.feature.local.keypoints.Keypoint;
import tcc.ifes.edu.br.dao.ImagemDAO;
import tcc.ifes.edu.br.dao.PontoDAO;
import tcc.ifes.edu.br.modelo.Imagem;
import tcc.ifes.edu.br.modelo.Ponto;

/**
 *
 * @author Bassini
 */
public class ImageExtractorThread extends Thread {

    private Imagem entrada;
    private JProgressBar barraProgresso;
    
    
    public ImageExtractorThread(Imagem entrada, JProgressBar jProgressBar){
        this.entrada = entrada;
        this.barraProgresso = jProgressBar;
    }
    
    
    public void run() {
        
        try{
            
            MBFImage image = ImageUtilities.readMBF( new File(entrada.getPath()) );
            
            barraProgresso.setValue(20);
            
            //DoGSIFTEngine engine = new DoGSIFTEngine();
            //LocalFeatureList<Keypoint> imageKeypoints = engine.findFeatures(image.flatten());
            
            ASIFTEngine engine = new ASIFTEngine();
            LocalFeatureList<Keypoint> imageKeypoints = engine.findKeypoints(image.flatten());
            
            barraProgresso.setValue(40);
            
            //ColourASIFTEngine engine = new ColourASIFTEngine();
            //LocalFeatureList<Keypoint> imageKeypoints = engine.findKeypoints(image);
            
            barraProgresso.setValue(50);
            
            entrada.setKeypoint(imageKeypoints);
            ImagemDAO imagemDAO = new ImagemDAO();
            imagemDAO.insert(entrada);
            barraProgresso.setValue(75);
            
            barraProgresso.setValue(100);
            
            System.out.println("imagem id: "+entrada.getId());
            
            barraProgresso.setValue(0);
            
            
            for(int i=0; i<imageKeypoints.size();i++){
                
                Keypoint point = imageKeypoints.get(i);
                
                Ponto ponto = new Ponto(entrada.getId(), point);
                
                PontoDAO pontoDAO = new PontoDAO();
                
                pontoDAO.insert(ponto);
                
                
                barraProgresso.setValue(((100*i)/imageKeypoints.size()));
                
                
            }
            barraProgresso.setValue(100);
            
            
        }
        catch(Exception e){
            System.out.println("Erro : ImageExtractorThread : "+e.getMessage());
        }
        List lista = new ArrayList();
        
        
    }
    
    
}
