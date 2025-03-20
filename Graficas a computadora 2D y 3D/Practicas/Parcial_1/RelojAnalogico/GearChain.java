import java.awt.Color;
import java.awt.Graphics2D;

public class GearChain {
    // gearMode: 0 = segundero, 1 = minutero, 2 = horario.
    public void draw(Graphics2D g2, int centroX, int centroY, int radio, double scale,
                     double anguloSegundos, double anguloMinutos, double anguloHoras, int gearMode) {
        // Dibujar la carcasa (los círculos de fondo)
        g2.setColor(Color.BLACK);
        g2.fillOval(centroX - radio - 1, centroY - radio - 1, 2 * radio + 2, 2 * radio + 2);
        g2.setColor(Color.GRAY);
        g2.fillOval(centroX - radio, centroY - radio, 2 * radio, 2 * radio);
        g2.setColor(Color.DARK_GRAY);
        g2.fillOval(centroX - radio + 30, centroY - radio + 30, 2 * radio - 60, 2 * radio - 60);
        
        // Cálculo de rotaciones (valores de diseño)
        double intermediateRotation = - (1.0 / 6.3) * anguloSegundos;
        double chainMinuteAngle = - (1.0 / 9.52381) * intermediateRotation;
        double intermediate2Rotation = - (1.0 / 2.0) * chainMinuteAngle;
        double chainHourAngle = - (1.0 / 30.0) * intermediate2Rotation;
        
        // Siempre se muestran los engranes del segundero
        Gear gearSecondsOuter = new Gear(120 * scale, 30, 8 * scale, anguloSegundos, centroX, centroY);
        gearSecondsOuter.draw(g2, Color.RED, Color.BLACK);
        Gear gearSecondsInner = new Gear(10 * scale, 15, 3 * scale, anguloSegundos + 0.15, centroX + (int)(3 * scale), centroY);
        gearSecondsInner.draw(g2, Color.RED, Color.BLACK);
        
        if (gearMode >= 1) {
            // En modos "minutero" y "horario" se muestra también el engrane intermedio 1 y el engrane del minutero.
            int interCenterX = (int)(centroX + (10 * scale) + (65 * scale) + 2);
            int interCenterY = centroY;
            Gear gearIntermediate1Outer = new Gear(65 * scale, 95, 2 * scale, intermediateRotation - 1.1, interCenterX, interCenterY);
            gearIntermediate1Outer.draw(g2, Color.LIGHT_GRAY, Color.BLACK);
            Gear gearIntermediate1Inner = new Gear(6.5 * scale, 10, 3 * scale, intermediateRotation, interCenterX, interCenterY);
            gearIntermediate1Inner.draw(g2, Color.LIGHT_GRAY, Color.BLACK);
            
            // Engranaje del minutero (compuesto: parte exterior e interior)
            Gear gearMinuteExterior = new Gear(70.5 * scale, 90, 5 * scale, chainMinuteAngle + 1, centroX, centroY);
            gearMinuteExterior.draw(g2, Color.BLUE, Color.BLACK);
            Gear gearMinuteInterior = new Gear(30 * scale, 30, 4 * scale, chainMinuteAngle, centroX, centroY);
            gearMinuteInterior.draw(g2, Color.BLUE, Color.BLACK);
        }
        
        if (gearMode == 2) {
            // En modo "horario": además se muestra el engrane intermedio 2 y el engrane del horario.
            int inter2CenterX = (int)(centroX - ((30 * scale) + (60 * scale)));
            int inter2CenterY = centroY;
            Gear gearIntermediate2Outer = new Gear((60 * scale) + 5, 60, 5 * scale, intermediate2Rotation + 1.5, inter2CenterX, inter2CenterY);
            gearIntermediate2Outer.draw(g2, Color.LIGHT_GRAY, Color.BLACK);
            Gear gearIntermediate2Inner = new Gear(12 * scale, 12, 3 * scale, intermediate2Rotation, inter2CenterX, inter2CenterY);
            gearIntermediate2Inner.draw(g2, Color.LIGHT_GRAY, Color.BLACK);
            
            Gear gearHour = new Gear(80 * scale, 72, 3 * scale, chainHourAngle - 0.5, centroX, centroY);
            gearHour.draw(g2, Color.GREEN, Color.BLACK);
        }
    }
}
