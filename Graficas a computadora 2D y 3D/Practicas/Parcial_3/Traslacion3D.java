// ProyeccionPerspectivaTranslate.java
import java.awt.Color;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

/**
 * Dibuja un cubo 3D usando proyección de perspectiva paramétrica:
 *    x' = x_c - ((x₁ - x_c)·z_c)/(z₁ - z_c)
 *    y' = y_c - ((y₁ - y_c)·z_c)/(z₁ - z_c)
 * 
 * Y permite trasladar el cubo continuamente con:
 *   W/S: mover arriba/abajo en Y
 *   D/A: mover derecha/izquierda en X
 *   Q/E: mover adelante/atrás en Z
 *
 * Usa Graficos3D para el dibujo (sin cambios respecto a la versión anterior).
 */
public class Traslacion3D extends JFrame {
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

    // Aristas como índices de vértices
    private final int[][] edges = {
        {0,1}, {1,2}, {2,3}, {3,0},   // cara trasera (z=-100)
        {4,5}, {5,6}, {6,7}, {7,4},   // cara frontal (z=+100)
        {0,4}, {1,5}, {2,6}, {3,7}    // aristas verticales
    };

    // Centro de proyección (x_c, y_c, z_c)
    // Situamos la cámara en (0, 0, -cameraDistance), proyectando sobre Z=0
    private final double cameraDistance = 500.0;
    private final double x_c = 0.0;
    private final double y_c = 0.0;
    private final double z_c = -cameraDistance;

    // Desplazamiento actual del cubo
    private double tx = 0.0, ty = 0.0, tz = 0.0;

    // Flags de teclas presionadas
    private boolean moveLeft  = false;
    private boolean moveRight = false;
    private boolean moveUp    = false;
    private boolean moveDown  = false;
    private boolean moveIn    = false;
    private boolean moveOut   = false;

    public Traslacion3D() {
        super("Traslacion 3D");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setLayout(new java.awt.BorderLayout());

        graficos = new Graficos3D(this);

        setSize(width, height);
        setLocationRelativeTo(null);

        // Listener de teclado para marcar flags
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_W -> moveUp    = true;
                    case KeyEvent.VK_S -> moveDown  = true;
                    case KeyEvent.VK_D -> moveRight = true;
                    case KeyEvent.VK_A -> moveLeft  = true;
                    case KeyEvent.VK_Q -> moveIn    = true;
                    case KeyEvent.VK_E -> moveOut   = true;
                }
            }
            @Override
            public void keyReleased(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_W -> moveUp    = false;
                    case KeyEvent.VK_S -> moveDown  = false;
                    case KeyEvent.VK_D -> moveRight = false;
                    case KeyEvent.VK_A -> moveLeft  = false;
                    case KeyEvent.VK_Q -> moveIn    = false;
                    case KeyEvent.VK_E -> moveOut   = false;
                }
            }
        });

        // Asegurar que el JFrame reciba foco para los eventos de teclado
        setFocusable(true);
        requestFocusInWindow();

        setVisible(true);

        // Timer para actualizar traslación continuamente cada 10 ms
        int delay = 10;
        new Timer(delay, ev -> {
            boolean changed = false;
            double delta = 5.0; // velocidad de traslación

            if (moveUp)    { ty += delta; changed = true; }
            if (moveDown)  { ty -= delta; changed = true; }
            if (moveRight) { tx += delta; changed = true; }
            if (moveLeft)  { tx -= delta; changed = true; }
            if (moveIn)    { tz += delta; changed = true; }
            if (moveOut)   { tz -= delta; changed = true; }

            if (changed) {
                drawScene();
            }
        }).start();

        SwingUtilities.invokeLater(this::drawScene);
    }

    private int[] project(double x, double y, double z) {
        // 1) Aplicar traslación
        double x_t = x + tx;
        double y_t = y + ty;
        double z_t = z + tz;

        // 2) Proyección perspectiva
        double xp = x_c - ((x_t - x_c) * z_c) / (z_t - z_c);
        double yp = y_c - ((y_t - y_c) * z_c) / (z_t - z_c);

        // 3) A coordenadas de pantalla (centro, invierte Y)
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
        SwingUtilities.invokeLater(Traslacion3D::new);
    }
}
