/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package tcc.ifes.edu.br.modelo;

import org.openimaj.image.feature.dense.gradient.dsift.ByteDSIFTKeypoint;
import org.openimaj.image.feature.local.keypoints.Keypoint;
import tcc.ifes.edu.br.dao.Model;

/**
 *
 * @author Bassini
 */
public class Ponto extends Model{
    
    private float x, y, scale, ori;
    private byte[] featureVector;
    private long imgId;

    public Ponto(){}
    
    public Ponto(long imgId, Keypoint keypoint) {
        this.imgId = imgId;
        
        this.x = keypoint.x;
        this.y = keypoint.y;
        this.scale = keypoint.scale;
        this.ori = keypoint.ori;
        this.featureVector = keypoint.ivec;
        
    }
    public Ponto(long imgId, ByteDSIFTKeypoint keypoint) {
        
        this.imgId = imgId;
        
        this.x = keypoint.x;
        this.y = keypoint.y;
        this.featureVector = keypoint.getFeatureVector().getVector();
        
    }
    
    

    public long getImgId() {
        return imgId;
    }

    public void setImgId(int imgId) {
        this.imgId = imgId;
    }
    

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getScale() {
        return scale;
    }

    public void setScale(float scale) {
        this.scale = scale;
    }

    public float getOri() {
        return ori;
    }

    public void setOri(float rotation) {
        this.ori = rotation;
    }

    public byte[] getFeatureVector() {
        return featureVector;
    }

    public void setFeatureVector(byte[] featureVector) {
        this.featureVector = featureVector;
    }
    
    
}
