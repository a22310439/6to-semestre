import javax.swing.JFrame;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

public class PracticaMouseListener extends JFrame {
    public PracticaMouseListener() {
        super("Mouse Listener");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(300, 200);

        // Escuchador para eventos básicos del mouse
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.out.println("Mouse clic en: " + e.getPoint());
            }
            
            @Override
            public void mousePressed(MouseEvent e) {
                System.out.println("Mouse presionado en: " + e.getPoint());
            }
            
            @Override
            public void mouseReleased(MouseEvent e) {
                System.out.println("Mouse liberado en: " + e.getPoint());
            }
            
            @Override
            public void mouseEntered(MouseEvent e) {
                System.out.println("Mouse entró en: " + e.getPoint());
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                System.out.println("Mouse salió de: " + e.getPoint());
            }
        });
        
        // Escuchador para eventos de movimiento del mouse
        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                System.out.println("Mouse movido a: " + e.getPoint());
            }
            
            @Override
            public void mouseDragged(MouseEvent e) {
                System.out.println("Mouse arrastrado a: " + e.getPoint());
            }
        });

        setVisible(true);
    }

    public static void main(String[] args) {
        new PracticaMouseListener();
    }
}
