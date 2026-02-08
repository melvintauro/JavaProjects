
package pctimer.com; 

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.MessageFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.filechooser.FileSystemView;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import java.io.File;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.PrinterException;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.event.TableModelEvent;

public class TableDemo extends JPanel implements ActionListener {
	static URL imageURL = TrayIconDemo.class.getResource("images/record.png");
	
	//URL imageURL1 = TrayIconDemo.class.getResource("images/deleterow.gif");
	//URL imageURL2 = TrayIconDemo.class.getResource("images/reset.png");
	
	static final private String CLEAR = "CLEAR";
    static final private String RESET = "RESET";
    static final private String SETTING = "SETTING";
    static final private String SAVE = "SAVE";
    static final private String PRINT = "PRINT";

	private static final TableModelEvent TableModelEvent = null;
    static JTable table =null;
    

   
    
    MessageFormat header = new MessageFormat(" Total Screen Time :-");
 // "{0}" in the footer format is replaced by the current page number
 MessageFormat footer = new MessageFormat("Page {0}"); 
    
   

	

	public TableDemo() {
		super (new BorderLayout());
		
		//Create the toolbar.
		 JToolBar toolBar = new JToolBar("Still draggable");
         
         
         addButtons(toolBar);
		 TrayIconDemo.labelTotalWorkHours = new JLabel();
				
		  //Create the table area
	          table = new JTable(MyThread.tableModel);
	          table.setFont(TrayIconDemo.allFont);
		 JScrollPane scrollPane = new JScrollPane(table);
		  table.getModel().addTableModelListener(MyThread.tableModel);
		 
		  TableColumnModel columnModel =   table.getColumnModel();
		  columnModel.getColumn(0).setPreferredWidth(130);
		  columnModel.getColumn(1).setPreferredWidth(60);
		  columnModel.getColumn(2).setPreferredWidth(60);
		  columnModel.getColumn(3).setPreferredWidth(60);
		  
		// Get the table header
		  JTableHeader header = table.getTableHeader();
		// Set the new font to the table header
		  header.setFont(TrayIconDemo.allFont);
		  
		//Lay out the main panel.
		    setPreferredSize(new Dimension(400, 300));
	        add(toolBar, BorderLayout.PAGE_START);
	        add(TrayIconDemo.labelTotalWorkHours,BorderLayout.PAGE_END );
	        add(scrollPane, BorderLayout.CENTER);
	       
	        
	        
	}
      
 
  
         
     

      
      protected void addButtons(JToolBar toolBar) {
          JButton button = null;

          //first button
          button = makeNavigationButton("deleterow",CLEAR ,
                                        "Clear Record from table",
                                        "Clear");
          toolBar.add(button);

          //second button
          button = makeNavigationButton("reset",RESET,
        		                             "Reset Record from table" + TrayIconDemo.textTotalWorkHours,
                                        "Reset");
          toolBar.add(button);

        //THIRD  button
          button = makeNavigationButton("setting",SETTING,
        		                             "Setting for Application",
                                        "Setting");
          toolBar.add(button);
      
        //FOURTH button
          button = makeNavigationButton("save",SAVE,
        		                             "Save the table",
                                        "save");
          toolBar.add(button);
      
          //FIFTH BUTTON
          button = makeNavigationButton("print",PRINT,
                  "print the table",
             "print");
          toolBar.add(button);
          
      
      }
      
      protected JButton makeNavigationButton(String imageName,
              String actionCommand,
              String toolTipText,
              String altText) {
    	  //Look for the image.
          String imgLocation = "images/"
                               + imageName
                               + ".gif";
          URL imageURL = TrayIconDemo.class.getResource(imgLocation); 

//Create and initialize the button.
JButton button = new JButton();
button.setActionCommand(actionCommand);
button.setToolTipText(toolTipText);
button.addActionListener(this);

if (imageURL != null) {                      //image found
    button.setIcon(new ImageIcon(imageURL, altText));
} else {                                     //no image found
    button.setText(altText);
    System.err.println("Resource not found: "
                       + imgLocation);
}

return button;
}

      
      static  public void addRow(String a,String b , String c,String d)
      {
    	   Object[] data= { a, b,c,d,Boolean.FALSE};
    	 
    	    MyThread.tableModel.addRow(data);

    	  
      }
      
   

	  // button action write here
      public void actionPerformed(ActionEvent e){
    	  String buttonCmd = e.getActionCommand();
    	if(CLEAR.equals(buttonCmd))  
      	{
        while( MyThread.tableModel.getRowCount()>1)
        	 MyThread.tableModel.removeRow(0);
      	}
    	else if(RESET.equals(buttonCmd))
    	{
    		
    		TrayIconDemo.setRestartThreadParameters();
    	} else if(SETTING.equals(buttonCmd))
    	{
    		 SwingUtilities.invokeLater(new Runnable() {
                 public void run() {
                     //Turn off metal's use of bold fonts
     	        UIManager.put("swing.boldMetal", Boolean.FALSE);
     	       GuiSettings.createAndShowGUI();
                 }
             });
    		
    	}
    	
    	else if(SAVE.equals(buttonCmd))
    	{
    		String csvFileName =  "/"+LocalDate.now().format(DateTimeFormatter.ofPattern("ddMMMyyy"))+
					LocalTime.now().format(DateTimeFormatter.ofPattern("hhmm"))+".csv";
    		convertToCSV(table,Paths.get(System.getProperty("user.dir")+"/report/"),csvFileName);
      
      
    	}
    	
    	
    	else if(PRINT.equals(buttonCmd))
    	{
    		
    		printTable(table);
    	}
    	
    	
      }

   // save tool bar button 

   public static boolean convertToCSV(JTable table,
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

// print tool bar button 
   public void printTable(JTable table) {  
   try {
	    // PrintMode.FIT_WIDTH scales the table to fit the page width
	    table.print(JTable.PrintMode.FIT_WIDTH, header, footer,true,null,true,null);
	} catch (PrinterException e) {
	    JOptionPane.showMessageDialog(null, "Printing Failed: " + e.getMessage(), "Print Error", JOptionPane.ERROR_MESSAGE);
	    e.printStackTrace();
	}  
   }

      static void createAndShowGUI() {
    	  
    	  
    	     //Create and set up the window.
         JFrame  frame = new JFrame("TableDemo");
          frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
           
             
          //Add content to the window.
          frame.add(new TableDemo());
          try {
			frame.setIconImage(new ImageIcon(imageURL).getImage());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
          frame.setTitle("Event History");
          frame.setFont(TrayIconDemo.allFont);
          frame.setLocation(350,100);
          CheckboxTableModel.timeLabelCreator();
          //Display the window.
          frame.pack();
          frame.setVisible(true);
        
      
      }








} 
      
               