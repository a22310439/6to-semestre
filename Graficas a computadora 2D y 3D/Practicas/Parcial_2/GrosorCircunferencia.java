import javax.swing.JFrame;
import java.awt.Color;

public class GrosorCircunferencia extends JFrame {

    private final Graficos g;

    public GrosorCircunferencia() {
        setTitle("Circunferencia con Grosor Real");
        setSize(600, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

        g = new Graficos(this);

        g.drawThickCircle(150, 150, 70, Color.BLACK, 1);
        g.drawThickCircle(450, 150, 70, Color.BLUE, 3);
        g.drawThickCircle(150, 450, 70, Color.RED, 5);
        g.drawThickCircle(450, 450, 70, Color.MAGENTA, 8);
    }

    public static void main(String[] args) {
        new GrosorCircunferencia();
    }
}
