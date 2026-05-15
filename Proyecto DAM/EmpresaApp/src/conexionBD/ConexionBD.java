package conexionBD;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBD {
	private static Connection conexionMySql;
	private static String url = "jdbc:mysql://localhost:3306/empresa_db";
	private static String user = "root";
	private static String password = "12345";
	
	public void abrirConexion() throws SQLException {
		conexionMySql = DriverManager.getConnection(url, user, password);
        System.out.println("Conexión exitosa con la base de datos");
    }
	
	public Connection getConexion() {
	    return conexionMySql;
	}
	
	public void cerrarConexion() throws SQLException {
        if (conexionMySql != null) conexionMySql.close();
    }
}
