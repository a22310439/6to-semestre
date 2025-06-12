import java.util.ArrayList;
import java.util.List;

public class Cubo implements Figura3D {
    private final double lado;

    public Cubo(double lado) {
        this.lado = lado;
    }

    @Override
    public List<Linea3D> getLineas() {
        double h = lado / 2.0;
        // Definir los 8 vértices del cubo
        Point3D[] v = {
            new Point3D(-h, -h, -h), new Point3D( h, -h, -h),
            new Point3D( h,  h, -h), new Point3D(-h,  h, -h),
            new Point3D(-h, -h,  h), new Point3D( h, -h,  h),
            new Point3D( h,  h,  h), new Point3D(-h,  h,  h)
        };
        // Indices de las aristas
        int[][] aristas = {
            {0,1},{1,2},{2,3},{3,0}, // cara trasera
            {4,5},{5,6},{6,7},{7,4}, // cara frontal
            {0,4},{1,5},{2,6},{3,7}  // conexiones entre caras
        };
        List<Linea3D> lineas = new ArrayList<>();
        for (int[] e : aristas) {
            lineas.add(new Linea3D(v[e[0]], v[e[1]]));
        }
        return lineas;
    }
}