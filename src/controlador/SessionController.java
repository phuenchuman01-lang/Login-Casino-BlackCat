package controlador;

import modelo.Usuario;

import java.util.ArrayList;
import java.util.List;

public class SessionController {

    //La base de datos de usuarios ahora vive en el Controlador
    private final List<Usuario> usuariosRegistrados = new ArrayList<>();

    //Memoria de quién está jugando actualmente
    private Usuario usuarioActual;

    // --- MÉTODOS DE NEGOCIO ---

    public void registrarUsuario(String u, String p, String n) {
        // Validación de seguridad
        if (u == null || u.isBlank() || p == null || p.isBlank() || n == null || n.isBlank()) {
            throw new IllegalArgumentException("Error: Todos los campos son obligatorios.");
        }
        // Crear y guardar al nuevo usuario
        usuariosRegistrados.add(new Usuario(u, p, n));
    }

    public boolean iniciarSesion(String u, String p) {
        // Buscamos si el usuario existe y la clave es correcta
        for (Usuario usuario : usuariosRegistrados) {
            if (usuario.validarCredenciales(u, p)) {
                this.usuarioActual = usuario; // Inicia sesión
                return true;
            }
        }
        return false; // Credenciales incorrectas
    }

    public void cerrarSesion() {
        this.usuarioActual = null;
    }

    // --- GETTERS ---

    public boolean hayUsuario() {
        return usuarioActual != null;
    }

    public String getNombreUsuario() {
        return hayUsuario() ? usuarioActual.getNombre() : "";
    }

    public Usuario getUsuarioActual() {
        return usuarioActual;
    }
}
