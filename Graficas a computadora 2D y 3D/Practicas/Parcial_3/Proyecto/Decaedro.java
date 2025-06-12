import java.util.ArrayList;
import java.util.List;

public class Decaedro implements Figura3D {
    private final double radius; // radio de la circunferencia base
    private final double height; // altura de cada pirámide desde el centro de la base
    private static final int SIDES = 5;

    public Decaedro(double size) {
        this(size, size);
    }

    public Decaedro(double radius, double height) {
        this.radius = radius;
        this.height = height;
    }

    @Override
    public List<Linea3D> getLineas() {
        List<Linea3D> lineas = new ArrayList<>();
        // Vértices de la base pentagonal
        Point3D[] base = new Point3D[SIDES];
        for (int i = 0; i < SIDES; i++) {
            double theta = 2 * Math.PI * i / SIDES;
            double x = radius * Math.cos(theta);
            double z = radius * Math.sin(theta);
            base[i] = new Point3D(x, 0, z);
        }
        // Vértices de los ápices
        Point3D top    = new Point3D(0,  height, 0);
        Point3D bottom = new Point3D(0, -height, 0);
        // Conectar ápices con cada vértice de la base
        for (Point3D p : base) {
            lineas.add(new Linea3D(top,    p));
            lineas.add(new Linea3D(bottom, p));
        }
        // Conectar la base circularmente
        for (int i = 0; i < SIDES; i++) {
            Linea3D edge = new Linea3D(base[i], base[(i+1) % SIDES]);
            lineas.add(edge);
        }
        return lineas;
    }
}
