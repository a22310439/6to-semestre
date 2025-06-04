// ProyeccionPerspectiva.java
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class ProyeccionPerspectiva extends JFrame {
    private final Graficos3D graficos;
    private final int        width  = 600;
    private final int        height = 600;

    // Vértices originales del cubo en 3D (centrado en 0,0,0, arista = 200)
    private final double[][] baseVertices = {
        {-100, -100, -100}, // 0
        { 100, -100, -100}, // 1
        { 100,  100, -100}, // 2
        {-100,  100, -100}, // 3
        {-100, -100,  100}, // 4
        { 100, -100,  100}, // 5
        { 100,  100,  100}, // 6
        {-100,  100,  100}  // 7
    };

    // Aristas como pares de índices de vértices
    private final int[][] edges = {
        {0, 1}, {1, 2}, {2, 3}, {3, 0},
        {4, 5}, {5, 6}, {6, 7}, {7, 4},
        {0, 4}, {1, 5}, {2, 6}, {3, 7}
    };

    // Ángulos de rotación alrededor de los ejes X e Y
    private double angleX = Math.toRadians(35.264);
    private double angleY = Math.toRadians(45.0);

    // Cámara en (0, 0, -cameraDistance)
    // Para proyectar sobre el plano Z = 0.
    private final double cameraDistance = 500.0;
    private final double x_c = 0.0;
    private final double y_c = 0.0;
    private final double z_c = -cameraDistance;

    // Posición previa del mouse al arrastrar
    private int prevMouseX;
    private int prevMouseY;

    public ProyeccionPerspectiva() {
        super("Proyección de Perspectiva");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setLayout(new java.awt.BorderLayout());

        graficos = new Graficos3D(this);

        setSize(width, height);
        setLocationRelativeTo(null);
        setVisible(true);

        // MouseAdapter para rotar al arrastrar
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
                angleY += dx * sens;
                angleX += dy * sens;
                prevMouseX = e.getX();
                prevMouseY = e.getY();
                drawScene();
            }
        };
        getContentPane().addMouseListener(ma);
        getContentPane().addMouseMotionListener(ma);

        SwingUtilities.invokeLater(this::drawScene);
    }

    private int[] project(double x, double y, double z) {
        // 1) Rotación alrededor de Y
        double cosY = Math.cos(angleY), sinY = Math.sin(angleY);
        double x1 = x * cosY - z * sinY;
        double z1 = x * sinY + z * cosY;
        double y1 = y;

        // 2) Rotación alrededor de X
        double cosX = Math.cos(angleX), sinX = Math.sin(angleX);
        double y2 = y1 * cosX - z1 * sinX;
        double z2 = y1 * sinX + z1 * cosX;
        double x2 = x1;

        // 3) Proyección perspectiva
        double xp = x_c - ((x2 - x_c) * z_c) / (z2 - z_c);
        double yp = y_c - ((y2 - y_c) * z_c) / (z2 - z_c);

        // 4) Trasladar al centro de la ventana y voltear Y
        int screenX = (int) Math.round(width  / 2.0 + xp);
        int screenY = (int) Math.round(height / 2.0 - yp);
        return new int[]{screenX, screenY};
    }

    private void drawScene() {
        graficos.clear(Color.WHITE);

        int[][] verts2D = new int[8][2];
        for (int i = 0; i < baseVertices.length; i++) {
            double[] v = baseVertices[i];
            verts2D[i] = project(v[0], v[1], v[2]);
        }

        Color edgeColor = Color.BLACK;
        for (int[] edge : edges) {
            int i0 = edge[0], i1 = edge[1];
            int x0 = verts2D[i0][0], y0 = verts2D[i0][1];
            int x1 = verts2D[i1][0], y1 = verts2D[i1][1];
            graficos.drawLine(x0, y0, x1, y1, edgeColor);
        }

        graficos.present();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ProyeccionPerspectiva::new);
    }
}
