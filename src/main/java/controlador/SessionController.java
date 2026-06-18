package controlador;

import modelo.Usuario;
import modelo.IRepositorioResultados;
import modelo.RepositorioArchivo;
import java.util.ArrayList;
import java.util.List;

public class SessionController {
    private final List<Usuario> usuariosRegistrados = new ArrayList<>();

    // Memoria de quién está jugando actualmente
    private Usuario usuarioActual;

    // --- MÉTODOS DE NEGOCIO ---

    public void registrarUsuario(String u, String p, String n) {
        // CASO 1: Regla de dominio (Excepción si el usuario ya existe)
        // La validación de campos vacíos ahora se hace en la Vista.
        for (Usuario usuario : usuariosRegistrados) {
            if (usuario.getUsername().equalsIgnoreCase(u)) {
                throw new IllegalStateException("El usuario '" + u + "' ya se encuentra registrado.");
            }
        }

        IRepositorioResultados repo = new RepositorioArchivo(u);
        usuariosRegistrados.add(new Usuario(u, p, n, repo));
    }

    public void iniciarSesion(String u, String p) {
        // Busca si el usuario existe y la clave es correcta
        for (Usuario usuario : usuariosRegistrados) {
            if (usuario.validarCredenciales(u, p)) {
                this.usuarioActual = usuario;
                return;
            }
        }
        // CASO 1: Regla de dominio, Excepción si las credenciales fallan
        throw new IllegalStateException("Credenciales incorrectas o usuario no registrado.");
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