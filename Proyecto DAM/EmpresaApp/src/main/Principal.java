package main;

import java.awt.EventQueue;
import conexionBD.ConexionBD;
import dao.EmpleadoDAO;
import vista.LoginFrame;

public class Principal {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    ConexionBD conexionBD = new ConexionBD();
                    conexionBD.abrirConexion();
                    
                    EmpleadoDAO empleadoDAO = new EmpleadoDAO(conexionBD);
                    
                    LoginFrame login = new LoginFrame(conexionBD, empleadoDAO);
                    login.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
	}
}