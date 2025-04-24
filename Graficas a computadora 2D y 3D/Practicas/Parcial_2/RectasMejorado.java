import java.awt.Color;
import java.awt.image.BufferedImage;
import javax.swing.JFrame;

public class RectasMejorado extends JFrame {
    
    private final BufferedImage buffer;

    public RectasMejorado() {
        setTitle("Practica 2 – Rectas con Modelo Mejorado");
        setSize(600, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

        buffer = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);

        // Casos de prueba
        drawLine(30, 150, 280, 150, Color.BLACK);
        drawLine(450, 50, 450, 280, Color.BLACK);
        drawLine(200, 580, 280, 330, Color.BLACK);
        drawLine(580, 580, 330, 330, Color.BLACK);
    }

    public void drawPixel(int x, int y, Color c) {
        buffer.setRGB(0, 0, c.getRGB());
        this.getGraphics().drawImage(buffer, x, y, this);
    }

    public final void drawLine(int x0, int y0, int x1, int y1, Color c) {
        // Normalizar: siempre dibujar de izquierda a derecha (x0 < x1)
        if (x0 > x1) {
            int tempX = x0; x0 = x1; x1 = tempX;
            int tempY = y0; y0 = y1; y1 = tempY;
        }

        int dx = x1 - x0;
        int dy = y1 - y0;

        // Casos especiales: vertical y horizontal
        if (dx == 0) { // Línea vertical
            int step = (y0 < y1) ? 1 : -1;
            for (int y = y0; y != y1; y += step) {
                drawPixel(x0, y, c);
            }
            drawPixel(x1, y1, c);
        } else if (dy == 0) { // Línea horizontal
            for (int x = x0; x <= x1; x++) {
                drawPixel(x, y0, c);
            }
        } else {
            double m = (double) dy / dx;

            if (Math.abs(m) <= 1) {
                // Iterar sobre X (pendiente suave)
                double y = y0;
                for (int x = x0; x <= x1; x++) {
                    drawPixel(x, (int) Math.round(y), c);
                    y += m;
                }
            } else {
                // Iterar sobre Y (pendiente empinada), también normalizar Y
                if (y0 > y1) {
                    int tempY = y0; y0 = y1; y1 = tempY;
                }
                double invM = (double) dx / dy;
                double x = x0;
                for (int y = y0; y <= y1; y++) {
                    drawPixel((int) Math.round(x), y, c);
                    x += invM;
                }
            }
        }
    }

    @SuppressWarnings("unused")
    public static void main(String[] args) {
        RectasMejorado rectas = new RectasMejorado();
    }
}
