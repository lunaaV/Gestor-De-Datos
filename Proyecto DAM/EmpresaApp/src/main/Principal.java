package main;

import java.awt.EventQueue;
import conexionBD.ConexionBD;
import dao.EmpleadoDAO;
import vista.LoginFrame;

public class Principal {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		EventQueue.invokeLater(() -> {
            try {
                // Inicializar conexión
                ConexionBD conexionBD = new ConexionBD();
                conexionBD.abrirConexion();

                // Inicializar DAOs
                EmpleadoDAO empleadoDAO = new EmpleadoDAO(conexionBD);

                // Abrir login
                LoginFrame login = new LoginFrame(conexionBD, empleadoDAO);
                login.setVisible(true);

            } catch (Exception e) {
                e.printStackTrace();
            }
        });
	}

}
