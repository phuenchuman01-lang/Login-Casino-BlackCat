package controlador;

import modelo.Resultado;
import modelo.Ruleta;
import modelo.TipoApuesta;

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

    // El Controlador toma el control del giro y del registro
    public Resultado ejecutarJugada(String tipo, String seleccion, int monto) {
        int numeroGanador = ruleta.girarCilindro();
        String colorGanador = ruleta.obtenerColor(numeroGanador);
        boolean gano = false;
        TipoApuesta enumApuesta = null;

        if (tipo.equals("Número exacto")) {
            int numElegido = Integer.parseInt(seleccion);
            gano = ruleta.evaluarApuestaNumero(numeroGanador, numElegido, monto);
        } else {
            enumApuesta = TipoApuesta.valueOf(seleccion);
            gano = ruleta.evaluarApuesta(numeroGanador, enumApuesta, monto);
        }

        // Crear el resultado y lo guardamos
        Resultado r = new Resultado(numeroGanador, colorGanador, gano, monto, enumApuesta);
        session.getUsuarioActual().agregarResultado(r);

        return r; // Devolvemos el resultado a la ventana para que lo conserve
    }
}