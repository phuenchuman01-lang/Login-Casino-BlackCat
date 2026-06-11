package modelo;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class EstadisticaTest {

    @Test
    void testCalculoEstadisticasConHistorialMixto() {
        RepositorioEnMemoria repo = new RepositorioEnMemoria();

        ApuestaColor apuestaColor1 = new ApuestaColor(100, "ROJO");
        ApuestaColor apuestaColor2 = new ApuestaColor(100, "NEGRO");
        ApuestaNumero apuestaNum = new ApuestaNumero(50, "15");

        repo.agregar(new Resultado(1, "Rojo", true, apuestaColor1)); // Victoria 1
        repo.agregar(new Resultado(2, "Negro", true, apuestaColor2)); // Victoria 2
        repo.agregar(new Resultado(3, "Rojo", false, apuestaNum));    // Derrota

        Estadistica est = new Estadistica();
        est.calcular(repo);

        assertEquals(3, est.getTotalJugadas(), "El total de jugadas debe ser 3");
        assertEquals(2, est.getVictorias(), "Deben registrarse exactamente 2 victorias");
        assertEquals(2, est.getRachaMaxima(), "La racha máxima de victorias consecutivas debe ser 2");
        assertEquals((2.0 / 3.0) * 100, est.getPorcentajeVictorias(), 0.01, "El porcentaje de victorias debe ser aprox 66.66%");
        assertEquals("Color", est.getTipoMasJugado(), "El tipo más jugado debe ser 'Color'");
    }
}