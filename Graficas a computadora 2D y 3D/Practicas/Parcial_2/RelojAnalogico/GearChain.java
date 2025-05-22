import java.awt.Color;

public class GearChain {

    public void draw(Graficos g,
                     int centroX, int centroY, int radio,
                     double anguloSegundos,
                     double anguloMinutos,
                     double anguloHoras,
                     int gearMode) {
        
        double scale = radio / 190.0;

        fillCircle(g, centroX, centroY, radio + 1, Color.BLACK);
        fillCircle(g, centroX, centroY, radio,     Color.GRAY);
        fillCircle(g, centroX, centroY, radio - 30, Color.DARK_GRAY);

        double interRot  = - (1.0 / 6.3)     * anguloSegundos;
        double chainMin  = - (1.0 / 9.52381) * interRot;
        double inter2Rot = - (1.0 / 2.0)     * chainMin;
        double chainHr   = - (1.0 / 30.0)    * inter2Rot;

        Gear outerSec = new Gear(120 * scale, 30,  8 * scale,
                                  anguloSegundos, centroX, centroY);
        outerSec.draw(g, Color.RED, Color.BLACK);

        Gear innerSec = new Gear(10 * scale,  15,  3 * scale,
                                  anguloSegundos + 0.15,
                                  centroX + (int)(3 * scale),
                                  centroY);
        innerSec.draw(g, Color.RED, Color.BLACK);

        if (gearMode >= 1) {
            int ix = (int)(centroX + (10 * scale) + (65 * scale) + 2);
            int iy = centroY;

            Gear inter1Outer = new Gear(65 * scale, 95, 2 * scale,
                                        interRot - 1.1, ix, iy);
            inter1Outer.draw(g, Color.LIGHT_GRAY, Color.BLACK);

            Gear inter1Inner = new Gear(6.5 * scale, 10, 3 * scale,
                                        interRot, ix, iy);
            inter1Inner.draw(g, Color.LIGHT_GRAY, Color.BLACK);

            Gear minuteExt = new Gear(70.5 * scale, 90, 5 * scale,
                                       chainMin + 1, centroX, centroY);
            minuteExt.draw(g, Color.BLUE, Color.BLACK);

            Gear minuteInt = new Gear(30 * scale, 30, 4 * scale,
                                       chainMin, centroX, centroY);
            minuteInt.draw(g, Color.BLUE, Color.BLACK);
        }

        if (gearMode == 2) {
            int i2x = (int)(centroX - ((30 * scale) + (60 * scale)));
            int i2y = centroY;

            Gear inter2Outer = new Gear((60 * scale) + 5, 60, 5 * scale,
                                        inter2Rot + 1.5, i2x, i2y);
            inter2Outer.draw(g, Color.LIGHT_GRAY, Color.BLACK);

            Gear inter2Inner = new Gear(12 * scale, 12, 3 * scale,
                                        inter2Rot, i2x, i2y);
            inter2Inner.draw(g, Color.LIGHT_GRAY, Color.BLACK);

            Gear hourGear = new Gear(80 * scale, 72, 3 * scale,
                                      chainHr - 0.5, centroX, centroY);
            hourGear.draw(g, Color.GREEN, Color.BLACK);
        }
    }

    private void fillCircle(Graficos g, int xc, int yc, int r, Color c) {
        for (int dy = -r; dy <= r; dy++) {
            int dx = (int)Math.round(Math.sqrt(r * r - dy * dy));
            g.drawLine(xc - dx, yc + dy, xc + dx, yc + dy, c);
        }
    }
}
