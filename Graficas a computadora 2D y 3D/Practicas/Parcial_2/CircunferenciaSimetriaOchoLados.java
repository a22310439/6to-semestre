import java.awt.Color;
import javax.swing.JFrame;

public class CircunferenciaSimetriaOchoLados extends JFrame {
    private final Graficos g = new Graficos(this);

    public CircunferenciaSimetriaOchoLados() {
        setTitle("Circunferencia con simetría de 8 lados");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        drawCircle(400, 300, 250);
    }

    public final void drawCircle(int xc, int yc, int r) {
        for (double t = 0; t <= Math.PI/4; t += 0.01) {
            int dx = (int) Math.round(r * Math.cos(t));
            int dy = (int) Math.round(r * Math.sin(t));
    
            g.drawPixel(xc + dx, yc + dy, Color.BLACK); // Primer octante
            g.drawPixel(xc - dx, yc + dy, Color.BLACK); // Segundo octante
            g.drawPixel(xc - dx, yc - dy, Color.BLACK); // Tercer octante
            g.drawPixel(xc + dx, yc - dy, Color.BLACK); // Cuarto octante
            g.drawPixel(xc + dy, yc + dx, Color.BLACK); // Quinto octante
            g.drawPixel(xc - dy, yc + dx, Color.BLACK); // Sexto octante
            g.drawPixel(xc - dy, yc - dx, Color.BLACK); // Séptimo octante
            g.drawPixel(xc + dy, yc - dx, Color.BLACK); // Octavo octante
        }
    }

    @SuppressWarnings("unused")
    public static void main(String[] args) {
        CircunferenciaSimetriaOchoLados circunferencia = new CircunferenciaSimetriaOchoLados();
    }

}
