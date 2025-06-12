import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

public class Main extends JFrame {
    private final GraficosProyecto graficos;
    private final JLabel rotationLabel;
    private final JLabel shapeLabel;

    // Proyección
    private boolean perspective = false;
    private final double focalLength    = 400;
    private final double cameraDistance = 500;
    private final double axisLength     = 200;

    // Cámara
    private double cameraYaw   = Math.toRadians(45);
    private double cameraPitch = Math.toRadians(325);
    private final double camRotSpeed = 0.01;
    private int lastMouseX, lastMouseY;

    // Traslación (posición del centro de la figura en el mundo)
    private double tx = 0, ty = 0, tz = 0;
    private final double transStep = 10;

    // Rotación sobre su propio eje
    private double rotX = 0, rotY = 0, rotZ = 0;
    private boolean rotatingSelfX = false,
                    rotatingSelfY = false,
                    rotatingSelfZ = false;
    private final double rotSpeed = 0.02;

    // Órbita (rotación de la traslación alrededor del origen)
    private boolean rotatingOrbit = false;
    private char orbitAxis = ' ';  // 'X','Y','Z' o ' ' ninguno
    private final double orbitSpeed = 0.02;

    // Figuras
    private final List<Figura3D> figuras = new ArrayList<>();
    private int currentIndex = 0;

    public Main() {
        super("Visualizador 3D");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

        graficos = new GraficosProyecto(this);

        // Panel de estado
        JPanel top = new JPanel(new BorderLayout());
        rotationLabel = new JLabel("", SwingConstants.LEFT);
        shapeLabel    = new JLabel("", SwingConstants.RIGHT);
        top.add(rotationLabel, BorderLayout.WEST);
        top.add(shapeLabel,    BorderLayout.EAST);
        getContentPane().add(top, BorderLayout.NORTH);

        // Cargar figuras
        figuras.add(new Cubo(50));
        figuras.add(new Esfera(50, 16, 16));
        figuras.add(new Tetraedro(80));
        figuras.add(new Decaedro(50));
        figuras.add(new Curva(100, 20, 4 * Math.PI, 200));
        figuras.add(new Superficie(-100, 100, -100, 100, 40, 40));
        figuras.add(new Cilindro(0.0, 2 * Math.PI, 0.0, 2 * Math.PI, 200, 60));
        updateShapeLabel();

        // Timer para animación continua (~60 fps)
        new Timer(16, e -> render()).start();

        // Controles de teclado
        setFocusable(true);
        requestFocusInWindow();
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int code = e.getKeyCode();
                switch (code) {
                    case KeyEvent.VK_SPACE -> {
                        currentIndex = (currentIndex + 1) % figuras.size();
                        updateShapeLabel();
                    }
                    case KeyEvent.VK_R -> {
                        tx = ty = tz = 0;
                        rotX = rotY = rotZ = 0;
                        rotatingSelfX = rotatingSelfY = rotatingSelfZ = false;
                        rotatingOrbit = false;
                        orbitAxis = ' ';
                    }
                    case KeyEvent.VK_P -> perspective = !perspective;
                    case KeyEvent.VK_W -> ty += transStep;
                    case KeyEvent.VK_S -> ty -= transStep;
                    case KeyEvent.VK_A -> tz -= transStep;
                    case KeyEvent.VK_D -> tz += transStep;
                    case KeyEvent.VK_Q -> tx += transStep;
                    case KeyEvent.VK_E -> tx -= transStep;

                    // Flechas para órbita alrededor de ejes globales
                    case KeyEvent.VK_LEFT -> {
                        rotatingOrbit = !rotatingOrbit;
                        orbitAxis = 'X';
                        // desactivar rotación propia
                        rotatingSelfX = rotatingSelfY = rotatingSelfZ = false;
                    }
                    case KeyEvent.VK_UP -> {
                        rotatingOrbit = !rotatingOrbit;
                        orbitAxis = 'Y';
                        rotatingSelfX = rotatingSelfY = rotatingSelfZ = false;
                    }
                    case KeyEvent.VK_RIGHT -> {
                        rotatingOrbit = !rotatingOrbit;
                        orbitAxis = 'Z';
                        rotatingSelfX = rotatingSelfY = rotatingSelfZ = false;
                    }

                    // Teclas para rotación propia
                    case KeyEvent.VK_X -> {
                        rotatingSelfZ = !rotatingSelfZ;
                        rotatingSelfX = rotatingSelfY = false;
                    }
                    case KeyEvent.VK_Y -> {
                        rotatingSelfY = !rotatingSelfY;
                        rotatingSelfX = rotatingSelfZ = false;
                    }
                    case KeyEvent.VK_Z -> {
                        rotatingSelfX = !rotatingSelfX;
                        rotatingSelfY = rotatingSelfZ = false;
                    }
                }
            }
        });

        // Rotación cámara con mouse
        getContentPane().addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                lastMouseX = e.getX();
                lastMouseY = e.getY();
            }
        });
        getContentPane().addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                int dx = e.getX() - lastMouseX;
                int dy = e.getY() - lastMouseY;
                cameraYaw   += dx * camRotSpeed;
                cameraPitch += dy * camRotSpeed;
                lastMouseX = e.getX();
                lastMouseY = e.getY();
            }
        });

        setVisible(true);
    }

    private void updateShapeLabel() {
        shapeLabel.setText(figuras.get(currentIndex).getClass().getSimpleName());
    }

    // Rotación sobre su propio eje
    private double[] rotateModel(double x, double y, double z) {
        if (rotatingSelfX) {
            double c = Math.cos(rotX), s = Math.sin(rotX);
            double y1 = y * c - z * s, z1 = y * s + z * c;
            y = y1; z = z1;
        }
        if (rotatingSelfY) {
            double c = Math.cos(rotY), s = Math.sin(rotY);
            double x1 = x * c + z * s, z1 = -x * s + z * c;
            x = x1; z = z1;
        }
        if (rotatingSelfZ) {
            double c = Math.cos(rotZ), s = Math.sin(rotZ);
            double x1 = x * c - y * s, y1 = x * s + y * c;
            x = x1; y = y1;
        }
        return new double[]{x, y, z};
    }

    // Rotaciones globales para la traslación
    private double[] rotateAroundX(double x, double y, double z, double ang) {
        double c = Math.cos(ang), s = Math.sin(ang);
        return new double[]{ x, y * c - z * s, y * s + z * c };
    }
    private double[] rotateAroundY(double x, double y, double z, double ang) {
        double c = Math.cos(ang), s = Math.sin(ang);
        return new double[]{ x * c + z * s, y, -x * s + z * c };
    }
    private double[] rotateAroundZ(double x, double y, double z, double ang) {
        double c = Math.cos(ang), s = Math.sin(ang);
        return new double[]{ x * c - y * s, x * s + y * c, z };
    }

    // Transformación mundo → cámara
    private double[] transform(double x, double y, double z) {
        x = -x; z = -z;
        double cosY = Math.cos(cameraYaw), sinY = Math.sin(cameraYaw);
        double x1 = x * cosY - z * sinY;
        double z1 = x * sinY + z * cosY;
        double cosP = Math.cos(cameraPitch), sinP = Math.sin(cameraPitch);
        double y2 = y * cosP - z1 * sinP;
        double z2 = y * sinP + z1 * cosP;
        return new double[]{ x1, y2, z2 };
    }

    // Proyección ortográfica/perspectiva
    private Point project(double x, double y, double z) {
        if (perspective) {
            double f = focalLength / (z + cameraDistance);
            return new Point((int)(x * f), (int)(y * f));
        } else {
            return new Point((int)x, (int)y);
        }
    }

    private void render() {
        // 1) Actualizar ángulos propios
        if (rotatingSelfX) rotX += rotSpeed;
        if (rotatingSelfY) rotY += rotSpeed;
        if (rotatingSelfZ) rotZ += rotSpeed;

        // 2) Actualizar traslación orbital (rotar tx,ty,tz alrededor del origen)
        if (rotatingOrbit) {
            double[] t;
            switch (orbitAxis) {
                case 'X' -> t = rotateAroundX(tx, ty, tz, orbitSpeed);
                case 'Y' -> t = rotateAroundY(tx, ty, tz, orbitSpeed);
                case 'Z' -> t = rotateAroundZ(tx, ty, tz, orbitSpeed);
                default  -> t = new double[]{tx, ty, tz};
            }
            tx = t[0]; ty = t[1]; tz = t[2];
        }

        // 3) Dibujar fondo y ejes
        graficos.clear(Color.BLACK);
        int cx = getContentPane().getWidth()  / 2;
        int cy = getContentPane().getHeight() / 2;

        rotationLabel.setText(
            String.format("Yaw: %.1f°, Pitch: %.1f°",
                Math.toDegrees(cameraYaw),
                Math.toDegrees(cameraPitch))
        );

        var o3  = transform(0, 0, 0);
        var ex3 = transform(axisLength, 0, 0);
        var ey3 = transform(0, axisLength, 0);
        var ez3 = transform(0, 0, axisLength);
        var o   = project(o3[0], o3[1], o3[2]);
        var ex  = project(ex3[0], ex3[1], ex3[2]);
        var ey  = project(ey3[0], ey3[1], ey3[2]);
        var ez  = project(ez3[0], ez3[1], ez3[2]);

        // Colores intercambiados X↔Z
        graficos.drawLine(cx+o.x, cy-o.y, cx+ex.x, cy-ex.y, Color.GREEN); // eje X en verde
        graficos.drawLine(cx+o.x, cy-o.y, cx+ey.x, cy-ey.y, Color.RED);   // eje Y en rojo
        graficos.drawLine(cx+o.x, cy-o.y, cx+ez.x, cy-ez.y, Color.BLUE);  // eje Z en azul

        // 4) Dibujar figura
        Figura3D fig = figuras.get(currentIndex);
        for (Linea3D L : fig.getLineas()) {
            // a) rotación propia
            double[] pA = rotateModel(L.a.x, L.a.y, L.a.z);
            double[] pB = rotateModel(L.b.x, L.b.y, L.b.z);

            // b) escala si es cilindro, luego traslación (ya actualizada por órbita)
            double ax, ay, az, bx, by, bz;
            if (fig instanceof Cilindro) {
                double scale = 30.0;
                ax = pA[0] * scale + tx;
                ay = pA[1] * scale + ty;
                az = pA[2] * scale + tz;
                bx = pB[0] * scale + tx;
                by = pB[1] * scale + ty;
                bz = pB[2] * scale + tz;
            } else {
                ax = pA[0] + tx;
                ay = pA[1] + ty;
                az = pA[2] + tz;
                bx = pB[0] + tx;
                by = pB[1] + ty;
                bz = pB[2] + tz;
            }

            // c) color cilindro
            Color lineColor = Color.WHITE;
            if (fig instanceof Cilindro) {
                double tNorm = ((L.a.y + L.b.y) / 2.0) / (2 * Math.PI);
                tNorm = Math.max(0, Math.min(1, tNorm));
                lineColor = Color.getHSBColor((float)((2.0/3.0)*(1 - tNorm)), 1f, 1f);
            }

            // d) cámara + proyección
            var A = transform(ax, ay, az);
            var B = transform(bx, by, bz);
            var p1 = project(A[0], A[1], A[2]);
            var p2 = project(B[0], B[1], B[2]);

            // e) dibujar línea
            graficos.drawLine(cx + p1.x, cy - p1.y,
                              cx + p2.x, cy - p2.y,
                              lineColor);
        }

        // 5) presentar
        graficos.present();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Main::new);
    }
}
