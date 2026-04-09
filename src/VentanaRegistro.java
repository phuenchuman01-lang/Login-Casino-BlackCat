import javax.swing.*;

public class VentanaRegistro {
    // --- UI ---
    private final JFrame frame = new JFrame("Registro - Casino Black Cat");
    private final JTextField txtUsuario = new JTextField();
    private final JPasswordField txtClave = new JPasswordField();
    private final JTextField txtNombre = new JTextField();
    private final JButton btnCrear = new JButton("Registrar");

    public VentanaRegistro() {
        frame.setSize(300, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new java.awt.GridLayout(4, 2, 5, 5));

        frame.add(new JLabel("Nuevo Usuario:"));
        frame.add(txtUsuario);
        frame.add(new JLabel("Nueva Clave:"));
        frame.add(txtClave);
        frame.add(new JLabel("Nombre Real:"));
        frame.add(txtNombre);
        frame.add(new JLabel("")); // Espacio vacío
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

        // 1. Validar que todos los campos estén completos
        if (u.isEmpty() || p.isEmpty() || n.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Error: Todos los campos son obligatorios.", "Error", JOptionPane.ERROR_MESSAGE);
            return; // Corta la ejecución aquí si hay error
        }

        guardarUsuario(u, p, n);
    }

    private void guardarUsuario(String u, String p, String n) {
        // 2. Agrega el usuario a la lista dinámica oficial de VentanaLogin
        VentanaLogin.USUARIOS.add(new Usuario(u, p, n));

        // 3. Mensaje de éxito
        JOptionPane.showMessageDialog(frame, "¡Cuenta creada con éxito! Por favor inicia sesión.");

        // 4. Volver al Login
        frame.dispose();
        VentanaLogin login = new VentanaLogin();
        login.mostrarVentana();
    }
}