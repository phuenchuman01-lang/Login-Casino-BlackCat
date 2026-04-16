package launcher;

import controlador.SessionController;
import vista.VentanaLogin;

public class Launcher {
    public static void main(String[] args) {

        // Crear AL GERENTE
        SessionController sesionGlobal = new SessionController();

        //entregar al gerente para que trabaje con la ventana
        VentanaLogin ventana = new VentanaLogin(sesionGlobal);

        //Mostrar la ventana
        ventana.mostrarVentana();
    }
}
