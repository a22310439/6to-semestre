import java.util.ArrayList;
import java.util.List;

public class Superficie implements Figura3D {
    private final double xMin, xMax, yMin, yMax;
    private final int nx, ny;

    public Superficie(double xMin, double xMax, double yMin, double yMax, int nx, int ny) {
        this.xMin = xMin; this.xMax = xMax;
        this.yMin = yMin; this.yMax = yMax;
        this.nx = Math.max(1, nx);
        this.ny = Math.max(1, ny);
    }

    @Override
    public List<Linea3D> getLineas() {
        List<Linea3D> lineas = new ArrayList<>();
        Point3D[][] grid = new Point3D[nx+1][ny+1];
        // Construir la malla de puntos 3D
        for (int i = 0; i <= nx; i++) {
            double x = xMin + (xMax - xMin) * i / nx;
            for (int j = 0; j <= ny; j++) {
                double y = yMin + (yMax - yMin) * j / ny;
                double r = Math.hypot(x, y);
                double z;
                if (r == 0) {
                    z = 50.0;
                } else {
                    z = 50.0 * Math.sin(r/10.0) / (r/10.0);
                }
                grid[i][j] = new Point3D(x, y, z);
            }
        }
        // Conectar líneas en dirección Y (columnas)
        for (int i = 0; i <= nx; i++) {
            for (int j = 0; j < ny; j++) {
                lineas.add(new Linea3D(grid[i][j], grid[i][j+1]));
            }
        }
        // Conectar líneas en dirección X (filas)
        for (int j = 0; j <= ny; j++) {
            for (int i = 0; i < nx; i++) {
                lineas.add(new Linea3D(grid[i][j], grid[i+1][j]));
            }
        }
        return lineas;
    }
}