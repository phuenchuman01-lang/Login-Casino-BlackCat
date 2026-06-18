package vista;

import controlador.SessionController;
import javax.swing.*;
import java.awt.*;

public class VentanaLogin {

    private final SessionController session; // El gerente del casino
    private final JFrame frame = new JFrame("Login - Casino Black Cat");
    private final JLabel lblUsuario = new JLabel("Usuario:");
    private final JTextField txtUsuario = new JTextField();
    private final JLabel lblClave = new JLabel("Clave:");
    private final JPasswordField txtClave = new JPasswordField();
    private final JButton btnIngresar = new JButton("Ingresar");
    private final JButton btnRegistrar = new JButton("Crear Cuenta");

    public VentanaLogin(SessionController session) {
        this.session = session;

        frame.setSize(300, 150);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new java.awt.GridLayout(4, 2, 5, 5));
        frame.add(lblUsuario);
        frame.add(txtUsuario);
        frame.add(lblClave);
        frame.add(txtClave);
        frame.add(new JLabel(""));
        frame.add(btnIngresar);
        frame.add(new JLabel("¿Nuevo jugador?"));
        frame.add(btnRegistrar);
        btnRegistrar.addActionListener(e -> abrirRegistro());
    }

    public void mostrarVentana() {
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        btnIngresar.addActionListener(e -> login());
    }

    private void login() {
        String u = txtUsuario.getText();
        String p = new String(txtClave.getPassword());

        // CASO 1: Validación de flujo normal en presentación
        if (u.isBlank() || p.isBlank()) {
            JOptionPane.showMessageDialog(frame, "Por favor, complete ambos campos.", "Validación", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            session.iniciarSesion(u, p);

            JOptionPane.showMessageDialog(frame, "¡Bienvenido al Casino Black Cat, " + session.getNombreUsuario() + "!");
            frame.dispose();

            VentanaMenu menu = new VentanaMenu(session);
            menu.mostrarVentana();

        } catch (IllegalStateException ex) {
            JOptionPane.showMessageDialog(frame, ex.getMessage(), "Acceso Denegado", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void abrirRegistro() {
        frame.dispose();
        VentanaRegistro registro = new VentanaRegistro(session);
        registro.mostrarVentana();
    }
}