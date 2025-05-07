import java.awt.Color;
import javax.swing.JFrame;

public class GrosorLineas extends JFrame {
    private final Graficos g = new Graficos(this);

    public GrosorLineas() {
        setTitle("Grosor de Líneas");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        g.drawThickLine(10, 10, 190, 250, Color.BLACK, 1);
        g.drawThickLine(210, 150, 390, 150, Color.BLACK, 4);
        g.drawThickLine(590, 10, 410, 250, Color.BLACK, 5);
        g.drawThickLine(790, 150, 610, 150, Color.BLACK, 2);
    }

    @SuppressWarnings("unused")
    public static void main(String[] args) {
        GrosorLineas grosor = new GrosorLineas();
    }
    
}
