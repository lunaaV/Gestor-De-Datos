package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import conexionBD.ConexionBD;
import modelo.Usuario;

public class LoginDAO {
	private static ConexionBD connBD;
	
	public LoginDAO(ConexionBD conexionBD) {
		connBD = conexionBD;
	}
	
	public Usuario login(String email, String password) {
        Usuario usuario = null;
        try {
        	String sql =
        			"select u.id_usuario, u.nombre, u.email, u.password, r.nombre_rol " +
        			"from usuarios u " +
        			"join roles r ON u.rol = r.id_rol " +
        			"where u.email = ? AND u.password = ?";
            PreparedStatement ps = connBD.getConexion().prepareStatement(sql);
            ps.setString(1, email);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
            	int id = rs.getInt("id_usuario");
                String nombre = rs.getString("nombre");
                String emailBD = rs.getString("email");
                String passBD = rs.getString("password");
                String rolNombre = rs.getString("nombre_rol");

                usuario = new Usuario(nombre, emailBD, passBD, 0);
                usuario.setIdUsuario(id);
                usuario.setRolNombre(rolNombre);
            }
        } catch (Exception e) {
            System.out.println("Error en login: " + e.getMessage());
        }
        return usuario;
    }
}