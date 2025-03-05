import java.awt.Image;
import java.awt.Toolkit;
import javax.swing.JFrame;
import javax.swing.JScrollPane;

public class VisorImagen extends JFrame {
    private final JScrollPane scroll;
    private final Pantalla pantalla;

    public VisorImagen(String archivo) {
        super("Visor de Imagen");
        
        Image img = Toolkit.getDefaultToolkit().getImage(archivo);
        pantalla = new Pantalla(img);
        scroll = new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        getContentPane().add(scroll);
        scroll.setViewportView(pantalla);
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 400);
        setVisible(true);
    }

    public static void main(String[] args) {
        new VisorImagen("imagen.jpg");
    }
}
