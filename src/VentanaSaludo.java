import javax.swing.*;

public class VentanaSaludo {
    private final JFrame frame = new JFrame("Sala de Juegos - Black Cat");

    // Recibe el nombre del jugador para saludarlo
    public VentanaSaludo(String nombreJugador) {
        frame.setSize(400, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Un texto simple centrado en la ventana
        JLabel mensaje = new JLabel("¡La mesa de ruleta está lista para ti, " + nombreJugador + "!", SwingConstants.CENTER);
        frame.add(mensaje);
    }

    public void mostrarVentana() {
        frame.setLocationRelativeTo(null); // Centrar en pantalla
        frame.setVisible(true);
    }
}