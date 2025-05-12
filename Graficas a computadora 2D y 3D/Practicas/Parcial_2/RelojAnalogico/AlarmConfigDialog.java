import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerListModel;
import javax.swing.SpinnerNumberModel;

public class AlarmConfigDialog {
    // Muestra un diálogo para configurar la alarma usando spinners y devuelve un arreglo {hour, minute, second} en formato 24h.
    // Devuelve null si se cancela.
    public static int[] showDialog() {
        JPanel alarmPanel = new JPanel(new GridLayout(2, 4, 5, 5));
        alarmPanel.add(new JLabel("Hora:"));
        alarmPanel.add(new JLabel("Minutos:"));
        alarmPanel.add(new JLabel("Segundos:"));
        alarmPanel.add(new JLabel("Periodo:"));
        JSpinner spinnerHour = new JSpinner(new SpinnerNumberModel(12, 1, 12, 1));
        JSpinner spinnerMinute = new JSpinner(new SpinnerNumberModel(0, 0, 59, 1));
        JSpinner spinnerSecond = new JSpinner(new SpinnerNumberModel(0, 0, 59, 1));
        JSpinner spinnerPeriod = new JSpinner(new SpinnerListModel(new String[] {"AM", "PM"}));
        alarmPanel.add(spinnerHour);
        alarmPanel.add(spinnerMinute);
        alarmPanel.add(spinnerSecond);
        alarmPanel.add(spinnerPeriod);
        int result = JOptionPane.showConfirmDialog(null, alarmPanel, "Configurar Alarma (HH:MM:SS)", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            int h = (Integer) spinnerHour.getValue();
            int m = (Integer) spinnerMinute.getValue();
            int s = (Integer) spinnerSecond.getValue();
            String period = (String) spinnerPeriod.getValue();
            if ("PM".equals(period) && h < 12) {
                h += 12;
            }
            if ("AM".equals(period) && h == 12) {
                h = 0;
            }
            return new int[]{h, m, s};
        }
        return null;
    }
}
