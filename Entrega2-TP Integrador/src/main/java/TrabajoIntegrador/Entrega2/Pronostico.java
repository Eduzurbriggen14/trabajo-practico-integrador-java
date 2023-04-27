package TrabajoIntegrador.Entrega2;

public class Pronostico {

	Partido partido_usuario = new Partido();
	Enum resultado;

	public Pronostico() {}
	
	// Metodos
		
		public Partido getPartido_usuario2() {
			return partido_usuario;
		}
		
		public String getPartido_usuario() {
			return partido_usuario.getEq1();
		}

		public void setPartido_usuario(Partido partido_usuario) {
			this.partido_usuario = partido_usuario;
		}

		public Enum getResultado() {
			return resultado;
		}

		public void setResultado(Enum resultado) {
			this.resultado = resultado;
		}

			
}
