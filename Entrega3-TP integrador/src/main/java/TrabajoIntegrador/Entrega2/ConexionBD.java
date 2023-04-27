package TrabajoIntegrador.Entrega2;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class ConexionBD {
	
	private String driver;
	private String servidor;
	private String nombreBD;
	private String usuario;
	private String pass;
	
	public ConexionBD(){
		Properties properties= new Properties();
		
		try {
			properties.load(new FileInputStream(new File("DatosConexionBD.properties")));
			this.driver= (String)properties.getProperty("driver");
			this.servidor= (String)properties.getProperty("servidor");
			this.nombreBD= (String)properties.getProperty("nombreBD");
			this.usuario= (String)properties.getProperty("usuario");
			this.pass= (String)properties.getProperty("pass");
			
		}catch (FileNotFoundException e) {
		      e.printStackTrace();
		} catch (IOException e) {
		      e.printStackTrace();
		}
	}
	
	private Connection con;
	
	public void Conexion() {
		
		try {
			Class.forName(driver);
			con= DriverManager.getConnection(servidor + nombreBD, usuario, pass);
			System.out.println("----- La conexion fue exitosa ----- \n");
			
		}catch(ClassNotFoundException | SQLException e) {
			System.out.println("----- La conexion ha fallado ----- \n" + e.getMessage());
		}
	}
	
	public Connection getConection() {
		Conexion();
		return con;
	}

}