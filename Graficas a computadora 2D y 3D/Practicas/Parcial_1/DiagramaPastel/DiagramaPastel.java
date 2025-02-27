package DiagramaPastel;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.util.Map;

class DiagramaPastel extends Component {
    private final Pregunta pregunta;

    public DiagramaPastel(Pregunta pregunta) {
        this.pregunta = pregunta;
    }

    // Método para agregar una respuesta contestada.
    public void agregarRespuesta(String respuesta) {
        pregunta.actualizarEstadistica(respuesta);
        // Se fuerza el repintado del componente para actualizar el diagrama.
        repaint();
    }

    @Override
    public void paint(Graphics g) {
        int total = pregunta.getTotalRespuestas();
        if (total == 0) {
            g.drawString("No hay datos", 10, 20);
            return;
        }

        // Se define la posición y el tamaño del diagrama.
        int x = 15;
        int y = 0;
        int diameter = 200;
        int startAngle = 0;

        // Se obtienen las estadísticas (opciones y sus conteos).
        Map<String, Integer> estadisticas = pregunta.getEstadisticas();

        // Se dibuja un arco por cada opción, calculando el ángulo correspondiente.
        for (Map.Entry<String, Integer> entry : estadisticas.entrySet()) {
            int count = entry.getValue();
            int angle = (int) Math.round((count * 360.0) / total);

            g.setColor(getColorForOption(entry.getKey()));
            g.fillArc(x, y, diameter, diameter, startAngle, angle);

            startAngle += angle;
        }
    }

    // Método auxiliar para asignar un color en función de la opción (utilizando el hash de la cadena).
    private Color getColorForOption(String opcion) {
        int hash = Math.abs(opcion.hashCode());
        return new Color((hash >> 16) % 256, (hash >> 8) % 256, hash % 256);
    }
}