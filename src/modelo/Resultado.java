package modelo;

public class Resultado {
    private final int numeroGanador;
    private final String colorGanador;
    private final boolean gano;
    private final int montoApostado;
    private final TipoApuesta tipoApuesta;

    public Resultado(int numeroGanador, String colorGanador, boolean gano, int montoApostado, TipoApuesta tipoApuesta) {
        this.numeroGanador = numeroGanador;
        this.colorGanador = colorGanador;
        this.gano = gano;
        this.montoApostado = montoApostado;
        this.tipoApuesta = tipoApuesta;
    }

    @Override
    public String toString() {
        String estado = gano ? "GANÓ" : "PERDIÓ";
        String apuestaTxt = (tipoApuesta != null) ? tipoApuesta.toString() : "N° Exacto";
        return String.format("N°: %d (%s) | Apuesta: %s | Monto: $%d | %s",
                numeroGanador, colorGanador, apuestaTxt, montoApostado, estado);
    }
}