package pctimer.com;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CSVFileDataRead{
	Boolean recordNotFound = true;
	 Collection <List> collectionOfArraysList = new ArrayList<>();
	 
	public CSVFileDataRead() {
        super();
    }
	public void  CSVFileDataReadFunc (){
		Path path = Paths.get(System.getProperty("user.dir")+"/report/");
		String csvFileNameDayLog =  "/"+"DayLog"+".csv";
		String varToday = LocalDate.now().format(DateTimeFormatter.ofPattern("ddMMMyyy"));
		
        //String filePath = "data.csv"; // Replace with your file path
        String line = "";
        String delimiter = ",";
        List<String> firstColumnData = new ArrayList<>();
        List<String> secondColumnData = new ArrayList<>();
       
        try (BufferedReader br = new BufferedReader(new FileReader(path.toString()+csvFileNameDayLog))) {
            while ((line = br.readLine()) != null) {
                // Use comma as separator
                String[] columns = line.split(delimiter, -1); // -1 ensures trailing empty strings are included
                if (columns.length > 0) {
                    firstColumnData.add(columns[0]);
                    secondColumnData.add(columns[1]);
                }
            }
            
            
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Print the data from the first column
        for (String value1 : firstColumnData) {
        if (varToday.equals(value1))  
        {	
        secondColumnData.set(firstColumnData.indexOf(value1), Float.toString(CheckboxTableModel.countCheckedCheckboxes(TableDemo.table,4)));
        CSVFileDataRead.CSVWriteDayLog(path,csvFileNameDayLog,firstColumnData,secondColumnData);
        System.out.println("inside if csvfiledataread");
        recordNotFound =false;
       }
        
        }
       
        if (recordNotFound)
        {
        	   firstColumnData.add(varToday);
        	   secondColumnData.add(Float.toString(CheckboxTableModel.countCheckedCheckboxes(TableDemo.table,4)));
        	   CSVFileDataRead.CSVWriteDayLog(path,csvFileNameDayLog,firstColumnData,secondColumnData);
        	   System.out.println("inside else csvfiledataread--");
        	   recordNotFound=true;
        }
     
	    
 
       
	
	}
	
	 // save tool bar button 

	   public static boolean CSVWriteDayLog(Path path,String csvFileName,List<String> firstColumnData,List<String> secondColumnData) {
	   try {
	  
	       Files.createDirectories(path);
	       FileWriter csv = new FileWriter(new File(path.toString()+csvFileName));
	      
                                 
	       for (int i = 0; i <  firstColumnData.size(); i++) {
	           csv.write(firstColumnData.get(i) + ",");
	           csv.write(secondColumnData.get(i) );
	           csv.write("\n");
	       }
	       
	       csv.close();  
	       return true;
	   } catch (IOException e) {
	       e.printStackTrace();
	   }
	   return false;
	   }
	
	
	
	
}
	