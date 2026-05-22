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
        String sql = "select ep.id_empleado, ep.id_proyecto, ep.rol_proyecto, ep.horas_asignadas " +
                     "from empleados_proyectos ep where ep.id_proyecto = ? order by ep.id_empleado asc";
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
        String sql = "select ep.id_empleado, ep.id_proyecto, ep.rol_proyecto, ep.horas_asignadas " +
                     "from empleados_proyectos ep order by ep.id_empleado asc";
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
    
    public List<EmpleadoProyecto> obtenerAsignacionesFiltradas(String busqueda) {
        List<EmpleadoProyecto> lista = new ArrayList<>();
        String sql = "select e.id_empleado, e.nombre, e.apellido, " +
                "p.id_proyecto, p.nombre_proyecto, " +
                "ep.rol_proyecto, ep.horas_asignadas " +
                "from empleados_proyectos ep " +
                "join empleados e on e.id_empleado = ep.id_empleado " +
                "join proyectos p on p.id_proyecto = ep.id_proyecto " +
                "where ? = '' " +
                "or lower(e.nombre)          like concat('%', lower(?), '%') " +
                "or lower(e.apellido)        like concat('%', lower(?), '%') " +
                "or lower(p.nombre_proyecto) like concat('%', lower(?), '%') " +
                "or lower(ep.rol_proyecto)   like concat('%', lower(?), '%') " +
                "order by e.id_empleado asc " +
                "limit 1000";
        try (PreparedStatement ps = connBD.getConexion().prepareStatement(sql)) {
            for (int i = 1; i <= 5; i++) ps.setString(i, busqueda);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                lista.add(new EmpleadoProyecto(
                    rs.getInt("id_empleado"),
                    rs.getInt("id_proyecto"),
                    rs.getString("rol_proyecto"),
                    rs.getInt("horas_asignadas"),
                    rs.getString("nombre"),
                    rs.getString("apellido"),
                    rs.getString("nombre_proyecto")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
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