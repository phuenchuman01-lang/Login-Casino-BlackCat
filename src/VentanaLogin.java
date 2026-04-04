import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class VentanaLogin {
    // --- Lista dinámica de usuarios ---
    public static final List<Usuario> USUARIOS = new ArrayList<>();

    // --- UI ---
    private final JFrame frame = new JFrame("Login - Casino Black Cat");
    private final JLabel lblUsuario = new JLabel("Usuario:");
    private final JTextField txtUsuario = new JTextField();
    private final JLabel lblClave = new JLabel("Clave:");
    private final JPasswordField txtClave = new JPasswordField();
    private final JButton btnIngresar = new JButton("Ingresar");
    // AQUÍ CREAS EL BOTÓN NUEVO (Línea 16)
    private final JButton btnRegistrar = new JButton("Crear Cuenta");

    public VentanaLogin() {
        // ... (código de los usuarios) ...

        // 2. Configuración básica
        frame.setSize(300, 150);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // ¡OJO AQUÍ! Cambiamos el GridLayout de 3 a 4 filas para hacer espacio
        frame.setLayout(new java.awt.GridLayout(4, 2, 5, 5));

        // 3. Botones
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
        frame.setLocationRelativeTo(null); // Esto hace que aparezca centrada en la pantalla
        frame.setVisible(true); // Esto enciende la ventana
        btnIngresar.addActionListener(e -> login());
    }

    private void login() {
        // 1. Capturamos lo que el usuario escribió en las cajas de texto
        String u = txtUsuario.getText();
        String p = new String(txtClave.getPassword()); // JPasswordField requiere esta conversión

        // 2. Le preguntamos al detector si esos datos existen
        String nombreReal = validarCredenciales(u, p);

        // 3. El juez decide qué mensaje mostrar
        if (!nombreReal.isEmpty()) {
            // Correcto
            JOptionPane.showMessageDialog(frame, "¡Bienvenido al Casino Black Cat, " + nombreReal + "!");

            // LO NUEVO
            frame.dispose(); // Esto destruye y cierra la ventana de login para que no estorbe

            VentanaSaludo salaDeJuegos = new VentanaSaludo(nombreReal);
            salaDeJuegos.mostrarVentana();
            // ----------------

        } else {
            // Alarma de incorrecto
            JOptionPane.showMessageDialog(frame, "Error: Usuario o clave incorrectos.", "Acceso Denegado", JOptionPane.ERROR_MESSAGE);
        }

    }

    private String validarCredenciales(String u, String p) {
        // Un ciclo que revisa cada 'usuario' dentro de nuestra lista 'USUARIOS'
        for (Usuario usuario : USUARIOS) {
            if (usuario.validarCredenciales(u, p)) {
                return usuario.getNombre(); // Devuelve el nombre real
            }
        }
        return ""; // Si termina de buscar y no encontró nada, devuelve texto vacío
    }

    void abrirRegistro() {
        frame.dispose(); // Destruimos la ventana de login actual
        VentanaRegistro registro = new VentanaRegistro(); // Creamos la de registro
        registro.mostrarVentana(); // La mostramos
    }

}