package pctimer.com;
 

import javax.swing.*;
import java.awt.*;
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
import java.util.List;

public class SwingLineChartExample extends JPanel {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<Integer> dataPoints =new ArrayList<>();
	private List<String> dataPointsLabel =new ArrayList<>();
	List<String> firstColumnData = new ArrayList<>();
    List<String> secondColumnData = new ArrayList<>();
    List<List<String>> nestedList = new ArrayList<>();
    int x1 =0;
    int y1 = 0;
    int y2= 0;
    int x2= 0;
    
    
    public SwingLineChartExample() {
      
        setPreferredSize(new Dimension(400, 300));
    }

  
	@Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        nestedList = CSVFileDataReadFunc();
        
        for(int i = 1 ;i<nestedList.get(1).size();i++)
        {
          dataPoints.add(Integer.parseInt(nestedList.get(1).get(i)));
          dataPointsLabel.add(nestedList.get(0).get(i));
        }
        
      
        int padding = 40;
        int labelPadding = 25;
        int plotWidth = getWidth() - 2 * padding - labelPadding;
        int plotHeight = getHeight() - 2 * padding - labelPadding;

        // Draw axes (basic representation)
        g2d.drawLine(padding + labelPadding, getHeight() - padding - labelPadding, padding + labelPadding + plotWidth, getHeight() - padding - labelPadding);
        g2d.drawLine(padding + labelPadding, getHeight() - padding - labelPadding, padding + labelPadding, padding);
        
        for (int i = 0; i < dataPoints.size()-1 ; i++) {
        g2d.drawString(dataPointsLabel.get(i), x1-20, getHeight()+10 - padding - labelPadding);
        }
        
        // Find max value to scale the data
        int maxData = dataPoints.stream().mapToInt(v -> v).max().orElse(100);

        // Plot the line
        g2d.setColor(Color.BLUE);
        for (int i = 0; i < dataPoints.size()-1 ; i++) {
             x1 = padding + labelPadding + i * plotWidth / (dataPoints.size() - 1);
             y1 = getHeight() - padding - labelPadding - dataPoints.get(i) * plotHeight / maxData;
             x2 = padding + labelPadding + (i + 1) * plotWidth / (dataPoints.size() - 1);
             y2 = getHeight() - padding - labelPadding - dataPoints.get(i + 1) * plotHeight / maxData;
            g2d.drawLine(x1, y1, x2, y2);
         	
            
        }
        
       System.out.println(dataPointsLabel);       
    }
	
	
	
	public  List<List<String>> CSVFileDataReadFunc (){
		Path path = Paths.get(System.getProperty("user.dir")+"/report/");
		String csvFileNameDayLog =  "/"+"DayLog"+".csv";
		String varToday = LocalDate.now().format(DateTimeFormatter.ofPattern("ddMMMyyy"));
		
        //String filePath = "data.csv"; // Replace with your file path
        String line = "";
        String delimiter = ",";
        List<String> firstColumnData = new ArrayList<>();
        List<String> secondColumnData = new ArrayList<>();
        List<List<String>> nestedList = new ArrayList<>();
       
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

        boolean recordNotFound = false;
		// Print the data from the first column
        for (String value1 : firstColumnData) {
        if (varToday.equals(value1))  
        {	
        secondColumnData.set(firstColumnData.indexOf(value1), Float.toString((float) 250.23));
        CSVFileDataRead.CSVWriteDayLog(path,csvFileNameDayLog,firstColumnData,secondColumnData);
        System.out.println("inside if csvfiledataread");
        recordNotFound =false;
       }
        
        }
       
        if (recordNotFound)
        {
        	   firstColumnData.add(varToday);
        	   secondColumnData.add(Float.toString((float) 250.23));
        	   CSVFileDataRead.CSVWriteDayLog(path,csvFileNameDayLog,firstColumnData,secondColumnData);
        	   System.out.println("inside else csvfiledataread--");
        	   recordNotFound=true;
        }
        
	    nestedList.add(firstColumnData);
	    nestedList.add(secondColumnData);
	    firstColumnData =null;
	    secondColumnData =null;
        return nestedList;
       
	
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
	
	

    public static void main(String[] args) {
     
    
    
        JFrame frame = new JFrame("Single Line Chart Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(new SwingLineChartExample());
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
