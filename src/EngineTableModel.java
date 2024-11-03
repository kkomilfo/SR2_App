import engine.Engine;

import javax.swing.table.AbstractTableModel;
import java.util.List;

/**
 * The EngineTableModel class serves as a data model for displaying engine
 * information in a JTable within a Swing application. It extends the
 * AbstractTableModel to provide the necessary functionality for
 * managing and displaying a list of Engine objects.
 */
public class EngineTableModel extends AbstractTableModel {
    private List<Engine> engines;
    private final String[] columnNames = {"Engine Type", "Horsepower"};

    /**
     * Constructs an EngineTableModel with the specified list of engines.
     *
     * @param engines the initial list of Engine objects to display.
     */
    public EngineTableModel(List<Engine> engines) {
        this.engines = engines;
    }

    /**
     * Returns the number of rows in the model, which corresponds to the number of engines.
     *
     * @return the number of engines in the list.
     */
    @Override
    public int getRowCount() {
        return engines.size();
    }

    /**
     * Retrieves the list of engines.
     *
     * @return the current list of Engine objects.
     */
    public List<Engine> getEngines() {
        return engines;
    }

    /**
     * Updates the value of a specific cell in the table model.
     *
     * @param aValue     the new value to set in the cell.
     * @param rowIndex   the index of the row to update.
     * @param columnIndex the index of the column to update.
     */
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

    /**
     * Returns the number of columns in the model.
     *
     * @return the number of columns, which is fixed at 2 (Engine Type, Horsepower).
     */
    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    /**
     * Retrieves the value at a specific cell in the table model.
     *
     * @param rowIndex   the index of the row to retrieve.
     * @param columnIndex the index of the column to retrieve.
     * @return the value at the specified cell.
     */
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Engine engine = engines.get(rowIndex);
        return switch (columnIndex) {
            case 0 -> engine.getEngineType();
            case 1 -> engine.getHorsepower();
            default -> throw new IndexOutOfBoundsException("Invalid column index");
        };
    }

    /**
     * Returns the name of the specified column.
     *
     * @param column the index of the column for which to retrieve the name.
     * @return the name of the column.
     */
    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    /**
     * Updates the list of engines in the model and notifies listeners of the change.
     *
     * @param engines the new list of Engine objects to set in the model.
     */
    public void setEngines(List<Engine> engines) {
        this.engines = engines;
        fireTableDataChanged();
    }

    /**
     * Determines whether a specific cell is editable.
     *
     * @param rowIndex   the index of the row to check.
     * @param columnIndex the index of the column to check.
     * @return true if the cell is editable, false otherwise.
     */
    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return columnIndex == 0 || columnIndex == 1; // Both columns are editable
    }
}
