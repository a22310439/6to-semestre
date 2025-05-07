import javax.swing.JFrame;

class CircunferenciaCoordPolares extends JFrame{
    private final Graficos g = new Graficos(this);

    public CircunferenciaCoordPolares() {
        setTitle("Circunferencia en coordenadas polares");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        drawCircle(400, 300, 250);
    }

    public final void drawCircle(int xc, int yc, int r) {
        //dibujar el circulo con la formula matemática con coordenadas polares x = xc + r sin(t) y y = yc + r cos(t), donde (xc, yc) es el centro del circulo y r es el radio. No se usa el algoritmo de Bresenham.
        for (double t = 0; t < 2 * Math.PI; t += 0.01) {
            int x = (int) (xc + r * Math.sin(t));
            int y = (int) (yc + r * Math.cos(t));
            g.drawPixel(x, y, java.awt.Color.BLACK);
        }
    }

    @SuppressWarnings("unused")
    public static void main(String[] args) {
        CircunferenciaCoordPolares circunferencia = new CircunferenciaCoordPolares();
    }

}