package calculadora;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class calculador extends JFrame {
    private static final long serialVersionUID = 1L;
    private JTextField txtDisplay;
    private String operador = "";
    private double resultado = 0;
    private boolean nuevaOperacion = true;

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                calculador frame = new calculador(); 
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public calculador () {
        setTitle("Calculadora Windows 11");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 400, 600);
        setLayout(new BorderLayout());
        
        // Display
        txtDisplay = new JTextField();
        txtDisplay.setFont(new Font("Segoe UI", Font.PLAIN, 36));
        txtDisplay.setHorizontalAlignment(JTextField.RIGHT);
        txtDisplay.setEditable(false);
        txtDisplay.setBackground(Color.WHITE);
        add(txtDisplay, BorderLayout.NORTH);
        
        // Panel de botones
        JPanel panelBotones = new JPanel();
        panelBotones.setLayout(new GridLayout(5, 4, 5, 5));
        
        String[] botones = {
            "C", "±", "%", "÷",
            "7", "8", "9", "×",
            "4", "5", "6", "-",
            "1", "2", "3", "+",
            "0", ".", "=", "+"
        };
        
        for (String texto : botones) {
            JButton btn = new JButton(texto);
            btn.setFont(new Font("Segoe UI", Font.PLAIN, 24));
            btn.addActionListener(new ManejadorEventos());
            panelBotones.add(btn);
        }
        
        add(panelBotones, BorderLayout.CENTER);
    }

    private class ManejadorEventos implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String texto = ((JButton) e.getSource()).getText();

            if (texto.matches("\\d") || texto.equals(".")) {
                if (nuevaOperacion) {
                    txtDisplay.setText("");
                    nuevaOperacion = false;
                }
                txtDisplay.setText(txtDisplay.getText() + texto);
            } else if (texto.equals("C")) {
                txtDisplay.setText("");
                operador = "";
                resultado = 0;
            } else if (texto.equals("±")) {
                double valor = Double.parseDouble(txtDisplay.getText());
                txtDisplay.setText(String.valueOf(-valor));
            } else if (texto.equals("%")) {
                double valor = Double.parseDouble(txtDisplay.getText());
                txtDisplay.setText(String.valueOf(valor / 100));
            } else if (texto.equals("=")) {
                calcular(Double.parseDouble(txtDisplay.getText()));
                txtDisplay.setText(String.valueOf(resultado));
                nuevaOperacion = true;
            } else {
                if (!operador.isEmpty()) {
                    calcular(Double.parseDouble(txtDisplay.getText()));
                    txtDisplay.setText(String.valueOf(resultado));
                } else {
                    resultado = Double.parseDouble(txtDisplay.getText());
                }
                operador = texto;
                nuevaOperacion = true;
            }
        }

        private void calcular(double numero) {
            switch (operador) {
                case "+": resultado += numero; break;
                case "-": resultado -= numero; break;
                case "×": resultado *= numero; break;
                case "÷": resultado /= numero; break;
            }
        }
    }
}