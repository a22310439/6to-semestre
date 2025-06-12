import java.util.ArrayList;
import java.util.List;

public class Esfera implements Figura3D {
    private final double radio;
    private final int slices; // divisiones alrededor del eje vertical
    private final int stacks; // divisiones de polo a polo

    public Esfera(double radio, int slices, int stacks) {
        this.radio = radio;
        this.slices = Math.max(3, slices);
        this.stacks = Math.max(2, stacks);
    }

    @Override
    public List<Linea3D> getLineas() {
        List<Linea3D> lineas = new ArrayList<>();
        // Círculos de latitud
        for (int i = 0; i <= stacks; i++) {
            double phi = -Math.PI/2 + i * (Math.PI / stacks);
            double z = radio * Math.sin(phi);
            double r = radio * Math.cos(phi);
            Point3D prev = null;
            for (int j = 0; j <= slices; j++) {
                double theta = j * (2 * Math.PI / slices);
                double x = r * Math.cos(theta);
                double y = r * Math.sin(theta);
                Point3D curr = new Point3D(x, y, z);
                if (prev != null) {
                    lineas.add(new Linea3D(prev, curr));
                }
                prev = curr;
            }
        }
        // Meridianos (longitud)
        for (int j = 0; j < slices; j++) {
            double theta = j * (2 * Math.PI / slices);
            Point3D prev = null;
            for (int i = 0; i <= stacks; i++) {
                double phi = -Math.PI/2 + i * (Math.PI / stacks);
                double x = radio * Math.cos(phi) * Math.cos(theta);
                double y = radio * Math.cos(phi) * Math.sin(theta);
                double z = radio * Math.sin(phi);
                Point3D curr = new Point3D(x, y, z);
                if (prev != null) {
                    lineas.add(new Linea3D(prev, curr));
                }
                prev = curr;
            }
        }
        return lineas;
    }
}
