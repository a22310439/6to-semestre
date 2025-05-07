import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ScanlineFill extends JFrame {

    private final Graficos g;

    public ScanlineFill() {
        setTitle("Relleno por Scan-Line Mejorado");
        setSize(600, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

        g = new Graficos(this);

        // Definir un polígono arbitrario
        List<Point> polygon = new ArrayList<>();
        polygon.add(new Point(100, 100));
        polygon.add(new Point(300, 150));
        polygon.add(new Point(350, 300));
        polygon.add(new Point(300, 250));
        polygon.add(new Point(200, 450));
        polygon.add(new Point(120, 270));

        // Rellenar el polígono
        fillPolygonScanline(polygon, Color.GREEN);
        
        // Dibujar contorno del polígono
        for (int i = 0; i < polygon.size(); i++) {
            Point p1 = polygon.get(i);
            Point p2 = polygon.get((i + 1) % polygon.size());
            g.drawLine(p1.x, p1.y, p2.x, p2.y, Color.BLACK);
        }

    }

    public void fillPolygonScanline(List<Point> vertices, Color fillColor) {
        // Encontrar los límites verticales del polígono
        int minY = Integer.MAX_VALUE;
        int maxY = Integer.MIN_VALUE;
        for (Point p : vertices) {
            minY = Math.min(minY, p.y);
            maxY = Math.max(maxY, p.y);
        }

        // Recorrer cada línea horizontal del polígono
        for (int y = minY; y <= maxY; y++) {
            List<Integer> interseccionesX = new ArrayList<>();

            for (int i = 0; i < vertices.size(); i++) {
                Point p1 = vertices.get(i);
                Point p2 = vertices.get((i + 1) % vertices.size());

                if (p1.y == p2.y) continue; // Ignorar bordes horizontales
                if (y < Math.min(p1.y, p2.y)) continue;
                if (y > Math.max(p1.y, p2.y)) continue;

                // Evitar contar vértices superiores duplicados
                if ((p1.y < p2.y && y == p2.y) || (p2.y < p1.y && y == p1.y)) continue;

                // Calcular intersección con la línea horizontal y
                int x = p1.x + (y - p1.y) * (p2.x - p1.x) / (p2.y - p1.y);
                interseccionesX.add(x);
            }

            // Ordenar intersecciones de menor a mayor
            Collections.sort(interseccionesX);

            // Rellenar entre pares de intersecciones
            for (int i = 0; i < interseccionesX.size() - 1; i += 2) {
                int xStart = interseccionesX.get(i);
                int xEnd = interseccionesX.get(i + 1);

                // No rellenar los extremos para no tocar el borde
                for (int x = xStart + 1; x < xEnd; x++) {
                    g.drawPixel(x, y, fillColor);
                }
            }
        }
    }

    public static void main(String[] args) {
        new ScanlineFill();
    }
}
