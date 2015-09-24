/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tcc.ifes.edu.br.control;

import java.awt.Color;
import java.awt.Rectangle;
import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JProgressBar;
import org.openimaj.feature.local.list.LocalFeatureList;
import org.openimaj.image.ImageUtilities;
import org.openimaj.image.MBFImage;
import org.openimaj.image.feature.dense.gradient.dsift.ByteDSIFTKeypoint;
import org.openimaj.image.feature.dense.gradient.dsift.DenseSIFT;
import org.openimaj.image.feature.local.affine.ASIFT;
import org.openimaj.image.feature.local.affine.BasicASIFT;
import org.openimaj.image.feature.local.engine.DoGSIFTEngine;
import org.openimaj.image.feature.local.engine.Engine;
import org.openimaj.image.feature.local.engine.asift.ASIFTEngine;
import org.openimaj.image.feature.local.keypoints.Keypoint;
import tcc.ifes.edu.br.dao.ImagemDAO;
import tcc.ifes.edu.br.dao.PontoASIFTDAO;
import tcc.ifes.edu.br.dao.PontoDSIFTDAO;
import tcc.ifes.edu.br.dao.PontoSIFTDAO;
import tcc.ifes.edu.br.modelo.Imagem;
import tcc.ifes.edu.br.modelo.Ponto;

/**
 *
 * @author Bassini
 */
public class FeaturesExtractorThread extends Thread {

    private List<Imagem> imagens;
    private JProgressBar barraProgresso;

    public FeaturesExtractorThread(List<Imagem> imagens, JProgressBar jProgressBar1) {
        this.barraProgresso = jProgressBar1;
        barraProgresso.setStringPainted(true);

        this.imagens = imagens;

    }

    public void run() {

        try {

            for (Imagem imagem : imagens) {

                MBFImage image = ImageUtilities.readMBF(imagem.getArquivo());

                barraProgresso.setValue(5);
                imagem.setKeypointSIFT(this.findSIFTFeatures(image));

                barraProgresso.setValue(30);
                imagem.setKeypointASIFT(this.findASIFTFeatures(image));

                barraProgresso.setValue(50);
                imagem.setKeypointDSIFT(this.findDSIFTFeatures(image));

                barraProgresso.setValue(70);
                ImagemDAO imagemDAO = new ImagemDAO();
                imagemDAO.openConnection();
                imagemDAO.insert(imagem);
                imagemDAO.closeConnection();
                barraProgresso.setValue(100);

                barraProgresso.setValue(0);
                barraProgresso.setForeground(Color.YELLOW);
                barraProgresso.setString("Salvando SIFT Keypoints...");
                salvarSIFTKeypoins(imagem);

                barraProgresso.setValue(0);
                barraProgresso.setForeground(Color.GREEN);
                barraProgresso.setString("Salvando ASIFT Keypoints...");
                salvarASIFTKeypoins(imagem);

                barraProgresso.setValue(0);
                barraProgresso.setForeground(Color.ORANGE);
                barraProgresso.setString("Salvando DSIFT Keypoints...");
                salvarDSIFTKeypoins(imagem);
                
                barraProgresso.setForeground(Color.RED);

                barraProgresso.setValue(100);
                barraProgresso.setString("Concluido!!");

            }

        } catch (Exception e) {
            System.out.println("Erro : ImageExtractorThread : " + e.getMessage());
        } catch (Throwable t) {
            System.out.println("Erro : ImageExtractorThread : " + t.getMessage());
        }

    }

    private LocalFeatureList<Keypoint> findSIFTFeatures(MBFImage image) {
        DoGSIFTEngine engineSIFT = new DoGSIFTEngine();
        return engineSIFT.findFeatures(image.flatten());
    }

    private LocalFeatureList<Keypoint> findASIFTFeatures(MBFImage image) {
        Engine engineASIFT = new ASIFTEngine();
        return engineASIFT.findFeatures(image.flatten());
    }

    private LocalFeatureList<ByteDSIFTKeypoint> findDSIFTFeatures(MBFImage image) {
        DenseSIFT engineDSIFT = new DenseSIFT();
        engineDSIFT.analyseImage(image.flatten());
        return engineDSIFT.getByteKeypoints();
    }

    

    private void salvarSIFTKeypoins(Imagem imagem) throws SQLException, ClassNotFoundException {

        LocalFeatureList<Keypoint> siftKeypoints = imagem.getKeypointSIFT();
        PontoSIFTDAO pontoSIFTDAO = new PontoSIFTDAO();
        pontoSIFTDAO.openConnection();

        for (int i = 0; i < siftKeypoints.size(); i++) {

            Keypoint featureVector = siftKeypoints.get(i);
            Ponto ponto = new Ponto(imagem.getId(), featureVector);
            pontoSIFTDAO.insert(ponto);

            barraProgresso.setValue(((100 * i) / siftKeypoints.size()));

        }
        pontoSIFTDAO.closeConnection();
    }

    private void salvarASIFTKeypoins(Imagem imagem) throws ClassNotFoundException, SQLException {
        LocalFeatureList<Keypoint> siftKeypoints = imagem.getKeypointASIFT();
        PontoASIFTDAO pontoASIFTDAO = new PontoASIFTDAO();
        pontoASIFTDAO.openConnection();

        for (int i = 0; i < siftKeypoints.size(); i++) {

            Keypoint featureVector = siftKeypoints.get(i);
            Ponto ponto = new Ponto(imagem.getId(), featureVector);
            pontoASIFTDAO.insert(ponto);

            barraProgresso.setValue(((100 * i) / siftKeypoints.size()));

        }
        pontoASIFTDAO.closeConnection();
    }

    private void salvarDSIFTKeypoins(Imagem imagem) throws SQLException, ClassNotFoundException {
        LocalFeatureList<ByteDSIFTKeypoint> dsiftKeypoints = imagem.getKeypointDSIFT();
        PontoDSIFTDAO pontoDSIFTDAO = new PontoDSIFTDAO();
        pontoDSIFTDAO.openConnection();

        for (int i = 0; i < dsiftKeypoints.size(); i++) {

            ByteDSIFTKeypoint featureVector = dsiftKeypoints.get(i);
            Ponto ponto = new Ponto(imagem.getId(), featureVector);
            pontoDSIFTDAO.insert(ponto);

            barraProgresso.setValue(((100 * i) / dsiftKeypoints.size()));

        }
        pontoDSIFTDAO.closeConnection();
    }

}
