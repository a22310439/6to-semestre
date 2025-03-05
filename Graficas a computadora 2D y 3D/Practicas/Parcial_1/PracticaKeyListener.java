import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;

public class PracticaKeyListener extends JFrame implements KeyListener {
    public PracticaKeyListener() {
        super("Practica KeyListener");
        addKeyListener(this);
        setSize(400, 400);
        setVisible(true);
    }

    public void keyTyped(KeyEvent e) {
        System.out.println("Tecla presionada: " + e.getKeyChar());
    }

    public void keyPressed(KeyEvent e) {
        System.out.println("Tecla presionada: " + e.getKeyChar());
    }

    public void keyReleased(KeyEvent e) {
        System.out.println("Tecla liberada: " + e.getKeyChar());
    }

    public static void main(String[] args) {
        new PracticaKeyListener();
    }
    
}
