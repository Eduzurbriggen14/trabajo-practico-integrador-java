package TrabajoIntegrador.Entrega2;

import java.util.ArrayList;

public class Usuario {
	
	String nombre_usuario;
	ArrayList<Pronostico> pronosticos_list = new ArrayList<>();
	int puntos_usuario = 0;
	
	public Usuario(String nombre_usuario) {
		this.nombre_usuario = nombre_usuario;
		this.puntos_usuario = 0;
	}
	
	public String getNombre_usuario() {
		return nombre_usuario;
	}
	
	public void setNombre_usuario(String nombre_usuario) {
		this.nombre_usuario = nombre_usuario;
	}

	public int getPuntos_usuario() {
		return puntos_usuario;
	}
	
	public ArrayList<Pronostico> getPronosticos_list() {
		return pronosticos_list;
	}

	public void setPronosticos_list(ArrayList<Pronostico> pronosticos_list) {
		this.pronosticos_list = pronosticos_list;
	}

	public void sumarPuntos_usuario() {
		this.puntos_usuario = puntos_usuario++;
	}

	
	public int sumaPuntos(boolean acierto) {
		if (acierto == true) {
			puntos_usuario++;
		}
		return puntos_usuario;
	}
}
