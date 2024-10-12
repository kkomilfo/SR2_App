import vehicle.Car;
import vehicle.Engine;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Vehicle extends JFrame {
    private JPanel panel1;
    private JTextField typeTextField;
    private JTextField powerTextField;
    private JButton buildEngineButton;
    private JLabel engineResult;
    private JTextField modelTextField;
    private JTextField carTypeTextField;
    private JTextField yearTextField;
    private JButton buildCarButton;
    private JLabel carResult;
    private JPanel fuelPanel;
    private JButton calculateEfficiencyButton;
    private JLabel fuelResult;
    private Engine engine;
    private Car car;

    public Vehicle(Integer labNumber) {
        setContentPane(panel1);
        setTitle("Lab Vehicle");
        fuelPanel.setVisible(labNumber == 7);

        buildEngineButton.addActionListener(_ -> {
            var type = typeTextField.getText();
            var power = Integer.parseInt(powerTextField.getText());
            engine = new Engine(type, power);
            engineResult.setText(engine.getInfo());
        });

        buildCarButton.addActionListener(_ -> {
            var type = carTypeTextField.getText();
            var year = Integer.parseInt(yearTextField.getText());
            var model = modelTextField.getText();
            car = new Car(model, year, type, engine);
            carResult.setText(car.getInfo());
        });

        calculateEfficiencyButton.addActionListener(_ -> {
            Double efficiency = car.calculateFuelEfficiency();
            fuelResult.setText(efficiency.toString());
        });
    }
}
