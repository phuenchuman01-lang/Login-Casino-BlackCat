package vista;

import controlador.SessionController;
import javax.swing.*;

public class VentanaRegistro {

    private final SessionController session; // El gerente
    private final JFrame frame = new JFrame("Registro - Casino Black Cat");
    private final JTextField txtUsuario = new JTextField();
    private final JPasswordField txtClave = new JPasswordField();
    private final JTextField txtNombre = new JTextField();
    private final JButton btnCrear = new JButton("Registrar");

    // Recibimos al gerente
    public VentanaRegistro(SessionController session) {
        this.session = session;

        frame.setSize(300, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new java.awt.GridLayout(4, 2, 5, 5));

        frame.add(new JLabel("Nuevo Usuario:"));
        frame.add(txtUsuario);
        frame.add(new JLabel("Nueva Clave:"));
        frame.add(txtClave);
        frame.add(new JLabel("Nombre Real:"));
        frame.add(txtNombre);
        frame.add(new JLabel(""));
        frame.add(btnCrear);

        btnCrear.addActionListener(e -> intentarRegistro());
    }

    public void mostrarVentana() {
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void intentarRegistro() {
        String u = txtUsuario.getText();
        String p = new String(txtClave.getPassword());
        String n = txtNombre.getText();

        try {
            // El Controlador se encarga de validar y guardar
            session.registrarUsuario(u, p, n);

            JOptionPane.showMessageDialog(frame, "¡Cuenta creada con éxito! Por favor inicia sesión.");
            frame.dispose();

            // Volvemos al Login pasándole la misma sesión
            VentanaLogin login = new VentanaLogin(session);
            login.mostrarVentana();

        } catch (IllegalArgumentException ex) {
            // Si el Controlador detecta un error (ej. campo vacío), mostramos el mensaje
            JOptionPane.showMessageDialog(frame, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}