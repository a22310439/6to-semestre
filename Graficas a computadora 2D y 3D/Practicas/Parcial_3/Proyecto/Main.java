// Main.java
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Main {
    // Representación 3D
    static class Vector3 {
        float x, y, z;
        Vector3(float x, float y, float z) { this.x = x; this.y = y; this.z = z; }
        Vector3 subtract(Vector3 o) { return new Vector3(x - o.x, y - o.y, z - o.z); }
        Vector3 cross(Vector3 o) {
            return new Vector3(
                y * o.z - z * o.y,
                z * o.x - x * o.z,
                x * o.y - y * o.x
            );
        }
        Vector3 normalize() {
            float len = (float)Math.sqrt(x*x + y*y + z*z);
            return new Vector3(x/len, y/len, z/len);
        }
    }
    static class Face {
        int[] idx; Color color;
        Face(int[] idx, Color c) { this.idx = idx; this.color = c; }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("3D Cube");
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Graficos3D g3 = new Graficos3D(frame);

        // Vértices y caras del cubo
        Vector3[] verts = {
            new Vector3(-1,-1,-1), new Vector3(1,-1,-1),
            new Vector3(1,1,-1),   new Vector3(-1,1,-1),
            new Vector3(-1,-1,1),  new Vector3(1,-1,1),
            new Vector3(1,1,1),    new Vector3(-1,1,1)
        };
        Face[] faces = {
            new Face(new int[]{0,1,2,3}, Color.RED),
            new Face(new int[]{4,5,6,7}, Color.GREEN),
            new Face(new int[]{0,1,5,4}, Color.BLUE),
            new Face(new int[]{2,3,7,6}, Color.YELLOW),
            new Face(new int[]{0,3,7,4}, Color.MAGENTA),
            new Face(new int[]{1,2,6,5}, Color.CYAN)
        };
        final Vector3[] world = new Vector3[verts.length];

        // Estado interacción
        final float[] angle = {0f,0f};
        final float zoom = 1f;

        // Rotación con mouse
        frame.addMouseMotionListener(new MouseAdapter() {
            int lastX, lastY;
            @Override
            public void mousePressed(MouseEvent e) {
                lastX = e.getX(); lastY = e.getY();
            }
            @Override
            public void mouseDragged(MouseEvent e) {
                int dx = e.getX() - lastX;
                int dy = e.getY() - lastY;
                angle[1] += dx * 0.01f;
                angle[0] += dy * 0.01f;
                lastX = e.getX(); lastY = e.getY();
            }
        });

        frame.setVisible(true);

        new Timer(16, ev -> {
            g3.clear(Color.BLACK);
            // Transformación y proyección
            for (int i = 0; i < verts.length; i++) {
                Vector3 v = verts[i];
                v = rotateX(v, angle[0]);
                v = rotateY(v, angle[1]);
                v = new Vector3(v.x*zoom, v.y*zoom, v.z*zoom + 4);
                world[i] = v;
            }
            for (Face f : faces) {
                if (!isBackface(world, f)) {
                    Point p0 = project(world[f.idx[0]], g3);
                    Point p1 = project(world[f.idx[1]], g3);
                    Point p2 = project(world[f.idx[2]], g3);
                    Point p3 = project(world[f.idx[3]], g3);
                    g3.drawFilledTriangle(p0, p1, p2, f.color);
                    g3.drawFilledTriangle(p0, p2, p3, f.color);
                }
            }
            g3.present();
        }).start();
    }

    // Métodos geométricos
    static Vector3 rotateX(Vector3 v, float a) {
        float c = (float)Math.cos(a), s = (float)Math.sin(a);
        return new Vector3(v.x, v.y*c - v.z*s, v.y*s + v.z*c);
    }
    static Vector3 rotateY(Vector3 v, float a) {
        float c = (float)Math.cos(a), s = (float)Math.sin(a);
        return new Vector3(v.x*c + v.z*s, v.y, -v.x*s + v.z*c);
    }
    static boolean isBackface(Vector3[] w, Face f) {
        Vector3 v0 = w[f.idx[0]], v1 = w[f.idx[1]], v2 = w[f.idx[2]];
        Vector3 n = v1.subtract(v0).cross(v2.subtract(v0)).normalize();
        return n.z >= 0;
    }
    static Point project(Vector3 v, Graficos3D g3) {
        float d = 2;
        float x = v.x * (d / v.z);
        float y = v.y * (d / v.z);
        int sx = Math.round((x + 1) * g3.getWidth()  / 2);
        int sy = Math.round((1 - y) * g3.getHeight() / 2);
        return new Point(sx, sy);
    }
}
