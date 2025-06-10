import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class Cilindro3D extends JFrame {
    private final Graficos3D graficos;
    private final int        width  = 600;
    private final int        height = 600;

    // Rango para t (altura paramétrica) solo de 0 a 2π (mitad superior)
    private final double T_MIN = 0.0;
    private final double T_MAX = 2 * Math.PI;

    // Rango completo para φ: 0 a 2π (rodear completamente el cilindro)
    private final double PHI_MIN = 0.0;
    private final double PHI_MAX = 2 * Math.PI;

    // Resolución de la malla
    private final int    NT = 60;   // divisiones en t (0..2π)
    private final int    NP = 60;   // divisiones en φ (0..2π)

    // Factor de escala en el espacio 3D
    private final double S = 50.0;

    // Posición del observador / cámara en (x_c, y_c, z_c)
    private final double x_c = 0.0;
    private final double y_c = 0.0;
    private final double z_c = -500.0;

    // Ángulos iniciales de rotación de la escena (en radianes).
    private double angleX = Math.toRadians(20);
    private double angleY = 0.0;

    // Variables para seguimiento de arrastre del ratón
    private int prevMouseX;
    private int prevMouseY;

    public Cilindro3D() {
        super("Cilindro 3D");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setLayout(new java.awt.BorderLayout());

        graficos = new Graficos3D(this);

        setSize(width, height);
        setLocationRelativeTo(null);

        MouseAdapter ma = new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                prevMouseX = e.getX();
                prevMouseY = e.getY();
            }
            @Override
            public void mouseDragged(MouseEvent e) {
                int dx = e.getX() - prevMouseX;
                int dy = e.getY() - prevMouseY;
                double sens = 0.01;
                angleY += dx * sens;   // rotación en Y al mover el ratón horizontalmente
                angleX -= dy * sens;   // rotación en X al mover el ratón verticalmente
                prevMouseX = e.getX();
                prevMouseY = e.getY();
                drawScene();
            }
        };
        addMouseListener(ma);
        addMouseMotionListener(ma);

        setVisible(true);
        SwingUtilities.invokeLater(this::drawScene);
    }

    private double[] projectWithDepth(double x, double y, double z) {
        // 1) Escalar
        double x_s = x * S;
        double y_s = y * S;
        double z_s = z * S;

        // 2) Rotar alrededor de Y
        double cosY = Math.cos(angleY), sinY = Math.sin(angleY);
        double x1 = x_s * cosY + z_s * sinY;
        double z1 = -x_s * sinY + z_s * cosY;
        double y1 = y_s;

        // 3) Rotar alrededor de X
        double cosX = Math.cos(angleX), sinX = Math.sin(angleX);
        double y2 = y1 * cosX - z1 * sinX;
        double z2 = y1 * sinX + z1 * cosX;
        double x2 = x1;

        // 4) Proyección de perspectiva
        double xp = x_c - ((x2 - x_c) * z_c) / (z2 - z_c);
        double yp = y_c - ((y2 - y_c) * z_c) / (z2 - z_c);

        // 5) Devolver coords de pantalla y z2
        double screenX = width  / 2.0 + xp;
        double screenY = height / 2.0 - yp;
        return new double[]{screenX, screenY, z2};
    }

    private static class Triangle {
        int x0, y0, x1, y1, x2, y2;
        double depth;
        Color color;
        Triangle(int x0, int y0, int x1, int y1, int x2, int y2, double depth, Color color) {
            this.x0 = x0; this.y0 = y0;
            this.x1 = x1; this.y1 = y1;
            this.x2 = x2; this.y2 = y2;
            this.depth = depth;
            this.color = color;
        }
    }

    private void drawScene() {
        graficos.clear(Color.WHITE);

        // 1) Construir la malla 3D: coordenadas (x,y,z)
        double[][][] grid = new double[NT+1][NP+1][3];
        for (int i = 0; i <= NT; i++) {
            double t = T_MIN + (T_MAX - T_MIN) * i / NT; // t ∈ [0,2π]
            double radio = 2 + Math.cos(t);
            double y = t - Math.PI; // eje Y centrado en 0
            for (int j = 0; j <= NP; j++) {
                double phi = PHI_MIN + (PHI_MAX - PHI_MIN) * j / NP; // φ ∈ [0,2π]
                double x = radio * Math.cos(phi);
                double z = radio * Math.sin(phi);
                grid[i][j][0] = x;
                grid[i][j][1] = y;
                grid[i][j][2] = z;
            }
        }

        // 2) Proyectar cada punto 3D a 2D y guardar z2 (profundidad) en paralelo
        double[][][] depth = new double[NT+1][NP+1][1];
        int[][][] pts2D = new int[NT+1][NP+1][2];
        for (int i = 0; i <= NT; i++) {
            for (int j = 0; j <= NP; j++) {
                double[] p = projectWithDepth(
                    grid[i][j][0],
                    grid[i][j][1],
                    grid[i][j][2]
                );
                pts2D[i][j][0] = (int) Math.round(p[0]);
                pts2D[i][j][1] = (int) Math.round(p[1]);
                depth[i][j][0] = p[2];
            }
        }

        // 3) Crear lista de triángulos con su color y profundidad promedio
        List<Triangle> tris = new ArrayList<>();
        for (int i = 0; i < NT; i++) {
            for (int j = 0; j < NP; j++) {
                // Parámetro t en vértices
                double t00 = T_MIN + (T_MAX - T_MIN) * i     / NT;
                double t10 = T_MIN + (T_MAX - T_MIN) * (i+1) / NT;

                // Color en cada vértice del espectro
                double v00 = (t00 - T_MIN) / (T_MAX - T_MIN);
                double v10 = (t10 - T_MIN) / (T_MAX - T_MIN);
                double v01 = v00;
                double v11 = v10;
                Color c00 = colorFromNormalized(v00);
                Color c10 = colorFromNormalized(v10);
                Color c01 = colorFromNormalized(v01);
                Color c11 = colorFromNormalized(v11);

                // Puntos 2D proyectados
                int x00 = pts2D[i][j][0],     y00 = pts2D[i][j][1];
                int x10 = pts2D[i+1][j][0],   y10 = pts2D[i+1][j][1];
                int x01 = pts2D[i][j+1][0],   y01 = pts2D[i][j+1][1];
                int x11 = pts2D[i+1][j+1][0], y11 = pts2D[i+1][j+1][1];

                // Profundidad z2 promedio para cada triángulo
                double z00 = depth[i][j][0];
                double z10 = depth[i+1][j][0];
                double z01 = depth[i][j+1][0];
                double z11 = depth[i+1][j+1][0];

                double depth1 = (z00 + z10 + z11) / 3.0;
                Color fill1 = averageColor(c00, c10, c11);
                tris.add(new Triangle(x00, y00, x10, y10, x11, y11, depth1, fill1));

                double depth2 = (z00 + z11 + z01) / 3.0;
                Color fill2 = averageColor(c00, c11, c01);
                tris.add(new Triangle(x00, y00, x11, y11, x01, y01, depth2, fill2));
            }
        }

        tris.sort(Comparator.comparingDouble(tr -> tr.depth));

        // 5) Pintar cada triángulo
        for (Triangle tr : tris) {
            fillTriangle(tr.x0, tr.y0,
                         tr.x1, tr.y1,
                         tr.x2, tr.y2,
                         tr.color);
        }

        // 6) Dibujar la malla encima, con un color más oscuro
        for (int i = 0; i <= NT; i++) {
            for (int j = 0; j < NP; j++) {
                double t0 = T_MIN + (T_MAX - T_MIN) * i / NT;
                double tNorm = (t0 - T_MIN) / (T_MAX - T_MIN);
                Color base = colorFromNormalized(tNorm);
                Color meshColor = base.darker().darker();

                int x0 = pts2D[i][j][0],   y0 = pts2D[i][j][1];
                int x1 = pts2D[i][j+1][0], y1 = pts2D[i][j+1][1];
                graficos.drawLine(x0, y0, x1, y1, meshColor);
            }
        }
        for (int j = 0; j <= NP; j++) {
            for (int i = 0; i < NT; i++) {
                double t0 = T_MIN + (T_MAX - T_MIN) * i / NT;
                double tNorm = (t0 - T_MIN) / (T_MAX - T_MIN);
                Color base = colorFromNormalized(tNorm);
                Color meshColor = base.darker().darker();

                int x0 = pts2D[i][j][0],   y0 = pts2D[i][j][1];
                int x1 = pts2D[i+1][j][0], y1 = pts2D[i+1][j][1];
                graficos.drawLine(x0, y0, x1, y1, meshColor);
            }
        }

        graficos.present();
    }

    private void fillTriangle(int x0, int y0,
                              int x1, int y1,
                              int x2, int y2,
                              Color c) {
        int minX = Math.min(x0, Math.min(x1, x2));
        int maxX = Math.max(x0, Math.max(x1, x2));
        int minY = Math.min(y0, Math.min(y1, y2));
        int maxY = Math.max(y0, Math.max(y1, y2));

        double denom = ((y1 - y2)*(x0 - x2) + (x2 - x1)*(y0 - y2));

        for (int py = minY; py <= maxY; py++) {
            for (int px = minX; px <= maxX; px++) {
                double l1 = ((y1 - y2)*(px - x2) + (x2 - x1)*(py - y2)) / denom;
                double l2 = ((y2 - y0)*(px - x2) + (x0 - x2)*(py - y2)) / denom;
                double l3 = 1.0 - l1 - l2;
                if (l1 >= 0 && l2 >= 0 && l3 >= 0) {
                    graficos.drawPixel(px, py, c);
                }
            }
        }
    }

    private Color averageColor(Color c0, Color c1, Color c2) {
        int r = (c0.getRed()   + c1.getRed()   + c2.getRed())   / 3;
        int g = (c0.getGreen() + c1.getGreen() + c2.getGreen()) / 3;
        int b = (c0.getBlue()  + c1.getBlue()  + c2.getBlue())  / 3;
        return new Color(r, g, b);
    }

    private Color colorFromNormalized(double tNorm) {
        if (tNorm < 0) tNorm = 0;
        if (tNorm > 1) tNorm = 1;
        float hue = (float)((2.0/3.0) * (1.0 - tNorm));
        return Color.getHSBColor(hue, 1.0f, 1.0f);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Cilindro3D::new);
    }
}
