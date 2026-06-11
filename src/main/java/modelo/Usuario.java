package modelo;

import java.util.List;

public class Usuario {
    private String username;
    private String password;
    private String nombre;
    private final IRepositorioResultados repositorio;
    private final Estadistica estadistica = new Estadistica();

    // Constructor que recibe el repositorio
    public Usuario(String username, String password, String nombre, IRepositorioResultados repositorio) {
        this.username = username;
        this.password = password;
        this.nombre = nombre;
        this.repositorio = repositorio;
    }

    public void agregarResultado(Resultado r) {
        if (r != null) {
            repositorio.agregar(r);
            estadistica.calcular(repositorio); // Le pasamos la interfaz a estadística
        }
    }

    public List<Resultado> getHistorial() {
        return repositorio.obtenerTodos();
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

    public Estadistica getEstadistica() { return estadistica; }
}