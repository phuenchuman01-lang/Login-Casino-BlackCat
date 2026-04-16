package vista;

import controlador.SessionController;
import modelo.Ruleta;
import javax.swing.*;
import java.awt.*;

public class VentanaMenu {

    private final SessionController session;
    private final Ruleta motorJuego; // El modelo compartido para el saldo

    private final JFrame frame = new JFrame("RULETA - Casino Black Cat");
    private final JPanel panelBotones = new JPanel(new GridLayout(5, 1, 5, 5)); // Ahora son 5 botones
    private final JTextArea textoCentral = new JTextArea();
    private final JLabel lblSaldoCentral = new JLabel("Saldo: $0", SwingConstants.CENTER); // Muestra el saldo siempre

    public VentanaMenu(SessionController session) {
        this.session = session;
        this.motorJuego = new Ruleta(); // Inicia con saldo 0 (exigido en el PDF)

        armarVentana();
        configurarEventos();
    }

    private void armarVentana() {
        frame.setSize(550, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        panelBotones.add(new JButton("Inicio"));
        panelBotones.add(new JButton("Perfil")); // NUEVO BOTÓN
        panelBotones.add(new JButton("Jugar"));
        panelBotones.add(new JButton("Historial"));
        panelBotones.add(new JButton("Salir"));

        frame.add(panelBotones, BorderLayout.WEST);

        textoCentral.setText("Bienvenido/a al menú principal, " + session.getNombreUsuario() + ".\n\n" +
                "Recuerda visitar tu 'Perfil' para recargar saldo antes de jugar.");
        textoCentral.setEditable(false);
        frame.add(textoCentral, BorderLayout.CENTER);

        // Agregamos el saldo en la parte superior
        lblSaldoCentral.setFont(new Font("Arial", Font.BOLD, 16));
        frame.add(lblSaldoCentral, BorderLayout.NORTH);
    }

    private void configurarEventos() {
        // Botón Perfil (Índice 1)
        JButton btnPerfil = (JButton) panelBotones.getComponent(1);
        btnPerfil.addActionListener(e -> abrirPerfil());

        // Botón Jugar (Índice 2)
        JButton btnJugar = (JButton) panelBotones.getComponent(2);
        btnJugar.addActionListener(e -> {
            // Le pasamos el nombre Y el motor de la ruleta a la mesa
            VentanaJuego juego = new VentanaJuego(session.getNombreUsuario(), motorJuego);
            juego.mostrarVentana();

            // Un pequeño truco: cuando se cierre la ventana de juego, actualizamos el saldo en el menú
            juego.alCerrar(() -> actualizarSaldoVista());
        });

        // Botón Historial (Índice 3)
        JButton btnHistorial = (JButton) panelBotones.getComponent(3);
        btnHistorial.addActionListener(e -> {
            VentanaHistorial ventanaH = new VentanaHistorial();
            ventanaH.mostrarVentana();
        });

        // Botón Salir (Índice 4)
        JButton btnSalir = (JButton) panelBotones.getComponent(4);
        btnSalir.addActionListener(e -> {
            session.cerrarSesion();
            frame.dispose();
            new VentanaLogin(session).mostrarVentana(); // Vuelve al login
        });
    }

    private void abrirPerfil() {
        // Creamos un panel a medida para el Perfil
        JPanel panel = new JPanel(new GridLayout(4, 2, 5, 5));

        panel.add(new JLabel("Usuario (ID):"));
        panel.add(new JLabel(session.getUsuarioActual().getUsername())); // Solo lectura (Getter)

        panel.add(new JLabel("Nombre visible:"));
        JTextField txtNombre = new JTextField(session.getNombreUsuario());
        panel.add(txtNombre); // Editable

        panel.add(new JLabel("Saldo Actual:"));
        JLabel lblSaldoPerfil = new JLabel("$" + motorJuego.getSaldo()); // Getter
        panel.add(lblSaldoPerfil);

        JButton btnRecargar = new JButton("Recargar");
        btnRecargar.addActionListener(e -> {
            String montoStr = JOptionPane.showInputDialog(frame, "Ingrese monto a depositar:");
            try {
                int monto = Integer.parseInt(montoStr);
                motorJuego.depositar(monto); // Método de negocio validado
                lblSaldoPerfil.setText("$" + motorJuego.getSaldo());
                actualizarSaldoVista();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "Error: Ingrese un monto válido mayor a 0.");
            }
        });
        panel.add(btnRecargar);

        // Mostramos el panel en un cuadro de diálogo
        int resultado = JOptionPane.showConfirmDialog(frame, panel, "Mi Perfil", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (resultado == JOptionPane.OK_OPTION) {
            try {
                // Usamos el Setter validado para cambiar el nombre
                session.getUsuarioActual().setNombre(txtNombre.getText());
                textoCentral.setText("Nombre actualizado a: " + session.getNombreUsuario());
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(frame, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void actualizarSaldoVista() {
        lblSaldoCentral.setText("Saldo: $" + motorJuego.getSaldo());
    }

    public void mostrarVentana() {
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}