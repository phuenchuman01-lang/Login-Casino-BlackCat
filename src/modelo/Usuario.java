package modelo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Usuario {
    private String username;
    private String password;
    private String nombre;

    // Asociación 1:N
    private final List<Resultado> historial = new ArrayList<>();

    public Usuario(String username, String password, String nombre) {
        this.username = username;
        this.password = password;
        this.nombre = nombre;
    }

    public Usuario() {
        this("invitado", "123", "Invitado");
    }

    public void agregarResultado(Resultado r) {
        if (r != null) {
            historial.add(r);
        }
    }

    public List<Resultado> getHistorial() {
        // Encapsulamiento: devolvemos una lista que no se puede modificar desde afuera
        return Collections.unmodifiableList(historial);
    }

    public boolean validarCredenciales(String u, String p) {
        return this.username.equals(u) && this.password.equals(p);
    }

    public String getUsername() { return username; }
    public String getNombre() { return nombre; }

    public void setNombre(String nuevoNombre) {
        if (nuevoNombre != null && !nuevoNombre.trim().isEmpty()) {
            this.nombre = nuevoNombre;
        } else {
            throw new IllegalArgumentException("Error: El nombre no puede estar vacío.");
        }
    }
}