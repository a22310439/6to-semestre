import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class Superficie3D extends JFrame {
    private final Graficos3D graficos;
    private final int        width  = 600;
    private final int        height = 600;

    // Rango y pasos para la malla en X y Y
    private final double X_MIN = -100;
    private final double X_MAX =  100;
    private final double Y_MIN = -100;
    private final double Y_MAX =  100;
    private final int    NX    =  40;  // número de divisiones en X
    private final int    NY    =  40;  // número de divisiones en Y

    // Posición del observador fijo en (x_c, y_c, z_c)
    private final double x_c = 0.0;
    private final double y_c = 0.0;
    private final double z_c = -200.0;

    // Ángulos actuales de rotación
    private double angleX = Math.toRadians(20);
    private double angleY = Math.toRadians(-30);

    // Variables para seguimiento del drag
    private int prevMouseX;
    private int prevMouseY;

    public Superficie3D() {
        super("Superficie 3D");
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
                angleY += dx * sens;
                angleX += dy * sens;
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

    private int[] project(double x, double y, double z) {
        // 1) Rotar alrededor de Y
        double cosY = Math.cos(angleY), sinY = Math.sin(angleY);
        double x1 = x * cosY + z * sinY;
        double z1 = -x * sinY + z * cosY;
        double y1 = y;

        // 2) Rotar alrededor de X
        double cosX = Math.cos(angleX), sinX = Math.sin(angleX);
        double y2 = y1 * cosX - z1 * sinX;
        double z2 = y1 * sinX + z1 * cosX;
        double x2 = x1;

        // 3) Proyección de perspectiva
        double xp = x_c - ((x2 - x_c) * z_c) / (z2 - z_c);
        double yp = y_c - ((y2 - y_c) * z_c) / (z2 - z_c);

        // 4) Transformar a coordenadas de pantalla
        int screenX = (int)Math.round(width  / 2.0 + xp);
        int screenY = (int)Math.round(height / 2.0 - yp);
        return new int[]{screenX, screenY};
    }

    private void drawScene() {
        graficos.clear(Color.WHITE);

        // Precomputar coordenadas 3D de la malla
        double[][][] grid = new double[NX+1][NY+1][3];
        for (int i = 0; i <= NX; i++) {
            double x = X_MIN + (X_MAX - X_MIN) * i / NX;
            for (int j = 0; j <= NY; j++) {
                double y = Y_MIN + (Y_MAX - Y_MIN) * j / NY;
                double r = Math.hypot(x, y);
                double z;
                if (r == 0) {
                    z = 50; // limitar r=0
                } else {
                    z = 50 * Math.sin(r / 10.0) / (r / 10.0);
                }
                grid[i][j][0] = x;
                grid[i][j][1] = y;
                grid[i][j][2] = z;
            }
        }

        // Proyectar todos los puntos
        int[][][] pts2D = new int[NX+1][NY+1][2];
        for (int i = 0; i <= NX; i++) {
            for (int j = 0; j <= NY; j++) {
                double[] v = grid[i][j];
                pts2D[i][j] = project(v[0], v[1], v[2]);
            }
        }

        // Dibujar líneas en la dirección i (X)
        Color meshColor = Color.GRAY;
        for (int i = 0; i <= NX; i++) {
            for (int j = 0; j < NY; j++) {
                int x0 = pts2D[i][j][0],     y0 = pts2D[i][j][1];
                int x1 = pts2D[i][j+1][0],   y1 = pts2D[i][j+1][1];
                graficos.drawLine(x0, y0, x1, y1, meshColor);
            }
        }

        // Dibujar líneas en la dirección j (Y)
        for (int j = 0; j <= NY; j++) {
            for (int i = 0; i < NX; i++) {
                int x0 = pts2D[i][j][0],     y0 = pts2D[i][j][1];
                int x1 = pts2D[i+1][j][0],   y1 = pts2D[i+1][j][1];
                graficos.drawLine(x0, y0, x1, y1, meshColor);
            }
        }

        graficos.present();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Superficie3D::new);
    }
}
