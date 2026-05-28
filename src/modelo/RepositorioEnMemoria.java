package modelo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RepositorioEnMemoria implements IRepositorioResultados {
    private final List<Resultado> historial = new ArrayList<>();

    @Override
    public void agregar(Resultado resultado) {
        if (resultado != null) {
            historial.add(resultado);
        }
    }

    @Override
    public List<Resultado> obtenerTodos() {
        return Collections.unmodifiableList(historial);
    }
}