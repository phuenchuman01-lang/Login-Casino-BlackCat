package modelo;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class RepositorioArchivo implements IRepositorioResultados {
    private final String rutaArchivo;
    private final List<Resultado> cacheMemoria;

    public RepositorioArchivo(String username) {
        this.rutaArchivo = "historial_" + username + ".csv";
        this.cacheMemoria = new ArrayList<>();
        cargarDesdeArchivo();
    }

    @Override
    public void agregar(Resultado resultado) {
        if (resultado != null) {
            cacheMemoria.add(resultado);
            guardarEnArchivo(resultado);
        }
    }

    @Override
    public List<Resultado> obtenerTodos() {
        return cacheMemoria;
    }

    private void guardarEnArchivo(Resultado r) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(rutaArchivo, true))) {
            // Formato CSV
            String linea = r.getNumeroGanador() + "," +
                    r.getColorGanador() + "," +
                    r.isGano() + "," +
                    r.getApuesta().getClass().getSimpleName() + "," +
                    r.getApuesta().getMonto() + "," +
                    r.getApuesta().getSeleccion();
            bw.write(linea);
            bw.newLine();
        } catch (IOException e) {
            System.err.println("Error al guardar en el historial: " + e.getMessage());
        }
    }

    private void cargarDesdeArchivo() {
        File archivo = new File(rutaArchivo);

        // CASO 4: Verificación normal antes de usar el archivo
        if (!archivo.exists()) return; // Es un usuario nuevo sin historial
        if (!archivo.canRead()) {
            System.err.println("Advertencia: Sin permisos de lectura para el historial.");
            return;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(",");
                // CASO 7: Checks ligeros (cantidad de campos)
                if (datos.length == 6) {
                    try {
                        int numGanador = Integer.parseInt(datos[0]);
                        String colorGanador = datos[1];
                        boolean gano = Boolean.parseBoolean(datos[2]);
                        String claseApuesta = datos[3];
                        int monto = Integer.parseInt(datos[4]);
                        String seleccion = datos[5];

                        ApuestaBase apuesta;
                        if (claseApuesta.equals("ApuestaColor")) {
                            apuesta = new ApuestaColor(monto, seleccion);
                        } else if (claseApuesta.equals("ApuestaParidad")) {
                            apuesta = new ApuestaParidad(monto, seleccion);
                        } else {
                            apuesta = new ApuestaNumero(monto, seleccion);
                        }

                        cacheMemoria.add(new Resultado(numGanador, colorGanador, gano, apuesta));
                    } catch (NumberFormatException e) {
                        // CASO 7: Se captura localmente y se continúa con el resto del historial
                        System.err.println("Línea corrupta detectada e ignorada.");
                    }
                }
            }
        } catch (IOException e) {
            // CASO 4: Excepción por fallo de E/S del sistema
            System.err.println("Error crítico del sistema de archivos al cargar: " + e.getMessage());
        }
    }
}