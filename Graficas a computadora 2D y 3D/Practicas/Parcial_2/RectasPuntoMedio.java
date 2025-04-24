import java.awt.Color;
import java.awt.image.BufferedImage;
import javax.swing.JFrame;

public class RectasPuntoMedio extends JFrame {

    private final BufferedImage buffer;

    public RectasPuntoMedio() {
        setTitle("Práctica 1 – Punto Medio");
        setSize(600, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

        buffer = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);

        // Ejemplos de líneas
        drawLine(30, 150, 280, 150, Color.BLACK);     // Horizontal
        drawLine(450, 50, 450, 280, Color.BLACK);     // Vertical
        drawLine(30, 580, 280, 330, Color.BLACK);     // Diagonal descendente
        drawLine(330, 330, 580, 580, Color.BLACK);    // Diagonal ascendente
    }

    public void drawPixel(int x, int y, Color c) {
        buffer.setRGB(0, 0, c.getRGB());
        this.getGraphics().drawImage(buffer, x, y, this);
    }

    /** Dibuja una línea usando el algoritmo de Punto Medio */
    public final void drawLine(int x0, int y0, int x1, int y1, Color c) {
        int dx = x1 - x0;
        int dy = y1 - y0;

        int sx = (dx < 0) ? -1 : 1;
        int sy = (dy < 0) ? -1 : 1;

        dx = Math.abs(dx);
        dy = Math.abs(dy);

        int x = x0;
        int y = y0;

        // Línea más horizontal (pendiente <= 1)
        if (dx > dy) {
            int d = 2 * dy - dx;
            int incrE = 2 * dy;
            int incrNE = 2 * (dy - dx);
            drawPixel(x, y, c);

            while (x != x1) {
                x += sx;
                if (d < 0) {
                    d += incrE;
                } else {
                    y += sy;
                    d += incrNE;
                }
                drawPixel(x, y, c);
            }
        } else {      // Línea más vertical (pendiente > 1)
            int d = 2 * dx - dy;
            int incrE = 2 * dx;
            int incrNE = 2 * (dx - dy);
            drawPixel(x, y, c);

            while (y != y1) {
                y += sy;
                if (d < 0) {
                    d += incrE;
                } else {
                    x += sx;
                    d += incrNE;
                }
                drawPixel(x, y, c);
            }
        }
    }

    @SuppressWarnings("unused")
    public static void main(String[] args) {
        RectasPuntoMedio rectas = new RectasPuntoMedio();
    }
}
