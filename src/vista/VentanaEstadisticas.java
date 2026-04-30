package vista;

import controlador.SessionController;
import modelo.Estadistica;
import javax.swing.*;
import java.awt.*;

public class VentanaEstadisticas {

    private final JFrame frame = new JFrame("Estadísticas - Black Cat");
    private final SessionController session;

    public VentanaEstadisticas(SessionController session) {
        this.session = session;
        armarVentana();
    }

    private void armarVentana() {
        frame.setSize(350, 250);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(new GridLayout(6, 1, 5, 5));

        // CAPA DE SEGURIDAD: Verificamos si hay una sesión activa antes de pedir datos
        if (!session.hayUsuario()) {
            frame.add(new JLabel("Error: No hay sesión activa.", SwingConstants.CENTER));
            return;
        }

        // Obtener el usuario y sus estadísticas
        Estadistica est = session.getUsuarioActual().getEstadistica();

        frame.add(new JLabel("---Estadísticas de: " + session.getNombreUsuario() + "---", SwingConstants.CENTER));
        frame.add(new JLabel("Total Jugadas: " + est.getTotalJugadas(), SwingConstants.CENTER));
        frame.add(new JLabel("Victorias: " + est.getVictorias(), SwingConstants.CENTER));
        frame.add(new JLabel(String.format("Porcentaje de Victorias: %.2f%%", est.getPorcentajeVictorias()), SwingConstants.CENTER));
        frame.add(new JLabel("Racha Máxima: " + est.getRachaMaxima() + " seguidas", SwingConstants.CENTER));

        String tipoFav = (est.getTipoMasJugado() != null) ? est.getTipoMasJugado().toString() : "Ninguna";
        frame.add(new JLabel("Apuesta Favorita: " + tipoFav, SwingConstants.CENTER));
    }

    public void mostrarVentana() {
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}