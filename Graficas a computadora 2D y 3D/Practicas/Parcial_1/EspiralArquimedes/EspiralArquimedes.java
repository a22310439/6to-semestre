package EspiralArquimedes;

import javax.swing.*;
import java.awt.*;

public class EspiralArquimedes extends JFrame {
    private static final int WIDTH = 400;
    private static final int HEIGHT = 400;

    public EspiralArquimedes() {
        super("Espiral de Arquímedes");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        EspiralPanel panel = new EspiralPanel();
        panel.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        add(panel);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);

        // Iniciar la animación utilizando un hilo con Runnable
        Thread animThread = new Thread(new AnimationRunnable(panel));
        animThread.start();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new EspiralArquimedes());
    }
}
