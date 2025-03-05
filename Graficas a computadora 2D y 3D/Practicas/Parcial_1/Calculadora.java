import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class Calculadora extends JFrame implements ActionListener {

    private final JTextField pantalla;
    
    // Variables para la lógica
    private double operando1 = 0;      // Primer operando o acumulador
    private String operador = "";      // Operador actual (+, -, *, /)
    private boolean nuevaOperacion = true; // Indica si hay que iniciar un número nuevo en pantalla

    public Calculadora() {
        super("Calculadora");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);

        // Campo de texto, alineado a la derecha y no editable
        pantalla = new JTextField("0");
        pantalla.setHorizontalAlignment(JTextField.RIGHT);
        pantalla.setEditable(false);
        add(pantalla, BorderLayout.NORTH);

        // Panel con GridBagLayout para los botones
        JPanel panelBotones = new JPanel(new GridBagLayout());

        // Fila 0
        addButton(panelBotones, "C", 0, 0, 1, 1);
        addButton(panelBotones, "/", 0, 1, 1, 1);
        addButton(panelBotones, "*", 0, 2, 1, 1);
        addButton(panelBotones, "-", 0, 3, 1, 1);

        // Fila 1
        addButton(panelBotones, "7", 1, 0, 1, 1);
        addButton(panelBotones, "8", 1, 1, 1, 1);
        addButton(panelBotones, "9", 1, 2, 1, 1);
        // "+" abarca 2 filas (fila 1 y 2)
        addButton(panelBotones, "+", 1, 3, 2, 1);

        // Fila 2
        addButton(panelBotones, "4", 2, 0, 1, 1);
        addButton(panelBotones, "5", 2, 1, 1, 1);
        addButton(panelBotones, "6", 2, 2, 1, 1);

        // Fila 3
        addButton(panelBotones, "1", 3, 0, 1, 1);
        addButton(panelBotones, "2", 3, 1, 1, 1);
        addButton(panelBotones, "3", 3, 2, 1, 1);
        // "=" abarca 2 filas (fila 3 y 4)
        addButton(panelBotones, "=", 3, 3, 2, 1);

        // Fila 4
        // "0" abarca 2 columnas
        addButton(panelBotones, "0", 4, 0, 1, 2);
        addButton(panelBotones, ".", 4, 2, 1, 1);
        // El "=" de la fila 4 está fusionado con el de arriba

        add(panelBotones, BorderLayout.CENTER);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    /**
     * Añade un botón al panel con GridBagConstraints.
     */
    private void addButton(JPanel panel, String text,
                           int row, int col,
                           int rowSpan, int colSpan) {
        JButton boton = new JButton(text);
        boton.addActionListener(this);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = col;
        gbc.gridy = row;
        gbc.gridwidth = colSpan;
        gbc.gridheight = rowSpan;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;

        panel.add(boton, gbc);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String comando = e.getActionCommand();

        switch (comando) {
            case "C" -> {
                // Reiniciar todo
                pantalla.setText("0");
                operando1 = 0;
                operador = "";
                nuevaOperacion = true;
            }
            case "+", "-", "*", "/" -> {
                // Al pulsar un operador, primero resuelve la operación anterior (si la hay)
                realizarOperacionPendiente();
                operador = comando;           // Guarda el nuevo operador
                nuevaOperacion = true;        // A partir del siguiente dígito se empieza un número nuevo
            }
            case "=" -> {
                // Al pulsar "=", resuelve la operación pendiente con el valor actual en pantalla
                realizarOperacionPendiente();
                operador = "";               // Ya no hay operador pendiente
                nuevaOperacion = true;       // El siguiente dígito inicia nueva operación
            }
            case "." -> {
                // Punto decimal
                if (nuevaOperacion) {
                    // Si se inicia un número nuevo y se pulsa ".", empezamos con "0."
                    pantalla.setText("0.");
                    nuevaOperacion = false;
                } else {
                    // Si el punto no existe en el texto, lo añadimos
                    if (!pantalla.getText().contains(".")) {
                        pantalla.setText(pantalla.getText() + ".");
                    }
                }
            }
            default -> {
                // Son dígitos (0-9)
                if (nuevaOperacion || pantalla.getText().equals("Error")) {
                    // Si es nueva operación o está en "Error", reiniciamos pantalla
                    pantalla.setText(comando);
                    nuevaOperacion = false;
                } else {
                    // Concatenar dígito
                    pantalla.setText(pantalla.getText() + comando);
                }
            }
        }
    }

    /**
     * Método para ejecutar la operación pendiente con el valor que esté en pantalla.
     * - Si no hay operador, simplemente se almacena en operando1 el valor actual.
     * - Si sí hay operador, se hace la operación con operando1 y el valor actual en pantalla.
     */
    private void realizarOperacionPendiente() {
        try {
            double valorEnPantalla = Double.parseDouble(pantalla.getText());
            if (operador.isEmpty()) {
                // No había operación pendiente
                operando1 = valorEnPantalla;
            } else {
                // Hay un operador pendiente
                switch (operador) {
                    case "+" -> operando1 = operando1 + valorEnPantalla;
                    case "-" -> operando1 = operando1 - valorEnPantalla;
                    case "*" -> operando1 = operando1 * valorEnPantalla;
                    case "/" -> {
                        if (valorEnPantalla == 0) {
                            pantalla.setText("Error");
                            operador = "";
                            return; // Evita seguir haciendo cálculos
                        } else {
                            operando1 = operando1 / valorEnPantalla;
                        }
                    }
                }
            }
            // Muestra el resultado de la operación
            pantalla.setText(quitarCeroDecimal(operando1));
        } catch (NumberFormatException e) {
            pantalla.setText("Error");
        }
    }

    /**
     * Método auxiliar para eliminar ceros innecesarios, por ejemplo si el resultado es 5.0
     * se muestra "5", o si es 5.50, se muestra "5.5".
     */
    private String quitarCeroDecimal(double valor) {
        // Convierte a String y luego quita ceros sobrantes
        String txt = Double.toString(valor);
        if (txt.endsWith(".0")) {
            txt = txt.substring(0, txt.length() - 2);
        }
        return txt;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new Calculadora();
        });
    }
}
