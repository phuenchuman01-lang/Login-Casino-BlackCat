package modelo;

public class Resultado {
    private final int numeroGanador;
    private final String colorGanador;
    private final boolean gano;
    private final ApuestaBase apuesta;

    public Resultado(int numeroGanador, String colorGanador, boolean gano, ApuestaBase apuesta) {
        this.numeroGanador = numeroGanador;
        this.colorGanador = colorGanador;
        this.gano = gano;
        this.apuesta = apuesta;
    }

    @Override
    public String toString() {
        String estado = gano ? "GANÓ" : "PERDIÓ";
        return String.format("N°: %d (%s) | Apuesta: %s | Monto: $%d | %s",
                numeroGanador, colorGanador, apuesta.getNombreTipo(), apuesta.getMonto(), estado);
    }

    public boolean isGano() { return gano; }
    public ApuestaBase getApuesta() { return apuesta; }
}