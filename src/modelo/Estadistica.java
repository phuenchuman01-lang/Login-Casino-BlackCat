package modelo;

import java.util.List;

public class Estadistica {

	private int totalJugadas;
	private int victorias;
	private double porcentajeVictorias;
	private TipoApuesta tipoMasJugado;
	private int rachaMaxima;

	// --- MÉTODOS GETTERS PARA LA INTERFAZ GRÁFICA ---
	public int getTotalJugadas() { return totalJugadas; }
	public int getVictorias() { return victorias; }
	public double getPorcentajeVictorias() { return porcentajeVictorias; }
	public TipoApuesta getTipoMasJugado() { return tipoMasJugado; }
	public int getRachaMaxima() { return rachaMaxima; }

	// --- LÓGICA DE NEGOCIO ---
	public void calcular(List<Resultado> historial) {
		if (historial == null || historial.isEmpty()) {
			this.totalJugadas = 0;
			this.victorias = 0;
			this.porcentajeVictorias = 0.0;
			this.rachaMaxima = 0;
			this.tipoMasJugado = null;
			return;
		}

		this.totalJugadas = historial.size();
		this.victorias = 0;

		int rachaActual = 0;
		this.rachaMaxima = 0;

		int conteoRojo = 0, conteoNegro = 0, conteoPar = 0, conteoImpar = 0;

		for (Resultado r : historial) {
			// 1. Calcular Victorias y Racha
			if (r.isGano()) {
				this.victorias++;
				rachaActual++;
				if (rachaActual > this.rachaMaxima) {
					this.rachaMaxima = rachaActual; // Guardamos el récord
				}
			} else {
				rachaActual = 0; // La racha se rompe al perder
			}

			// 2. Contar Tipos de Apuesta (ignora números exactos que son null)
			TipoApuesta tipo = r.getTipoApuesta();
			if (tipo != null) {
				if (tipo == TipoApuesta.ROJO) conteoRojo++;
				else if (tipo == TipoApuesta.NEGRO) conteoNegro++;
				else if (tipo == TipoApuesta.PAR) conteoPar++;
				else if (tipo == TipoApuesta.IMPAR) conteoImpar++;
			}
		}

		// 3. Calcular Porcentaje
		this.porcentajeVictorias = (this.victorias * 100.0) / this.totalJugadas;

		// 4. Determinar el Tipo Más Jugado
		int max = Math.max(Math.max(conteoRojo, conteoNegro), Math.max(conteoPar, conteoImpar));
		if (max == 0) {
			this.tipoMasJugado = null;
		} else if (max == conteoRojo) {
			this.tipoMasJugado = TipoApuesta.ROJO;
		} else if (max == conteoNegro) {
			this.tipoMasJugado = TipoApuesta.NEGRO;
		} else if (max == conteoPar) {
			this.tipoMasJugado = TipoApuesta.PAR;
		} else {
			this.tipoMasJugado = TipoApuesta.IMPAR;
		}
	}
}