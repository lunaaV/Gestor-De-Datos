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
	
	// Obtener todos los puestos
	public List<Puesto> obtenerTodos() {
        List<Puesto> lista = new ArrayList<>();
        String sql = "select * from puestos";
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