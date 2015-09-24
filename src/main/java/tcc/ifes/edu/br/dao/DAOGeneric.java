package tcc.ifes.edu.br.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class DAOGeneric{
	
	private Connection con;
	private Statement statement;
        
	public void openConnection() throws SQLException, ClassNotFoundException
	{
		 
		Class.forName("org.postgresql.Driver");
		
		String url = "jdbc:postgresql://localhost:5432/tcc";
		Properties properties = new Properties();
		properties.setProperty("user", "postgres");
		properties.setProperty("password", "1234");
		con = DriverManager.getConnection(url,properties);
        
        }
        
	public void closeConnection() throws SQLException
	{
            con.close();
	}		
	
        
        protected void createStatement() throws SQLException{
            this.statement = this.con.createStatement();
        }
        protected void closeStetatement() throws SQLException{
            this.statement.close();
        }
	
	protected void execute(String query) throws SQLException
	{
		this.statement.execute(query);
	}
	
	protected ResultSet executeQuery(String query) throws SQLException
	{
		ResultSet rs = statement.executeQuery(query);
		return rs;
	}
	
	
	protected int executeUpdate(String query) throws SQLException
	{
		int numero = 0;
		// Comando para update, insert e delete		
		this.statement.executeUpdate(query,Statement.RETURN_GENERATED_KEYS);
		
		ResultSet rs = this.statement.getGeneratedKeys();
		
		if (rs.next())
		{
			numero = rs.getInt(1);
		}
		return numero;
	}
	
	

}
