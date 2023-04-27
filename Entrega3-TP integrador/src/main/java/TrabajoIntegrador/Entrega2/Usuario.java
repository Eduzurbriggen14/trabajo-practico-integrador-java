package TrabajoIntegrador.Entrega2;

import java.util.ArrayList;

public class Usuario {
	
	String nombre_usuario;
	ArrayList<Pronostico> pronostico = new ArrayList<>();
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
		return pronostico;
	}

	public void setPronosticos_list(ArrayList<Pronostico> pronosticos_list) {
		this.pronostico = pronosticos_list;
	}

	public void sumarPuntos_usuario() {
		this.puntos_usuario = puntos_usuario++;
	}

	
	public int sumaPuntos() {
		puntos_usuario++;
		return puntos_usuario;
	}
	
	public int sumaPuntosExtra() {
		puntos_usuario+=3;
		return puntos_usuario;
	}
	
}
