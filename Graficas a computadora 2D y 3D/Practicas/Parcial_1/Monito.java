import java.awt.Graphics;
import javax.swing.JFrame;

public class Monito extends JFrame {
    public Monito() {
        super("Monito");
        setSize(200, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

    @Override
    public void paint(Graphics g) {
        // Cabeza
        g.drawString("Monito", 100, 50);
        g.drawArc(50, 60, 50, 50, 0, 360);
        g.drawArc(60, 70, 30, 30, 180, 180);
        g.fillOval(65, 75, 5, 5);
        g.fillOval(80, 75, 5, 5);
        // Cuerpo
        g.drawLine(75, 110, 75, 200);
        // Brazos
        g.drawLine(75, 130, 50, 150);
        g.drawLine(75, 130, 100, 150);
        // Piernas
        g.drawLine(75, 200, 50, 250);
        g.drawLine(75, 200, 100, 250);
    }

    public static void main(String[] args) {
        Monito monito = new Monito();
        monito.repaint();
    }
}
