import java.awt.Color;
import javax.swing.JFrame;

public class TiposCircunferencia extends JFrame {
    Graficos g = new Graficos(this);

    public TiposCircunferencia() {
        setTitle("Circunferencias con Máscara");
        setSize(600, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

        int CONTINUA     = 0b1111111111111111;
        int PUNTEADA     = 0b1010101010101010;
        int SEGMENTADA   = 0b1111000011110000;
        int GUION_PUNTO  = 0b1110100011101000;

        g.drawTypeCircle(150, 150, 100, Color.BLACK, CONTINUA);
        g.drawTypeCircle(450, 150, 100, Color.BLACK, PUNTEADA);
        g.drawTypeCircle(150, 450, 100, Color.BLACK, SEGMENTADA);
        g.drawTypeCircle(450, 450, 100, Color.BLACK, GUION_PUNTO);
    }

    public static void main(String[] args) {
        new TiposCircunferencia();
    }
}
