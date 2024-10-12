import engine.Engine;
import engine.EngineUseCase;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.TableRowSorter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class EngineManagerView extends JFrame {
    private JTextField powerEngine;
    private JTextField typeEngine;
    private JButton actionButton;
    private JTextField filterPowerTextField;
    private JPanel mainPanel;
    private JTable enginesTable;
    private JButton minMaxValuesButton;
    private JTextField filterTypeTextField;
    private JLabel filterTypeLabel;
    private JButton saveToFileButton;
    private JLabel filterPowerLabel;
    private JButton showFromBinaryButton;
    private final Integer labNumber;
    private final EngineUseCase engineUseCase;
    private final EngineTableModel enginesTableModel;
    private final TableRowSorter<EngineTableModel> sorter;
    private static Timer timer;

    public EngineManagerView(Integer labNumber, EngineUseCase engineUseCase) throws Exception {
        this.labNumber = labNumber;
        this.engineUseCase = engineUseCase;
        this.enginesTableModel = new EngineTableModel(engineUseCase.getEngines());
        this.sorter = new TableRowSorter<>(enginesTableModel);
        ConfigurePanel();
        ConfigureDataTable();
        ConfigureAddNewItemPanel();
        ConfigureFilterTextField();
        ConfigureMinMaxButton();
        ConfigureFilterTypeTextField();
        ConfigureSaveToFile();
        ConfigureShowFromBinaryButton();
    }

    private void ConfigureShowFromBinaryButton() {
        showFromBinaryButton.setVisible(labNumber == 3);
        showFromBinaryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    List<Engine> engines = engineUseCase.displayEnginesFromBinFile();
                    JOptionPane.showMessageDialog(EngineManagerView.this, GetEnginesString(engines));
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(EngineManagerView.this, ex.getMessage());
                }
            }
        });
    }

    private void ConfigureSaveToFile() {
        saveToFileButton.setVisible(labNumber == 3);
        saveToFileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    engineUseCase.save(enginesTableModel.getEngines());
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(EngineManagerView.this, ex.getMessage());
                }
            }
        });
    }

    private void ConfigurePanel() {
        setContentPane(mainPanel);
        String message = String.format("Lab %d", labNumber);
        setTitle(message);
    }

    private void ConfigureDataTable() {
        sorter.setComparator(1, (engine1, engine2) -> {
            Engine hp1 = (Engine) engine1;
            Engine hp2 = (Engine) engine2;
            return Integer.compare(hp1.getHorsepower(), hp2.getHorsepower());
        });
        enginesTableModel.addTableModelListener(e -> {
            int row = e.getFirstRow();
            Engine updatedEngine = enginesTableModel.getEngines().get(row);
            try {
                engineUseCase.update(updatedEngine);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(EngineManagerView.this, ex.getMessage());
            }
        });
        enginesTable.setRowSorter(sorter);
        enginesTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        enginesTable.setModel(enginesTableModel);
    }

    private void ConfigureAddNewItemPanel() {
        typeEngine.setVisible(labNumber != 3);
        actionButton.setVisible(labNumber != 3);
        powerEngine.setVisible(labNumber != 3);
        actionButton.addActionListener(_ -> {
            try {
                engineUseCase.tryCreateEngine(typeEngine.getText(), powerEngine.getText());
                enginesTableModel.setEngines(engineUseCase.getEngines());
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(EngineManagerView.this, ex.getMessage());
                typeEngine.setText("Engine Type");
                powerEngine.setText("0");
            }
        });
    }

    private void ConfigureFilterTextField() {
        filterPowerLabel.setVisible(labNumber != 3);
        filterPowerTextField.setVisible(labNumber != 3);
        filterPowerTextField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                updateFilter();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                updateFilter();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                updateFilter();
            }

            private void updateFilter() {
                if (timer != null) {
                    timer.stop();
                }
                timer = new Timer(1000, _ -> {
                    String filterText = filterPowerTextField.getText();
                    try {
                        if (filterText.trim().isEmpty()) {
                            enginesTableModel.setEngines(engineUseCase.getEngines());
                        } else {
                            enginesTableModel.setEngines(engineUseCase.filterByHorsePower(filterText));
                        }
                    } catch (Exception e) {
                        sorter.setRowFilter(null);
                    }
                });
                timer.setRepeats(false);
                timer.start();
            }
        });
    }

    private void ConfigureMinMaxButton() {
        minMaxValuesButton.setVisible(labNumber == 2);
        minMaxValuesButton.addActionListener(_ -> {
            try {
                String message = GetEnginesString(engineUseCase.displayMinMaxPowerEngines());
                JOptionPane.showMessageDialog(EngineManagerView.this, message);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(EngineManagerView.this, e.getMessage());
            }
        });
    }

    private static String GetEnginesString(List<Engine> engines) {
        StringBuilder sb = new StringBuilder();
        for (Engine engine : engines) {
            sb.append(engine.getInfo()).append("\n");
        }
        return sb.toString();
    }

    private void ConfigureFilterTypeTextField() {
        filterTypeLabel.setVisible(labNumber == 2);
        filterTypeTextField.setVisible(labNumber == 2);
        filterTypeTextField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                updateFilter();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                updateFilter();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                updateFilter();
            }

            private void updateFilter() {
                if (timer != null) {
                    timer.stop();
                }
                timer = new Timer(1000, _ -> {
                    String filterText = filterTypeTextField.getText();
                    try {
                        if (filterText.trim().isEmpty()) {
                            enginesTableModel.setEngines(engineUseCase.getEngines());
                        } else {
                            enginesTableModel.setEngines(engineUseCase.displayEnginesMatchingPattern(filterText));
                        }
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(EngineManagerView.this, e.getMessage());
                    }
                });
                timer.setRepeats(false);
                timer.start();
            }
        });
    }
}



