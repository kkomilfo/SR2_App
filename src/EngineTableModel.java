import engine.Engine;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class EngineTableModel extends AbstractTableModel {
    private List<Engine> engines;
    private final String[] columnNames = {"Engine Type", "Horsepower"};

    public EngineTableModel(List<Engine> engines) {
        this.engines = engines;
    }

    @Override
    public int getRowCount() {
        return engines.size();
    }

    public List<Engine> getEngines() {
        return engines;
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        Engine engine = engines.get(rowIndex);
        switch (columnIndex) {
            case 0:
                engine.setEngineType((String) aValue); // Update engine type
                break;
            case 1:
                int value = Integer.parseInt((String) aValue);
                engine.setHorsepower(value); // Update horsepower
                break;
        }
        fireTableCellUpdated(rowIndex, columnIndex); // Notify that a cell has been updated
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Engine engine = engines.get(rowIndex);
        return switch (columnIndex) {
            case 0 -> engine.getEngineType();
            case 1 -> engine.getHorsepower();
            default -> throw new IndexOutOfBoundsException("Invalid column index");
        };
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }


    public void setEngines(List<Engine> engines) {
        this.engines = engines;
        fireTableDataChanged();
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return columnIndex == 0 || columnIndex == 1;
    }
}
