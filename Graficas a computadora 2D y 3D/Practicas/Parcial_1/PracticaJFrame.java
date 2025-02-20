import javax.swing.JFrame;

public class PracticaJFrame extends JFrame {
    public PracticaJFrame() {
        super("Practica JFrame");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public static void main(String[] args) {
        new PracticaJFrame();
    }
}
