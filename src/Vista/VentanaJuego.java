package Vista;

import Modelo.Ruleta;

import javax.swing.*;
import java.awt.*;

public class VentanaJuego {

    private final JFrame frame = new JFrame("Mesa de Modelo.Ruleta - Black Cat");
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
        // El botón de girar que ya tenías
        btnGirar.addActionListener(e -> procesarApuesta());

        // EL NUEVO TRUCO: Escuchar cuando cambias el tipo de apuesta
        comboTipoApuesta.addActionListener(e -> {
            String tipo = (String) comboTipoApuesta.getSelectedItem();

            comboColor.removeAllItems(); // Limpiamos las opciones viejas

            if (tipo.equals("Color")) {
                comboColor.addItem("Rojo");
                comboColor.addItem("Negro");
            }
            else if (tipo.equals("Paridad")) {
                comboColor.addItem("Par");
                comboColor.addItem("Impar");
            }
            else if (tipo.equals("Número exacto")) {
                // Un ciclo 'for' rápido para agregar los números del 0 al 36
                for (int i = 0; i <= 36; i++) {
                    comboColor.addItem(String.valueOf(i));
                }
            }
        });
    }

    private void procesarApuesta() {
        try {
            int monto = Integer.parseInt(txtMonto.getText());

            // --- NUEVO: CANDADO DE SEGURIDAD ---
            if (monto <= 0) {
                lblResultado.setText("Error: La apuesta debe ser mayor a $0.");
                return; // El 'return' expulsa al jugador del método, cancelando la apuesta
            }
            if (monto > motorJuego.getSaldo()) {
                lblResultado.setText("Error: Fondos insuficientes. Tu saldo es $" + motorJuego.getSaldo());
                return; // Cancelamos la apuesta
            }
            // -----------------------------------

            String tipo = (String) comboTipoApuesta.getSelectedItem();
            String seleccion = (String) comboColor.getSelectedItem();

            // Pequeño parche: Evita un error si el menú secundario se está actualizando
            if (seleccion == null) return;

            ejecutarGiro(tipo, seleccion, monto);

        } catch (NumberFormatException ex) {
            lblResultado.setText("Error: Ingrese un monto válido en números.");
        }
    }

    private void ejecutarGiro(String tipo, String seleccion, int monto) {
        int numeroGanador = motorJuego.girarCilindro();
        boolean gano = false;

        if (tipo.equals("Color")) {
            gano = motorJuego.evaluarApuestaColor(numeroGanador, seleccion, monto);
        }
        else if (tipo.equals("Paridad")) {
            gano = motorJuego.evaluarApuestaParidad(numeroGanador, seleccion, monto);
        }
        else if (tipo.equals("Número exacto")) {
            // Convertimos la selección de texto a un número entero
            try {
                int numElegido = Integer.parseInt(seleccion);
                gano = motorJuego.evaluarApuestaNumero(numeroGanador, numElegido, monto);
            } catch (NumberFormatException e) {
                lblResultado.setText("Error: Para Número exacto, seleccione un número.");
                return; // Cortamos la ejecución aquí
            }
        }

        mostrarResultados(numeroGanador, monto, gano);
    }

    private void mostrarResultados(int numeroGanador, int monto, boolean gano) {
        String colorStr = motorJuego.obtenerColor(numeroGanador);
        String estado = gano ? "GANASTE" : "PERDISTE";

        String mensaje = String.format("Número: %d (%s) | Monto: $%d | %s",
                numeroGanador, colorStr, monto, estado);

        //Guardamos el registro en la memoria
        Ruleta.historialGlobal.add(mensaje);

        lblResultado.setText(mensaje);
        lblSaldo.setText("Saldo: $" + motorJuego.getSaldo());
    }

    public void mostrarVentana() {
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}