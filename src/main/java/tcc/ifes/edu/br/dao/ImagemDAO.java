package tcc.ifes.edu.br.dao;

import java.io.File;
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

    public void criarTabela() throws ClassNotFoundException, SQLException {
        this.createStatement();

        String sql = "CREATE TABLE imagem ( "
                + "	id SERIAL,"
                + "	nome varchar(30) NOT NULL,"
                + "       figura lo,"
                + "       icone lo,"
                + "       PRIMARY KEY (id)"
                + ");";

        this.execute(sql);
        this.closeStetatement();
    }

    public void insert(Imagem obj) throws SQLException, ClassNotFoundException {

        this.createStatement();

        String sql = "INSERT INTO imagem(nome, figura, icone) "
                + "VALUES ('" + obj.getName() + "',"
                + "lo_import( '" + obj.getArquivo().getAbsolutePath() + "'),"
                + "lo_import( '" + obj.getIcone().getAbsolutePath() + "')); ";

        int id = this.executeUpdate(sql);

        obj.setId(id);

        this.closeStetatement();
    }

    public List<String> consultar(List<String> matriz, String tipo) throws SQLException, ClassNotFoundException {

        List<String> lista = new ArrayList<String>();
        this.openConnection();
        this.createStatement();

        String temp = "CREATE TEMP TABLE tabelaTemp(id SERIAL, featurevector SMALLINT[])";

        this.execute(temp);

        for (String vet : matriz) {

            String insert = "INSERT INTO tabelaTemp(featurevector) values('" + vet + "')";
            this.execute(insert);
        }

        String sql = "SELECT * FROM consultarImagem"+tipo+"() as (imagem integer, quantidade bigint, distancia decimal);";

        System.out.println("iniciando busca!");

        ResultSet result = this.executeQuery(sql);

        System.out.println("Fim da busca.");

            //System.out.println("ID /t Quantidade /t Distancia");
        boolean flag = result.next();

        while (flag) {

            //System.out.println(result.getString(1)+" /t "+result.getString(2)+" /t"+ result.getNString(3));
            String linha = result.getString(1) + ", " + result.getString(2) + ", " + result.getString(3);

            lista.add(linha);

            flag = result.next();

            if (!flag) {
                System.out.println("fim!");
            }
        }

        this.closeConnection();

        return lista;
    }

    public List<Imagem> findAll() throws SQLException, ClassNotFoundException {

        List<Imagem> lista = new ArrayList<Imagem>();

        this.createStatement();
        String sql = "SELECT id, nome FROM imagem;";

        ResultSet result = this.executeQuery(sql);

        while (result.next()) {

            Imagem imagem = new Imagem();

            imagem.setId(result.getLong("id"));
            imagem.setName(result.getString("nome"));

            lista.add(imagem);
        }

        for (Imagem imagem : lista) {
            String query;
            String caminhoIcone = "E:/IFES/TCC/Imagens TCC/Imagens Temporarias/temp_icone_";
            String caminhoFigura = "E:/IFES/TCC/Imagens TCC/Imagens Temporarias/temp_figura_";

            long id = imagem.getId();
            String expIcone = caminhoIcone + imagem.getName() + ".jpg";
            String expFigura = caminhoFigura + imagem.getName() + ".jpg";

            //exportando o icone da imagem para uma pasta temporaria
            query = "SELECT lo_export(icone,'" + expIcone + "')"
                    + "FROM imagem WHERE id=" + id + " ;";
            this.execute(query);

            //exportando o figura da imagem para uma pasta temporaria
            query = "SELECT lo_export(figura,'" + expFigura + "') "
                    + "FROM imagem WHERE id=" + id + " ;";
            this.execute(query);

            imagem.setArquivo(new File(expFigura));
            imagem.setIcone(new File(expIcone));

        }

        this.closeStetatement();
        return lista;
    }

}
