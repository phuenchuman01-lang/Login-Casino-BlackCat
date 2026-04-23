package vista;

import controlador.RuletaController;
import controlador.SessionController;
import modelo.Ruleta;
import javax.swing.*;
import java.awt.*;

public class VentanaMenu {

    private final SessionController session;
    private final RuletaController ruletaController;

    private final JFrame frame = new JFrame("RULETA - Casino Black Cat");
    private final JPanel panelBotones = new JPanel(new GridLayout(5, 1, 5, 5));
    private final JTextArea textoCentral = new JTextArea();
    private final JLabel lblSaldoCentral = new JLabel("Saldo: $0", SwingConstants.CENTER);

    public VentanaMenu(SessionController session) {
        this.session = session;

        // base del juego
        Ruleta motorJuego = new Ruleta();

        // Creamos al gerente de la ruleta (Controlador) y le pasamos el modelo y la sesión
        this.ruletaController = new RuletaController(motorJuego, session);

        armarVentana();
        configurarEventos();
    }

    private void armarVentana() {
        frame.setSize(550, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        panelBotones.add(new JButton("Inicio"));
        panelBotones.add(new JButton("Perfil"));
        panelBotones.add(new JButton("Jugar"));
        panelBotones.add(new JButton("Historial"));
        panelBotones.add(new JButton("Salir"));
        frame.add(panelBotones, BorderLayout.WEST);
        textoCentral.setText("Bienvenido/a al menú principal, " + session.getNombreUsuario() + ".\n\n" +
                "Recuerda visitar tu 'Perfil' para recargar saldo antes de jugar.");
        textoCentral.setEditable(false);
        frame.add(textoCentral, BorderLayout.CENTER);
        lblSaldoCentral.setFont(new Font("Arial", Font.BOLD, 16));
        frame.add(lblSaldoCentral, BorderLayout.NORTH);
    }

    private void configurarEventos() {
        // Botón Perfil
        JButton btnPerfil = (JButton) panelBotones.getComponent(1);
        btnPerfil.addActionListener(e -> abrirPerfil());

        // Botón Jugar
        JButton btnJugar = (JButton) panelBotones.getComponent(2);
        btnJugar.addActionListener(e -> {
            VentanaJuego juego = new VentanaJuego(ruletaController);
            juego.mostrarVentana();
            juego.alCerrar(() -> actualizarSaldoVista());
        });

        // Botón Historial
        JButton btnHistorial = (JButton) panelBotones.getComponent(3);
        btnHistorial.addActionListener(e -> {
            VentanaHistorial ventanaH = new VentanaHistorial(session);
            ventanaH.mostrarVentana();
        });

        // Botón Salir
        JButton btnSalir = (JButton) panelBotones.getComponent(4);
        btnSalir.addActionListener(e -> {
            session.cerrarSesion();
            frame.dispose();
            new VentanaLogin(session).mostrarVentana();
        });
    }

    private void abrirPerfil() {
        JPanel panel = new JPanel(new GridLayout(4, 2, 5, 5));

        panel.add(new JLabel("Usuario (ID):"));
        panel.add(new JLabel(session.getUsuarioActual().getUsername()));

        panel.add(new JLabel("Nombre visible:"));
        JTextField txtNombre = new JTextField(session.getNombreUsuario());
        panel.add(txtNombre);

        panel.add(new JLabel("Saldo Actual:"));
        // El controlador nos da el saldo
        JLabel lblSaldoPerfil = new JLabel("$" + ruletaController.getSaldo());
        panel.add(lblSaldoPerfil);

        JButton btnRecargar = new JButton("Recargar");
        btnRecargar.addActionListener(e -> {
            String montoStr = JOptionPane.showInputDialog(frame, "Ingrese monto a depositar:");
            try {
                int monto = Integer.parseInt(montoStr);
                // El controlador maneja el depósito
                ruletaController.depositar(monto);
                lblSaldoPerfil.setText("$" + ruletaController.getSaldo());
                actualizarSaldoVista();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "Error: Ingrese un monto válido mayor a 0.");
            }
        });
        panel.add(btnRecargar);

        int resultado = JOptionPane.showConfirmDialog(frame, panel, "Mi Perfil", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (resultado == JOptionPane.OK_OPTION) {
            try {
                session.getUsuarioActual().setNombre(txtNombre.getText());
                textoCentral.setText("Nombre actualizado a: " + session.getNombreUsuario());
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(frame, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void actualizarSaldoVista() {
        lblSaldoCentral.setText("Saldo: $" + ruletaController.getSaldo());
    }

    public void mostrarVentana() {
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}