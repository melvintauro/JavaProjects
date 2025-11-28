// Extending Thread in Class
package pctimer.com; 
import java.io.*;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import java.time.LocalTime;
import java.awt.GridBagLayout;
import java.awt.Image;

// Class inheriting Thread class
class MyThread extends Thread 
{
	LocalTime myObj = LocalTime.now();
    LocalTime breakTime = myObj.plusMinutes(1);
	boolean exit=true;
	int value =0 ;
	JDialog dialog =new JDialog();
	JLabel dialogLabel = new JLabel("Break time ");
	
	
		
	// Overriding the run method
  	@Override
    public void run(){
         dialog.setLayout(new GridBagLayout());
  		dialog.add(dialogLabel);
  		dialog.setPreferredSize(new java.awt.Dimension(200,200));
  		dialog.setIconImage(new ImageIcon("images/pcworker.png").getImage());
  		dialog.pack();
  		
  		while(exit) {
  			 value = LocalTime.now().compareTo(breakTime);
  		if(value>0 ) {
        	
        int i=3;
        		for(int j=0;j<i;j++) {
        
        	try {
        		    dialog.setVisible(true); 
        		     MyThread.sleep(2000);
				dialog.setVisible(false);
				MyThread.sleep(2000);
        	} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        
        		}
        	
        	exit =false;
        } }
        
  	System.out.println("exited run");
  	}
    
        
              
  	
}
