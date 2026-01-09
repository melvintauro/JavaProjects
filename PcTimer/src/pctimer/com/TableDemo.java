
package pctimer.com; 

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.net.URL;

import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TableDemo extends JPanel implements ActionListener {
	static URL imageURL = TrayIconDemo.class.getResource("images/record.png");
	//URL imageURL1 = TrayIconDemo.class.getResource("images/deleterow.gif");
	//URL imageURL2 = TrayIconDemo.class.getResource("images/reset.png");
	
	static final private String CLEAR = "CLEAR";
    static final private String RESET = "RESET";
    static final private String SETTING = "SETTING";
    
    static DefaultTableModel tableModel = new DefaultTableModel();
    static JFrame frame=null;
    static Boolean frameExists = true;
	

	public TableDemo() {
		super (new BorderLayout());
		
		//Create the toolbar.
		 JToolBar toolBar = new JToolBar("Still draggable");
		 addButtons(toolBar);
		 
		 //create table model
		 
		  tableModel.addColumn("Status");
          tableModel.addColumn("Start");
          tableModel.addColumn("End");
          tableModel.addColumn("Gap in Min");
          
		  //Create the table area
		 JTable table = new JTable(tableModel);
		 JScrollPane scrollPane = new JScrollPane(table);
	
		//Lay out the main panel.
		    setPreferredSize(new Dimension(450, 130));
	        add(toolBar, BorderLayout.PAGE_START);
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
        		                             "Reset Record from table",
                                        "Reset");
          toolBar.add(button);

        //second button
          button = makeNavigationButton("setting",SETTING,
        		                             "Setting for Application",
                                        "Setting");
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
    	  tableModel.addRow(new Object[] { a, b,c,d });

    	  
      }
      
     
  // button action write here
      public void actionPerformed(ActionEvent e){
    	  String buttonCmd = e.getActionCommand();
    	if(CLEAR.equals(buttonCmd))  
      	{
        while(tableModel.getRowCount()>3)
      	tableModel.removeRow(0);
      	}
    	else if(RESET.equals(buttonCmd))
    	{
    		
    		TrayIconDemo.setRestartThreadParameters();
    	} else if(SETTING.equals(buttonCmd))
    	{
    		new GuiSettings().slider();
    		
    	}
    	
      }


      static void createAndShowGUI() {
    	  
    	      if (frameExists== true)
    	      {   //Create and set up the window.
             frame = new JFrame("TableDemo");
          //frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

          //Add content to the window.
          frame.add(new TableDemo());
          try {
			frame.setIconImage(new ImageIcon(imageURL).getImage());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
          frame.setTitle("Event History");
          frame.setFont(new Font("Verdana", Font.PLAIN, 18));
          frame.setLocation(450,300);
       
          //Display the window.
          frame.pack();
          frame.setVisible(true);
          frameExists=false;
    	      }else {   frame.setVisible(true);        } 
        
      
      }
      

} 
      
               