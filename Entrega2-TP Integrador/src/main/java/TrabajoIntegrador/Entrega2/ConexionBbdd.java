package TrabajoIntegrador.Entrega2;

import java.io.File;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConexionBbdd {

	private String driver;
	private String servidor;
	private String nombreBd;
	private String usuario;
	private String password;

	ConexionBbdd() {

		Properties properties = new Properties();

		try {
			properties.load(new FileInputStream(new File("Conexion.properties")));
			this.driver = (String) properties.getProperty("driver");
			this.servidor = (String) properties.getProperty("servidor");
			this.nombreBd = (String) properties.getProperty("nombreBd");
			this.usuario = (String) properties.getProperty("usuario");
			this.password = (String) properties.getProperty("password");

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private Connection con;

	public void Conexion() {

		try {

			Class.forName(driver);

			con = DriverManager.getConnection(servidor + nombreBd, usuario, password);

			System.out.println("La conexion fue exitosa");

		} catch (ClassNotFoundException | SQLException e) {

			System.out.println("La conexion ha fallado" + e.getMessage());
		}
	}

	public Connection getConection() {
		Conexion();
		return con;

	}

}
