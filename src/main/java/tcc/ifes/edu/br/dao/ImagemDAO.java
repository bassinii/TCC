package tcc.ifes.edu.br.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import tcc.ifes.edu.br.modelo.Imagem;


public class ImagemDAO extends DAOGeneric implements DAO<Imagem> {

	public Imagem create() {
		
		return new Imagem();
	}
	//CRIANDO A TABELA USUARIO
	public void criarTabela() throws ClassNotFoundException, SQLException
	{
		this.openConnection();
		
		String sql =    "CREATE TABLE imagem ( " +
                                "	id SERIAL," +
                                "	nome varchar(30) NOT NULL," +
                                "       figura lo," +
                                "       PRIMARY KEY (id)" +
                                ");";
		
		this.execute(sql);
		
		this.closeConnection();
	}

	public void insert(Imagem obj) throws SQLException, ClassNotFoundException {
		
		this.openConnection();
		
		String sql = "INSERT INTO imagem(nome, figura) VALUES ('"+ obj.getName()+"', lo_import( '"+obj.getPath()+"' )); "; 
		
		int id = this.executeUpdate(sql);
		
		obj.setId(id);
		
		this.closeConnection();
		
	}
        
        public void consultar(String vetor, int tamanho) throws SQLException, ClassNotFoundException{
            this.openConnection();
            
            String sql = "SELECT imageMatcher("+ vetor+","+ tamanho +")";
            
            System.out.println("iniciando busca!");
            System.out.println("Tamanho: "+tamanho);
            
            ResultSet result = this.executeQuery(sql);
            System.out.println("Fim da busca.");
            
            System.out.println("ID /t Quantidade");
            
            boolean flag = result.next();
            while(flag){
                System.out.println(result.getString(1)+" /t "+result.getString(2));
                flag = result.next();
                
                if(!flag){
                    System.out.println("fim!");
                }
            }
            
            this.closeConnection();
        }


}
