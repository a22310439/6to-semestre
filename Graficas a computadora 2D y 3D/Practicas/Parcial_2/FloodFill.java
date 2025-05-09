import javax.swing.JFrame;
import java.awt.Color;
import java.awt.Point;
import java.awt.Robot;
import java.util.ArrayList;
import java.util.List;

public class FloodFill extends JFrame {

    private final Graficos g;

    public FloodFill() {
        setTitle("Relleno por Flood Fill");
        setSize(600, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

        g = new Graficos(this);

        //Poligono
        List<Point> polygon = new ArrayList<>();
        polygon.add(new Point(100, 100));
        polygon.add(new Point(300, 150));
        polygon.add(new Point(350, 300));
        polygon.add(new Point(300, 250));
        polygon.add(new Point(200, 450));
        polygon.add(new Point(120, 270));

        //Contorno del polígono
        for (int i = 0; i < polygon.size(); i++) {
            Point p1 = polygon.get(i);
            Point p2 = polygon.get((i + 1) % polygon.size());
            g.drawLine(p1.x, p1.y, p2.x, p2.y, Color.BLACK);
        }

        //Relleno
        fillPolygonFloodFill(polygon, Color.GREEN);
    }

    public void fillPolygonFloodFill(List<Point> vertices, Color fillColor) {
        int sumX = 0, sumY = 0;
        for (Point p : vertices) {
            sumX += p.x;
            sumY += p.y;
        }
        final int seedX = sumX / vertices.size();
        final int seedY = sumY / vertices.size();

        try {
            Robot robot = new Robot();
            Point winLoc = this.getLocationOnScreen();
            final int offsetX = winLoc.x;
            final int offsetY = winLoc.y;

            int w = getWidth(), h = getHeight();
            boolean[][] visited = new boolean[w][h];

            Color boundaryColor = Color.BLACK;

            floodFillScreen(seedX, seedY, fillColor, boundaryColor, visited, robot, offsetX, offsetY);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void floodFillScreen(int x, int y,
                                 Color fillColor,
                                 Color boundaryColor,
                                 boolean[][] visited,
                                 Robot robot,
                                 int offsetX, int offsetY) {
        if (x < 0 || x >= visited.length || y < 0 || y >= visited[0].length) return;
        if (visited[x][y]) return;
        visited[x][y] = true;

        Color current = robot.getPixelColor(x + offsetX, y + offsetY);

        if (current.equals(boundaryColor) || current.equals(fillColor)) {
            return;
        }

        g.drawPixel(x, y, fillColor);

        floodFillScreen(x + 1, y,     fillColor, boundaryColor, visited, robot, offsetX, offsetY);
        floodFillScreen(x - 1, y,     fillColor, boundaryColor, visited, robot, offsetX, offsetY);
        floodFillScreen(x,     y + 1, fillColor, boundaryColor, visited, robot, offsetX, offsetY);
        floodFillScreen(x,     y - 1, fillColor, boundaryColor, visited, robot, offsetX, offsetY);
    }

    public static void main(String[] args) {
        new FloodFill();
    }
}
