import javax.swing.*;
import java.awt.*;

public class VentanaMenu {

    private final JFrame frame = new JFrame("RULETA - Casino Black Cat");
    private final JPanel panelBotones = new JPanel(new GridLayout(4, 1, 5, 5));
    private final JTextArea textoCentral = new JTextArea();
    private final JButton btnSalir = new JButton("Salir");
    private final String nombreUsuario;

    public VentanaMenu(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
        armarVentana();
        configurarEventos();
    }

    private void armarVentana() {
        frame.setSize(550, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout()); // Permite poner cosas a los lados (Norte, Sur, Este, Oeste, Centro)

        crearPanelIzquierdo();
        crearPanelCentral();
        frame.add(new JLabel("  " + nombreUsuario), BorderLayout.SOUTH);
    }

    private void crearPanelIzquierdo() {
        panelBotones.add(new JButton("Inicio"));
        panelBotones.add(new JButton("Jugar"));
        panelBotones.add(new JButton("Historial"));
        panelBotones.add(btnSalir);
        frame.add(panelBotones, BorderLayout.WEST); // Lo anclamos a la izquierda (Oeste)
    }

    private void crearPanelCentral() {
        textoCentral.setText("Bienvenido/a al menú principal.\n\n" +
                "A la izquierda tienes:\n" +
                "• Jugar: abre la ventana de juego (se implementará aparte).\n" +
                "• Historial: abre la ventana de historial (se implementará aparte).\n" +
                "• Salir: cierra sesión y vuelve al login.");
        textoCentral.setEditable(false); // Para que el usuario no pueda borrar el texto
        frame.add(textoCentral, BorderLayout.CENTER); // Lo anclamos al centro
    }

    private void configurarEventos() {
        // Dentro de configurarEventos() en VentanaMenu:
        JButton btnJugar = (JButton) panelBotones.getComponent(1); // El segundo botón de tu panel
        btnJugar.addActionListener(e -> {
            VentanaJuego juego = new VentanaJuego(nombreUsuario);
            juego.mostrarVentana();
            // No usamos frame.dispose() aquí porque queremos que el menú quede abierto de fondo
        });

        btnSalir.addActionListener(e -> {
            frame.dispose(); // Cierra este menú
            // Aquí deberías llamar a tu VentanaLogin para volver a la pantalla inicial
        });
    }

    public void mostrarVentana() {
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}