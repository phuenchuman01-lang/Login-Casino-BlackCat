package Modelo;

import java.util.ArrayList;

public class Ruleta {

    public static ArrayList<String> historialGlobal = new ArrayList<>();

    public static final int MULTIPLICADOR_COLOR = 2;
    public static final int MULTIPLICADOR_PARIDAD = 2;
    public static final int MULTIPLICADOR_NUMERO = 36;

    private int saldo;

    //Constructor con parámetros
    public Ruleta(int saldoInicial) {
        this.saldo = saldoInicial;
    }

    //Constructor sin parámetros
    public Ruleta() {
        this.saldo = 0;
    }

    // --- GETTERS Y MÉTODOS DE NEGOCIO ---
    public int getSaldo() {
        return this.saldo;
    }

    // NUEVO: Setter inteligente (Método de negocio)
    public void depositar(int monto) {
        if (monto > 0) {
            this.saldo += monto;
        } else {
            throw new IllegalArgumentException("El monto a depositar debe ser mayor a $0.");
        }
    }

    public int girarCilindro() {
        return (int) (Math.random() * 37);
    }

    public String obtenerColor(int numero) {
        if (numero == 0) return "Verde";
        return (numero % 2 == 0) ? "Rojo" : "Negro";
    }

    // Evaluación unificada usando el ENUM
    public boolean evaluarApuesta(int numeroGanador, TipoApuesta tipo, int monto) {
        this.saldo -= monto; // Cobra la apuesta

        if (numeroGanador == 0) return false;

        // La magia del switch con Enum
        boolean gano = switch (tipo) {
            case ROJO -> obtenerColor(numeroGanador).equals("Rojo");
            case NEGRO -> obtenerColor(numeroGanador).equals("Negro");
            case PAR -> (numeroGanador % 2 == 0);
            case IMPAR -> (numeroGanador % 2 != 0);
        };

        if (gano) {
            this.saldo += (monto * MULTIPLICADOR_COLOR);
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