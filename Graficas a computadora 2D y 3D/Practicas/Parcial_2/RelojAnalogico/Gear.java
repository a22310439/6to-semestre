import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Gear {
    private final double radius;
    private final int    teeth;
    private final double toothDepth;
    private final double cx;
    private final double cy;
    private double       startAngle;
    
    public Gear(double radius, int teeth, double toothDepth,
                double startAngle, double cx, double cy) {
        this.radius     = radius;
        this.teeth      = teeth;
        this.toothDepth = toothDepth;
        this.startAngle = startAngle;
        this.cx         = cx;
        this.cy         = cy;
    }
    
    public void setStartAngle(double startAngle) {
        this.startAngle = startAngle;
    }
    
    public void draw(Graficos g, Color fillColor, Color strokeColor) {
        int nPts = teeth * 4;
        int[] px = new int[nPts];
        int[] py = new int[nPts];
        
        int rOut = (int)Math.round(radius);
        int rIn  = (int)Math.round(radius - toothDepth);
        double toothAngle = 2 * Math.PI / teeth;
        
        for (int i = 0; i < teeth; i++) {
            double base = startAngle + i * toothAngle;
            double a0 = base;
            double a1 = base + toothAngle / 4;
            double a2 = base + toothAngle / 2;
            double a3 = base + 3 * toothAngle / 4;
            int idx = i * 4;
            
            px[idx    ] = (int)Math.round(cx + rOut * Math.cos(a0));
            py[idx    ] = (int)Math.round(cy + rOut * Math.sin(a0));
            
            px[idx + 1] = (int)Math.round(cx + rOut * Math.cos(a1));
            py[idx + 1] = (int)Math.round(cy + rOut * Math.sin(a1));
            
            px[idx + 2] = (int)Math.round(cx + rIn  * Math.cos(a2));
            py[idx + 2] = (int)Math.round(cy + rIn  * Math.sin(a2));
            
            px[idx + 3] = (int)Math.round(cx + rIn  * Math.cos(a3));
            py[idx + 3] = (int)Math.round(cy + rIn  * Math.sin(a3));
        }
        
        fillPolygon(g, px, py, fillColor);
        
        for (int k = 0; k < nPts; k++) {
            int next = (k + 1) % nPts;
            g.drawLine(px[k], py[k], px[next], py[next], strokeColor);
        }
    }
    
    private void fillPolygon(Graficos g, int[] xPoints, int[] yPoints, Color c) {
        int n = xPoints.length;
        int minY = yPoints[0], maxY = yPoints[0];
        for (int i = 1; i < n; i++) {
            if (yPoints[i] < minY) minY = yPoints[i];
            if (yPoints[i] > maxY) maxY = yPoints[i];
        }
        
        for (int y = minY; y <= maxY; y++) {
            List<Integer> nodes = new ArrayList<>();
            for (int i = 0; i < n; i++) {
                int j = (i + 1) % n;
                int yi = yPoints[i], yj = yPoints[j];
                if ((yi < y && yj >= y) || (yj < y && yi >= y)) {
                    int xi = xPoints[i], xj = xPoints[j];
                    int x = xi + (y - yi) * (xj - xi) / (yj - yi);
                    nodes.add(x);
                }
            }
            if (nodes.size() < 2) continue;
            Collections.sort(nodes);
            for (int k = 0; k < nodes.size(); k += 2) {
                int xStart = nodes.get(k);
                int xEnd   = nodes.get(k + 1);
                g.drawLine(xStart, y, xEnd, y, c);
            }
        }
    }
}
