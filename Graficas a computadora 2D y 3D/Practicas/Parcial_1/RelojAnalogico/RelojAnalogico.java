import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.util.Calendar;
import java.util.concurrent.ScheduledExecutorService;
import javax.swing.ButtonGroup;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;


public class RelojAnalogico extends JPanel {
    private BufferedImage buffer;
    private double anguloSegundos = 0;
    private double anguloMinutos  = 0;
    private double anguloHoras    = 0;
    private boolean mostrarEngranes = false;
    private int gearMode = 0;
    
    // Instancias de clases separadas
    private AlarmManager alarmManager = new AlarmManager();
    private final ClockFace clockFace = new ClockFace();
    private final GearChain gearChain = new GearChain();
    
    public RelojAnalogico() {
        setPreferredSize(new java.awt.Dimension(1000, 1000));
        
        ScheduledExecutorService executor = java.util.concurrent.Executors.newSingleThreadScheduledExecutor();
        executor.scheduleAtFixedRate(() -> {
            Calendar cal = Calendar.getInstance();
            int hora = cal.get(Calendar.HOUR);
            int minuto = cal.get(Calendar.MINUTE);
            int segundo = cal.get(Calendar.SECOND);
            int milisegundo = cal.get(Calendar.MILLISECOND);
            
            double fraccionSegundo = segundo + milisegundo / 1000.0;
            double fraccionMinuto  = minuto + fraccionSegundo / 60.0;
            double fraccionHora    = hora + fraccionMinuto / 60.0;
            
            anguloSegundos = Math.toRadians(fraccionSegundo * 6 - 90);
            anguloMinutos  = Math.toRadians(fraccionMinuto  * 6 - 90);
            anguloHoras    = Math.toRadians(fraccionHora    * 30 - 90);
            
            alarmManager.checkAlarm(cal);
            
            repaint();
        }, 0, 20, java.util.concurrent.TimeUnit.MILLISECONDS);
    }
    
    public void setMostrarEngranes(boolean mostrar) {
        this.mostrarEngranes = mostrar;
    }
    
    public void setGearMode(int mode) {
        this.gearMode = mode;
    }
    
    public void setAlarmTime(int hour, int minute, int second) {
        alarmManager.setAlarmTime(hour, minute, second);
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (buffer == null || buffer.getWidth() != getWidth() || buffer.getHeight() != getHeight()) {
            buffer = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
        }
        Graphics2D g2 = buffer.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(getBackground());
        g2.fillRect(0, 0, getWidth(), getHeight());
        
        int centroX = getWidth() / 2;
        int centroY = getHeight() / 2;
        int radio = Math.min(getWidth(), getHeight()) / 2 - 10;
        double scale = radio / 190.0;
        
        if (mostrarEngranes) {
            gearChain.draw(g2, centroX, centroY, radio, scale, anguloSegundos, anguloMinutos, anguloHoras, gearMode);
        } else {
            clockFace.draw(g2, centroX, centroY, radio, anguloSegundos, anguloMinutos, anguloHoras);
        }
        
        g2.dispose();
        g.drawImage(buffer, 0, 0, null);
    }
    
    public static void main(String[] args) {
        JFrame frame = new JFrame("Reloj con Engranes, Alarma y Configuración");
        RelojAnalogico reloj = new RelojAnalogico();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        JCheckBox checkBox = new JCheckBox("Mostrar engranes");
        JPanel radioPanel = new JPanel();
        JRadioButton rbSegundero = new JRadioButton("Segundero");
        JRadioButton rbMinutero  = new JRadioButton("Minutero");
        JRadioButton rbHorario   = new JRadioButton("Horario");
        ButtonGroup bg = new ButtonGroup();
        bg.add(rbSegundero);
        bg.add(rbMinutero);
        bg.add(rbHorario);
        rbHorario.setSelected(true);
        reloj.setGearMode(2);
        radioPanel.add(rbSegundero);
        radioPanel.add(rbMinutero);
        radioPanel.add(rbHorario);
        radioPanel.setVisible(false);
        
        rbSegundero.addActionListener((ActionEvent e) -> {
            reloj.setGearMode(0);
            reloj.repaint();
        });
        rbMinutero.addActionListener((ActionEvent e) -> {
            reloj.setGearMode(1);
            reloj.repaint();
        });
        rbHorario.addActionListener((ActionEvent e) -> {
            reloj.setGearMode(2);
            reloj.repaint();
        });
        
        checkBox.addActionListener((ActionEvent e) -> {
            boolean sel = checkBox.isSelected();
            reloj.setMostrarEngranes(sel);
            radioPanel.setVisible(sel);
            if (sel) {
                rbHorario.setSelected(true);
                reloj.setGearMode(2);
            }
            reloj.repaint();
        });
        
        JMenuBar menuBar = new JMenuBar();
        JMenu menuAlarma = new JMenu("Alarma");
        JMenuItem configurarItem = new JMenuItem("Configurar Alarma");
        configurarItem.addActionListener((ActionEvent e) -> {
            int[] alarmTime = AlarmConfigDialog.showDialog();
            if (alarmTime != null) {
                reloj.setAlarmTime(alarmTime[0], alarmTime[1], alarmTime[2]);
                JOptionPane.showMessageDialog(frame, "Alarma configurada para " + String.format("%02d:%02d:%02d", alarmTime[0], alarmTime[1], alarmTime[2]));
            }
        });
        menuAlarma.add(configurarItem);
        menuBar.add(menuAlarma);
        frame.setJMenuBar(menuBar);
        
        JPanel controlPanel = new JPanel(new BorderLayout());
        controlPanel.add(checkBox, BorderLayout.NORTH);
        controlPanel.add(radioPanel, BorderLayout.SOUTH);
        
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(reloj, BorderLayout.CENTER);
        panel.add(controlPanel, BorderLayout.SOUTH);
        
        frame.getContentPane().add(panel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
