package dao;

import conexionBD.ConexionBD;
import modelo.EmpleadoProyecto;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmpleadoProyectoDAO {
	private static ConexionBD connBD;
	
    public EmpleadoProyectoDAO(ConexionBD conexionBD) {
        connBD = conexionBD;
    }
    
    public List<EmpleadoProyecto> obtenerPorProyecto(int idProyecto) {
        List<EmpleadoProyecto> lista = new ArrayList<>();
        String sql = "select * from empleados_proyectos where id_proyecto = ?";
        try (PreparedStatement ps = connBD.getConexion().prepareStatement(sql)) {
            ps.setInt(1, idProyecto);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                lista.add(new EmpleadoProyecto(
                        rs.getInt("id_empleado"),
                        rs.getInt("id_proyecto"),
                        rs.getString("rol_proyecto"),
                        rs.getInt("horas_asignadas")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }
    
    public List<EmpleadoProyecto> obtenerTodos() {
        List<EmpleadoProyecto> lista = new ArrayList<>();
        String sql = "select * from empleados_proyectos";
        try (PreparedStatement ps = connBD.getConexion().prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                lista.add(new EmpleadoProyecto(
                    rs.getInt("id_empleado"),
                    rs.getInt("id_proyecto"),
                    rs.getString("rol_proyecto"),
                    rs.getInt("horas_asignadas")
                ));
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return lista;
    }
    
    public void asignar(EmpleadoProyecto ep) {
        String sql = "insert into empleados_proyectos " +
                     "(id_empleado, id_proyecto, rol_proyecto, horas_asignadas) " +
                     "values (?, ?, ?, ?)";
        try (PreparedStatement ps = connBD.getConexion().prepareStatement(sql)) {
            ps.setInt(1, ep.getIdEmpleado());
            ps.setInt(2, ep.getIdProyecto());
            ps.setString(3, ep.getRolProyecto());
            ps.setInt(4, ep.getHorasAsignadas());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void desasignar(int idEmpleado, int idProyecto) {
        String sql = "delete from empleados_proyectos where id_empleado = ? and id_proyecto = ?";
        try (PreparedStatement ps = connBD.getConexion().prepareStatement(sql)) {
            ps.setInt(1, idEmpleado);
            ps.setInt(2, idProyecto);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}