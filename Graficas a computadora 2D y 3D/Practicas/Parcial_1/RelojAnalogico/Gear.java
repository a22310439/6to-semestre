import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;

public class Gear {
    private final double radius;
    private final int teeth;
    private final double toothDepth;
    private final double cx;
    private final double cy;
    private double startAngle;
    
    public Gear(double radius, int teeth, double toothDepth, double startAngle, double cx, double cy) {
        this.radius = radius;
        this.teeth = teeth;
        this.toothDepth = toothDepth;
        this.startAngle = startAngle;
        this.cx = cx;
        this.cy = cy;
    }
    
    public void setStartAngle(double startAngle) {
        this.startAngle = startAngle;
    }
    
    /**
     * Genera la forma del engrane usando 4 puntos por diente.
     */
    public Path2D getShape() {
        double outerRadius = radius;
        double innerRadius = radius - toothDepth;
        double toothAngle = 2 * Math.PI / teeth;
        Path2D gear = new Path2D.Double();
        
        // Comenzamos en el primer punto exterior
        double x0 = outerRadius * Math.cos(startAngle);
        double y0 = outerRadius * Math.sin(startAngle);
        gear.moveTo(x0, y0);
        
        // Por cada diente definimos 4 puntos (perfil más detallado)
        for (int i = 0; i < teeth; i++) {
            double baseAngle = startAngle + i * toothAngle;
            double a0 = baseAngle;                   // Borde exterior inicial
            double a1 = baseAngle + toothAngle / 4;    // Punta exterior
            double a2 = baseAngle + toothAngle / 2;    // Borde interior
            double a3 = baseAngle + 3 * toothAngle / 4;  // Punta interior
            gear.lineTo(outerRadius * Math.cos(a0), outerRadius * Math.sin(a0));
            gear.lineTo(outerRadius * Math.cos(a1), outerRadius * Math.sin(a1));
            gear.lineTo(innerRadius * Math.cos(a2), innerRadius * Math.sin(a2));
            gear.lineTo(innerRadius * Math.cos(a3), innerRadius * Math.sin(a3));
        }
        
        gear.closePath();
        AffineTransform at = AffineTransform.getTranslateInstance(cx, cy);
        return (Path2D) at.createTransformedShape(gear);
    }
    
    /**
     * Dibuja el engrane en el contexto gráfico indicado.
     *
     * @param g2         Objeto Graphics2D sobre el que dibujar.
     * @param fillColor  Color de relleno.
     * @param strokeColor Color de trazo.
     */
    public void draw(Graphics2D g2, Color fillColor, Color strokeColor) {
        Path2D shape = getShape();
        g2.setColor(fillColor);
        g2.fill(shape);
        g2.setColor(strokeColor);
        g2.draw(shape);
    }
}
