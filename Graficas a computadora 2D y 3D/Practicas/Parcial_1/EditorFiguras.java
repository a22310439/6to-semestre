import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class EditorFiguras extends JFrame implements ActionListener, MouseListener, MouseMotionListener {

    private ButtonGroup modos;
    private JPanel area;
    private JLabel status;
    private Image buffer;
    private Image temporal;

    private static final int PUNTOS = 1;
    private static final int LINEAS = 2;
    private static final int RECTANGULOS = 3;
    private static final int CIRCULOS = 4;
    
    private int modo;
    private int startX;
    private int startY;

    public EditorFiguras() {
        super("Editor de Figuras");

        JMenuBar menuBar = new JMenuBar();
        //Menu archivo
        JMenu menuArchivo = new JMenu("Archivo");

        //Opcion nueva
        JMenuItem opcionNuevo = new JMenuItem("Nuevo");
        opcionNuevo.addActionListener(this);
        opcionNuevo.setActionCommand("Nuevo");
        menuArchivo.add(opcionNuevo);

        menuArchivo.addSeparator();
        //Opcion de salir
        JMenuItem opcionSalir = new JMenuItem("Salir", 'S');
        opcionSalir.addActionListener(this);
        opcionSalir.setActionCommand("Salir");
        menuArchivo.add(opcionSalir);

        menuBar.add(menuArchivo);

        modos = new ButtonGroup();
        
        //Menu modo
        JMenu menuModo = new JMenu("Modo");
        //Opcion puntos
        JRadioButtonMenuItem opcionPuntos = new JRadioButtonMenuItem("Puntos", true);
        opcionPuntos.addActionListener(this);
        opcionPuntos.setActionCommand("Puntos");
        menuModo.add(opcionPuntos);
        modos.add(opcionPuntos);
        
        //Opcion lineas
        JRadioButtonMenuItem opcionLineas = new JRadioButtonMenuItem("Lineas");
        opcionLineas.addActionListener(this);
        opcionLineas.setActionCommand("Lineas");
        menuModo.add(opcionLineas);
        modos.add(opcionLineas);

        //Opcion rectangulos
        JRadioButtonMenuItem opcionRectangulos = new JRadioButtonMenuItem("Rectangulos");
        opcionRectangulos.addActionListener(this);
        opcionRectangulos.setActionCommand("Rectangulos");
        menuModo.add(opcionRectangulos);
        modos.add(opcionRectangulos);

        //Opcion circulos
        JRadioButtonMenuItem opcionCirculos = new JRadioButtonMenuItem("Circulos");
        opcionCirculos.addActionListener(this);
        opcionCirculos.setActionCommand("Circulos");
        menuModo.add(opcionCirculos);
        modos.add(opcionCirculos);

        menuBar.add(menuModo);

        area = new JPanel();
        area.addMouseListener(this);
        area.addMouseMotionListener(this);
        status = new JLabel("Status", JLabel.LEFT);

        //Asignar barra de menus
        setJMenuBar(menuBar);

        //Agregar zona grafica
        getContentPane().add(area, BorderLayout.CENTER);

        //Agregar barra de estado
        getContentPane().add(status, BorderLayout.SOUTH);
        modo = PUNTOS;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setVisible(true);
        buffer = area.createImage(area.getWidth(), area.getHeight());
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        java.awt.Graphics g = temporal.getGraphics();
        switch (modo) {
            case PUNTOS:
                g.fillOval(e.getX(), e.getY(), 2, 2);
                area.getGraphics().drawImage(temporal, 0, 0, this);
                break;
            case LINEAS:
                g.drawImage(buffer, 0, 0, area);
                g.drawLine(startX, startY, e.getX(), e.getY());
                area.getGraphics().drawImage(temporal, 0, 0, this);
                break;
            case RECTANGULOS:
                g.drawImage(buffer, 0, 0, area);
                g.drawRect(startX, startY, e.getX() - startX, e.getY() - startY);
                area.getGraphics().drawImage(temporal, 0, 0, this);
                break;
            case CIRCULOS:
                g.drawImage(buffer, 0, 0, area);
                g.drawOval(startX, startY, e.getX() - startX, e.getY() - startY);
                area.getGraphics().drawImage(temporal, 0, 0, this);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + modo);
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        status.setText("x = " + e.getX() + " y = " + e.getY()
        );
    }

    @Override
    public void mouseClicked(MouseEvent e) {
      // No se usa el metodo
    }

    @Override
    public void mousePressed(MouseEvent e) {
        startX = e.getX();
        startY = e.getY();
        temporal = area.createImage(area.getWidth(), area.getHeight());
        temporal.getGraphics().drawImage(temporal, 0, 0, this);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        buffer.getGraphics().drawImage(temporal, 0, 0, this);
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
    }

    @Override
    public void mouseExited(MouseEvent e) {
        setCursor(Cursor.getDefaultCursor());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String comando = e.getActionCommand();
        if (comando.equals("Nuevo")) {
            area.getGraphics().clearRect(0, 0, area.getWidth(), area.getHeight());
            buffer = area.createImage(area.getWidth(), area.getHeight());
        } else if (comando.equals("Salir")) {
            if (JOptionPane.showConfirmDialog(this, "¿En verdad deseas salir?", "Confirmar", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                dispose();
                System.exit(0); 
            }
        } else if (comando.equals("Puntos")) {
            modo = PUNTOS;
        } else if (comando.equals("Lineas")) {
            modo = LINEAS;
        } else if (comando.equals("Rectangulos")) {
            modo = RECTANGULOS;
        } else if (comando.equals("Circulos")) {
            modo = CIRCULOS;
        }
    }

    public static void main(String[] args) {
        new EditorFiguras();
    }
    
}
