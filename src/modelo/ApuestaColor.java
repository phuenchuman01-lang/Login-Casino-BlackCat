package modelo;

public class ApuestaColor extends ApuestaBase {
    public ApuestaColor(int monto, String seleccion) {
        super(monto, seleccion);
    }

    @Override
    public boolean esGanadora(int numeroGanador, String colorGanador) {
        if (numeroGanador == 0) return false;
        return this.seleccion.equalsIgnoreCase(colorGanador);
    }

    @Override
    public int calcularPremio() {
        return this.monto * Ruleta.MULTIPLICADOR_COLOR;
    }

    @Override
    public String getNombreTipo() {
        return this.seleccion.toUpperCase(); // ROJO o NEGRO
    }
}