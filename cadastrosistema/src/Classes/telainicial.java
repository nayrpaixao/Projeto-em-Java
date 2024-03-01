package Classes;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class telainicial {
	
	public static Connection faz_conexao() throws SQLException {
	
		try {
			
				
		Class.forName("com.mysql.cj.jdbc.Driver");
		
		return DriverManager.getConnection("jdbc:mysql://localhost/db_clientes", "root", "130108");
		
			
			
			
			
			
			
		}	catch (ClassNotFoundException e) {
		
			throw new SQLException(e.getException());
			
		}
		
	}
	
}


	


