package TrabajoIntegrador.Entrega2;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Scanner;

public class Ronda {
	
	// Variables
	int ronda_nro = 1;
	ArrayList<Partido> partido = new ArrayList<>();

	// Constructor
		public Ronda() {
		}
	
	public int getRonda_nro() {
		return ronda_nro;
	}

	public void setRonda_nro(int ronda_nro) {
		this.ronda_nro = ronda_nro;
	}

	public ArrayList<Partido> getPartidos_list() {
		return partido;
	}

	public void setPartidos_list(ArrayList<Partido> partido) {
		this.partido = partido;
	}

/*	public ArrayList<Partido> leerArchivo(String archivo) {
		
		String equipo1, equipo2;
		int golesEquipo1, golesEquipo2;
		File file = new File(archivo);
		
		try (Scanner fileScn = new Scanner(file, StandardCharsets.UTF_8)) {

			while (fileScn.hasNextLine()) {

				String[] vector = (fileScn.nextLine()).split(";");
				equipo1 = vector[0];
				equipo2 = vector[3];
				golesEquipo1 = Integer.parseInt(vector[1]);
				golesEquipo2 = Integer.parseInt(vector[2]);
				
				Equipo Equipo11 = new Equipo (equipo1,equipo1);
				Equipo Equipo22 = new Equipo (equipo2,equipo2);
				
				Partido partido = new Partido();
				partido.Eq1 = Equipo11;
				partido.Eq2 = Equipo22;
				partido.golesEq1 = golesEquipo1;
				partido.golesEq2 = golesEquipo2;
				partidos.add(partido);

			}*/
}
