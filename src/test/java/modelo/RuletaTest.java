package modelo;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class RuletaTest {

    @Test
    void testConstructorRechazaSaldoNegativo() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Ruleta(-500);
        });
        assertEquals("Saldo inicial inválido", exception.getMessage(), "Debe rechazar saldos menores a 0");
    }

    @Test
    void testDepositarIncrementaSaldo() {
        Ruleta ruleta = new Ruleta(1000);
        ruleta.depositar(500);
        assertEquals(1500, ruleta.getSaldo(), "El saldo debe ser la suma del saldo anterior más el monto");
    }

    @Test
    void testApuestaNulaEsRechazada() {
        Ruleta ruleta = new Ruleta(1000);
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            ruleta.evaluarApuesta(15, null);
        });
        assertEquals("Apuesta requerida", exception.getMessage(), "No se puede evaluar una apuesta nula");
    }

    @Test
    void testApuestaMontoMayorAlSaldo() {
        Ruleta ruleta = new Ruleta(500);
        ApuestaColor apuestaAlta = new ApuestaColor(1000, "ROJO");
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            ruleta.evaluarApuesta(10, apuestaAlta);
        });
        assertEquals("Saldo insuficiente", exception.getMessage(), "Debe impedir apostar más dinero del disponible");
    }
}