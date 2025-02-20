package EspiralArquimedes;

import javax.swing.JPanel;

import java.awt.Graphics;

class EspiralPanel extends JPanel {
    // Variables para controlar la animación de la espiral
    private double theta = 0.0;
    private final double a = 0.0;
    private final double b = 1.0;
    private final double maxTheta = 70 * Math.PI; // Maximo de vueltas
    private final double incremento = 0.01; // Incremento del ángulo en cada iteración

    // Métodos para controlar el avance de la espiral
    public synchronized double getTheta() {
        return theta;
    }

    public synchronized void incrementTheta() {
        theta += incremento;
    }

    public synchronized boolean canIncrement() {
        return theta < maxTheta;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Centro del panel
        int centroX = getWidth() / 2;
        int centroY = getHeight() / 2;

        // Variables para almacenar el último punto de cada arco
        int prevX1 = centroX;
        int prevY1 = centroY;
        int prevX2 = centroX;
        int prevY2 = centroY;

        // Dibujar dos arcos: uno y su reflejo (rotado 180°)
        for (double t = incremento; t < theta; t += incremento) {
            double r = a + b * t;
            int x1 = centroX + (int) (r * Math.cos(t));
            int y1 = centroY + (int) (r * Math.sin(t));
            g.drawLine(prevX1, prevY1, x1, y1);
            prevX1 = x1;
            prevY1 = y1;

            // Segundo arco: usando t + PI para el reflejo
            int x2 = centroX + (int) (r * Math.cos(t + Math.PI));
            int y2 = centroY + (int) (r * Math.sin(t + Math.PI));
            g.drawLine(prevX2, prevY2, x2, y2);
            prevX2 = x2;
            prevY2 = y2;
        }
    }
}
