// ProyeccionParalela.java
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class ProyeccionParalela extends JFrame {
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
        {0, 1}, {1, 2}, {2, 3}, {3, 0}, // cara trasera (z=-100)
        {4, 5}, {5, 6}, {6, 7}, {7, 4}, // cara frontal (z=+100)
        {0, 4}, {1, 5}, {2, 6}, {3, 7}  // aristas verticales
    };

    // Ángulos de rotación alrededor de los ejes X e Y
    private double angleX = Math.toRadians(30);
    private double angleY = Math.toRadians(45);

    // Vector de proyección paralela (x_p, y_p, Z_p)
    // Aquí: x_p = 1, y_p = 1, Z_p = 2  →  x' = x₂ - (1·z₂/2), y' = y₂ - (1·z₂/2)
    private final double xpDir = 1.0;
    private final double ypDir = 1.0;
    private final double Zp    = 2.0;

    // Posición previa del mouse al arrastrar
    private int prevMouseX;
    private int prevMouseY;

    public ProyeccionParalela() {
        super("Proyección Paralela");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setLayout(new java.awt.BorderLayout());

        // Instanciamos Graficos3D y lo añadimos al JFrame
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

        // Dibujar el cubo cuando la ventana esté lista
        SwingUtilities.invokeLater(this::drawScene);
    }

    private int[] project(double x, double y, double z) {
    // 1) Aplicar rotación alrededor de Y:
    double cosY = Math.cos(angleY), sinY = Math.sin(angleY);
    double x1 = x * cosY - z * sinY;
    double z1 = x * sinY + z * cosY;
    double y1 = y;

    // 2) Aplicar rotación alrededor de X:
    double cosX = Math.cos(angleX), sinX = Math.sin(angleX);
    double y2 = y1 * cosX - z1 * sinX;
    double z2 = y1 * sinX + z1 * cosX;
    double x2 = x1;

    double eps = 1e-3;
    double angYmod = Math.abs((angleY % (2*Math.PI) + 2*Math.PI) % (2*Math.PI));

    boolean isLateral = Math.abs(angYmod - Math.PI/2) < eps
                     || Math.abs(angYmod - 3*Math.PI/2) < eps;

    double xp, yp;
    if (isLateral) {
        xp = x2;
        yp = y2;
    } else {
        // Proyección paralela
        xp = x2 - (xpDir * z2 / Zp);
        yp = y2 - (ypDir * z2 / Zp);
    }

    // 3) Trasladar al centro de la ventana y voltear Y
    int screenX = (int)Math.round(width  / 2.0 + xp);
    int screenY = (int)Math.round(height / 2.0 - yp);
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
        SwingUtilities.invokeLater(ProyeccionParalela::new);
    }
}
