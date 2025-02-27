package DiagramaPastel;

import java.util.HashMap;
import java.util.Map;

public class Pregunta {
    private final Map<String, Integer> estadisticas;

    public Pregunta() {
        estadisticas = new HashMap<>();
    }

    // Actualiza la estadística incrementando el contador para la respuesta recibida.
    public void actualizarEstadistica(String respuesta) {
        estadisticas.put(respuesta, estadisticas.getOrDefault(respuesta, 0) + 1);
    }

    // Retorna el mapa de estadísticas.
    public Map<String, Integer> getEstadisticas() {
        return estadisticas;
    }

    // Calcula el total de respuestas recibidas.
    public int getTotalRespuestas() {
        int total = 0;
        for (int count : estadisticas.values()) {
            total += count;
        }
        return total;
    }
}