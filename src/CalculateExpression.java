import expression.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CalculateExpression extends JFrame {
    private JPanel panel1;
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;
    private JButton calculateButton;
    private JList list1;

    public CalculateExpression(Integer labNumber) {
        setContentPane(panel1);
        String message = String.format("Lab %d", labNumber);
        setTitle(message);
        calculateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    var x = Double.parseDouble(textField1.getText());
                    var c = Double.parseDouble(textField2.getText());
                    var t = Double.parseDouble(textField3.getText());
                    Expression expressionEvaluator = createInstance(labNumber);
                    DefaultListModel<String> listModel = new DefaultListModel<>();
                    list1.setModel(listModel);
                    switch (labNumber) {
                        case 1, 2, 3:
                            var result = expressionEvaluator.evaluate(x, c, t);
                            listModel.addElement(String.valueOf(result));
                            break;

                        case 4, 5:
                            var result1 = expressionEvaluator.evaluateRange(c, t);
                            for (String item : result1) {
                                listModel.addElement(item);
                            }
                            break;

                        default:
                            throw new Exception();
                    }
                } catch (InvalidInputException ex) {
                    JOptionPane.showMessageDialog(CalculateExpression.this, "Something went wrong with: " + ex.getMessage());
                    textField1.setText("");
                    textField2.setText("");
                    textField3.setText("");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(CalculateExpression.this, "Something went wrong with");
                    textField1.setText("");
                    textField2.setText("");
                    textField3.setText("");
                }
            }
        });
    }

    private static Expression createInstance(int number) {
        return switch (number) {
            case 1 -> new Lab1();
            case 2 -> new Lab2();
            case 3 -> new Lab3();
            case 4 -> new Lab4();
            case 5 -> new Lab5();
            default -> new EmptyEvaluator();
        };
    }
}
