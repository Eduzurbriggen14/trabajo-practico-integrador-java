package TrabajoIntegrador.Entrega2;

public class Pronostico {

	Partido partido_usuario = new Partido();
	int idpartido;
	Enum resultado;

	public Pronostico() {
	}
	
	// Metodos
		
		public Partido getPartido_usuario() {
			return partido_usuario;
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

		public int getIdpartido() {
			return idpartido;
		}

		public void setIdpartido(int idpartido) {
			this.idpartido = idpartido;
		}

			
}
