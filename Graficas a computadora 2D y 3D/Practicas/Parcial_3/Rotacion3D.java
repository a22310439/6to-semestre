import java.awt.Color;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

public class Rotacion3D extends JFrame {
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

    // Aristas, definidas por índices de vértices
    private final int[][] edges = {
        {0,1}, {1,2}, {2,3}, {3,0},   // cara trasera (z=-100)
        {4,5}, {5,6}, {6,7}, {7,4},   // cara frontal (z=+100)
        {0,4}, {1,5}, {2,6}, {3,7}    // aristas verticales
    };

    // Coordenadas del observador fijo en (x_c, y_c, z_c)
    private final double x_c = 0.0;
    private final double y_c = 0.0;
    private final double z_c = -500.0;

    // Ángulos de rotación del cubo (en radianes)
    private double angleX = 0.0;
    private double angleY = 0.0;
    private double angleZ = 0.0;

    // Flags que indican qué teclas están presionadas
    private boolean rotXPos = false; // W
    private boolean rotXNeg = false; // S
    private boolean rotYPos = false; // D
    private boolean rotYNeg = false; // A
    private boolean rotZPos = false; // E
    private boolean rotZNeg = false; // Q

    public Rotacion3D() {
        super("Rotacion 3D");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setLayout(new java.awt.BorderLayout());

        graficos = new Graficos3D(this);

        setSize(width, height);
        setLocationRelativeTo(null);

        // Listener de teclado para marcar flags de rotación
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_W -> rotXNeg = true;
                    case KeyEvent.VK_S -> rotXPos = true;
                    case KeyEvent.VK_D -> rotYPos = true;
                    case KeyEvent.VK_A -> rotYNeg = true;
                    case KeyEvent.VK_E -> rotZPos = true;
                    case KeyEvent.VK_Q -> rotZNeg = true;
                }
            }
            @Override
            public void keyReleased(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_W -> rotXNeg = false;
                    case KeyEvent.VK_S -> rotXPos = false;
                    case KeyEvent.VK_D -> rotYPos = false;
                    case KeyEvent.VK_A -> rotYNeg = false;
                    case KeyEvent.VK_E -> rotZPos = false;
                    case KeyEvent.VK_Q -> rotZNeg = false;
                }
            }
        });

        // Asegurar que el JFrame reciba foco para eventos de teclado
        setFocusable(true);
        requestFocusInWindow();

        setVisible(true);

        // Timer para actualizar ángulos de rotación cada 10 ms
        int delay = 10; // ms
        new Timer(delay, ev -> {
            boolean changed = false;
            double delta = Math.toRadians(2); // 2° en radianes

            if (rotXNeg) { angleX -= delta; changed = true; }
            if (rotXPos) { angleX += delta; changed = true; }
            if (rotYNeg) { angleY -= delta; changed = true; }
            if (rotYPos) { angleY += delta; changed = true; }
            if (rotZNeg) { angleZ -= delta; changed = true; }
            if (rotZPos) { angleZ += delta; changed = true; }

            if (changed) {
                drawScene();
            }
        }).start();

        // Dibuja el cubo inicialmente
        SwingUtilities.invokeLater(this::drawScene);
    }

    private int[] project(double x, double y, double z) {
        // 1) Rotar alrededor de X
        double cosX = Math.cos(angleX), sinX = Math.sin(angleX);
        double y1 = y * cosX - z * sinX;
        double z1 = y * sinX + z * cosX;
        double x1 = x;

        // 2) Rotar alrededor de Y
        double cosY = Math.cos(angleY), sinY = Math.sin(angleY);
        double x2 = x1 * cosY + z1 * sinY;
        double z2 = -x1 * sinY + z1 * cosY;
        double y2 = y1;

        // 3) Rotar alrededor de Z
        double cosZ = Math.cos(angleZ), sinZ = Math.sin(angleZ);
        double x3 = x2 * cosZ - y2 * sinZ;
        double y3 = x2 * sinZ + y2 * cosZ;
        double z3 = z2;

        // 4) Proyección de perspectiva
        double xp = x_c - ((x3 - x_c) * z_c) / (z3 - z_c);
        double yp = y_c - ((y3 - y_c) * z_c) / (z3 - z_c);

        // 5) Transformar a coordenadas de pantalla
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
        SwingUtilities.invokeLater(Rotacion3D::new);
    }
}
