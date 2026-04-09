import java.util.ArrayList;

public class Ruleta {

    public static ArrayList<String> historialGlobal = new ArrayList<>();

    // 1. Constantes
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

    public boolean evaluarApuestaColor(int numeroGanador, String eleccion, int monto) {
        this.saldo -= monto; // El casino cobra la apuesta
        String colorGanador = obtenerColor(numeroGanador);

        // Si acierta el color
        if (colorGanador.equalsIgnoreCase(eleccion) && numeroGanador != 0) {
            this.saldo += (monto * MULTIPLICADOR_COLOR);
            return true;
        }
        return false;
    }

    public boolean evaluarApuestaParidad(int numeroGanador, String eleccion, int monto) {
        this.saldo -= monto;

        if (numeroGanador == 0) return false; // El 0 siempre pierde en paridad

        boolean esPar = (numeroGanador % 2 == 0);
        boolean apostoPar = eleccion.equalsIgnoreCase("Par");

        if (esPar == apostoPar) {
            this.saldo += (monto * MULTIPLICADOR_PARIDAD);
            return true;
        }
        return false;
    }

    public boolean evaluarApuestaNumero(int numeroGanador, int numeroElegido, int monto) {
        this.saldo -= monto;

        if (numeroGanador == numeroElegido) {
            this.saldo += (monto * MULTIPLICADOR_NUMERO);
            return true;
        }
        return false;
    }
}