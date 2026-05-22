package graph.com;
 

import javax.swing.*;


import java.awt.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import pctimer.com.*;

@SuppressWarnings("serial")
public class BarLineChart extends JPanel  {
    /**
	 * 
	 */
	
	public List<Float> dataPoints =new ArrayList<>();
	public List<Integer> dataPointsInt =new ArrayList<>();
	public  List<String> dataPointsLabel =new ArrayList<>();
	public  List<String> dataPointsHRSLabel =new ArrayList<>();
    public List<List<String>> nestedList = new ArrayList<>();  // nested list 
    int x1 =0;
    float y1 = 0;
    int y2= 0;
    int x2= 0;
   int varNestedList;
    
    URL imageURL = TrayIconDemo.class.getResource("images/barchart.gif");	
  
    
    public BarLineChart() {
        
         setPreferredSize(new Dimension(500, 300));
         nestedList = CSVFileDataReadFunc();
         varNestedList = nestedList.get(1).size();
         changeBarParameters();
        
        
    }
 
   
  
	@Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
       
        Graphics2D g2d = (Graphics2D) g;
        
      
        int padding = 40;
        int labelPadding = 20;
        int plotWidth = getWidth() - 2 * padding - labelPadding;
        int plotHeight = getHeight() - 2 * padding - labelPadding;

        // Draw axes (basic representation)
        g2d.drawLine(padding + labelPadding, getHeight() - padding - labelPadding, padding + labelPadding + plotWidth, getHeight() - padding - labelPadding);
        
        //y axis 
        g2d.drawLine(padding + labelPadding, getHeight() - padding - labelPadding, padding + labelPadding, padding);
        
                
        // Find max value to scale the data
        int maxData = dataPointsInt.stream().mapToInt(v -> v).max().orElse(100);

        // Plot the line
        g2d.setColor(Color.BLACK);
        for (int i = 1; i < dataPoints.size() ; i++) {
      /*   //  old code here 
        	    x1 = padding + labelPadding + i * plotWidth / (dataPoints.size() - 1);
            y1 = getHeight() - padding - labelPadding - dataPoints.get(i) * plotHeight / maxData;
            x2 = padding + labelPadding + (i + 1) * plotWidth / (dataPoints.size() - 1);
            y2 = getHeight() - padding - labelPadding - dataPoints.get(i + 1) * plotHeight / maxData;
            g2d.drawLine(x1, y1, x2, y2);
            
     */     	x1 = padding + labelPadding + i* plotWidth / (dataPoints.size());
            y1 = getHeight() - padding - labelPadding - dataPoints.get(i) * plotHeight / maxData;
             	
        	    x2= padding + labelPadding + i* plotWidth / (dataPoints.size() ) ;
            y2= getHeight() - padding - labelPadding -7 ; // constant added to match y2 with the line
            g2d.setStroke(new BasicStroke(15.0f));
            g2d.drawLine(x1, (int) y1, x2, y2);// cast added to convert decimal to integer
            
            g2d.drawString(dataPointsHRSLabel.get(i), x1, y1-10); // constant added to make label visible
            g2d.drawString(dataPointsLabel.get(i), x2, y2+20);// constant added to make label visible
           
           }  
        
        
        g2d.dispose();// Dispose of the copy
   	
	 }
	// Function to change the bar chart parameters
	public void changeBarParameters() {
		 
		   dataPointsInt.clear();
	        dataPoints.clear();
	        dataPointsLabel.clear();
	        dataPointsHRSLabel.clear();
		 
		for(int i =varNestedList-10  ;i<varNestedList-1;i++){
	    
      	dataPointsInt.add((int) (Float.parseFloat(nestedList.get(1).get(i))/60));
       dataPoints.add(Float.parseFloat(nestedList.get(1).get(i))/60);
       dataPointsLabel.add(nestedList.get(0).get(i).substring(0, 5));
       dataPointsHRSLabel.add(String.format("%.2f",Float.parseFloat(nestedList.get(1).get(i))/60));
    
       
		}
	
	}
	// read file funtion start below.
	
	public  List<List<String>> CSVFileDataReadFunc (){
		Path path = Paths.get(System.getProperty("user.dir")+"/report");
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
       
        recordNotFound =false;
       }
        
        }
       
        if (recordNotFound)
        {
        	   firstColumnData.add(varToday);
        	   secondColumnData.add(Float.toString((float) 250.23));
        	   CSVFileDataRead.CSVWriteDayLog(path,csvFileNameDayLog,firstColumnData,secondColumnData);
        	   
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
	      
                                 
	       for (int i = 0; i <=  firstColumnData.size(); i++) {
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
	
	// Call function from other objects 
	   public void createAndShowGUI() {
		  	  
		     //Create and set up the window.
	    JFrame  frame = new JFrame("BarChart");
	     frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	
	        
	     //Add content to the window.
	     frame.add(new BarLineChart());
	     try {
			frame.setIconImage(new ImageIcon(imageURL).getImage());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	     frame.setTitle("BarChart");
	     frame.setFont(TrayIconDemo.allFont);
	     frame.setLocation(450,300);
	  
	     //Display the window.
	     frame.pack();
	     frame.setVisible(true);
	    
	 
	 }

	   
	   
	   
	   // Main function 
        public static void main(String[] args) {
        JFrame frame = new JFrame("Single Line Chart Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(new BarLineChart());
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
       
        
        
        
        }
		

   
}
