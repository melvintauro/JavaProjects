
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

public class TableDemo implements ActionListener {
	URL imageURL = TrayIconDemo.class.getResource("images/record.png");
	URL imageURL1 = TrayIconDemo.class.getResource("images/deleterow.gif");
	 
	DefaultTableModel tableModel = new DefaultTableModel();
	
      JTable table = new JTable(tableModel);
      JFrame f = new JFrame();
      JButton bt = new JButton();
      JToolBar tb = new JToolBar();
      
      
      // create a panel
      JPanel p1 = new JPanel();
     
     
  
         
      public void tableFrame() {
    	      tableModel.addColumn("Status");
          tableModel.addColumn("Start");
          tableModel.addColumn("End");
          tableModel.addColumn("Gap in Min");
          
          // set layout for frame
          f.setLayout(new BorderLayout());
          
         
          
          bt.setIcon(new ImageIcon(imageURL1));  
         
         //
         f.setSize(420,250);
                
        
         f.setLocation(500,250);
         f.setIconImage(new ImageIcon(imageURL).getImage());
         f.setTitle("Event History");
         f.setFont(new Font("Verdana", Font.PLAIN, 18));
      
       
         
         tb.add(bt);
         p1.add(tb,BorderLayout.PAGE_START);
         p1.add(new JScrollPane(table),BorderLayout.CENTER);
         f.add(p1);
          
         bt.addActionListener(this);
       
         
      }

      public void addRow(String a,String b , String c,String d)
      {
    	  tableModel.addRow(new Object[] { a, b,c,d });

    	  
      }
      
      public void setVisible(Boolean b) 
      {
    	  
    	  f.setVisible(b);
    	  
      }
  // button action write heere
      public void actionPerformed(ActionEvent e){
      	System.out.println("inside button");
     	//System.out.println(tableModel.getRowCount());
     	//int i = tableModel.getRowCount();
       // tableModel.fireTableRowsDeleted(1, i);	
        while(tableModel.getRowCount()>3)
      	tableModel.removeRow(0);
      
      }



} 
      
               