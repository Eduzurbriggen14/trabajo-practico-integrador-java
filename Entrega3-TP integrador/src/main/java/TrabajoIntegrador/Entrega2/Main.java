package TrabajoIntegrador.Entrega2;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		
		// LECTURA DE LOS RESULTADOS DE LAS RONDAS
		String archRonda1 = "src\\resultados.csv";
		ArrayList<Ronda> rondas_list = new ArrayList<>();
		ArrayList<Partido> lista_partidos = new ArrayList<>();
		ArrayList<Partido> partidos_list = new ArrayList<>();
		String equipo1, equipo2;
		int inicio_ronda=1, ronda_nro, golesEquipo1, golesEquipo2;
				
		File file_result = new File(archRonda1);
		
		try (Scanner fileScn = new Scanner(file_result, StandardCharsets.UTF_8)) {
			while (fileScn.hasNextLine()) {
				String[] vector = fileScn.nextLine().split(";");
				ronda_nro = Integer.parseInt(vector[0]);
				
				if (ronda_nro != inicio_ronda) {
					Ronda ronda = new Ronda();
					ronda.ronda_nro = inicio_ronda;
					ronda.partido = new ArrayList<>(partidos_list);
					rondas_list.add(ronda);
					partidos_list.clear();
					inicio_ronda = ronda_nro;
				}

				equipo1 = vector[1];
				equipo2 = vector[4];
				golesEquipo1 = Integer.parseInt(vector[2]);
				golesEquipo2 = Integer.parseInt(vector[3]);
				
				Equipo Equipo11 = new Equipo (equipo1,equipo1);
				Equipo Equipo22 = new Equipo (equipo2,equipo2);
				
				Partido partido = new Partido();
				partido.Eq1 = Equipo11;
				partido.Eq2 = Equipo22;
				partido.golesEq1 = golesEquipo1;
				partido.golesEq2 = golesEquipo2;
				partidos_list.add(partido);
				lista_partidos.add(partido);
				
			}
			Ronda ronda = new Ronda();
			ronda.ronda_nro = inicio_ronda;
			ronda.partido = new ArrayList<>(partidos_list);
			rondas_list.add(ronda);
			
		} catch (IOException e) {
			e.printStackTrace();

		}	
			
		// CONEXION A BD
		int acierto_ronda=0;
		ArrayList<Pronostico> pronosticos_list = new ArrayList<>();
		ArrayList<Usuario> usuarios_list = new ArrayList<>();
		Enum resultado_usuario= ResultadoEnum.PERDEDOR;
		String inicio_usuario="", nombre_usuario = "";
		int idpartida=1;
		
		try {
			ConexionBD BD = new ConexionBD();
			Connection con = BD.getConection();
			Statement miStatement = con.createStatement();
			
			String pron_BD = "SELECT us.nombre_usuario, pa.nombre_eq1, pa.nombre_eq2, re.idresultado, pr.partido_ronda_idronda, pa.idpartido FROM pronostico pr " +
				"JOIN resultado re ON pr.resultado_idresultado = re.idresultado " +
				"JOIN partido pa ON pr.partido_idpartido = pa.idpartido " +
				"JOIN ronda ro ON pr.partido_ronda_idronda = ro.idronda " +
				"JOIN usuario us ON pr.usuario_idusuario = us.idusuario " +
				"ORDER BY idpartido";
		
			
		// LECTURA DE LOS PRONOSTICOS
			ResultSet miResultset = miStatement.executeQuery(pron_BD);

	        while (miResultset.next()){
	        	
	        	nombre_usuario = miResultset.getString("nombre_usuario");
	        	String equipo1_pron = miResultset.getString("nombre_eq1");
	        	String equipo2_pron = miResultset.getString("nombre_eq2");
	        	int resultado = miResultset.getInt("idresultado");
	        	idpartida = miResultset.getInt("idpartido");
	        	acierto_ronda = miResultset.getInt("partido_ronda_idronda");

	        // CHEQUEA SI EL USUARIO EXISTE EN LA LISTA DE USUARIOS
	        	if (inicio_usuario == "") {
	        		inicio_usuario = nombre_usuario;
	        	}
	        	if (nombre_usuario != inicio_usuario & inicio_usuario!= "" ) {
	        		Usuario usuario = new Usuario(inicio_usuario);
					usuario.pronostico = new ArrayList<>(pronosticos_list);
					usuarios_list.add(usuario);
					pronosticos_list.clear();
					inicio_usuario = nombre_usuario;
	        	}
	        	
					
				if (resultado == 1) {
					resultado_usuario = ResultadoEnum.GANADOR;
				} else if (resultado == 2) {
					resultado_usuario = ResultadoEnum.PERDEDOR;
				} else if (resultado == 3) {
					resultado_usuario = ResultadoEnum.EMPATADO;
				}
				
				Equipo Equipo11_pron = new Equipo (equipo1_pron,equipo1_pron);
				Equipo Equipo22_pron = new Equipo (equipo2_pron,equipo2_pron);
				
				Partido partido_pron = new Partido();
				partido_pron.Eq1 = Equipo11_pron;
				partido_pron.Eq2 = Equipo22_pron;
				
				Pronostico pronostico = new Pronostico();
				pronostico.partido_usuario = partido_pron;
				pronostico.resultado = resultado_usuario;
				pronosticos_list.add(pronostico);
				
			}
				
	        

			// PARA AGREGAR AL ULTIMO USUARIO LEIDO
	        Usuario usuario = new Usuario(nombre_usuario);
			usuario.pronostico = new ArrayList<>(pronosticos_list);
			usuarios_list.add(usuario);
	
		} catch (SQLException e1) {
			e1.printStackTrace();
		}

		boolean acierto = false;
		int ronda1=0, ronda2=0, ronda3=0, j=0;;
		
		
		for (Ronda ronda : rondas_list) {
			System.out.println("\n*********** RESULTADOS RONDA " + ronda.getRonda_nro() + ": ***********");
			for (Partido partido : ronda.getPartidos_list()) {
				System.out.println("\n******** " + partido.getEq1() +  " " + partido.getGolesEq1() + " - " + partido.getGolesEq2() + " " +  partido.getEq2() + " ********");
				for (Usuario usuarios : usuarios_list) {
					for (Pronostico pronostico : usuarios.getPronosticos_list()) {
						System.out.println("\nEl usuario " + usuarios.getNombre_usuario() + " pronostico que " + pronostico.getPartido_usuario().getEq1() +  " seria " + pronostico.getResultado());
			
						
						
		// SUMA DE PUNTAJES
						if (pronostico.getResultado() == partido.resultado(partido.Eq1)) {
							acierto = true;
							if (acierto=true) {
								usuarios.sumaPuntos();
							}
						} else {
							acierto = false;
						}
				
		// SUMA PUNTAJES EXTRA
						/*if(ronda1 == 2) {
							usuarios.sumaPuntosExtra();
						} else if(ronda2 == 2) {
							usuarios.sumaPuntosExtra();
						}if(ronda3 == 2) {
							usuarios.sumaPuntosExtra();
						}*/
						
						System.out.println("El equipo salio " + partido.resultado(partido.Eq1));
						System.out.println("Puntaje de " + usuarios.getNombre_usuario() + " es: " + usuarios.getPuntos_usuario() );
						
					}
				}
			}
		}
		
		for (Usuario usuarios : usuarios_list) {
			
			
			System.out.println("\n*******************");
			System.out.println("PUNTAJE TOTAL: " + usuarios.getNombre_usuario() + " es: " + usuarios.getPuntos_usuario());
		}
	}
	/*public static void crearTabla() {
		
		try {
			
			ConexionBD con= new ConexionBD();
			Connection conexion= con.getConection();
			
			String crearTabla= "create table puntosTotales(\r\n"
					+ "nombre varchar(30) not null,\r\n"
					+ "puntos int not null\r\n"
					+ ");\r\n"
					+ "";
			
			Statement st1= conexion.createStatement();
			
			ResultSet rs1= st1.executeQuery(crearTabla);
			
			
			
			
			
		}catch(SQLException e) {
			System.out.println(e.getMessage());
	}
		
	}
	
	public static void insertarDatos(String nombre, int puntos) throws SQLException {
		
		ConexionBD con1= new ConexionBD();
		Connection conexion1= con1.getConection();
		
		String insertDatos= "insert into puntosTotales (nombre, puntos) values(?.?)";
				
		PreparedStatement ps= conexion1.prepareStatement(insertDatos);
		
		ps.setString(1, nombre);;
		ps.setInt(2,puntos);
		
		ps.executeUpdate();
				
						
		}*/
	

}
