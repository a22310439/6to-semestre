import java.awt.Color;
import java.awt.image.BufferedImage;
import javax.swing.JFrame;

public class RectasDDA extends JFrame {
    
    private final BufferedImage buffer;

    public RectasDDA() {
        setTitle("Practica DDA");
        setSize(600, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

        buffer = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);

        // Líneas de prueba
        drawLine(30, 150, 280, 150, Color.BLACK);
        drawLine(450, 50, 450, 280, Color.BLACK);
        drawLine(30, 580, 280, 330, Color.BLACK);
        drawLine(330, 330, 580, 580, Color.BLACK);
    }

    /** Dibuja un píxel en la ventana */
    public void drawPixel(int x, int y, Color c) {
        buffer.setRGB(0, 0, c.getRGB());
        this.getGraphics().drawImage(buffer, x, y, this);
    }

    /**
     * Dibuja una línea usando el algoritmo DDA
     */
    public final void drawLine(int x0, int y0, int x1, int y1, Color c) {
        int dx = x1 - x0;
        int dy = y1 - y0;

        // Número de pasos = la mayor diferencia en coordenadas
        int pasos = Math.max(Math.abs(dx), Math.abs(dy));

        // Incremento en cada paso
        double xInc = dx / (double) pasos;
        double yInc = dy / (double) pasos;

        double x = x0;
        double y = y0;

        for (int i = 0; i <= pasos; i++) {
            drawPixel((int) Math.round(x), (int) Math.round(y), c);
            x += xInc;
            y += yInc;
        }
    }

    @SuppressWarnings("unused")
    public static void main(String[] args) {
        RectasDDA ventana = new RectasDDA();
    }
}
