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

        // CASO 1: Validación de flujo normal con IF en la presentación
        if (u.isBlank() || p.isBlank() || n.isBlank()) {
            JOptionPane.showMessageDialog(frame, "Error: Todos los campos son obligatorios.", "Validación", JOptionPane.WARNING_MESSAGE);
            return; // Corta la ejecución aquí sin lanzar excepciones
        }

        try {
            // El Controlador se encarga de aplicar reglas de negocio
            session.registrarUsuario(u, p, n);

            JOptionPane.showMessageDialog(frame, "¡Cuenta creada con éxito! Por favor inicia sesión.");
            frame.dispose();

            // Volvemos al Login pasándole la misma sesión
            VentanaLogin login = new VentanaLogin(session);
            login.mostrarVentana();

        } catch (IllegalStateException ex) {
            // Si el Controlador detecta un error de dominio.
            JOptionPane.showMessageDialog(frame, ex.getMessage(), "Error de Registro", JOptionPane.ERROR_MESSAGE);
        }
    }
}