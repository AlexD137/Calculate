package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Main extends JFrame implements ActionListener {
    private JTextField textField;
    private String operator;
    private double firstOperand;
    private boolean isNewOperation;

    public Main() {
        setTitle("Калькулятор");
        setSize(400, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        textField = new JTextField();
        textField.setEditable(false);
        textField.setPreferredSize(new Dimension(250, 80));
        textField.setFont(new Font("Arial", Font.PLAIN, 40));
        add(textField, BorderLayout.NORTH);


        textField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                char keyChar = e.getKeyChar();
                if (Character.isDigit(keyChar) || keyChar == '.') {
                    textField.setText(textField.getText() + keyChar);
                } else if (keyChar == KeyEvent.VK_ENTER) {
                    calculateResult();
                } else if ("+-*/".indexOf(keyChar) >= 0) {
                    setOperator(String.valueOf(keyChar));
                } else if (keyChar == 'c' || keyChar == 'C') {
                    clear();
                }
            }
        });

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.ipadx = 20;
        gbc.ipady = 20;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;


        for (int i = 1; i <= 9; i++) {
            JButton button = createButton(String.valueOf(i));
            gbc.gridx = (i - 1) % 3;
            gbc.gridy = (i - 1) / 3 + 1;
            mainPanel.add(button, gbc);
        }

        JButton zeroButton = createButton("0");
        gbc.gridx = 1;
        gbc.gridy = 4;
        mainPanel.add(zeroButton, gbc);

        JButton pointButton = createButton(".");
        gbc.gridx = 2;
        gbc.gridy = 4;
        mainPanel.add(pointButton, gbc);

        JButton clearButton = createButton("C");
        gbc.gridx = 0;
        gbc.gridy = 4;
        mainPanel.add(clearButton, gbc);

        JPanel operationsPanel = new JPanel();
        operationsPanel.setLayout(new GridLayout(5, 1));

        JButton sum = createButton("+");
        JButton minus = createButton("-");
        JButton multiply = createButton("*");
        JButton divide = createButton("/");
        JButton equals = createButton("=");

        JButton[] operationButtons = {sum, minus, multiply, divide, equals};
        for (JButton button : operationButtons) {
            operationsPanel.add(button);
        }

        add(mainPanel, BorderLayout.CENTER);
        add(operationsPanel, BorderLayout.EAST);
    }

    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.PLAIN, 30));
        button.setPreferredSize(new Dimension(80, 80));
        button.addActionListener(this);
        return button;
    }

    private void clear() {
        textField.setText("");
        operator = null;
        firstOperand = 0;
        isNewOperation = false;
    }

    private void setOperator(String op) {
        if (operator != null) {
            calculateResult();
        }
        firstOperand = Double.parseDouble(textField.getText());
        operator = op;
        isNewOperation = true;
    }

    private void calculateResult() {
        double secondOperand = Double.parseDouble(textField.getText());
        double result = 0;

        switch (operator) {
            case "+":
                result = firstOperand + secondOperand;
                break;
            case "-":
                result = firstOperand - secondOperand;
                break;
            case "*":
                result = firstOperand * secondOperand;
                break;
            case "/":
                if (secondOperand != 0) {
                    result = firstOperand / secondOperand;
                } else {
                    textField.setText("Ошибка: деление на 0");
                    return;
                }
                break;
            default:
                return;
        }

        textField.setText(String.valueOf(result));
        operator = null;
        isNewOperation = true;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String buttonText = e.getActionCommand();

        if ("0123456789".contains(buttonText)) {
            if (isNewOperation) {
                textField.setText(buttonText);
                isNewOperation = false;
            } else {
                textField.setText(textField.getText() + buttonText);
            }
        } else if (".".equals(buttonText)) {
            if (!textField.getText().contains(".")) {
                textField.setText(textField.getText() + buttonText);
            }
        } else if ("+-*/".contains(buttonText)) {
            setOperator(buttonText);
        } else if ("=".equals(buttonText)) {
            calculateResult();
        } else if ("C".equals(buttonText)) {
            clear();
        }
    }

    public static void main(String[] args) {
        Main frame = new Main();
        frame.setVisible(true);
    }
}
