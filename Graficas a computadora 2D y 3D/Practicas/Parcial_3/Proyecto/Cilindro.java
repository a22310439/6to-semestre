import java.util.ArrayList;
import java.util.List;

public class Cilindro implements Figura3D {
    private final double tMin, tMax;
    private final double phiMin, phiMax;
    private final int tSteps, phiSteps;

    public Cilindro(double tMin, double tMax, double phiMin, double phiMax,
                    int tSteps, int phiSteps) {
        this.tMin     = tMin;
        this.tMax     = tMax;
        this.phiMin   = phiMin;
        this.phiMax   = phiMax;
        this.tSteps   = Math.max(1, tSteps);
        this.phiSteps = Math.max(3, phiSteps);
    }

    @Override
    public List<Linea3D> getLineas() {
        List<Linea3D> lineas = new ArrayList<>();
        double dt  = (tMax   - tMin)   / tSteps;
        double dphi= (phiMax - phiMin) / phiSteps;

        // Generar puntos de la malla
        Point3D[][] grid = new Point3D[tSteps+1][phiSteps+1];
        for (int i = 0; i <= tSteps; i++) {
            double t = tMin + i * dt;
            double r = 2 + Math.cos(t);
            for (int j = 0; j <= phiSteps; j++) {
                double phi = phiMin + j * dphi;
                double x = r * Math.cos(phi);
                double z = r * Math.sin(phi);
                double y = t;  // eje Y como altura
                grid[i][j] = new Point3D(x, y, z);
            }
        }

        // Conectar líneas verticales (en t)
        for (int j = 0; j <= phiSteps; j++) {
            for (int i = 0; i < tSteps; i++) {
                lineas.add(new Linea3D(grid[i][j], grid[i+1][j]));
            }
        }
        // Conectar líneas horizontales (en φ)
        for (int i = 0; i <= tSteps; i++) {
            for (int j = 0; j < phiSteps; j++) {
                lineas.add(new Linea3D(grid[i][j], grid[i][j+1]));
            }
        }

        return lineas;
    }
}
