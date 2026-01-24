package pctimer.com;  
import javax.swing.table.DefaultTableModel;

public class CheckboxTableModel extends DefaultTableModel {
    public CheckboxTableModel() {
        super();
    }

    @Override
    public Class<?> getColumnClass(int column) {
        // Return Boolean.class for the column that should have checkboxes
        // For other columns, return their appropriate class or Object.class
        if (column == 4) { // Assuming the first column (index 0) will have checkboxes
            return Boolean.class;
        } else {
            return super.getColumnClass(column);
        }
    }
    
    // Optional: Make sure the checkbox column is editable by default (DefaultTableModel does this already, but good practice)
    @Override
    public boolean isCellEditable(int row, int column) {
        return true; 
    }
}