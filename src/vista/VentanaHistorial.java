package vista;

import modelo.Ruleta;

import javax.swing.*;
import java.awt.*;

public class VentanaHistorial {

    private final JFrame frame = new JFrame("Historial de Apuestas - Black Cat");
    private final JTextArea areaTexto = new JTextArea();

    public VentanaHistorial() {
        armarVentana();
    }

    private void armarVentana() {
        frame.setSize(450, 300);
        frame.setLayout(new BorderLayout());

        areaTexto.setEditable(false); // Para que el usuario no pueda borrar el historial
        areaTexto.setFont(new Font("Consolas", Font.PLAIN, 14));

        // Cargar los datos de la memoria
        if (Ruleta.historialGlobal.isEmpty()) {
            areaTexto.setText("La mesa está limpia. ¡Aún no hay apuestas registradas!");
        } else {
            areaTexto.setText("--- REGISTRO DE JUGADAS ---\n\n");
            for (String registro : Ruleta.historialGlobal) {
                areaTexto.append(registro + "\n");
            }
        }

        // Le ponemos un Scroll por si el jugador es un adicto y juega 100 veces
        JScrollPane scroll = new JScrollPane(areaTexto);
        frame.add(scroll, BorderLayout.CENTER);
    }

    public void mostrarVentana() {
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}