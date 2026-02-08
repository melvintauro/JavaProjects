package pctimer.com;  
import javax.swing.JTable;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;

public class CheckboxTableModel extends DefaultTableModel implements TableModelListener {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

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

    
    public static float countCheckedCheckboxes(JTable table, int columnWithCheckboxes) {
        float checkedCount = 0;
        // Always work with the model for consistency, especially if sorting/filtering is applied
        javax.swing.table.TableModel model = table.getModel(); 

        for (int i = 0; i < model.getRowCount(); i++) {
            // Retrieve the value as a Boolean object from the model
            Object value = model.getValueAt(i, columnWithCheckboxes);

            // Check if the value is Boolean.TRUE
            if (Boolean.TRUE.equals(value)) {
            	Object value1 = model.getValueAt(i, 3);
              checkedCount = Integer.parseInt(value1.toString()) + checkedCount;
                          
            }
        }
        return checkedCount;
    }

	@Override
	public void tableChanged(TableModelEvent e) {
		
			  timeLabelCreator();
		
	}

	  public static void timeLabelCreator()
	  {
		  int hrs = 0; 
			int min=0;
			try {
				hrs = (int)(countCheckedCheckboxes(TableDemo.table,4)/60);
				min = (int) (((countCheckedCheckboxes(TableDemo.table,4)/60)-hrs)*60);
							} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			TrayIconDemo.textTotalWorkHours=" Total Screen Time :- " + hrs +":"+ min;
			TrayIconDemo.labelTotalWorkHours.setText(TrayIconDemo.textTotalWorkHours);
				  
		  
	  }


}