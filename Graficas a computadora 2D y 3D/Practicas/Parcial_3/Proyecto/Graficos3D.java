// Graficos3D.java
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.Arrays;
import java.util.Comparator;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class Graficos3D {
    private BufferedImage sceneBuffer;
    private int[]         scenePixels;
    private final JLabel  canvasLabel;
    private final JFrame  ventana;

    public Graficos3D(JFrame ventana) {
        this.ventana = ventana;
        initSceneBuffer();

        // JLabel mostrará sceneBuffer
        canvasLabel = new JLabel(new ImageIcon(sceneBuffer));
        ventana.getContentPane().add(canvasLabel, java.awt.BorderLayout.CENTER);

        // Reconstruir escena al cambiar tamaño
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

    public int getWidth()  { return sceneBuffer.getWidth(); }
    public int getHeight() { return sceneBuffer.getHeight(); }

    /** Limpia el buffer con color */
    public void clear(Color c) {
        Arrays.fill(scenePixels, c.getRGB());
    }

    /** Dibuja un píxel */
    public void drawPixel(int x, int y, Color c) {
        if (x < 0 || y < 0 || x >= sceneBuffer.getWidth() || y >= sceneBuffer.getHeight()) return;
        scenePixels[y * sceneBuffer.getWidth() + x] = c.getRGB();
    }

    /** Línea de Bresenham */
    public void drawLine(int x0, int y0, int x1, int y1, Color c) {
        int dx = Math.abs(x1 - x0), dy = Math.abs(y1 - y0);
        int sx = x0 < x1 ? 1 : -1, sy = y0 < y1 ? 1 : -1;
        int err = dx - dy, e2;
        while (true) {
            drawPixel(x0, y0, c);
            if (x0 == x1 && y0 == y1) break;
            e2 = 2 * err;
            if (e2 > -dy) { err -= dy; x0 += sx; }
            if (e2 < dx)  { err += dx; y0 += sy; }
        }
    }

    /** Rellena un triángulo (scanline) */
    public void drawFilledTriangle(Point p0, Point p1, Point p2, Color col) {
        // Ordena por y ascendente
        Point[] pts = {p0, p1, p2};
        Arrays.sort(pts, Comparator.comparingInt(p -> p.y));
        Point a = pts[0], b = pts[1], c = pts[2];
        int totalHeight = c.y - a.y;
        for (int i = 0; i <= totalHeight; i++) {
            boolean secondHalf = i > (b.y - a.y) || b.y == a.y;
            int segmentHeight = secondHalf ? c.y - b.y : b.y - a.y;
            float alpha = totalHeight == 0 ? 0f : (float)i / totalHeight;
            float beta = segmentHeight == 0 ? 0f : (float)(i - (secondHalf ? (b.y - a.y) : 0)) / segmentHeight;
            Point A = new Point(
                a.x + Math.round((c.x - a.x) * alpha),
                a.y + i
            );
            Point B = secondHalf
                ? new Point(
                    b.x + Math.round((c.x - b.x) * beta),
                    b.y + (i - (b.y - a.y))
                  )
                : new Point(
                    a.x + Math.round((b.x - a.x) * beta),
                    a.y + i
                  );
            if (A.x > B.x) { int t = A.x; A.x = B.x; B.x = t; }
            drawLine(A.x, A.y, B.x, B.y, col);
        }
    }

    /** Actualiza la pantalla */
    public void present() {
        canvasLabel.setIcon(new ImageIcon(sceneBuffer));
    }
}