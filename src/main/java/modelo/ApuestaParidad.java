package modelo;

public class ApuestaParidad extends ApuestaBase {
    public ApuestaParidad(int monto, String seleccion) {
        super(monto, seleccion);
    }

    @Override
    public boolean esGanadora(int numeroGanador, String colorGanador) {
        if (numeroGanador == 0) return false;
        if (seleccion.equalsIgnoreCase("PAR")) {
            return numeroGanador % 2 == 0;
        } else {
            return numeroGanador % 2 != 0;
        }
    }

    @Override
    public int calcularPremio() {
        return this.monto * Ruleta.MULTIPLICADOR_PARIDAD;
    }

    @Override
    public String getNombreTipo() {
        return this.seleccion.toUpperCase(); // PAR o IMPAR
    }
}