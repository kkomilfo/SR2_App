import engine.EngineInMemoryStore;
import engine.EngineUseCase;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main extends JFrame {
    private JPanel MainPanel;

    String[] buttonNames = {"Lab 8", "Lab 9", "Lab 10", "Lab 11"};

    public Main() {
        setTitle("SR_1 Anastasiia Borodai");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel labelPanel = new JPanel();
        labelPanel.setLayout(new GridLayout(2, 1));
        labelPanel.add(new JLabel("Author: Anastasiia Borodai"));
        labelPanel.add(new JLabel("KN-21002b"));
        labelPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(7, 1));

        for (String name : buttonNames) {
            JButton button = new JButton(name);
            button.addActionListener(new ButtonClickListener());
            buttonPanel.add(button);
        }

        add(labelPanel, BorderLayout.WEST);
        add(buttonPanel, BorderLayout.CENTER);
    }

    private void showEngineManagerView(Integer labNumber) {
        EngineUseCase engineUseCase = getEngineUseCase(labNumber);
        JFrame form = new EngineManagerView(labNumber, engineUseCase);
        form.setSize(700, 500);
        form.setVisible(true);
    }

    private EngineUseCase getEngineUseCase(Integer labNumber) {
        return switch (labNumber) {
            case 8 -> new EngineUseCase(new EngineInMemoryStore());
            default -> new EngineUseCase(new EngineInMemoryStore());
        };
    }
    private void showForm2(Integer labNumber) {
        JFrame form = new Vehicle(labNumber);
        form.setSize(600, 400);
        form.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Main app = new Main();
            app.setVisible(true);
        });
    }

    private class ButtonClickListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String command = e.getActionCommand();
            char lastChar = command.charAt(command.length() - 1);
            int lastDigit = Character.getNumericValue(lastChar);
            showEngineManagerView(lastDigit);
//            if (lastDigit >= 1 && lastDigit < 6) {
//                showForm1(lastDigit);
//            } else if (lastDigit >= 6 && lastDigit <= 7) {
//                showForm2(lastDigit);
//            } else {
//                JOptionPane.showMessageDialog(Main.this, "Something went wrong with: " + command);
//            }
        }
    }
}
