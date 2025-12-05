// Extending Thread in Class
package pctimer.com; 
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;


import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.awt.GridBagLayout;

// Class inheriting Thread class
class MyThread extends Thread 
{     
	URL imageURL = TrayIconDemo.class.getResource("images/bulb.gif");
	LocalTime myObj = LocalTime.now();
    int workTime = 25;
	int breakTime = 15; 
	String dialogLabelMessage= "Break Time ";
	boolean workTimeStatus = true;
	boolean breakTimeStatus = false;
	boolean captureRecord=true;
	boolean historyVisible = false;
    LocalTime additionalTime=myObj.plusMinutes(workTime);
	boolean exit=true;
	int value =0 ;
	JDialog dialog =new JDialog();
	JLabel dialogLabel = new JLabel(dialogLabelMessage);
	 String threadMessage;
	 TableDemo td =new TableDemo();
    DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("HH:mm");
		
	// Overriding the run method
  	@Override
    public void run(){
         dialog.setLayout(new GridBagLayout());
  		dialog.add(dialogLabel);
  		dialog.setPreferredSize(new java.awt.Dimension(200,200));
  		dialog.setLocation(500,200);
  		dialog.setIconImage(new ImageIcon(imageURL).getImage());
  		dialog.setTitle ("Action Status");
  		td.tableFrame();
  		dialog.pack();
  		
  		while(exit) {
  			 value = LocalTime.now().compareTo(additionalTime);
  	        if(captureRecord==true)
  	        { td.addRow("work Time:"+Boolean.toString(workTimeStatus),LocalTime.now().format(myFormatObj) ,additionalTime.format(myFormatObj));
  	          captureRecord=false;
  	        }
  		  	
  	           	        
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
       
        	 
        
        	
        		                   } //for block 
        	
       // 	exit =false;
               // convert status of work/breaktime set next additionaltime                 
        		 workTimeStatus= !workTimeStatus;
             breakTimeStatus=!breakTimeStatus; 
             
         //create Next time line.    
               if (workTimeStatus==true) {
               	  additionalTime = LocalTime.now().plusMinutes(workTime);
                  dialogLabelMessage="Break Time - Walk";
           	      System.out.print(dialogLabelMessage + "\n");
           	      captureRecord=true;
               }
               if(breakTimeStatus==true) {
               additionalTime = LocalTime.now().plusMinutes(breakTime);
               dialogLabelMessage="work Time";
               System.out.print(dialogLabelMessage + "\n" );
               captureRecord=true;
               } //if block close
  		                        }// if block
  		 dialogLabel.setText(dialogLabelMessage);
  		
  		  }  //while block 
        
  	System.out.println("exited run");
  	
  
  	
  	}//run end
    
  //function end 
  
}
