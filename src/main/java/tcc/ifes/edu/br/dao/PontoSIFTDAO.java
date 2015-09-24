package tcc.ifes.edu.br.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import tcc.ifes.edu.br.modelo.Ponto;


public class PontoSIFTDAO extends DAOGeneric implements DAO<Ponto> {

	public Ponto create() {
		
		return new Ponto();
	}
	//CRIANDO A TABELA USUARIO
	public void criarTabela() throws ClassNotFoundException, SQLException
	{
		this.createStatement();
		String sql =    "CREATE TABLE pontoSIFT(" +
                            "   id SERIAL,\n" +
                            "	imgid INTEGER NOT NULL," +
                            "	ori FLOAT," +
                            "	scale FLOAT," +
                            "	x FLOAT," +
                            "	y FLOAT," +
                            "	featurevector SMALLINT[]," +
                            "	PRIMARY KEY (id)" +
                            "   );";
		
		this.execute(sql);
		
		this.closeStetatement();
	}

	public void insert(Ponto obj) throws SQLException, ClassNotFoundException {
		
		this.createStatement();
                
                String vetor = "'{ ";
                byte[] fv = obj.getFeatureVector();
                
                for(int i=0;i<fv.length;i++){
                    int valor = fv[i];                  
                    vetor += valor;
                    
                    if(i<fv.length-1){
                        vetor += ", ";
                    }
                }
                vetor+=" }'";
        
                String sql = "  INSERT INTO pontoSIFT(imgid, x, y, ori, scale, featurevector) "
                        +   "   VALUES ("
                        +   obj.getImgId()
                        +   ","+obj.getX()
                        +   ","+obj.getY()
                        +   ","+obj.getOri()
                        +   ","+obj.getScale()
                        +   ","+vetor
                        +   "); "; 
		
		int id = this.executeUpdate(sql);
		
		obj.setId(id);
		
		this.closeStetatement();
		
	}

    public List<Ponto> findAll() throws SQLException, ClassNotFoundException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }


}
