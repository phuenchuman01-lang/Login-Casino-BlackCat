package launcher; // o launcher (depende de cómo hayas nombrado la carpeta)

import controlador.SessionController;
import vista.VentanaLogin;

public class Launcher {
    public static void main(String[] args) {
        SessionController sesionGlobal = new SessionController();
        VentanaLogin ventana = new VentanaLogin(sesionGlobal);
        ventana.mostrarVentana();
    }
}