package DiagramaPastel;

import javax.swing.JFrame;

public class Main {
    public static void main(String[] args) {
        // Se crea el objeto Pregunta que almacenará las estadísticas.
        Pregunta pregunta = new Pregunta();

        // Se crea el componente DiagramaPastel asociado a la pregunta.
        DiagramaPastel diagrama = new DiagramaPastel(pregunta);

        // Configuración del JFrame para visualizar el diagrama.
        JFrame frame = new JFrame("Diagrama de Pastel");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(245, 245);
        frame.add(diagrama);
        frame.setVisible(true);

        // Simulación de respuestas contestadas, lo que actualiza el diagrama.
        diagrama.agregarRespuesta("Manzana");
        diagrama.agregarRespuesta("Manzana");
        diagrama.agregarRespuesta("Platano");
        diagrama.agregarRespuesta("Platano");
        diagrama.agregarRespuesta("Sandia");
        diagrama.agregarRespuesta("Sandia");
    }
}