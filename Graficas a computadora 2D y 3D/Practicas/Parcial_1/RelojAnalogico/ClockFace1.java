import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;

public class ClockFace1 {
    public void draw(Graphics2D g2, int centroX, int centroY, int radio,
                     double anguloSegundos, double anguloMinutos, double anguloHoras) {
        // Dibujar la esfera del reloj
        g2.setColor(Color.WHITE);
        g2.fillOval(centroX - radio, centroY - radio, 2 * radio, 2 * radio);
        g2.setColor(Color.BLACK);
        g2.setFont(new Font("Arial", Font.BOLD, 16));
        FontMetrics fm = g2.getFontMetrics();
        for (int i = 1; i <= 12; i++) {
            double anguloNum = Math.toRadians((30 * i - (double) 90));
            int radioNum = radio - 30;
            int x = centroX + (int)(radioNum * Math.cos(anguloNum));
            int y = centroY + (int)(radioNum * Math.sin(anguloNum));
            String numStr = String.valueOf(i);
            int anchoStr = fm.stringWidth(numStr);
            int alturaStr = fm.getAscent();
            g2.drawString(numStr, x - anchoStr / 2, y + alturaStr / 2);
        }
        // Dibujar las manecillas
        int xSec = centroX + (int)(radio * 0.9 * Math.cos(anguloSegundos));
        int ySec = centroY + (int)(radio * 0.9 * Math.sin(anguloSegundos));
        g2.setColor(Color.RED);
        g2.drawLine(centroX, centroY, xSec, ySec);
        
        int xMin = centroX + (int)(radio * 0.75 * Math.cos(anguloMinutos));
        int yMin = centroY + (int)(radio * 0.75 * Math.sin(anguloMinutos));
        g2.setColor(Color.BLUE);
        g2.drawLine(centroX, centroY, xMin, yMin);
        
        int xHora = centroX + (int)(radio * 0.5 * Math.cos(anguloHoras));
        int yHora = centroY + (int)(radio * 0.5 * Math.sin(anguloHoras));
        g2.setColor(Color.GREEN);
        g2.drawLine(centroX, centroY, xHora, yHora);
        
        g2.fillOval(centroX - 5, centroY - 5, 10, 10);
    }
}
