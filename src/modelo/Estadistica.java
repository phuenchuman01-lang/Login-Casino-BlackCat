package modelo;

import java.util.List;

public class Estadistica {
	private int totalJugadas;
	private int victorias;
	private double porcentajeVictorias;
	private String tipoMasJugado; // Ahora es String, no el Enum
	private int rachaMaxima;

	public int getTotalJugadas() { return totalJugadas; }
	public int getVictorias() { return victorias; }
	public double getPorcentajeVictorias() { return porcentajeVictorias; }
	public String getTipoMasJugado() { return tipoMasJugado; }
	public int getRachaMaxima() { return rachaMaxima; }

	public void calcular(List<Resultado> historial) {
		if (historial == null || historial.isEmpty()) {
			this.totalJugadas = 0;
			this.victorias = 0;
			this.porcentajeVictorias = 0.0;
			this.rachaMaxima = 0;
			this.tipoMasJugado = "Ninguna";
			return;
		}

		this.totalJugadas = historial.size();
		this.victorias = 0;
		int rachaActual = 0;
		this.rachaMaxima = 0;
		int cColor = 0, cParidad = 0, cNumero = 0;

		for (Resultado r : historial) {
			if (r.isGano()) {
				this.victorias++;
				rachaActual++;
				if (rachaActual > this.rachaMaxima) this.rachaMaxima = rachaActual;
			} else {
				rachaActual = 0;
			}

			// Tipo de apuesta por su clase
			ApuestaBase a = r.getApuesta();
			if (a instanceof ApuestaColor) cColor++;
			else if (a instanceof ApuestaParidad) cParidad++;
			else if (a instanceof ApuestaNumero) cNumero++;
		}

		this.porcentajeVictorias = (this.victorias * 100.0) / this.totalJugadas;
		int max = Math.max(cColor, Math.max(cParidad, cNumero));
		if (max == 0) this.tipoMasJugado = "Ninguna";
		else if (max == cColor) this.tipoMasJugado = "Color";
		else if (max == cParidad) this.tipoMasJugado = "Paridad";
		else this.tipoMasJugado = "Número Exacto";
	}
}