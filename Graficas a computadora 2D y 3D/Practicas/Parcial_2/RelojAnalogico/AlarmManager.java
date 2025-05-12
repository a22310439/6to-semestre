import java.io.IOException;
import java.util.Calendar;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class AlarmManager {
    private boolean alarmEnabled = false;
    private int alarmHour = -1;
    private int alarmMinute = -1;
    private int alarmSecond = -1;
    private boolean alarmTriggered = false;
    
    public void setAlarmTime(int hour, int minute, int second) {
        this.alarmHour = hour;
        this.alarmMinute = minute;
        this.alarmSecond = second;
        this.alarmEnabled = true;
    }
    
    public void checkAlarm(Calendar current) {
        int currentHour = current.get(Calendar.HOUR_OF_DAY);
        int currentMinute = current.get(Calendar.MINUTE);
        int currentSecond = current.get(Calendar.SECOND);
        if (alarmEnabled) {
            if (currentHour == alarmHour && currentMinute == alarmMinute &&
                currentSecond == alarmSecond && !alarmTriggered) {
                // Dispara la alarma
                playAlarmSound();
                alarmTriggered = true;
            } else if (currentSecond != alarmSecond) {
                alarmTriggered = false;
            }
        }
    }
    
    private void playAlarmSound() {
        new Thread(() -> {
            try {
                // Supongamos que "alarma.wav" está en el mismo paquete que esta clase
                AudioInputStream audioIn = AudioSystem.getAudioInputStream(getClass().getResource("alarma.wav"));
                Clip clip = AudioSystem.getClip();
                clip.open(audioIn);
                clip.start();
            } catch (IOException | LineUnavailableException | UnsupportedAudioFileException ex) {
                System.out.println("Error al reproducir el sonido de la alarma: " + ex.getMessage());
            }
        }).start();
    }
}
