package modelo;

public class ApuestaNumero extends ApuestaBase {
    private int numeroApostado;

    public ApuestaNumero(int monto, String seleccion) {
        super(monto, seleccion);
        this.numeroApostado = Integer.parseInt(seleccion);
    }

    @Override
    public boolean esGanadora(int numeroGanador, String colorGanador) {
        return this.numeroApostado == numeroGanador;
    }

    @Override
    public int calcularPremio() {
        return this.monto * Ruleta.MULTIPLICADOR_NUMERO;
    }

    @Override
    public String getNombreTipo() {
        return "NÚMERO EXACTO";
    }
}