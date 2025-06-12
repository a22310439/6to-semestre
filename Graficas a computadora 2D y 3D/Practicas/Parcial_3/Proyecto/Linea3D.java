import java.util.Objects;

public class Linea3D {
    public final Point3D a;
    public final Point3D b;

    public Linea3D(Point3D a, Point3D b) {
        this.a = Objects.requireNonNull(a, "Punto a no puede ser nulo");
        this.b = Objects.requireNonNull(b, "Punto b no puede ser nulo");
    }
}
