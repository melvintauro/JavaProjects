package pctimer.com;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.swing.JTable;
import javax.swing.table.TableModel;

public class CSVFileDataWrite{
	

	public CSVFileDataWrite() {
        super();
    }

	
	 // save tool bar button 

	   public  boolean CSVWriteTimeLog(JTable table,
	                                       Path path,String csvFileName) {
	   try {
	       TableModel model = table.getModel();
	       Files.createDirectories(path);
	       FileWriter csv = new FileWriter(new File(path.toString()+csvFileName));
	      

	       for (int i = 0; i < model.getColumnCount(); i++) {
	           csv.write(model.getColumnName(i) + ",");
	       }
	       csv.write("\n");

	       for (int i = 0; i < model.getRowCount(); i++) {
	           for (int j = 0; j < model.getColumnCount(); j++) {
	               csv.write(model.getValueAt(i, j).toString() + ",");
	           }
	           
	          
	           csv.write("\n");
	       }
	       csv.write("\n");
	       csv.write(TrayIconDemo.labelTotalWorkHours.getText());
	       csv.write("\n");
	       csv.close();
	       return true;
	   } catch (IOException e) {
	       e.printStackTrace();
	   }
	   return false;
	   }

	
	   public  boolean CSVWriteDayLog(JTable table,
               Path path,String csvFileName) { 
		   try {
			
										
				if((new File(path.toString()+csvFileName).exists()))
				{
					new CSVFileDataRead().CSVFileDataReadFunc();
				  
				} else {
					     
					     Files.createDirectories(path);
							FileWriter csv = new FileWriter(new File(path.toString()+csvFileName)) ;
							
							csv.write(" Day "+",");
							csv.write(" Minutes "+",");
						    
							
							csv.write("\n");
							csv.write(LocalDate.now().format(DateTimeFormatter.ofPattern("ddMMMyyy"))+",");
							csv.write(Float.toString(CheckboxTableModel.countCheckedCheckboxes(TableDemo.table,4)));
							System.out.println("inside csvfiledatawrite");
						    csv.close();
						    return true; }	  
				  
			
			  
		   } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		   }
	      
		
		   return false;
	   }


}