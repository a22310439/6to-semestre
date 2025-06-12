import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.Arrays;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class GraficosProyecto {
    private BufferedImage sceneBuffer;
    private int[]         scenePixels;
    private final JLabel  canvasLabel;
    private final JFrame  ventana;

    public GraficosProyecto(JFrame ventana) {
        this.ventana = ventana;
        initSceneBuffer();

        // Creamos un JLabel que mostrará sceneBuffer
        canvasLabel = new JLabel(new ImageIcon(sceneBuffer));
        ventana.getContentPane().add(canvasLabel, java.awt.BorderLayout.CENTER);

        // Reconstruir sceneBuffer si la ventana cambia de tamaño
        ventana.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                initSceneBuffer();
                canvasLabel.setIcon(new ImageIcon(sceneBuffer));
            }
        });
    }

    private void initSceneBuffer() {
        Dimension d = ventana.getContentPane().getSize();
        int w = Math.max(d.width, 1);
        int h = Math.max(d.height, 1);
        sceneBuffer = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        scenePixels = ((DataBufferInt) sceneBuffer.getRaster().getDataBuffer()).getData();
    }

    /** Rellena todo el buffer con el color dado. */
    public void clear(Color c) {
        Arrays.fill(scenePixels, c.getRGB());
    }

    /** Dibuja un solo píxel en sceneBuffer. */
    public void drawPixel(int x, int y, Color c) {
        if (x < 0 || y < 0 || x >= sceneBuffer.getWidth() || y >= sceneBuffer.getHeight()) {
            return;
        }
        scenePixels[y * sceneBuffer.getWidth() + x] = c.getRGB();
    }

    /** Dibuja una línea con el algoritmo de Bresenham. */
    public void drawLine(int x0, int y0, int x1, int y1, Color c) {
        int dx = Math.abs(x1 - x0);
        int dy = Math.abs(y1 - y0);
        int sx = x0 < x1 ? 1 : -1;
        int sy = y0 < y1 ? 1 : -1;
        int err = dx - dy;
        int x = x0, y = y0;

        while (true) {
            drawPixel(x, y, c);
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
        }
    }

    /** Dibuja una línea punteada/guionada usando una máscara de 16 bits. */
    public void drawLineType(int x0, int y0, int x1, int y1, Color c, int mask) {
        int dx = Math.abs(x1 - x0);
        int dy = Math.abs(y1 - y0);
        int sx = x0 < x1 ? 1 : -1;
        int sy = y0 < y1 ? 1 : -1;
        int err = dx - dy;
        int patternIndex = 0;
        int x = x0, y = y0;

        while (true) {
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

    /** Dibuja una línea con grosor dado (simulando grosor desplazando líneas paralelas). */
    public void drawThickLine(int x0, int y0, int x1, int y1, Color c, int thickness) {
        int dx = x1 - x0;
        int dy = y1 - y0;
        boolean isSteep = Math.abs(dy) > Math.abs(dx);
        int offset = thickness / 2;

        for (int i = -offset; i <= offset; i++) {
            if (isSteep) {
                drawLine(x0 + i, y0, x1 + i, y1, c);
            } else {
                drawLine(x0, y0 + i, x1, y1 + i, c);
            }
        }
    }

    /** Dibuja el contorno de un rectángulo. */
    public void drawRectangle(int x0, int y0, int x1, int y1, Color c) {
        drawLine(x1, y0, x1, y1, c);
        drawLine(x1, y1, x0, y1, c);
        drawLine(x0, y1, x0, y0, c);
        drawLine(x0, y0, x1, y0, c);
    }

    /** Dibuja el contorno de un círculo con algoritmo de Bresenham (plot8). */
    public void drawCircle(int xc, int yc, int r, Color c) {
        int x = 0, y = r;
        int d = 3 - 2 * r;
        plot8(xc, yc, x, y, c);
        while (x < y) {
            if (d < 0) {
                d += 4 * x + 6;
            } else {
                d += 4 * (x - y) + 10;
                y--;
            }
            x++;
            plot8(xc, yc, x, y, c);
        }
    }

    /** Dibuja un círculo con línea punteada/guionada usando máscara. */
    public void drawTypeCircle(int xc, int yc, int r, Color c, int mask) {
        int x = 0, y = r;
        int d = 3 - 2 * r;
        int patternIndex = 0;

        while (x <= y) {
            if (((mask >> (patternIndex % 16)) & 1) == 1) {
                plot8(xc, yc, x, y, c);
            }
            if (d < 0) {
                d += 4 * x + 6;
            } else {
                d += 4 * (x - y) + 10;
                y--;
            }
            x++;
            patternIndex++;
        }
    }

    /** Dibuja un círculo con grosor usando círculos concéntricos (plotThick8). */
    public void drawThickCircle(int xc, int yc, int r, Color c, int thickness) {
        int x = 0, y = r;
        int d = 3 - 2 * r;
        while (x <= y) {
            plotThick8(xc, yc, x, y, c, thickness);
            if (d < 0) {
                d += 4 * x + 6;
            } else {
                d += 4 * (x - y) + 10;
                y--;
            }
            x++;
        }
    }

    /** Dibuja una elipse (algoritmo de Bresenham extendido). */
    public void drawElipse(int xc, int yc, int a, int b, Color c) {
        int a2 = a * a;
        int b2 = b * b;
        int x = 0, y = b;
        // Región 1
        int d1 = b2 - a2 * b + a2 / 4;
        plot4(xc, yc, x, y, c);
        while (2 * b2 * x < 2 * a2 * y) {
            if (d1 < 0) {
                d1 += 2 * b2 * x + 3 * b2;
            } else {
                d1 += 2 * b2 * x - 2 * a2 * y + 2 * a2 + 3 * b2;
                y--;
            }
            x++;
            plot4(xc, yc, x, y, c);
        }
        // Región 2
        int d2 = b2 * (x * x + x + 1 / 4) + a2 * (y * y - 2 * y + 1) - a2 * b2;
        while (y > 0) {
            if (d2 > 0) {
                d2 += -2 * a2 * y + 3 * a2;
            } else {
                d2 += 2 * b2 * x + 2 * b2 - 2 * a2 * y + 3 * a2;
                x++;
            }
            y--;
            plot4(xc, yc, x, y, c);
        }
    }

    /** Actualiza el JLabel con la imagen completa del sceneBuffer. */
    public void present() {
        canvasLabel.setIcon(new ImageIcon(sceneBuffer));
    }

    // ------------------- Métodos auxiliares -------------------

    /** Dibuja 8 puntos simétricos de un círculo (octantes). */
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

    /** Dibuja 8 puntos simétricos para un círculo grueso. */
    private void plotThick8(int xc, int yc, int x, int y, Color c, int thickness) {
        drawThickPixel(xc + x, yc + y, c, thickness);
        drawThickPixel(xc - x, yc + y, c, thickness);
        drawThickPixel(xc - x, yc - y, c, thickness);
        drawThickPixel(xc + x, yc - y, c, thickness);
        drawThickPixel(xc + y, yc + x, c, thickness);
        drawThickPixel(xc - y, yc + x, c, thickness);
        drawThickPixel(xc - y, yc - x, c, thickness);
        drawThickPixel(xc + y, yc - x, c, thickness);
    }

    /** Dibuja un pixel grueso de grosor dado (un cuadrado centrado). */
    private void drawThickPixel(int x, int y, Color c, int thickness) {
        int half = thickness / 2;
        for (int dx = -half; dx <= half; dx++) {
            for (int dy = -half; dy <= half; dy++) {
                drawPixel(x + dx, y + dy, c);
            }
        }
    }

    /** Dibuja 4 puntos simétricos de una elipse. */
    private void plot4(int xc, int yc, int x, int y, Color c) {
        drawPixel(xc + x, yc + y, c);
        drawPixel(xc - x, yc + y, c);
        drawPixel(xc - x, yc - y, c);
        drawPixel(xc + x, yc - y, c);
    }
}
