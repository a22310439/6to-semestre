import java.awt.Color;
import java.awt.image.BufferedImage;
import javax.swing.JFrame;

public class Rectas extends JFrame {
    
    private final BufferedImage buffer;

    public Rectas() {
        setTitle("Practica 1 - Rectas");
        setSize(600, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

        buffer = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);

        drawLine(30, 150, 280, 150, Color.BLACK);
        drawLine(450, 50, 450, 280, Color.BLACK);
        //drawLine(200, 580, 280, 330, Color.BLACK);
        //drawLine(330, 330, 580, 580, Color.BLACK);

        drawLine(200, 580, 280, 330, Color.BLACK);
        drawLine(580, 580, 330, 330, Color.BLACK);
    }

    public void drawPixel(int x, int y, Color c) {
        buffer.setRGB(0, 0, c.getRGB());
        this.getGraphics().drawImage(buffer, x, y, this);
    }

    public final void drawLine(int x0, int y0, int x1, int y1, Color c) {
        int dy = (y1 - y0);
        int dx = (x1 - x0);

        if (dx == 0) { // Línea vertical
            for (int y = y0; y != y1; y ++) {
                drawPixel(x0, y, c);
            }
            drawPixel(x1, y1, c);
        } else if (dy == 0) { // Línea horizontal
            for (int x = x0; x != x1; x ++) {
                drawPixel(x, y0, c);
            }
            drawPixel(x1, y1, c);
        } else { // Línea inclinada
            double m = (double) dy / dx;

            for (int x = x0; x != x1; x ++) {
                int y = (int) Math.round(m * (x - x0) + y0);
                drawPixel(x, y, c);
            }
            drawPixel(x1, y1, c);
        }
    }

    @SuppressWarnings("unused")
    public static void main(String[] args) {
        Rectas rectas = new Rectas();
    }
}
