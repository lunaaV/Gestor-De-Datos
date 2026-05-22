package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import conexionBD.ConexionBD;
import modelo.Puesto;

public class PuestoDAO {
	private static ConexionBD connBD;

	public PuestoDAO(ConexionBD conexionBD) {
		super();
		connBD = conexionBD;
	}
	
	public List<Puesto> obtenerTodos() {
	    List<Puesto> lista = new ArrayList<>();
	    String sql = "select p.id_puesto, p.nombre_puesto, p.descripcion, p.salario_base from puestos p";
	    try (Statement st = connBD.getConexion().createStatement();
	         ResultSet rs = st.executeQuery(sql)) {
	        while (rs.next()) {
	            Puesto p = new Puesto(
	                    rs.getInt("id_puesto"),
	                    rs.getString("nombre_puesto"),
	                    rs.getString("descripcion"),
	                    rs.getDouble("salario_base")
	            );
	            lista.add(p);
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return lista;
	}
}