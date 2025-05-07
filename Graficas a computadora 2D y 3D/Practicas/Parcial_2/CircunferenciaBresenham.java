import java.awt.Color;
import javax.swing.JFrame;

public class CircunferenciaBresenham extends JFrame {
    private final Graficos g = new Graficos(this);

    public CircunferenciaBresenham() {
        setTitle("Circunferencia con el algoritmo de Bresenham");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        drawCircle(400, 300, 250);
    }

    public final void drawCircle(int xc, int yc, int r) {
        int x = 0, y = r;
        int d = 3 - 2 * r;

        plot8(xc, yc, x, y);

        while (x < y) {
            if (d < 0) {
                d = d + 4 * x + 6;
            } else {
                d = d + 4 * (x - y) + 10;
                y--;
            }
            x++;
            plot8(xc, yc, x, y);
        }
    }

    // Helper para no repetir código
    private void plot8(int xc, int yc, int x, int y) {
        g.drawPixel(xc + x, yc + y, Color.BLACK);
        g.drawPixel(xc - x, yc + y, Color.BLACK);
        g.drawPixel(xc - x, yc - y, Color.BLACK);
        g.drawPixel(xc + x, yc - y, Color.BLACK);
        g.drawPixel(xc + y, yc + x, Color.BLACK);
        g.drawPixel(xc - y, yc + x, Color.BLACK);
        g.drawPixel(xc - y, yc - x, Color.BLACK);
        g.drawPixel(xc + y, yc - x, Color.BLACK);
    }
    
    @SuppressWarnings("unused")
    public static void main(String[] args) {
        CircunferenciaBresenham circunferencia = new CircunferenciaBresenham();
    }
}
