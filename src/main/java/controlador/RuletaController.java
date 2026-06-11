package controlador;

import modelo.ApuestaBase;
import modelo.ApuestaColor;
import modelo.ApuestaNumero;
import modelo.ApuestaParidad;
import modelo.Resultado;
import modelo.Ruleta;

public class RuletaController {

    private final Ruleta ruleta;
    private final SessionController session;

    // Inyección de Dependencias
    public RuletaController(Ruleta ruleta, SessionController session) {
        this.ruleta = ruleta;
        this.session = session;
    }

    public int getSaldo() {
        return ruleta.getSaldo();
    }

    public void depositar(int monto) {
        ruleta.depositar(monto);
    }

    public Resultado ejecutarJugada(String tipo, String seleccion, int monto) {
        int numeroGanador = ruleta.girarCilindro();
        String colorGanador = ruleta.obtenerColor(numeroGanador);

        // Apuesta específica usando HERENCIA
        ApuestaBase apuesta;
        if (tipo.equals("Color")) {
            apuesta = new ApuestaColor(monto, seleccion);
        } else if (tipo.equals("Paridad")) {
            apuesta = new ApuestaParidad(monto, seleccion);
        } else {
            apuesta = new ApuestaNumero(monto, seleccion);
        }

        // POLIMORFISMO
        boolean gano = ruleta.evaluarApuesta(numeroGanador, apuesta);

        // Crear el resultado y lo guardar en la sesión
        Resultado r = new Resultado(numeroGanador, colorGanador, gano, apuesta);
        session.getUsuarioActual().agregarResultado(r);

        return r;
    }
}