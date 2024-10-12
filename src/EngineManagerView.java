import engine.Engine;
import engine.EngineUseCase;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableRowSorter;

public class EngineManagerView extends JFrame {
    private JTextField powerEngine;
    private JTextField typeEngine;
    private JButton actionButton;
    private JTextField filterPowerTextField;
    private JPanel mainPanel;
    private JTable enginesTable;
    private final Integer labNumber;
    private final EngineUseCase engineUseCase;
    private final EngineTableModel enginesTableModel;
    private final TableRowSorter<EngineTableModel> sorter;
    private static Timer timer;

    public EngineManagerView(Integer labNumber, EngineUseCase engineUseCase) {
        this.labNumber = labNumber;
        this.engineUseCase = engineUseCase;
        this.enginesTableModel = new EngineTableModel(engineUseCase.getEngines());
        this.sorter = new TableRowSorter<>(enginesTableModel);
        ConfigurePanel();
        ConfigureDataTable();
        ConfigureAddNewItemPanel();
        ConfigureFilterTextField();

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
                engineUseCase.Update(updatedEngine);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(EngineManagerView.this, ex.getMessage());
            }
        });
        enginesTable.setRowSorter(sorter);
        enginesTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        enginesTable.setModel(enginesTableModel);
    }

    private void ConfigureAddNewItemPanel() {
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
                    if (filterText.trim().isEmpty()) {
                        sorter.setRowFilter(null);
                    } else {
                        try {
                            int filterValue = Integer.parseInt(filterText);
                            sorter.setRowFilter(new RowFilter<>() {
                                @Override
                                public boolean include(Entry<? extends EngineTableModel, ? extends Integer> entry) {
                                    return entry.getValue(1) != null && (int) entry.getValue(1) > filterValue;
                                }
                            });
                        } catch (NumberFormatException e) {
                            sorter.setRowFilter(null);
                        }
                    }
                });
                timer.setRepeats(false);
                timer.start();
            }
        });
    }
}



