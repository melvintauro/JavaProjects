package pctimer.com; 

import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class TableDemo  {
	URL imageURL = TrayIconDemo.class.getResource("images/record.png");	
	  DefaultTableModel tableModel = new DefaultTableModel();
      JTable table = new JTable(tableModel);
      JFrame f = new JFrame();
         
      public void tableFrame() {
    	      tableModel.addColumn("Status");
          tableModel.addColumn("Start");
          tableModel.addColumn("End");
         
         f.setSize(300,150);
         f.add(new JScrollPane(table));
         f.setLocation(500,250);
         f.setIconImage(new ImageIcon(imageURL).getImage());
         
         }

      public void addRow(String a,String b , String c)
      {
    	  tableModel.addRow(new Object[] { a, b,c });

    	  
      }
      
      public void setVisible(Boolean b) 
      {
    	  f.setVisible(b);
    	  
      }
} 
      
               