import java.awt.Color;
import javax.swing.JFrame;

public class RecorteCircunferencias extends JFrame {
    private Graficos g;

    // Coordenadas del rectángulo de recorte
    private final int xMin =  50, yMin =  50;
    private final int xMax = 300, yMax = 200;

    public RecorteCircunferencias() {
        setTitle("Recorte de Circunferencias");
        setSize(400, 350);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);

        // Instanciamos Graficos y dibujamos el rectángulo de recorte
        g = new Graficos(this);
        g.drawRectangle(xMin, yMin, xMax, yMax, Color.BLACK);

        // Definimos varias circunferencias: {xc, yc, radio}
        int[][] circles = {
            {175, 125,  40},   //visible
            { 50,  50,  60},   //parcial
            {350, 100,  50},   //invisible
            {  0,   0,  30},   //invisible
            {300, 200, 100}    //parcial
        };

        // Recortar y dibujar cada circunferencia
        for (int[] c : circles) {
            clipAndDrawCircle(c[0], c[1], c[2]);
        }
    }

    private void clipAndDrawCircle(int xc, int yc, int r) {
        // Caso trivial 1: círculo totalmente dentro
        if (xc - r >= xMin && xc + r <= xMax &&
            yc - r >= yMin && yc + r <= yMax) {
            g.drawCircle(xc, yc, r, Color.BLACK);
            return;
        }

        // Caso trivial 2: círculo totalmente fuera
        if (xc + r < xMin || xc - r > xMax ||
            yc + r < yMin || yc - r > yMax) {
            return;
        }

        // Caso parcial: dibujamos píxel a píxel con Bresenham,
        // pero sólo si el punto está dentro del rectángulo.
        int x = 0;
        int y = r;
        int d = 3 - 2 * r;
        plotClipped8(xc, yc, x, y);

        while (x < y) {
            if (d < 0) {
                d += 4 * x + 6;
            } else {
                d += 4 * (x - y) + 10;
                y--;
            }
            x++;
            plotClipped8(xc, yc, x, y);
        }
    }

    // Dibuja los 8 puntos simétricos de la circunferencia,
    // pero cada píxel sólo si queda dentro del rectángulo de recorte.
    private void plotClipped8(int xc, int yc, int x, int y) {
        drawIfClipped(xc + x, yc + y);
        drawIfClipped(xc - x, yc + y);
        drawIfClipped(xc - x, yc - y);
        drawIfClipped(xc + x, yc - y);
        drawIfClipped(xc + y, yc + x);
        drawIfClipped(xc - y, yc + x);
        drawIfClipped(xc - y, yc - x);
        drawIfClipped(xc + y, yc - x);
    }

    // Dibuja un píxel negro sólo si (x,y) está dentro de [xMin..xMax]×[yMin..yMax]
    private void drawIfClipped(int x, int y) {
        if (x >= xMin && x <= xMax && y >= yMin && y <= yMax) {
            g.drawPixel(x, y, Color.BLACK);
        }
    }

    public static void main(String[] args) {
        new RecorteCircunferencias();
    }
}
