package dao;

import conexionBD.ConexionBD;
import modelo.Usuario;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {
	private static ConexionBD connBD;
    
    public UsuarioDAO(ConexionBD conexionBD) {
    	connBD = conexionBD;
    }
    
    public boolean insertarUsuario(Usuario usuario) {
        String sql = "insert into usuarios (nombre, email, password, rol, fecha_creacion, activo) values (?, ?, ?, ?, ?, ?)";
        boolean insertUser = false;
        try (PreparedStatement ps = connBD.getConexion().prepareStatement(sql)) {
        	ps.setString(1, usuario.getNombre());
        	ps.setString(2, usuario.getEmail());
        	ps.setString(3, usuario.getPassword());
        	ps.setInt(4, usuario.getRol());
        	ps.setDate(5, new Date(usuario.getFechaCreacion().getTime()));
        	ps.setBoolean(6, usuario.isActivo());
        	ps.executeUpdate();
        	insertUser = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return insertUser;
    }
    
    public boolean existeEmail(String email) {
    	String sql = "select 1 from usuarios where email = ?";
        boolean existe = false;
        try (PreparedStatement ps = connBD.getConexion().prepareStatement(sql)) {
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            existe = rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return existe;
    }
    
    public List<Usuario> listarUsuarios() {
        List<Usuario> usuarios = new ArrayList<>();
        String sql = "select u.*, r.nombre_rol as nombre_rol from usuarios u " +
                    "left join roles r on u.rol = r.id_rol order by u.id_usuario desc";
        try (Statement stmt = connBD.getConexion().createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
            	String nombre = rs.getString("nombre");
                String email = rs.getString("email");
                String password = rs.getString("password");
                int rol = rs.getInt("rol");
                Usuario usuario = new Usuario(nombre, email, password, rol);
                usuario.setIdUsuario(rs.getInt("id_usuario"));
                usuario.setRolNombre(rs.getString("nombre_rol"));
                usuario.setFechaCreacion(rs.getDate("fecha_creacion"));
                usuario.setActivo(rs.getBoolean("activo"));
                usuarios.add(usuario);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return usuarios;
    }
}