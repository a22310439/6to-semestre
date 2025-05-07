import java.awt.Color;
import javax.swing.JFrame;

public class TiposLineas extends JFrame {
    Graficos g = new Graficos(this);

    public TiposLineas() {
        setTitle("Tipos de Líneas con Máscara");
        setSize(800, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

        // Definimos diferentes patrones (máscaras de 8 o 16 bits)
        int CONTINUA     = 0b1111111111111111; // línea sólida
        int PUNTEADA     = 0b1010101010101010; // línea punteada
        int SEGMENTADA   = 0b1111000011110000; // guiones largos
        int GUION_PUNTO  = 0b1110100011101000; // guión-punto

        // Dibujamos líneas con distintos tipos
        g.drawLineType(10, 10, 190, 250, Color.BLACK, CONTINUA);
        g.drawLineType(210, 150, 390, 150, Color.BLACK, PUNTEADA);
        g.drawLineType(590, 10, 410, 250, Color.BLACK, SEGMENTADA);
        g.drawLineType(790, 150, 610, 150, Color.BLACK, GUION_PUNTO);
    }

    @SuppressWarnings("unused")
    public static void main(String[] args) {
        TiposLineas tipos = new TiposLineas();
    }    
}
