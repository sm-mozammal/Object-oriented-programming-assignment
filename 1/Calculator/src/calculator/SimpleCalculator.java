package calculator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SimpleCalculator {
    private JFrame frame;
    private JTextField textField;
    private JButton[] numberButtons = new JButton[10];
    private JButton[] functionButtons = new JButton[6]; // Adjusted to 6
    private JPanel panel;

    private double num1 = 0, num2 = 0, result = 0;
    private char operator;

    public SimpleCalculator() {
        frame = new JFrame("Calculator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 550);
        frame.setLayout(null);

        textField = new JTextField();
        textField.setBounds(10, 10, 360, 80);
        textField.setFont(new Font("Arial", Font.PLAIN, 30));
        textField.setEditable(false);
        textField.setHorizontalAlignment(JTextField.RIGHT);

        panel = new JPanel();
        panel.setBounds(10, 100, 360, 400);
        panel.setLayout(new GridLayout(5, 4, 10, 10));

        String[] buttonLabels = {
                "7", "8", "9", "/",
                "4", "5", "6", "*",
                "1", "2", "3", "-",
                "0", ".", "=", "+",
                "%"
        };

        for (int i = 0; i < buttonLabels.length; i++) {
            JButton button = new JButton(buttonLabels[i]);
            button.setFont(new Font("Arial", Font.PLAIN, 18));
            button.setFocusPainted(false);

            if (i < 10) {
                button.addActionListener(new ButtonAction());
                numberButtons[i] = button;
            } else if (i < 15) {
                button.addActionListener(new OperatorAction());
                functionButtons[i - 10] = button;
            } else {
                button.addActionListener(new OperatorAction());
            }

            panel.add(button);
        }

        frame.add(textField);
        frame.add(panel);
        frame.setVisible(true);
    }

    private class ButtonAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JButton button = (JButton) e.getSource();
            textField.setText(textField.getText() + button.getText());
        }
    }

    private class OperatorAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JButton button = (JButton) e.getSource();
            String buttonText = button.getText();

            if (!buttonText.equals("=")) {
                if (!textField.getText().isEmpty()) {
                    num1 = Double.parseDouble(textField.getText());
                    operator = buttonText.charAt(0);
                    textField.setText("");
                }
            } else {
                if (!textField.getText().isEmpty()) {
                    num2 = Double.parseDouble(textField.getText());
                    calculateResult();
                }
            }
        }
    }

    private void calculateResult() {
        switch (operator) {
            case '+':
                result = num1 + num2;
                break;
            case '-':
                result = num1 - num2;
                break;
            case '*':
                result = num1 * num2;
                break;
            case '/':
                if (num2 != 0) {
                    result = num1 / num2;
                } else {
                    JOptionPane.showMessageDialog(null, "Error: Division by zero is not allowed.");
                    clearCalculator();
                    return;
                }
                break;
            case '%':
                result = num1 % num2;
                break;
        }
        textField.setText(String.valueOf(result));
    }

    private void clearCalculator() {
        num1 = 0;
        num2 = 0;
        result = 0;
        operator = '\0';
        textField.setText("");
    }

    public static void main(String[] args) {
        new SimpleCalculator();
    }
}
