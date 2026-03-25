package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.sql.Date;

import conexionBD.ConexionBD;
import modelo.Departamento;
import modelo.Empleado;
import modelo.Puesto;
import modelo.Sede;

public class EmpleadoDAO {
	private static ConexionBD connBD;
	
	public EmpleadoDAO(ConexionBD conexionBD) {
		connBD = conexionBD;
	}
	
	// Obtener todos los empleados
    public List<Empleado> obtenerTodos() {
        List<Empleado> empleados = new ArrayList<>();
        String sql = "SELECT e.*, " +
                "d.nombre_departamento, d.descripcion AS desc_dep, " +
                "p.nombre_puesto, p.descripcion AS desc_puesto, " +
                "s.nombre_sede, s.ciudad " +
                "FROM empleados e " +
                "JOIN departamentos d ON e.id_departamento = d.id_departamento " +
                "JOIN puestos p ON e.id_puesto = p.id_puesto " +
                "JOIN sedes s ON e.id_sede = s.id_sede";
        try (Statement st = connBD.getConexion().createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
            	Departamento dep = new Departamento(
            	        rs.getInt("id_departamento"),
            	        rs.getString("nombre_departamento"),
            	        rs.getString("desc_dep")
            	);
            	Puesto puesto = new Puesto(
            	        rs.getInt("id_puesto"),
            	        rs.getString("nombre_puesto"),
            	        "",
            	        0
            	);
            	Sede sede = new Sede(
            	        rs.getInt("id_sede"),
            	        rs.getString("nombre_sede"),
            	        rs.getString("ciudad")
            	);
                Empleado e = new Empleado(
                        rs.getInt("id_empleado"),
                        rs.getString("nombre"),
                        rs.getString("apellido"),
                        rs.getString("dni"),
                        rs.getString("email"),
                        rs.getString("telefono"),
                        rs.getDate("fecha_nacimiento"),
                        rs.getDate("fecha_contratacion"),
                        rs.getDouble("salario"),
                        rs.getInt("id_departamento"),
                        rs.getInt("id_puesto"),
                        rs.getInt("id_sede"),
                        rs.getBoolean("activo")
                );
                e.setDepartamento(dep);
                e.setPuesto(puesto);
                e.setSede(sede);
                empleados.add(e);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return empleados;
    }
    
    // Insertar empleado
    public void insertarEmpleado(Empleado e) {
        String sql = "INSERT INTO empleados(nombre, apellido, dni, email, telefono, fecha_nacimiento, fecha_contratacion, salario, id_departamento, id_puesto, id_sede, activo) "
                   + "VALUES(?,?,?,?,?,?,?,?,?,?,?,?)";
        try (PreparedStatement ps = connBD.getConexion().prepareStatement(sql)) {
            ps.setString(1, e.getNombre());
            ps.setString(2, e.getApellido());
            ps.setString(3, e.getDni());
            ps.setString(4, e.getEmail());
            ps.setString(5, e.getTelefono());
            ps.setDate(6, new Date(e.getFechaNacimiento().getTime()));
            ps.setDate(7, new Date(e.getFechaContratacion().getTime()));
            ps.setDouble(8, e.getSalario());
            ps.setInt(9, e.getIdDepartamento());
            ps.setInt(10, e.getIdPuesto());
            ps.setInt(11, e.getIdSede());
            ps.setBoolean(12, e.isActivo());
            ps.executeUpdate();
            System.out.println("Empleado insertado correctamente.");
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
    }
}
