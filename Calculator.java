import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class Calculator {
    private JFrame frame;
    private JTextField textField;
    private StringBuilder expression;

    Calculator() {
        expression = new StringBuilder();
        createGui();
    }

    public void createGui() {
        frame = new JFrame("Calculator");
        textField = new JTextField(30);
        textField.setEditable(false);
        textField.setHorizontalAlignment(JTextField.RIGHT);
        frame.add(textField, BorderLayout.NORTH);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 4, 10, 10));

        String[] buttonLabels = {"1", "2", "3", "+",
                                 "4", "5", "6", "-",
                                 "7", "8", "9", "*",
                                 "C", "0", "=", "/"};

        for (String label : buttonLabels) {
            JButton button = new JButton(label);
            button.setBackground(Color.WHITE);
            button.addActionListener(new ButtonClickListener());
            panel.add(button);
        }

        frame.add(panel, BorderLayout.CENTER);
        frame.setSize(300, 400);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private class ButtonClickListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String command = e.getActionCommand();

            if (command.charAt(0) >= '0' && command.charAt(0) <= '9') {
                expression.append(command);
                textField.setText(expression.toString());
            } else if ("+-*/".contains(command)) {
                expression.append(" ").append(command).append(" ");
                textField.setText(expression.toString());
            } else if (command.equals("=")) {
                try {
                    double result = evaluateExpression(expression.toString());
                    textField.setText(String.valueOf(result));
                    expression.setLength(0);
                    expression.append(result);
                } catch (Exception ex) {
                    textField.setText("Error");
                    expression.setLength(0);
                }
            } else if (command.equals("C")) {
                textField.setText("");
                expression.setLength(0);
            }
        }

        private double evaluateExpression(String expr) {
            String[] tokens = expr.split(" ");
            double result = Double.parseDouble(tokens[0]);
            for (int i = 1; i < tokens.length; i += 2) {
                String operator = tokens[i];
                double operand = Double.parseDouble(tokens[i + 1]);
                switch (operator) {
                    case "+":
                        result += operand;
                        break;
                    case "-":
                        result -= operand;
                        break;
                    case "*":
                        result *= operand;
                        break;
                    case "/":
                        result /= operand;
                        break;
                }
            }
            return result;
        }
    }

    public static void main(String[] args) {
        new Calculator();
    }
}
