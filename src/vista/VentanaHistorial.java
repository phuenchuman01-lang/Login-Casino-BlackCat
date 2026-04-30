package vista;

import controlador.SessionController;
import modelo.Resultado;
import javax.swing.*;
import java.awt.*;
import java.util.List;

public class VentanaHistorial {

    private final JFrame frame = new JFrame("Historial de Apuestas - Black Cat");
    private final JTextArea areaTexto = new JTextArea();
    private final SessionController session; // Recibimos la sesión

    // Pedimos el Controlador en el constructor
    public VentanaHistorial(SessionController session) {
        this.session = session;
        armarVentana();
    }

    private void armarVentana() {
        frame.setSize(550, 300);
        frame.setLayout(new BorderLayout());

        areaTexto.setEditable(false);
        areaTexto.setFont(new Font("Consolas", Font.PLAIN, 14));

        // --- LECTURA DEL HISTORIAL PERSONAL ---
        List<Resultado> historialUsuario = session.getUsuarioActual().getHistorial();

        if (historialUsuario.isEmpty()) {
            areaTexto.setText("La mesa está limpia. ¡" + session.getNombreUsuario() + ", aún no tienes apuestas registradas!");
        } else {
            areaTexto.setText("--- REGISTRO DE JUGADAS DE " + session.getNombreUsuario().toUpperCase() + " ---\n\n");

            for (Resultado r : historialUsuario) {
                areaTexto.append(r.toString() + "\n");
            }
        }

        JScrollPane scroll = new JScrollPane(areaTexto);
        frame.add(scroll, BorderLayout.CENTER);
    }

    public void mostrarVentana() {
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}