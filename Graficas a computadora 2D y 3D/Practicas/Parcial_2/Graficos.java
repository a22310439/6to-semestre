import java.awt.Color;
import java.awt.image.BufferedImage;
import javax.swing.JFrame;

public class Graficos {

    private final BufferedImage buffer;
    private final JFrame ventana;

    public Graficos(JFrame ventana) {
        this.ventana = ventana;
        buffer = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);
    }

    public void drawPixel(int x, int y, Color c) {
        buffer.setRGB(0, 0, c.getRGB());
        ventana.getGraphics().drawImage(buffer, x, y, ventana);
    }

    public void drawLine(int x0, int y0, int x1, int y1, Color c) {
        int dx = Math.abs(x1 - x0);
        int dy = Math.abs(y1 - y0);
        int sx = x0 < x1 ? 1 : -1;  // paso en x
        int sy = y0 < y1 ? 1 : -1;  // paso en y
        int err = dx - dy;          // diferencia de error inicial

        int x = x0;
        int y = y0;

        while (true) {
            drawPixel(x, y, c);

            // condición para salir del ciclo
            if (x == x1 && y == y1) {
                break;
            }

            int e2 = 2 * err;
            if (e2 > -dy) {
                err -= dy;
                x += sx;
            }
            if (e2 < dx) {
                err += dx;
                y += sy;
            }
        }
    }

    public void drawLineType(int x0, int y0, int x1, int y1, Color c, int mask) {
        int dx = Math.abs(x1 - x0);
        int dy = Math.abs(y1 - y0);
        int sx = x0 < x1 ? 1 : -1;
        int sy = y0 < y1 ? 1 : -1;
        int err = dx - dy;
    
        int patternIndex = 0;
        int x = x0;
        int y = y0;
    
        while (true) {
            // Aplica la máscara: dibuja el píxel solo si el bit es 1
            if (((mask >> (patternIndex % 16)) & 1) == 1) {
                drawPixel(x, y, c);
            }
    
            if (x == x1 && y == y1) break;
    
            int e2 = 2 * err;
            if (e2 > -dy) {
                err -= dy;
                x += sx;
            }
            if (e2 < dx) {
                err += dx;
                y += sy;
            }
    
            patternIndex++;
        }
    }

    public void drawThickLine(int x0, int y0, int x1, int y1, Color c, int thickness) {
        int dx = x1 - x0;
        int dy = y1 - y0;
    
        // Determina si la línea es más horizontal o vertical
        boolean isSteep = Math.abs(dy) > Math.abs(dx);
    
        // Si la línea es más vertical, desplazamos en X (horizontalmente el grosor)
        // Si es más horizontal, desplazamos en Y (verticalmente el grosor)
        int offset = thickness / 2;
    
        for (int i = -offset; i <= offset; i++) {
            if (isSteep) {
                // Para líneas más verticales, desplazamos horizontalmente (en x)
                drawLine(x0 + i, y0, x1 + i, y1, c);
            } else {
                // Para líneas más horizontales, desplazamos verticalmente (en y)
                drawLine(x0, y0 + i, x1, y1 + i, c);
            }
        }
    }

    public final void drawRectangle(int x0, int y0, int x1, int y1, Color c) {
        drawLine(x1, y0, x1, y1, c);
        drawLine(x1, y1, x0, y1, c);
        drawLine(x0, y1, x0, y0, c);
        drawLine(x0, y0, x1, y0, c);
    }

    public final void drawCircle(int xc, int yc, int r, Color c) {
        int x = 0, y = r;
        int d = 3 - 2 * r;

        plot8(xc, yc, x, y, c);

        while (x < y) {
            if (d < 0) {
                d = d + 4 * x + 6;
            } else {
                d = d + 4 * (x - y) + 10;
                y--;
            }
            x++;
            plot8(xc, yc, x, y, c);
        }
    }

    // Helper para no repetir código
    private void plot8(int xc, int yc, int x, int y, Color c) {
        drawPixel(xc + x, yc + y, c);
        drawPixel(xc - x, yc + y, c);
        drawPixel(xc - x, yc - y, c);
        drawPixel(xc + x, yc - y, c);
        drawPixel(xc + y, yc + x, c);
        drawPixel(xc - y, yc + x, c);
        drawPixel(xc - y, yc - x, c);
        drawPixel(xc + y, yc - x, c);
    }

    public void drawElipse(int xc, int yc, int a, int b, Color c) {
        int a2 = a*a, b2 = b*b;
        int x = 0, y = b;
    
        // Inicial d1 para región 1
        int d1 = b2 - a2*b + a2/4;
        plot4(xc, yc, x, y, c);
    
        // Región 1: mientras 2*b2*x < 2*a2*y
        while (2*b2*x < 2*a2*y) {
            if (d1 < 0) {
                d1 += 2*b2*x + 3*b2;
            } else {
                d1 += 2*b2*x - 2*a2*y + 2*a2 + 3*b2;
                y--;
            }
            x++;
            plot4(xc, yc, x, y, c);
        }
    
        // Inicial d2 para región 2 (desde el último x,y)
        // nota: (x+0.5)^2 = x*x + x + 1/4
        int d2 = b2*(x*x + x + 1/4)
               + a2*(y*y - 2*y + 1)
               - a2*b2;
    
        // Región 2: mientras y > 0
        while (y > 0) {
            if (d2 > 0) {
                d2 += -2*a2*y + 3*a2;
            } else {
                d2 +=  2*b2*x + 2*b2 - 2*a2*y + 3*a2;
                x++;
            }
            y--;
            plot4(xc, yc, x, y, c);
        }
    }
    
    // helper para dibujar los cuatro cuadrantes; luego graficas los espejos:
    private void plot4(int xc, int yc, int x, int y, Color c) {
        drawPixel(xc + x, yc + y, c);
        drawPixel(xc - x, yc + y, c);
        drawPixel(xc - x, yc - y, c);
        drawPixel(xc + x, yc - y, c);
    }
    
}
