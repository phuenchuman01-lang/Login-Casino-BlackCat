package controlador;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class SessionControllerTest {

    @Test
    void testInicioSesionUsuarioNoRegistrado() {
        SessionController session = new SessionController();
        boolean accesoConcedido = session.iniciarSesion("usuarioFantasma", "clave123");
        assertFalse(accesoConcedido, "El sistema debe rechazar el inicio de sesión de un usuario que no existe");
    }

    @Test
    void testRechazaUsernameNulo() {
        SessionController session = new SessionController();

        // Verifica que no se pueda registrar un usuario nulo
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            session.registrarUsuario(null, "password", "Juan Perez");
        });
        assertEquals("Error: Todos los campos son obligatorios.", exception.getMessage());

        // Verifica que se rechace un login con usuario nulo
        session.registrarUsuario("usuarioReal", "pass123", "Juan Real");
        boolean accesoNulo = session.iniciarSesion(null, "pass123");
        assertFalse(accesoNulo, "El sistema debe denegar el acceso si se envía un username nulo");
    }
}