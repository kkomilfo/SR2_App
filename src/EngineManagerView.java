import engine.Engine;
import engine.EngineUseCase;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.TableRowSorter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

// Клас EngineManagerView, який створює інтерфейс для взаємодії з двигунами
public class EngineManagerView extends JFrame {
    // Елементи інтерфейсу
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
    private JButton deleteSelection;
    // Лабораторний номер та випадок використання (use case) для роботи з даними
    private final Integer labNumber;
    private final EngineUseCase engineUseCase;
    // Модель таблиці для відображення двигунів
    private final EngineTableModel enginesTableModel;
    private final TableRowSorter<EngineTableModel> sorter;
    private static Timer timer;

    // Конструктор класу, який ініціалізує елементи інтерфейсу і конфігурації
    public EngineManagerView(Integer labNumber, EngineUseCase engineUseCase) throws Exception {
        this.labNumber = labNumber;
        this.engineUseCase = engineUseCase;
        this.enginesTableModel = new EngineTableModel(engineUseCase.getEngines());   // Ініціалізація моделі даних
        this.sorter = new TableRowSorter<>(enginesTableModel);  // Ініціалізація сортувальника для таблиці
        ConfigurePanel();
        ConfigureDataTable();
        ConfigureAddNewItemPanel();
        ConfigureFilterTextField();
        ConfigureMinMaxButton();
        ConfigureFilterTypeTextField();
        ConfigureSaveToFile();
        ConfigureShowFromBinaryButton();
        ConfigureDeleteButton();
    }

    // Метод конфігурації кнопки видалення для Лабораторії 4
    private void ConfigureDeleteButton() {
        deleteSelection.setVisible(labNumber == 4);   // Відображати кнопку лише для Лабораторії 4
        deleteSelection.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int id = enginesTableModel.getEngines().get(enginesTable.getSelectedRow()).getId();  // Отримання ID двигуна
                try {
                    engineUseCase.delete(id);   // Видалення двигуна
                    enginesTableModel.setEngines(engineUseCase.getEngines());  // Оновлення моделі таблиці
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
    }

    // Метод конфігурації кнопки для показу даних з двійкового файлу (Лабораторна 3)
    private void ConfigureShowFromBinaryButton() {
        showFromBinaryButton.setVisible(labNumber == 3);  // Відображати кнопку лише для Лабораторної 3
        showFromBinaryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    // Відображення двигунів з двійкового файлу
                    List<Engine> engines = engineUseCase.displayEnginesFromBinFile();
                    JOptionPane.showMessageDialog(EngineManagerView.this, GetEnginesString(engines));
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(EngineManagerView.this, ex.getMessage());
                }
            }
        });
    }

    // Метод для збереження даних до файлу (Лабораторна 3)
    private void ConfigureSaveToFile() {
        saveToFileButton.setVisible(labNumber == 3);  // Відображати кнопку лише для Лабораторної 3
        saveToFileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    engineUseCase.save(enginesTableModel.getEngines());  // Збереження двигунів до файлу
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(EngineManagerView.this, ex.getMessage());
                }
            }
        });
    }

    // Налаштування основної панелі
    private void ConfigurePanel() {
        setContentPane(mainPanel);  // Встановлення основної панелі вмісту
        String message = String.format("Lab %d", labNumber);
        setTitle(message);
    }

    // Конфігурація таблиці з даними про двигуни
    private void ConfigureDataTable() {
        sorter.setComparator(1, (engine1, engine2) -> {
            Engine hp1 = (Engine) engine1;
            Engine hp2 = (Engine) engine2;
            return Integer.compare(hp1.getHorsepower(), hp2.getHorsepower());  // Порівняння за потужністю
        });
        enginesTableModel.addTableModelListener(e -> {
            int row = e.getFirstRow();
            Engine updatedEngine = enginesTableModel.getEngines().get(row);  // Оновлення двигуна
            try {
                engineUseCase.update(updatedEngine);  // Оновлення двигуна в базі даних
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(EngineManagerView.this, ex.getMessage());
            }
        });
        enginesTable.setRowSorter(sorter);   // Встановлення сортування
        enginesTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);  // Дозволити вибір лише одного рядка
        enginesTable.setModel(enginesTableModel);  // Встановлення моделі таблиці
    }

    // Налаштування панелі для додавання нового елемента
    private void ConfigureAddNewItemPanel() {
        typeEngine.setVisible(labNumber != 3);  // Відображати поле "Тип двигуна", якщо це не Лабораторна 3
        actionButton.setVisible(labNumber != 3); // Відображати кнопку додавання, якщо це не Лабораторна 3
        powerEngine.setVisible(labNumber != 3);  // Відображати поле "Потужність", якщо це не Лабораторна 3
        actionButton.addActionListener(_ -> {
            try {
                engineUseCase.tryCreateEngine(typeEngine.getText(), powerEngine.getText());  // Створення двигуна
                enginesTableModel.setEngines(engineUseCase.getEngines());  // Оновлення таблиці
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(EngineManagerView.this, ex.getMessage());
                typeEngine.setText("Engine Type");
                powerEngine.setText("0");
            }
        });
    }

    // Налаштування фільтрації за потужністю (Лабораторна 1 і 2)
    private void ConfigureFilterTextField() {
        filterPowerLabel.setVisible(labNumber == 1 || labNumber == 2);  // Відображати фільтр за потужністю лише для Лабораторних 1 та 2
        filterPowerTextField.setVisible(labNumber == 1 || labNumber == 2);
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

            // Оновлення фільтра після затримки
            private void updateFilter() {
                if (timer != null) {
                    timer.stop();
                }
                timer = new Timer(1000, _ -> {
                    String filterText = filterPowerTextField.getText();
                    try {
                        if (filterText.trim().isEmpty()) {
                            enginesTableModel.setEngines(engineUseCase.getEngines());  // Показати всі двигуни
                        } else {
                            enginesTableModel.setEngines(engineUseCase.filterByHorsePower(filterText));  // Фільтрація за потужністю
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
        filterTypeLabel.setVisible(labNumber == 2 || labNumber == 4);
        filterTypeTextField.setVisible(labNumber == 2 || labNumber == 4);
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



