package vista;

import modelo.Ruleta;
import modelo.TipoApuesta;
import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class VentanaJuego {

    private final JFrame frame = new JFrame("Mesa de Ruleta - Black Cat");
    private final Ruleta motorJuego; // Recibido desde el menú

    private final JComboBox<String> comboTipoApuesta = new JComboBox<>(new String[]{"Color", "Paridad", "Número exacto"});
    private final JComboBox<String> comboSeleccion = new JComboBox<>(new String[]{"ROJO", "NEGRO"}); // Cambiado para el Enum
    private final JTextField txtMonto = new JTextField();
    private final JButton btnGirar = new JButton("Girar");
    private final JLabel lblResultado = new JLabel("Esperando apuesta...");
    private final JLabel lblSaldo;

    private Runnable accionAlCerrar; // Para avisarle al menú que actualice el saldo

    public VentanaJuego(String nombreJugador, Ruleta motorJuego) {
        this.motorJuego = motorJuego; // Usamos la ruleta compartida
        this.lblSaldo = new JLabel("Saldo: $" + motorJuego.getSaldo());

        armarVentana();
        configurarEventos();
    }

    private void armarVentana() {
        frame.setSize(600, 300);
        // Evitamos que cierre TODA la app al darle a la 'X', solo cierra esta ventana
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
                // Valores exactos del ENUM
                comboSeleccion.addItem("ROJO");
                comboSeleccion.addItem("NEGRO");
            }
            else if (tipo.equals("Paridad")) {
                // Valores exactos del ENUM
                comboSeleccion.addItem("PAR");
                comboSeleccion.addItem("IMPAR");
            }
            else if (tipo.equals("Número exacto")) {
                for (int i = 0; i <= 36; i++) {
                    comboSeleccion.addItem(String.valueOf(i));
                }
            }
        });

        // Evento para avisar al menú cuando cerramos esta ventana
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
            if (monto > motorJuego.getSaldo()) {
                lblResultado.setText("Error: Fondos insuficientes. Tu saldo es $" + motorJuego.getSaldo());
                return;
            }

            String tipo = (String) comboTipoApuesta.getSelectedItem();
            String seleccion = (String) comboSeleccion.getSelectedItem();

            if (seleccion == null) return;

            ejecutarGiro(tipo, seleccion, monto);

        } catch (NumberFormatException ex) {
            lblResultado.setText("Error: Ingrese un monto válido en números.");
        }
    }

    private void ejecutarGiro(String tipo, String seleccion, int monto) {
        int numeroGanador = motorJuego.girarCilindro();
        boolean gano = false;

        if (tipo.equals("Número exacto")) {
            try {
                int numElegido = Integer.parseInt(seleccion);
                gano = motorJuego.evaluarApuestaNumero(numeroGanador, numElegido, monto);
            } catch (NumberFormatException e) {
                lblResultado.setText("Error: Seleccione un número válido.");
                return;
            }
        } else {
            // MAGIA DEL ENUM: Convertimos el texto seleccionado directamente al Enum
            TipoApuesta enumApuesta = TipoApuesta.valueOf(seleccion);
            gano = motorJuego.evaluarApuesta(numeroGanador, enumApuesta, monto);
        }

        mostrarResultados(numeroGanador, monto, gano);
    }

    private void mostrarResultados(int numeroGanador, int monto, boolean gano) {
        String colorStr = motorJuego.obtenerColor(numeroGanador);
        String estado = gano ? "GANASTE" : "PERDISTE";

        String mensaje = String.format("Número: %d (%s) | Monto: $%d | %s",
                numeroGanador, colorStr, monto, estado);

        Ruleta.historialGlobal.add(mensaje);

        lblResultado.setText(mensaje);
        lblSaldo.setText("Saldo: $" + motorJuego.getSaldo());
    }

    // Método de utilidad para que el Menú se entere cuando esto se cierra
    public void alCerrar(Runnable accion) {
        this.accionAlCerrar = accion;
    }

    public void mostrarVentana() {
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}