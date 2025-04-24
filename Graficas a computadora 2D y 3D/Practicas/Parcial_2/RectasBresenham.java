import java.awt.Color;
import java.awt.image.BufferedImage;
import javax.swing.JFrame;

public class RectasBresenham extends JFrame {
    
    private final BufferedImage buffer;

    public RectasBresenham() {
        setTitle("Práctica 1 – Bresenham");
        setSize(600, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

        buffer = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);

        // Dibuja algunas líneas de ejemplo
        drawLine(30, 150, 280, 150, Color.BLACK);
        drawLine(450, 50, 450, 280, Color.BLACK);
        drawLine(30, 580, 280, 330, Color.BLACK);
        drawLine(330, 330, 580, 580, Color.BLACK);

        setVisible(true);
    }

    /** Dibuja un solo píxel en la ventana */
    public void drawPixel(int x, int y, Color c) {
        buffer.setRGB(0, 0, c.getRGB());
        this.getGraphics().drawImage(buffer, x, y, this);
    }

    /**
     * Dibuja una línea entre (x0,y0) y (x1,y1) usando Bresenham
     */
    public final void drawLine(int x0, int y0, int x1, int y1, Color c) {
        int dx = Math.abs(x1 - x0);
        int dy = Math.abs(y1 - y0);
        int sx = x0 < x1 ? 1 : -1;  // paso en x
        int sy = y0 < y1 ? 1 : -1;  // paso en y
        int err = dx - dy;
        
        while (true) {
            drawPixel(x0, y0, c);
            if (x0 == x1 && y0 == y1) break;
            int e2 = 2 * err;
            if (e2 > -dy) {
                err -= dy;
                x0 += sx;
            }
            if (e2 < dx) {
                err += dx;
                y0 += sy;
            }
        }
    }

    @SuppressWarnings("unused")
    public static void main(String[] args) {
        RectasBresenham frame = new RectasBresenham();
    }
}
