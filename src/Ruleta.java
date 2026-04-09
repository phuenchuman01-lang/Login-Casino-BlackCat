public class Ruleta {

    // 1. Constantes (Evitando números mágicos)
    public static final int MULTIPLICADOR_COLOR = 2;
    public static final int MULTIPLICADOR_PARIDAD = 2;
    public static final int MULTIPLICADOR_NUMERO = 36;

    // 2. Estado del juego
    private int saldo;

    // 3. Constructor
    public Ruleta(int saldoInicial) {
        this.saldo = saldoInicial;
    }

    // 4. Métodos cortos y específicos (Responsabilidad Única)
    public int getSaldo() {
        return this.saldo;
    }

    public int girarCilindro() {
        // Genera un número aleatorio entre 0 y 36
        return (int) (Math.random() * 37);
    }

    public String obtenerColor(int numero) {
        if (numero == 0) return "Verde";
        // Lógica simple: pares rojos, impares negros (puedes ajustarla a la ruleta real)
        return (numero % 2 == 0) ? "Rojo" : "Negro";
    }

    public boolean evaluarApuestaColor(int numeroGanador, String colorApostado, int monto) {
        String colorGanador = obtenerColor(numeroGanador);
        if (colorGanador.equalsIgnoreCase(colorApostado)) {
            this.saldo += (monto * MULTIPLICADOR_COLOR) - monto;
            return true;
        } else {
            this.saldo -= monto;
            return false;
        }
    }
}