package TrabajoIntegrador.Entrega2;

import java.io.File;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

	public static void Conexion() {

		try {

			ConexionBbdd con = new ConexionBbdd();

			Connection conexion = con.getConection();

			String verUsuarios = "select * from usuarios";

			Statement st = conexion.createStatement();

			ResultSet rs = st.executeQuery(verUsuarios);

			while (rs.next()) {

				System.out.println(
						rs.getString("id_usuario") + " " + rs.getString("nombre") + " " + rs.getString("puntos"));
			}

		} catch (SQLException e) {

			System.out.println("Error, la conexion fallo");

		}
	}
	
	public static void cargarDatosRonda(int nroronda, String nombreUsuario, int puntos ) throws SQLException {
		
		ConexionBbdd con1 = new ConexionBbdd();

		Connection conexion1 = con1.getConection();
		
		String insertar= "INSERT INTO ronda (id_ronda, usuario, puntos)\n "+ "VALUES" + "(?,?,?)";
		
		PreparedStatement ps= conexion1.prepareStatement(insertar);
		
		ps.setInt(1, nroronda );
		ps.setString(2, nombreUsuario) ;
		ps.setInt(3, puntos);
		
		ps.executeUpdate();
		
		System.out.println("Se insertaron los datos correctamente");
		
	}
	
	public static void datosUsuarioTotal(String nombre, int puntos) throws SQLException {
		
		ConexionBbdd con1 = new ConexionBbdd();

		Connection conexion1 = con1.getConection();
		
		String insertar= "INSERT INTO puntos_totales ( nombre, puntos)\n "+ "VALUES" + "(?,?)";
		
		PreparedStatement ps= conexion1.prepareStatement(insertar);
		
		ps.setString(1, nombre );
		ps.setInt(2, puntos) ;
		
		ps.executeUpdate();
		
		System.out.println("Se insertaron los datos correctamente");
		
	}

	public static void main(String[] args) throws SQLException {


		//Conexion();

		/// LECTURA DE LOS RESULTADOS DE LAS RONDAS
		String archRonda1 = "src\\resultados.csv";

		ArrayList<Ronda> rondas_list = new ArrayList<>();
		
		ArrayList<Partido> lista_partidos = new ArrayList<>();
		
		ArrayList<Partido> partidos_list = new ArrayList<>();
		
		String equipo1, equipo2;
		
		int inicio_ronda = 1, ronda_nro, golesEquipo1, golesEquipo2;
		

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

				Equipo Equipo11 = new Equipo(equipo1, equipo1);
				Equipo Equipo22 = new Equipo(equipo2, equipo2);

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

		// LECTURA DE LOS PRONOSTICOS
		String archPronostico1 = "src\\pronostico.csv";
		ArrayList<Pronostico> pronosticos_list = new ArrayList<>();
		ArrayList<Usuario> usuarios_list = new ArrayList<>();
		Enum resultado_usuario = ResultadoEnum.PERDEDOR;

		String nombre_usuario = "", equipo1_pron, equipo2_pron;
		File file_pron = new File(archPronostico1);

		try (Scanner fileScn2 = new Scanner(file_pron, StandardCharsets.UTF_8)) {
			while (fileScn2.hasNextLine()) {

				String[] vector = (fileScn2.nextLine()).split(";");
				nombre_usuario = vector[0];

				boolean usuarioExiste = false;
				for (Usuario u : usuarios_list) {
					if (u.nombre_usuario.equals(nombre_usuario)) {
						usuarioExiste = true;
						u.setPronosticos_list(new ArrayList<>(pronosticos_list));
						break;
					}
				}

				// Si el usuario no existe, agregarlo a la lista de usuarios
				if (!usuarioExiste) {
					Usuario usuario = new Usuario(nombre_usuario);
					usuarios_list.add(usuario);
				}

				pronosticos_list.clear();

				equipo1_pron = vector[1];
				equipo2_pron = vector[5];
				if (vector[2] != "") {
					resultado_usuario = ResultadoEnum.GANADOR;
				} else if (vector[3] != "") {
					resultado_usuario = ResultadoEnum.PERDEDOR;
				} else if (vector[4] != "") {
					resultado_usuario = ResultadoEnum.EMPATADO;
				}

				Equipo Equipo11_pron = new Equipo(equipo1_pron, equipo1_pron);
				Equipo Equipo22_pron = new Equipo(equipo2_pron, equipo2_pron);

				Partido partido_pron = new Partido();
				partido_pron.Eq1 = Equipo11_pron;
				partido_pron.Eq2 = Equipo22_pron;

				Pronostico pronostico = new Pronostico();
				pronostico.partido_usuario = partido_pron;
				pronostico.resultado = resultado_usuario;
				pronosticos_list.add(pronostico);

			}

		} catch (IOException e) {
			e.printStackTrace();

		}

		boolean acierto = false;
		
		int aciertos=0;
		for (Ronda ronda : rondas_list) {
			System.out.println("Resultados Ronda " + ronda.getRonda_nro() + ": ");
			for (Partido partido : ronda.getPartidos_list()) {
				System.out.println(partido.getEq1() + " " + partido.getGolesEq1() + " - " + partido.getGolesEq2() + " "
						+ partido.getEq2());
				for (Usuario usuarios : usuarios_list) {
					System.out.println("El usuario " + usuarios.nombre_usuario);
					for (Pronostico pronostico : usuarios.getPronosticos_list()) {
						System.out.println("pronostico que " + pronostico.getPartido_usuario() + " seria "
								+ pronostico.getResultado());

						if (pronostico.getResultado() == partido.resultado(partido.Eq1)) {
							
							acierto = true;
							usuarios.sumaPuntos(acierto);
							aciertos+=1;
						} else {
							acierto = false;
						}
						System.out.println("El equipo salio " + partido.resultado(partido.Eq1));
						System.out.println("Puntaje de " + usuarios.getNombre_usuario() + " es: "
								+ usuarios.getPuntos_usuario() + "\n*******************");
						
						//cargarDatosRonda(ronda.getRonda_nro(), usuarios.getNombre_usuario(),usuarios.getPuntos_usuario() );

					}
				}
			}
			
		}

		for (Usuario usuarios : usuarios_list) {
			System.out
					.println("Puntaje total: " + usuarios.getNombre_usuario() + " es: " + usuarios.getPuntos_usuario());
			System.out.println("acerto: "+aciertos);
			System.out.println("\n*******************");
			
			//datosUsuarioTotal(usuarios.getNombre_usuario(),usuarios.getPuntos_usuario() );
			
		}
	}

}
