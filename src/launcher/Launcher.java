package launcher; // o launcher (depende de cómo hayas nombrado la carpeta)

import controlador.SessionController;
import vista.VentanaLogin;

public class Launcher {
    public static void main(String[] args) {

        // 1. Creamos AL GERENTE (La sesión global)
        SessionController sesionGlobal = new SessionController();

        // 2. Creamos la ventana y le "entregamos" al gerente para que trabaje con ella
        VentanaLogin ventana = new VentanaLogin(sesionGlobal);

        // 3. Mostramos la ventana
        ventana.mostrarVentana();
    }
}