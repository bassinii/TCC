/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package tcc.ifes.edu.br.control;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import org.openimaj.feature.ByteFV;
import org.openimaj.feature.local.list.LocalFeatureList;
import org.openimaj.image.ImageUtilities;
import org.openimaj.image.MBFImage;
import org.openimaj.image.feature.dense.gradient.dsift.ByteDSIFTKeypoint;
import org.openimaj.image.feature.dense.gradient.dsift.DenseSIFT;
import org.openimaj.image.feature.local.engine.DoGSIFTEngine;
import org.openimaj.image.feature.local.engine.asift.ASIFTEngine;
import org.openimaj.image.feature.local.keypoints.Keypoint;
import tcc.ifes.edu.br.dao.ImagemDAO;
import tcc.ifes.edu.br.util.ManipuladorArquivo;

/**
 *
 * @author Bassini
 */
public class ImagemBusca {

    public void realizarBusca(File[] arquivos) {
        
        try{
            
            for(File arquivo : arquivos){
                List<String> listaSIFT, listaASIFT, listaDSIFT;
                
                ImagemDAO imagemDAO = new ImagemDAO();
                
                MBFImage image = ImageUtilities.readMBF( arquivo );
                
                System.out.println("extraindo SIFT features...");
                
                DoGSIFTEngine engineSIFT = new DoGSIFTEngine();
                LocalFeatureList<Keypoint> imageKeypointsSIFT = engineSIFT.findFeatures(image.flatten());
                List<String> vetorSIFT = this.gerarListaVetores(imageKeypointsSIFT);
                
                System.out.println("realizando busca SIFT...");
                
                listaSIFT = imagemDAO.consultar(vetorSIFT,"SIFT");
                System.out.println("Busca SIFT finalizada.");
                
                
                System.out.println("extraindo ASIFT features...");
                ASIFTEngine engineASIFT = new ASIFTEngine();
                LocalFeatureList<Keypoint> imageKeypointsASIFT = engineASIFT.findKeypoints(image.flatten());
                List<String> vetorASIFT = gerarListaVetores(imageKeypointsASIFT);
                
                System.out.println("realizando busca ASIFT...");
                
                listaASIFT = imagemDAO.consultar(vetorASIFT,"ASIFT");
                
                System.out.println("Busca ASIFT finalizada");
                
                
                System.out.println("extraindo DSIFT features...");
                
                DenseSIFT engineDSIFT = new DenseSIFT();
                engineDSIFT.analyseImage(image.flatten());
                LocalFeatureList<ByteDSIFTKeypoint>  imageKeypointsDSIFT = engineDSIFT.getByteKeypoints();;
                List<String> vetorDSIFT = gerarListaVetoresDSIFT(imageKeypointsDSIFT);
                
                System.out.println("realizando busca DSIFT...");
                
                listaDSIFT = imagemDAO.consultar(vetorDSIFT,"DSIFT");
                
                System.out.println("Busca DSIFT finalizada");
                
                
                File resultadoFolder  = new File("resultado");
                if(!resultadoFolder.exists())
                    resultadoFolder.mkdir();
                
                String resultPath = resultadoFolder+ File.separator +arquivo.getName()+"_resultado.txt";
                
                ManipuladorArquivo manipulador = new ManipuladorArquivo();
                manipulador.IniciarEscritaArquivoNovo(new File(resultPath));
                
                manipulador.escreverLinha("------------------------------------------------");
                manipulador.escreverLinha("Resultado SIFT");
                manipulador.escreverLinha("Imagem, Quantidade, Distancia");
                for(int i=0;i<listaSIFT.size() && i<5; i++){
                    String linha = listaSIFT.get(i);
                    manipulador.escreverLinha(linha);
                }
                
                manipulador.escreverLinha("");
                manipulador.escreverLinha("------------------------------------------------");
                manipulador.escreverLinha("Resultado ASIFT");
                manipulador.escreverLinha("Imagem, Quantidade, Distancia");
                for(int i=0;i<listaASIFT.size() && i<5; i++){
                    String linha = listaASIFT.get(i);
                    manipulador.escreverLinha(linha);
                }
                manipulador.escreverLinha("");
                manipulador.escreverLinha("------------------------------------------------");
                manipulador.escreverLinha("Resultado DSIFT");
                manipulador.escreverLinha("Imagem, Quantidade, Distancia");
                for(int i=0;i<listaDSIFT.size() && i<5; i++){
                    String linha = listaDSIFT.get(i);
                    manipulador.escreverLinha(linha);
                }
                manipulador.escreverLinha("------------------------------------------------");
                
                
            }
    
        }
        catch(Exception e){
            System.out.println("Falha na busca!"+e.getLocalizedMessage());
        }
    }
    
    
    private String gerarVetor(ByteFV obj){
        String vetor = "{ ";
        byte[] fv = obj.getVector();
                
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
    
    private List<String> gerarListaVetores(LocalFeatureList<Keypoint> vetores){
        
        List<String> lista = new ArrayList<String>();
        
        for(int i=0;i<vetores.size();i++){
            
            String vt = gerarVetor(vetores.get(i).getFeatureVector());
            //System.out.println("vetor: "+vt);
            lista.add(vt);
        }
        
        //System.out.println("}'");
        
        return lista;
    }
    
    private List<String> gerarListaVetoresDSIFT(LocalFeatureList<ByteDSIFTKeypoint> vetores){
        
        List<String> lista = new ArrayList<String>();
        
        for(int i=0;i<vetores.size();i++){
            
            String vt = gerarVetor(vetores.get(i).getFeatureVector());
            //System.out.println("vetor: "+vt);
            lista.add(vt);
        }
        
        //System.out.println("}'");
        
        return lista;
    }
}
