package tcc.ifes.edu.br.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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
                                "       icone lo," +
                                "       PRIMARY KEY (id)" +
                                ");";
		
		this.execute(sql);
		
		this.closeConnection();
	}

	public void insert(Imagem obj) throws SQLException, ClassNotFoundException {
		
            this.openConnection();

            //System.out.println("Figura: "+obj.getPathImagem());
            //System.out.println("Icone: "+obj.getPathIcone());

            String sql =    "INSERT INTO imagem(nome, figura, icone) "
                    +       "VALUES ('"+ obj.getName()+"',"
                    +       "lo_import( '"+obj.getPathImagem()+"'),"
                    +       "lo_import( '"+obj.getPathIcone()+"')); "; 

            int id = this.executeUpdate(sql);

            obj.setId(id);

            this.closeConnection();
		
	}
        
        public void consultar(String vetor, int tamanho) throws SQLException, ClassNotFoundException{
            /*
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
                    
            */
        }

    public List<Imagem> findAll() throws SQLException, ClassNotFoundException {
        
        List<Imagem> lista = new ArrayList<Imagem>();
        
        this.openConnection();
            
        String sql = "SELECT id, nome FROM imagem;";
            
        ResultSet result = this.executeQuery(sql);
            
        while(result.next()){

            Imagem imagem = new Imagem();

            imagem.setId(result.getLong("id"));
            imagem.setName(result.getString("nome"));

            String pathIcone  = "C:/ImagemTCC/Imagens Temporarias/TEMP_ICONE/temp_icone_"+imagem.getName()+".jpg";
            String pathFigura = "C:/ImagemTCC/Imagens Temporarias/TEMP_FIGURA/temp_figura_"+imagem.getName()+".jpg";

            imagem.setPathImagem(pathFigura);
            imagem.setPathIcone(pathIcone);

            //exportando o icone da imagem para uma pasta temporaria
            String query =    "SELECT lo_export(icone,'"+pathIcone+"')"
                            + "FROM imagem WHERE id="+imagem.getId()+" ;";
            this.executeQuery(query);

            //exportando o figura da imagem para uma pasta temporaria
            query =      "SELECT lo_export(figura,'"+pathFigura+"')"
                       + "FROM imagem WHERE id="+imagem.getId()+" ;";
            this.executeQuery(query);

            lista.add(imagem);
        }
            
        this.closeConnection();
        
        return lista;
    }
        
        


}
