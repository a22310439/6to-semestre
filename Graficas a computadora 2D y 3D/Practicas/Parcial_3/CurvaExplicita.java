import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class CurvaExplicita extends JFrame {
    private final Graficos3D graficos;
    private final int        width  = 600;
    private final int        height = 600;

    // Parámetros de la hélice: x = R cos(t), y = R sin(t), z = C·t
    private final double R = 100.0;
    private final double C = 20.0;
    private final int    STEPS = 200;     // número de segmentos de la curva
    private final double T_MAX = 4 * Math.PI;

    // Posición del observador fijo en (x_c, y_c, z_c)
    private final double x_c = 0.0;
    private final double y_c = 0.0;
    private final double z_c = -500.0;

    // Ángulos actuales de rotación
    private double angleX = Math.toRadians(20);
    private double angleY = Math.toRadians(-30);

    // Variables para seguimiento del drag
    private int prevMouseX;
    private int prevMouseY;

    public CurvaExplicita() {
        super("Curva Explicita");
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

        // 3) Proyección paramétrica de perspectiva
        double xp = x_c - ((x2 - x_c) * z_c) / (z2 - z_c);
        double yp = y_c - ((y2 - y_c) * z_c) / (z2 - z_c);

        // 4) Transformar a coordenadas de pantalla
        int screenX = (int)Math.round(width  / 2.0 + xp);
        int screenY = (int)Math.round(height / 2.0 - yp);
        return new int[]{screenX, screenY};
    }

    private void drawScene() {
        graficos.clear(Color.WHITE);

        // Calcular puntos de la curva en 3D y proyectarlos
        int[][] pts2D = new int[STEPS + 1][2];
        for (int i = 0; i <= STEPS; i++) {
            double t = (T_MAX * i) / STEPS;
            double x = R * Math.cos(t);
            double y = R * Math.sin(t);
            double z = C * t;
            pts2D[i] = project(x, y, z);
        }

        // Dibujar segmentos de la curva
        Color curveColor = Color.BLUE;
        for (int i = 0; i < STEPS; i++) {
            int x0 = pts2D[i][0],     y0 = pts2D[i][1];
            int x1 = pts2D[i+1][0],   y1 = pts2D[i+1][1];
            graficos.drawLine(x0, y0, x1, y1, curveColor);
        }

        graficos.present();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(CurvaExplicita::new);
    }
}
