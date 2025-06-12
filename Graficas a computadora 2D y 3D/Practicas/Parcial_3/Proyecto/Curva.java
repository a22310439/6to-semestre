import java.util.ArrayList;
import java.util.List;

public class Curva implements Figura3D {
    private final double R;
    private final double C;
    private final double tMax;
    private final int steps;

    public Curva(double R, double C, double tMax, int steps) {
        this.R = R;
        this.C = C;
        this.tMax = tMax;
        this.steps = Math.max(1, steps);
    }

    @Override
    public List<Linea3D> getLineas() {
        List<Linea3D> lineas = new ArrayList<>();
        Point3D prev = null;
        for (int i = 0; i <= steps; i++) {
            double t = tMax * i / steps;
            double x = R * Math.cos(t);
            double y = R * Math.sin(t);
            double z = C * t;
            Point3D curr = new Point3D(x, y, z);
            if (prev != null) {
                lineas.add(new Linea3D(prev, curr));
            }
            prev = curr;
        }
        return lineas;
    }
}