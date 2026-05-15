package dao;

import java.sql.*;
import conexionBD.ConexionBD;

public class RecuperarPasswordDAO {
	private static ConexionBD connBD;
	
    public RecuperarPasswordDAO(ConexionBD conexionBD) {
    	connBD = conexionBD;
    }
    
    public boolean existeEmail(String email) {
        String sql = "select 1 from usuarios where email = ? and activo = true";
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
    
    public boolean actualizarPassword(String email, String nuevaPassword) {
        String sql = "update usuarios set password = ? where email = ?";
        boolean passUpdate = false;
        try (PreparedStatement ps = connBD.getConexion().prepareStatement(sql)) {
            ps.setString(1, nuevaPassword);
            ps.setString(2, email);
            ps.executeUpdate();
            passUpdate = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return passUpdate;
    }
}