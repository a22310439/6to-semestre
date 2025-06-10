import java.awt.Color;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class Escalamiento3D extends JFrame {
    private final Graficos3D graficos;
    private final int        width  = 600;
    private final int        height = 600;

    // Vértices originales del cubo en 3D (arista = 200, centrado en 0,0,0)
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

    // Aristas definidas por índices de vértices
    private final int[][] edges = {
        {0,1}, {1,2}, {2,3}, {3,0},   // cara trasera (z=-100)
        {4,5}, {5,6}, {6,7}, {7,4},   // cara frontal (z=+100)
        {0,4}, {1,5}, {2,6}, {3,7}    // aristas verticales
    };

    private final double angleX = Math.toRadians(20);
    private final double angleY = Math.toRadians(-30);

    // Parámetros del observador / cámara
    private final double x_c = 0.0;
    private final double y_c = 0.0;
    private final double z_c = -500.0;

    // Factor de escala inicial
    private double scale = 1.0;

    public Escalamiento3D() {
        super("Escalamiento 3D");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setLayout(new java.awt.BorderLayout());

        graficos = new Graficos3D(this);

        setSize(width, height);
        setLocationRelativeTo(null);

        // Listener de teclado para ajustar escala
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_PLUS -> {
                        // Tecla '+'
                        scale *= 1.10;
                        drawScene();
                    }
                    case KeyEvent.VK_MINUS -> {
                        // Tecla '-'
                        scale /= 1.10;
                        drawScene();
                    }
                }
            }
        });

        setFocusable(true);
        requestFocusInWindow();

        setVisible(true);

        SwingUtilities.invokeLater(this::drawScene);
    }

    private int[] project(double x, double y, double z) {
        // 1) Escalar
        double x_s = x * scale;
        double y_s = y * scale;
        double z_s = z * scale;

        // 2) Rotar alrededor de Y
        double cosY = Math.cos(angleY), sinY = Math.sin(angleY);
        double x1 = x_s * cosY - z_s * sinY;
        double z1 = x_s * sinY + z_s * cosY;
        double y1 = y_s;

        // 3) Rotar alrededor de X
        double cosX = Math.cos(angleX), sinX = Math.sin(angleX);
        double y2 = y1 * cosX - z1 * sinX;
        double z2 = y1 * sinX + z1 * cosX;
        double x2 = x1;

        // 4) Proyección paramétrica de perspectiva
        double xp = x_c - ((x2 - x_c) * z_c) / (z2 - z_c);
        double yp = y_c - ((y2 - y_c) * z_c) / (z2 - z_c);

        // 5) A coordenadas de pantalla (centro, invierte Y)
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
        SwingUtilities.invokeLater(Escalamiento3D::new);
    }
}
