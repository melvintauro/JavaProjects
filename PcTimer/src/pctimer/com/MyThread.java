// Extending Thread in Class
package pctimer.com; 
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.table.DefaultTableModel;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.awt.GridBagLayout;
import java.awt.Toolkit;

// Class inheriting Thread class
class MyThread extends Thread 
{    
	FileUtil fu=new FileUtil(); // create the file util object.
	URL imageURL = TrayIconDemo.class.getResource("images/bulb.gif");
	LocalTime currentLocalTime = LocalTime.now();
    int workTime = fu.fileDBData[0];
	int breakTime= fu.fileDBData[1];  
	int blinkTime = 10;
	String dialogLabelMessage= "Break Time ";
	boolean workTimeStatus = true;
	boolean breakTimeStatus = false;
	boolean captureRecord=true;
	boolean historyVisible = false;
    LocalTime additionalTime=LocalTime.now().plusMinutes(workTime);
	boolean exit=true;
	int value =0 ;
	JFrame dialog =new JFrame();
	JLabel dialogLabel = new JLabel(dialogLabelMessage);
	String threadMessage;
	//TableDemo td =new TableDemo();
    DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("HH:mm");
    static CheckboxTableModel tableModel = null;
		
	// Overriding the run method
  	@Override
    public void run(){
      
        
  		dialog.setLayout(new GridBagLayout());
  		dialog.add(dialogLabel);
  		dialog.setPreferredSize(new java.awt.Dimension(200,200));
  		dialog.setLocation(600,250);  //500,200
  		dialog.setIconImage(new ImageIcon(imageURL).getImage());
  		dialog.setTitle ("Action Status");
  		  		dialog.pack();
  		  		createTableModel();
  		
  		while(exit) {
               
  			      if(captureRecord==true)
  	        {    System.out.println("capture entered");
  	        	   addARecord("Work Time:-");//add a record using function 
  	        	 TrayIconDemo.trayIcon.setToolTip(TrayIconDemo.getMessageInfoStatus());
  	        	   captureRecord=false;
  	        }
  	      value = LocalTime.now().compareTo(additionalTime);		  	           	        
  	        if(value>0 ) {
  	        if(workTimeStatus==true) // speaker will give a bell.
  	        {	beepPCSpeaker(1);}else {beepPCSpeaker(2);try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}}
  	        	
            int i=blinkTime;
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
               	  currentLocalTime=LocalTime.now();
                  dialogLabelMessage="Break Time - Walk";
           	      System.out.print(dialogLabelMessage + "\n");
           	      captureRecord=true;
           	      blinkTime=10;
               }
               if(breakTimeStatus==true) {
               additionalTime = LocalTime.now().plusMinutes(breakTime);
               currentLocalTime=LocalTime.now();
               dialogLabelMessage="work Time";
               System.out.print(dialogLabelMessage + "\n" );
               captureRecord=true;
               blinkTime=blinkTime/2;
               } //if block close
  		                        }// if block
  		 dialogLabel.setText(dialogLabelMessage);
  		
  		  }  //while block 
        
  	System.out.println("exited run");
  	
    
  	
  	}//run end
  	public void addARecord(String varWorkTimeText)
    {		
  		
  		TableDemo.addRow(varWorkTimeText+Boolean.toString(workTimeStatus),LocalTime.now().format(myFormatObj),
  additionalTime.format(myFormatObj),Long.toString((additionalTime.toSecondOfDay()-currentLocalTime.toSecondOfDay())/60)
   
  				);
  
  		} 
  //function end 
  	public void updateWorkBreakTime()  // udapte the new time based on GUI settings change.
    {	  
  	     workTime = fu.fileDBData[0];
  		 breakTime = fu.fileDBData[1]; 
  			
     System.out.println(fu.fileDBData[0]+" updateWorkBreakTime " + fu.fileDBData[1] +" " +additionalTime );
    } 
  	
  	public void beepPCSpeaker(int j )
  	{
  		for(int i=0;i<j;i++)
  		Toolkit.getDefaultToolkit().beep();//beep from pc speaker
  	}
    
  	 public void createTableModel()
  	 {
  		 //create table model
		 tableModel= new CheckboxTableModel();
		 tableModel.addColumn("Status");
         tableModel.addColumn("Start");
         tableModel.addColumn("End");
         tableModel.addColumn("Gap in Min");
         tableModel.addColumn("Select");
  	
         
  	 }



}
