package launcher;

import controlador.SessionController;
import vista.VentanaLogin;

public class Launcher {
    public static void main(String[] args) {
        SessionController sesionGlobal = new SessionController();
        VentanaLogin ventana = new VentanaLogin(sesionGlobal);
        ventana.mostrarVentana();
    }
}
