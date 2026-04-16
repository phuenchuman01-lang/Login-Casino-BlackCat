package Modelo;

public class Usuario {
    private String username;
    private String password;
    private String nombre;

    // Constructor con parámetros
    public Usuario(String username, String password, String nombre) {
        this.username = username;
        this.password = password;
        this.nombre = nombre;
    }

    // Constructor sin parámetros (Sobrecarga / Invitado por defecto)
    public Usuario() {
        this("invitado", "123", "Invitado"); // Llama al constructor de arriba
    }

    public boolean validarCredenciales(String u, String p) {
        return this.username.equals(u) && this.password.equals(p);
    }

    // --- GETTERS (Para leer los datos de forma segura) ---
    public String getUsername() {
        return username;
    }

    public String getNombre() {
        return nombre;
    }

    // --- SETTERS (Para modificar datos aplicando reglas lógicas) ---
    public void setNombre(String nuevoNombre) {
        // Validación: El nombre no puede ser nulo ni estar vacío
        if (nuevoNombre != null && !nuevoNombre.trim().isEmpty()) {
            this.nombre = nuevoNombre;
        } else {
            throw new IllegalArgumentException("Error: El nombre no puede estar vacío.");
        }
    }
}