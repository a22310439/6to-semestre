import java.util.ArrayList;
import java.util.List;

public class Tetraedro implements Figura3D {
    private final double lado;

    public Tetraedro(double lado) {
        this.lado = lado;
    }

    @Override
    public List<Linea3D> getLineas() {
        // Factor para escalar vértices base (distancia arista = 2*sqrt(2))
        double h = lado / (2 * Math.sqrt(2));
        // Vértices equidistantes
        Point3D[] v = {
            new Point3D( h,  h,  h),
            new Point3D( h, -h, -h),
            new Point3D(-h,  h, -h),
            new Point3D(-h, -h,  h)
        };
        // Índices de las 6 aristas
        int[][] aristas = {
            {0,1}, {0,2}, {0,3},
            {1,2}, {1,3}, {2,3}
        };
        List<Linea3D> lineas = new ArrayList<>();
        for (int[] e : aristas) {
            lineas.add(new Linea3D(v[e[0]], v[e[1]]));
        }
        return lineas;
    }
}
