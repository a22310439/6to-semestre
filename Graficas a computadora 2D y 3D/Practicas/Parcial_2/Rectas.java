import java.awt.Color;
import javax.swing.JFrame;

public class Rectas extends JFrame {

    private static Graficos graficos;

    public Rectas() {
        setTitle("Practica 1");
        setSize(600, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

        graficos = new Graficos(this);
        
        graficos.drawLine(30, 150, 280, 150, Color.BLACK);
        graficos.drawLine(450, 50, 450, 280, Color.BLACK);
        graficos.drawLine(30, 580, 280, 330, Color.BLACK);
        graficos.drawLine(330, 330, 580, 580, Color.BLACK);
    }

    @SuppressWarnings("unused")
    public static void main(String[] args) {
        Rectas rectas = new Rectas();
    }
}
