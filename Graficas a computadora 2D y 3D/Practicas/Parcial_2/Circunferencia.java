import javax.swing.JFrame;

public class Circunferencia extends JFrame {

    private final Graficos g = new Graficos(this);

    public Circunferencia() {
        setTitle("Circunferencia");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        drawCircle(400, 300, 100);
    }

    public final void drawCircle(int xc, int yc, int r) {
        for (int x = -r; x <= r; x++) {
            int y = (int) Math.sqrt(r * r - x * x);
            g.drawPixel(xc + x, yc + y, java.awt.Color.BLACK);
            g.drawPixel(xc + x, yc - y, java.awt.Color.BLACK);
        }
        
    }

    @SuppressWarnings("unused")
    public static void main(String[] args) {
        Circunferencia circunferencia = new Circunferencia();
    }
    
}
