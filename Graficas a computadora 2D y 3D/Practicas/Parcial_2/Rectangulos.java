import java.awt.Color;
import javax.swing.JFrame;

public class Rectangulos extends JFrame{
    
    private final Graficos g = new Graficos(this);

    public Rectangulos() {
        setTitle("Rectangulos");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        drawRectangle(200, 100, 600, 500);
    }

    public final void drawRectangle(int x0, int y0, int x1, int y1) {
        g.drawLine(x1, y0, x1, y1, Color.BLACK);
        g.drawLine(x1, y1, x0, y1, Color.BLACK);
        g.drawLine(x0, y1, x0, y0, Color.BLACK);
        g.drawLine(x0, y0, x1, y0, Color.BLACK);
    }

    @SuppressWarnings("unused")
    public static void main(String[] args) {
        Rectangulos rectangulos = new Rectangulos();
    }

}
