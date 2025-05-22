import java.awt.Color;

public class ClockFace {
    // Definición de dígitos 0–9 como mapas de 3×5
    private static final int[][] DIGITS = {
        {1,1,1,
         1,0,1,
         1,0,1,
         1,0,1,
         1,1,1}, // 0
        {0,1,0,
         1,1,0,
         0,1,0,
         0,1,0,
         1,1,1}, // 1
        {1,1,1,
         0,0,1,
         1,1,1,
         1,0,0,
         1,1,1}, // 2
        {1,1,1,
         0,0,1,
         1,1,1,
         0,0,1,
         1,1,1}, // 3
        {1,0,1,
         1,0,1,
         1,1,1,
         0,0,1,
         0,0,1}, // 4
        {1,1,1,
         1,0,0,
         1,1,1,
         0,0,1,
         1,1,1}, // 5
        {1,1,1,
         1,0,0,
         1,1,1,
         1,0,1,
         1,1,1}, // 6
        {1,1,1,
         0,0,1,
         0,1,0,
         0,1,0,
         0,1,0}, // 7
        {1,1,1,
         1,0,1,
         1,1,1,
         1,0,1,
         1,1,1}, // 8
        {1,1,1,
         1,0,1,
         1,1,1,
         0,0,1,
         1,1,1}  // 9
    };
    private static final int DIGIT_W = 3, DIGIT_H = 5;

    /**
     * Dibuja la carátula con esfera, marcas, números y manecillas.
     */
    public void draw(Graficos g,
                     int centroX, int centroY, int radio,
                     double angSeg, double angMin, double angHora) {
        // 1) Esfera
        fillCircle(g, centroX, centroY, radio, Color.WHITE);
        g.drawCircle(centroX, centroY, radio, Color.BLACK);

        // 2) Marcas de horas y minutos
        for (int i = 0; i < 60; i++) {
            double ang = Math.toRadians(i * 6 - 90);
            int len = (i % 5 == 0) ? 10 : 5;
            int x0 = centroX + (int)((radio - len) * Math.cos(ang));
            int y0 = centroY + (int)((radio - len) * Math.sin(ang));
            int x1 = centroX + (int)(radio * Math.cos(ang));
            int y1 = centroY + (int)(radio * Math.sin(ang));
            g.drawLine(x0, y0, x1, y1, Color.BLACK);
        }

        // 3) Números 1–12
        drawNumbers(g, centroX, centroY, radio - 25, 4, Color.BLACK);

        // 4) Manecillas
        int xSeg = centroX + (int)((radio - 15) * Math.cos(angSeg));
        int ySeg = centroY + (int)((radio - 15) * Math.sin(angSeg));
        g.drawLine(centroX, centroY, xSeg, ySeg, Color.RED);

        int xMin = centroX + (int)((radio - 25) * Math.cos(angMin));
        int yMin = centroY + (int)((radio - 25) * Math.sin(angMin));
        g.drawLine(centroX, centroY, xMin, yMin, Color.BLUE);

        int xHora = centroX + (int)((radio - 40) * Math.cos(angHora));
        int yHora = centroY + (int)((radio - 40) * Math.sin(angHora));
        g.drawLine(centroX, centroY, xHora, yHora, Color.GREEN);

        // 5) Punto central
        g.drawThickCircle(centroX, centroY, 5, Color.BLACK, 5);
    }

    /** Rellena un círculo escaneando líneas horizontales. */
    private void fillCircle(Graficos g, int xc, int yc, int r, Color c) {
        for (int dy = -r; dy <= r; dy++) {
            int dx = (int)Math.sqrt(r*r - dy*dy);
            g.drawLine(xc - dx, yc + dy, xc + dx, yc + dy, c);
        }
    }

    /**
     * Dibuja los números 1–12 con un pixelFont de tamaño pixelSize,
     * separados por 'spacing' píxeles.
     * textRadius: distancia del centro para posicionar el texto.
     */
    private void drawNumbers(Graficos g,
                             int centroX, int centroY,
                             int textRadius,
                             int pixelSize,
                             Color c) {
        for (int num = 1; num <= 12; num++) {
            String s = Integer.toString(num);
            // ancho y alto del bloque de texto
            int textW = s.length() * DIGIT_W * pixelSize
                        + (s.length()-1) * pixelSize;
            int textH = DIGIT_H * pixelSize;

            double ang = Math.toRadians(num * 30 - 90);
            int baseX = centroX + (int)(textRadius * Math.cos(ang));
            int baseY = centroY + (int)(textRadius * Math.sin(ang));

            // centramos el texto
            int startX = baseX - textW/2;
            int startY = baseY - textH/2;

            drawText(g, s, startX, startY, pixelSize, c);
        }
    }

    /** Dibuja una cadena de dígitos usando drawDigit. */
    private void drawText(Graficos g, String s,
                          int x, int y,
                          int pixelSize,
                          Color c) {
        int cursorX = x;
        for (char ch : s.toCharArray()) {
            drawDigit(g, ch, cursorX, y, pixelSize, c);
            cursorX += (DIGIT_W + 1) * pixelSize;
        }
    }

    /** Dibuja un solo dígito 0–9 en pixelFont 3×5. */
    private void drawDigit(Graficos g, char ch,
                           int x, int y,
                           int pixelSize,
                           Color c) {
        int idx = ch - '0';
        if (idx < 0 || idx > 9) return;

        int[] map = DIGITS[idx];
        for (int row = 0; row < DIGIT_H; row++) {
            for (int col = 0; col < DIGIT_W; col++) {
                if (map[row*DIGIT_W + col] == 1) {
                    // cada “píxel” es un bloque pixelSize×pixelSize
                    for (int dx = 0; dx < pixelSize; dx++) {
                        for (int dy = 0; dy < pixelSize; dy++) {
                            g.drawPixel(x + col*pixelSize + dx,
                                        y + row*pixelSize + dy,
                                        c);
                        }
                    }
                }
            }
        }
    }
}
