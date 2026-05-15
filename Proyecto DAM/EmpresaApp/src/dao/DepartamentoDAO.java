package dao;

import conexionBD.ConexionBD;
import modelo.Departamento;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DepartamentoDAO {
	private static ConexionBD connBD;

	public DepartamentoDAO(ConexionBD conexionBD) {
		connBD = conexionBD;
	}
	
	public List<Departamento> obtenerTodos() {
        List<Departamento> departamentos = new ArrayList<>();
        String sql = "SELECT id_departamento, nombre_departamento, descripcion FROM departamentos ORDER BY id_departamento";
        try (Statement st = connBD.getConexion().createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                departamentos.add(new Departamento(
                        rs.getInt("id_departamento"),
                        rs.getString("nombre_departamento"),
                        rs.getString("descripcion")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return departamentos;
    }
}
