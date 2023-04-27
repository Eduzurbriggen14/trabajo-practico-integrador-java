package TrabajoIntegrador.Entrega2;

public class Partido {
	// Variables
		Equipo Eq1, Eq2;
		Enum resultado;
		int golesEq1, golesEq2;
	
	// Constructor
	public Partido() {
	}

	// Getters and setters
	public String getEq1() {
		return Eq1.getNombre();
	}

	public String getEq2() {
		return Eq2.getNombre();
	}

	public int getGolesEq1() {
		return golesEq1;
	}

	public void setGolesEq1(int golesEq1) {
		this.golesEq1 = golesEq1;
	}

	public int getGolesEq2() {
		return golesEq2;
	}

	public void setGolesEq2(int golesEq2) {
		this.golesEq2 = golesEq2;
	}
	

	public Enum resultado (Equipo resultado_equipo) {
		if (golesEq1==golesEq2) {
			resultado = ResultadoEnum.EMPATADO;
		} else if (golesEq1>golesEq2){
			resultado = ResultadoEnum.GANADOR;
		} else if (golesEq1<golesEq2){
			resultado = ResultadoEnum.PERDEDOR;
		}
		return resultado;
	}
		
}
