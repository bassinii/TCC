/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package tcc.ifes.edu.br.control;

import java.io.File;
import java.util.List;
import org.openimaj.feature.local.list.LocalFeatureList;
import org.openimaj.feature.local.matcher.BasicMatcher;
import org.openimaj.feature.local.matcher.LocalFeatureMatcher;
import org.openimaj.feature.local.matcher.MatchingUtilities;
import org.openimaj.feature.local.matcher.consistent.ConsistentLocalFeatureMatcher2d;
import org.openimaj.image.DisplayUtilities;
import org.openimaj.image.ImageUtilities;
import org.openimaj.image.MBFImage;
import org.openimaj.image.colour.RGBColour;
import org.openimaj.image.feature.local.engine.asift.ASIFTEngine;
import org.openimaj.image.feature.local.keypoints.Keypoint;
import org.openimaj.math.geometry.point.Point2d;
import org.openimaj.math.geometry.transforms.AffineTransformModel;
import org.openimaj.math.model.fit.RANSAC;
import org.openimaj.util.pair.Pair;

/**
 *
 * @author Bassini
 */
public class ImageMatcherThread extends Thread{
    List<Imagem2> imagens;
    String queryName;

    public ImageMatcherThread(List<Imagem2> imagens, String queryName) {
        
        this.imagens = imagens;
        this.queryName = queryName;
    }
    
    public ImageMatcherThread(ThreadGroup group,List<Imagem2> imagens, String queryName) {
        super(group,"Matcher Imagem "+queryName);
        this.imagens = imagens;
        this.queryName = queryName;
    }
    

    public void run() {
        try{
            int maxMatchers =0;
            
            MBFImage query = ImageUtilities.readMBF(new File("imagens/"+queryName+".jpg"));
    
            
            ASIFTEngine engine = new ASIFTEngine();
            LocalFeatureList<Keypoint> queryKeypoints = engine.findKeypoints(query.flatten());
            
            Imagem2 winner= new Imagem2();
            List<Pair<Keypoint>> winnerListPair = null;
            
            
            for(int i=0;i<imagens.size();i++){
                
                LocalFeatureList<Keypoint> targetKeypoints = imagens.get(i).getKeypoint();    
                List<Pair<Keypoint>> listPairKeypoints= matcher(queryKeypoints, targetKeypoints );
                if(listPairKeypoints.size()>maxMatchers){
                    maxMatchers = listPairKeypoints.size();
                    winnerListPair = listPairKeypoints;
                    winner = imagens.get(i);
                    
                }
            }
            
            MBFImage target = ImageUtilities.readMBF( new File("imagens/"+winner.getImgName()+".jpg") );

            plotar(query, target, winnerListPair);
        
        }
        catch(Exception e){
            System.out.println("Erro: "+e.getMessage());
        }
    }
    
    private List<Pair<Keypoint>> matcher(LocalFeatureList<Keypoint> queryKeypoints, LocalFeatureList<Keypoint> targetKeypoints){
        
        AffineTransformModel fittingModel = new AffineTransformModel(5);
            
        RANSAC<Point2d, Point2d> ransac =
            new RANSAC<Point2d, Point2d>(fittingModel, 150,
            new RANSAC.PercentageInliersStoppingCondition(0.99), true);
            
        LocalFeatureMatcher<Keypoint> matcher = 
        new ConsistentLocalFeatureMatcher2d<Keypoint>(
        new BasicMatcher<Keypoint>(8), ransac);
        
        
        
        //LocalFeatureMatcher<Keypoint> matcher = new BasicTwoWayMatcher();
        matcher.setModelFeatures(queryKeypoints);
        matcher.findMatches(targetKeypoints);
            
        return matcher.getMatches();
        
        
    }
    
    private void plotar(MBFImage query, MBFImage target, List<Pair<Keypoint>> pairs ) {
        //AffineTransformModel fittingModel = new AffineTransformModel(5);
        //target.drawShape(query.getBounds().transform(fittingModel.getTransform().inverse()), 3,RGBColour.BLUE);
        MBFImage consistentMatches = MatchingUtilities.drawMatches(query, target, pairs, RGBColour.RED);
        DisplayUtilities.display(consistentMatches);
    }
    
    
}
