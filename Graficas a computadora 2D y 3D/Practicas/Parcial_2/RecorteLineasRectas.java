import java.awt.Color;
import javax.swing.JFrame;

public class RecorteLineasRectas extends JFrame {
    private Graficos g;

    private final int xMin =  50, yMin =  50;
    private final int xMax = 300, yMax = 200;

    public RecorteLineasRectas() {
        setTitle("Recorte de Líneas Rectas");
        setSize(400, 350);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);

        g = new Graficos(this);
        g.drawRectangle(xMin, yMin, xMax, yMax, Color.BLACK);

        int[][] lines = {
            {100, 90, 200, 150},     //visible
            {100, 30, 200, 250},    //parcial
            {0, 180, 400, 180},     //parcial
            {350, 150, 250, 230},   //parcial
            {320, 100, 380, 300}    //invisible
        };

        // Recortar y dibujar cada línea
        for (int[] L : lines) {
            clipAndDraw(L[0], L[1], L[2], L[3]);
        }
    }

    private int computeCode(int x, int y) {
        int code = 0;
        if (x < xMin)      code |= 1;  // 0001 = izquierda
        else if (x > xMax) code |= 2;  // 0010 = derecha
        if (y < yMin)      code |= 4;  // 0100 = arriba
        else if (y > yMax) code |= 8;  // 1000 = abajo
        return code;
    }

    // Recorta la línea (x0,y0)-(x1,y1) y, si hay parte dentro, la dibuja en rojo
    private void clipAndDraw(int x0, int y0, int x1, int y1) {
        int code0 = computeCode(x0, y0);
        int code1 = computeCode(x1, y1);
        boolean accept = false;

        while (true) {
            if ((code0 | code1) == 0) {
                // Ambos puntos dentro
                accept = true;
                break;
            } else if ((code0 & code1) != 0) {
                // Región fuera común → rechazar
                break;
            } else {
                // Al menos un extremo fuera → recortar
                int codeOut = (code0 != 0) ? code0 : code1;
                int x = 0, y = 0;

                if ((codeOut & 4) != 0) {            // arriba
                    x = x0 + (x1 - x0) * (yMin - y0) / (y1 - y0);
                    y = yMin;
                } else if ((codeOut & 8) != 0) {     // abajo
                    x = x0 + (x1 - x0) * (yMax - y0) / (y1 - y0);
                    y = yMax;
                } else if ((codeOut & 1) != 0) {     // izquierda
                    y = y0 + (y1 - y0) * (xMin - x0) / (x1 - x0);
                    x = xMin;
                } else if ((codeOut & 2) != 0) {     // derecha
                    y = y0 + (y1 - y0) * (xMax - x0) / (x1 - x0);
                    x = xMax;
                }

                if (codeOut == code0) {
                    x0 = x; y0 = y;
                    code0 = computeCode(x0, y0);
                } else {
                    x1 = x; y1 = y;
                    code1 = computeCode(x1, y1);
                }
            }
        }

        if (accept) {
            g.drawLine(x0, y0, x1, y1, Color.BLACK);
        }
    }

    public static void main(String[] args) {
        new RecorteLineasRectas();
    }
}
