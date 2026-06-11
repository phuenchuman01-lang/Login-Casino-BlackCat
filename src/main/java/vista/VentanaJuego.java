package vista;

import controlador.RuletaController;
import modelo.Resultado;
import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class VentanaJuego {

    private final JFrame frame = new JFrame("Mesa de Ruleta - Black Cat");

    // conexion con el controlador
    private final RuletaController ruletaController;

    private final JComboBox<String> comboTipoApuesta = new JComboBox<>(new String[]{"Color", "Paridad", "Número exacto"});
    private final JComboBox<String> comboSeleccion = new JComboBox<>(new String[]{"ROJO", "NEGRO"});
    private final JTextField txtMonto = new JTextField();
    private final JButton btnGirar = new JButton("Girar");
    private final JLabel lblResultado = new JLabel("Esperando apuesta...");
    private final JLabel lblSaldo;

    private Runnable accionAlCerrar;

    public VentanaJuego(RuletaController ruletaController) {
        this.ruletaController = ruletaController;
        this.lblSaldo = new JLabel("Saldo: $" + ruletaController.getSaldo());

        armarVentana();
        configurarEventos();
    }

    private void armarVentana() {
        frame.setSize(600, 300);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(new GridLayout(6, 2, 5, 5));

        frame.add(new JLabel("Tipo de apuesta:"));
        frame.add(comboTipoApuesta);

        frame.add(new JLabel("Seleccione opción:"));
        frame.add(comboSeleccion);

        frame.add(new JLabel("Monto:"));
        frame.add(txtMonto);

        frame.add(btnGirar);
        frame.add(lblSaldo);

        frame.add(lblResultado);
    }

    private void configurarEventos() {
        btnGirar.addActionListener(e -> procesarApuesta());

        comboTipoApuesta.addActionListener(e -> {
            String tipo = (String) comboTipoApuesta.getSelectedItem();
            comboSeleccion.removeAllItems();

            if (tipo.equals("Color")) {
                comboSeleccion.addItem("ROJO");
                comboSeleccion.addItem("NEGRO");
            }
            else if (tipo.equals("Paridad")) {
                comboSeleccion.addItem("PAR");
                comboSeleccion.addItem("IMPAR");
            }
            else if (tipo.equals("Número exacto")) {
                for (int i = 0; i <= 36; i++) {
                    comboSeleccion.addItem(String.valueOf(i));
                }
            }
        });

        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                if (accionAlCerrar != null) {
                    accionAlCerrar.run();
                }
            }
        });
    }

    private void procesarApuesta() {
        try {
            int monto = Integer.parseInt(txtMonto.getText());

            if (monto <= 0) {
                lblResultado.setText("Error: La apuesta debe ser mayor a $0.");
                return;
            }
            if (monto > ruletaController.getSaldo()) {
                lblResultado.setText("Error: Fondos insuficientes. Tu saldo es $" + ruletaController.getSaldo());
                return;
            }

            String tipo = (String) comboTipoApuesta.getSelectedItem();
            String seleccion = (String) comboSeleccion.getSelectedItem();

            if (seleccion == null) return;

            // el controlador hace el trabajo y devuelve el resultado
            Resultado r = ruletaController.ejecutarJugada(tipo, seleccion, monto);
            mostrarResultados(r);

        } catch (NumberFormatException ex) {
            lblResultado.setText("Error: Ingrese un monto válido en números.");
        }
    }

    private void mostrarResultados(Resultado r) {
        lblResultado.setText(r.toString());
        lblSaldo.setText("Saldo: $" + ruletaController.getSaldo());
    }

    public void alCerrar(Runnable accion) {
        this.accionAlCerrar = accion;
    }

    public void mostrarVentana() {
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}