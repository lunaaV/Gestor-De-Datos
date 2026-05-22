package conexionBD;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConexionBD {
	private static Connection conexionMySql;
	private static Properties props = new Properties();
	
	public ConexionBD() {
        props = new Properties();
        try (FileInputStream fis = new FileInputStream("config/db.properties")) {
            props.load(fis);
        } catch (IOException e) {
            throw new RuntimeException("❌ No se encontró db.properties", e);
        }
    }
	
	public void abrirConexion() throws SQLException {
		conexionMySql = DriverManager.getConnection(
            props.getProperty("db.url"),
            props.getProperty("db.user"),
            props.getProperty("db.password")
        );
        System.out.println("Conexión exitosa con la base de datos");
    }
	
	public Connection getConexion() {
	    return conexionMySql;
	}
	
	public void cerrarConexion() throws SQLException {
        if (conexionMySql != null) conexionMySql.close();
    }
}
