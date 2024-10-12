import engine.EngineDB;
import engine.EngineFileTextStore;
import engine.EngineInMemoryStore;
import engine.EngineUseCase;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main extends JFrame {
    private JPanel MainPanel;

    String[] buttonNames = {"Lab 1", "Lab 2", "Lab 3", "Lab 4"};

    public Main() {
        setTitle("SR_2 Anastasiia Borodai");
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

    private void showEngineManagerView(Integer labNumber) throws Exception {
        EngineUseCase engineUseCase = getEngineUseCase(labNumber);
        JFrame form = new EngineManagerView(labNumber, engineUseCase);
        form.setSize(700, 500);
        form.setVisible(true);
    }

    private EngineUseCase getEngineUseCase(Integer labNumber) throws Exception {
        return switch (labNumber) {
            case 3 -> new EngineUseCase(new EngineFileTextStore("data.txt"));
            case 4 -> new EngineUseCase(new EngineDB());
            default -> new EngineUseCase(new EngineInMemoryStore());
        };
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
            try {
                showEngineManagerView(lastDigit);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        }
    }
}
