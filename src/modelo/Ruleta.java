package modelo;

public class Ruleta {
    public static final int MULTIPLICADOR_COLOR = 2;
    public static final int MULTIPLICADOR_PARIDAD = 2;
    public static final int MULTIPLICADOR_NUMERO = 36;
    private int saldo;
    public Ruleta(int saldoInicial) { this.saldo = saldoInicial; }
    public Ruleta() { this.saldo = 0; }
    public int getSaldo() { return this.saldo; }

    public void depositar(int monto) {
        if (monto > 0) {
            this.saldo += monto;
        } else {
            throw new IllegalArgumentException("Monto inválido.");
        }
    }

    public int girarCilindro() { return (int) (Math.random() * 37); }

    public String obtenerColor(int numero) {
        if (numero == 0) return "Verde";
        return (numero % 2 == 0) ? "Rojo" : "Negro";
    }

    // POLIMORFISMO
    public boolean evaluarApuesta(int numeroGanador, ApuestaBase apuesta) {
        this.saldo -= apuesta.getMonto();
        String colorGanador = obtenerColor(numeroGanador);
        boolean gano = apuesta.esGanadora(numeroGanador, colorGanador);

        if (gano) {
            this.saldo += apuesta.calcularPremio();
        }
        return gano;
    }
}