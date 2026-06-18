package launcher;

import controlador.SessionController;
import vista.VentanaLogin;
import javax.swing.JOptionPane;

public class Launcher {
    public static void main(String[] args) {

        // CASO 6: Red de seguridad global para Swing
        Thread.setDefaultUncaughtExceptionHandler((thread, throwable) -> {
            JOptionPane.showMessageDialog(null,
                    "Ha ocurrido un error inesperado en el sistema.\nDetalle: " + throwable.getMessage(),
                    "Fallo del Sistema",
                    JOptionPane.ERROR_MESSAGE);
            throwable.printStackTrace();
        });

        SessionController sesionGlobal = new SessionController();
        VentanaLogin ventana = new VentanaLogin(sesionGlobal);
        ventana.mostrarVentana();
    }
}