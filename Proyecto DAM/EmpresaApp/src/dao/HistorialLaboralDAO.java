package dao;

import conexionBD.ConexionBD;
import modelo.HistorialLaboral;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class HistorialLaboralDAO {
	private static ConexionBD connBD;
	
    public HistorialLaboralDAO(ConexionBD conexionBD) {
        connBD = conexionBD;
    }
    
    public List<HistorialLaboral> obtenerPorEmpleado(int idEmpleado) {
        List<HistorialLaboral> lista = new ArrayList<>();
        String sql = "select h.id_historial, h.id_empleado, h.puesto_anterior, h.puesto_nuevo, " +
                     "h.salario_anterior, h.salario_nuevo, h.fecha_cambio " +
                     "from historial_laboral h where h.id_empleado = ? order by h.fecha_cambio desc";
        try (PreparedStatement ps = connBD.getConexion().prepareStatement(sql)) {
            ps.setInt(1, idEmpleado);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                lista.add(new HistorialLaboral(
                        rs.getInt("id_historial"),
                        rs.getInt("id_empleado"),
                        rs.getString("puesto_anterior"),
                        rs.getString("puesto_nuevo"),
                        rs.getDouble("salario_anterior"),
                        rs.getDouble("salario_nuevo"),
                        rs.getDate("fecha_cambio")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }
    
    public void insertar(HistorialLaboral h) {
        String sql = "insert into historial_laboral (id_empleado, puesto_anterior, puesto_nuevo, " +
                     "salario_anterior, salario_nuevo, fecha_cambio) values (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = connBD.getConexion().prepareStatement(sql)) {
            ps.setInt(1, h.getIdEmpleado());
            ps.setString(2, h.getPuestoAnterior());
            ps.setString(3, h.getPuestoNuevo());
            ps.setDouble(4, h.getSalarioAnterior());
            ps.setDouble(5, h.getSalarioNuevo());
            ps.setDate(6, new Date(h.getFechaCambio().getTime()));
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}