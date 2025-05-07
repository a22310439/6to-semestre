import java.awt.Color;
import javax.swing.JFrame;

public class CircunferenciaPuntoMedio extends JFrame {
    private final Graficos g = new Graficos(this);

    public CircunferenciaPuntoMedio() {
        setTitle("Circunferencia con el algoritmo del punto medio");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        drawCircle(400, 300, 250);
    }

    public final void drawCircle(int xc, int yc, int r) {
        int x = 0, y = r;
        int d = 1 - r;
    
        plot8(xc, yc, x, y);
    
        while (x < y) {
            if (d < 0) {
                d += 2*x + 3;
            } else {
                d += 2*(x - y) + 5;
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
        CircunferenciaPuntoMedio circunferencia = new CircunferenciaPuntoMedio();
    }
}