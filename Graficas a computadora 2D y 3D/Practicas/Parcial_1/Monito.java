import java.awt.Graphics;
import javax.swing.JFrame;

public class Monito extends JFrame {
    public Monito() {
        super("Monito");
        setSize(200, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    @Override
    public void paint(Graphics g) {
        // Cabeza
        g.drawArc(60, 60, 50, 50, 0, 360);
        g.drawArc(70, 70, 30, 30, 180, 180);
        g.fillOval(75, 75, 5, 5);
        g.fillOval(90, 75, 5, 5);
        // Cuerpo
        g.drawLine(85, 110, 85, 200);
        // Brazos
        g.drawLine(85, 130, 50, 150);
        g.drawLine(85, 130, 120, 150);
        // Piernas
        g.drawLine(85, 200, 60, 250);
        g.drawLine(85, 200, 110, 250);
    }

    public static void main(String[] args) {
        Monito monito = new Monito();
        monito.setVisible(true);
    }
}
