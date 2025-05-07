import java.awt.Color;
import javax.swing.JFrame;

public class Figuras extends JFrame {
    Graficos g = new Graficos(this);

    public Figuras() {
        setTitle("Figuras");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

        g.drawLine(10, 10, 190, 250, Color.BLACK);
        g.drawLine(210, 150, 390, 150, Color.BLACK);
        g.drawLine(590, 10, 410, 250, Color.BLACK);
        g.drawLine(790, 150, 610, 150, Color.BLACK);

        g.drawCircle(100, 450, 24, Color.BLACK);
        g.drawCircle(100, 450, 46, Color.BLACK);
        g.drawCircle(100, 450, 68, Color.BLACK);
        g.drawCircle(100, 450, 90, Color.BLACK);
        g.drawRectangle(266, 445, 334, 455, Color.BLACK);
        g.drawRectangle(367, 488, 233, 411, Color.BLACK);
        g.drawElipse(600, 450, 50, 10, Color.BLACK);
        g.drawElipse(600, 450, 90, 47, Color.BLACK);
        g.drawElipse(600, 450, 140, 84, Color.BLACK);
        g.drawElipse(600, 450, 190, 121, Color.BLACK);
    }
    
    @SuppressWarnings("unused")
    public static void main(String[] args) {
        Figuras figuras = new Figuras();
    }
}
