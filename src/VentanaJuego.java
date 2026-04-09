import javax.swing.*;
import java.awt.*;

public class VentanaJuego {

    private final JFrame frame = new JFrame("Mesa de Ruleta - Black Cat");
    private final Ruleta motorJuego; // cerebro sin interfaz visual

    // Componentes de la UI
    private final JComboBox<String> comboTipoApuesta = new JComboBox<>(new String[]{"Color", "Paridad", "Número exacto"});
    private final JComboBox<String> comboColor = new JComboBox<>(new String[]{"Rojo", "Negro"});
    private final JTextField txtMonto = new JTextField();
    private final JButton btnGirar = new JButton("Girar");
    private final JLabel lblResultado = new JLabel("Esperando apuesta...");
    private final JLabel lblSaldo;

    public VentanaJuego(String nombreJugador) {
        // Inicializamos el motor con saldo inicial
        this.motorJuego = new Ruleta(1100);
        this.lblSaldo = new JLabel("Saldo: $" + motorJuego.getSaldo());

        armarVentana();
        configurarEventos();
    }

    private void armarVentana() {
        frame.setSize(600, 300);
        frame.setLayout(new GridLayout(6, 2, 5, 5)); // Rejilla simple para organizar todo

        frame.add(new JLabel("Tipo de apuesta:"));
        frame.add(comboTipoApuesta);

        frame.add(new JLabel("Seleccione color:"));
        frame.add(comboColor);

        frame.add(new JLabel("Monto:"));
        frame.add(txtMonto);

        frame.add(btnGirar);
        frame.add(lblSaldo);

        frame.add(lblResultado);
    }

    private void configurarEventos() {
        // botón Girar con el motor
    }

    public void mostrarVentana() {
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}