import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.Calendar;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javax.swing.ButtonGroup;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.SwingUtilities;

public class RelojAnalogico {
    private final AlarmManager alarmManager = new AlarmManager();
    private final ClockFace clockFace = new ClockFace();
    private final GearChain gearChain = new GearChain();
    private final Graficos graficos;

    private double anguloSegundos = 0;
    private double anguloMinutos  = 0;
    private double anguloHoras    = 0;
    private boolean mostrarEngranes = false;
    private int gearMode = 0;

    public RelojAnalogico(JFrame frame) {
        this.graficos = new Graficos(frame);
        setupUI(frame);
        startClock(frame);
    }

    private void setupUI(JFrame frame) {
        JCheckBox checkBox = new JCheckBox("Mostrar Engranes");
        JPanel radioPanel = new JPanel();
        JRadioButton rbSegundero = new JRadioButton("Segundero");
        JRadioButton rbMinutero  = new JRadioButton("Minutero");
        JRadioButton rbHorario   = new JRadioButton("Horario");
        ButtonGroup bg = new ButtonGroup();
        bg.add(rbSegundero);
        bg.add(rbMinutero);
        bg.add(rbHorario);
        rbHorario.setSelected(true);
        relojInitRadioListeners(rbSegundero, rbMinutero, rbHorario);
        radioPanel.add(rbSegundero);
        radioPanel.add(rbMinutero);
        radioPanel.add(rbHorario);
        radioPanel.setVisible(false);

        checkBox.addActionListener(e -> {
            mostrarEngranes = checkBox.isSelected();
            radioPanel.setVisible(mostrarEngranes);
            if (mostrarEngranes) {
                rbHorario.setSelected(true);
                gearMode = 2;
            }
        });

        JMenuBar menuBar = new JMenuBar();
        JMenu menuAlarma = new JMenu("Alarma");
        JMenuItem configurarItem = new JMenuItem("Configurar Alarma");
        configurarItem.addActionListener(e -> {
            int[] alarmTime = AlarmConfigDialog.showDialog();
            if (alarmTime != null) {
                alarmManager.setAlarmTime(alarmTime[0], alarmTime[1], alarmTime[2]);
                JOptionPane.showMessageDialog(frame,
                    String.format("Alarma configurada para %02d:%02d:%02d",
                                  alarmTime[0], alarmTime[1], alarmTime[2]));
            }
        });
        menuAlarma.add(configurarItem);
        menuBar.add(menuAlarma);
        frame.setJMenuBar(menuBar);

        JPanel controlPanel = new JPanel(new BorderLayout());
        controlPanel.add(checkBox, BorderLayout.NORTH);
        controlPanel.add(radioPanel, BorderLayout.SOUTH);
        frame.getContentPane().add(controlPanel, BorderLayout.SOUTH);
    }

    private void relojInitRadioListeners(JRadioButton seg, JRadioButton min, JRadioButton hr) {
        seg.addActionListener(e -> gearMode = 0);
        min.addActionListener(e -> gearMode = 1);
        hr.addActionListener(e -> gearMode = 2);
    }

    private void startClock(JFrame frame) {
        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
        executor.scheduleAtFixedRate(() -> {
            Calendar cal = Calendar.getInstance();
            int hora = cal.get(Calendar.HOUR_OF_DAY);
            int minuto = cal.get(Calendar.MINUTE);
            int segundo = cal.get(Calendar.SECOND);
            int milisegundo = cal.get(Calendar.MILLISECOND);

            double fraccionSegundo = segundo + milisegundo / 1000.0;
            double fraccionMinuto  = minuto + fraccionSegundo / 60.0;
            double fraccionHora    = (hora % 12) + fraccionMinuto / 60.0;

            anguloSegundos = Math.toRadians(fraccionSegundo * 6 - 90);
            anguloMinutos  = Math.toRadians(fraccionMinuto  * 6 - 90);
            anguloHoras    = Math.toRadians(fraccionHora    * 30 - 90);

            alarmManager.checkAlarm(cal);
            SwingUtilities.invokeLater(() -> render(frame));
        }, 0, 20, TimeUnit.MILLISECONDS);
    }

    private void render(JFrame frame) {
        graficos.clear();

        Dimension size = frame.getContentPane().getSize();
        int centroX = size.width / 2;
        int centroY = (size.height) / 2;
        int radio   = Math.min(size.width, size.height - 50) / 2 - 10;

        if (mostrarEngranes) {
            gearChain.draw(graficos,
                centroX, centroY, radio,
                anguloSegundos, anguloMinutos, anguloHoras,
                gearMode
            );
        } else {
            clockFace.draw(graficos,
                centroX, centroY, radio,
                anguloSegundos, anguloMinutos, anguloHoras
            );
        }

        graficos.present();
    }

    @SuppressWarnings("unused")
    public static void main(String[] args) {
        JFrame frame = new JFrame("Reloj Analógico");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(new BorderLayout());
        
        RelojAnalogico reloj = new RelojAnalogico(frame);
        
        frame.setSize(600, 650);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
