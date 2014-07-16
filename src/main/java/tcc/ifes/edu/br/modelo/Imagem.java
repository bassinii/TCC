/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package tcc.ifes.edu.br.modelo;

import org.openimaj.feature.local.list.LocalFeatureList;
import org.openimaj.image.feature.local.keypoints.Keypoint;
import tcc.ifes.edu.br.dao.Model;

/**
 *
 * @author Bassini
 */
public class Imagem extends Model{
    
    private String name;
    private String path;
    private LocalFeatureList<Keypoint> keypoint;
    
    public Imagem(){    
        
    }
    
    public Imagem(String name, String path){
        this.name = name;
        this.path = path.replace("\\" ,"/" );
        
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path.replace("\\" ,"/" );
    }

    public LocalFeatureList<Keypoint> getKeypoint() {
        return keypoint;
    }

    public void setKeypoint(LocalFeatureList<Keypoint> keypoint) {
        this.keypoint = keypoint;
    }

    
}
