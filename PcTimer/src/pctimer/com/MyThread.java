// Extending Thread in Class
package pctimer.com; 
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.awt.GridBagLayout;
import java.awt.Toolkit;
import java.awt.TrayIcon;

// Class inheriting Thread class
class MyThread extends Thread 
{    
	FileUtil fu=new FileUtil(); // create the file util object.
	URL imageURL = TrayIconDemo.class.getResource("images/bulb.gif");
	LocalTime currentLocalTime = LocalTime.now();
    int workTime = FileUtil.fileDBData[0];
	int breakTime= FileUtil.fileDBData[1];  
	int blinkTime = 10;
	int lookAwayBlinkTime=1;
	String dialogLabelMessage= "Break Time ";
	boolean workTimeStatus = true;
	boolean breakTimeStatus = false;
	boolean captureRecord=true;
	boolean historyVisible = false;
	boolean lookAwayTimeBool =true;
	int timeFromWHO = 20;
    LocalTime additionalTime=LocalTime.now().plusMinutes(workTime);
    LocalTime lookAwayTime =LocalTime.now().plusMinutes(timeFromWHO);
    	boolean exit=true;
	int value =0 ;
	int lookAwayTimeValue=0;
	JFrame dialog =new JFrame();
	JLabel dialogLabel = new JLabel(dialogLabelMessage);
	String threadMessage;
	//TableDemo td =new TableDemo();
    DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("HH:mm");
    static CheckboxTableModel tableModel = null;
		
	// Overriding the run method
  	@SuppressWarnings("static-access")
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
  	        {    
  	        	   addARecord("Work Time:-");//add a record using function 
  	        	 TrayIconDemo.trayIcon.setToolTip(TrayIconDemo.getMessageInfoStatus());
  	        	   captureRecord=false;
  	        }
  	     
  		value = LocalTime.now().compareTo(additionalTime);		  	           	        
  	        if(value>0 ) { // if block starts here 
  	       
  	        	if(workTimeStatus==true) // speaker will give a bell.
  	        {	beepPCSpeaker(1);
  	           
  	        }else {beepPCSpeaker(2);try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} //catch ends here 
   	        }  // if block ends here 
  	        	
  	
  	        
           // int i=blinkTime;
        		for(int j=0;j<blinkTime;j++) {  //for starts here
        
        	try {
        		    dialog.setVisible(true); 
        		     MyThread.sleep(2000);
				dialog.setVisible(false);
				MyThread.sleep(2000);
        	} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	 
          } //for block  ends here 
        		
       // 	exit =false;
               // convert status of work/breaktime set next additionaltime                 
        		 workTimeStatus= !workTimeStatus;
             breakTimeStatus=!breakTimeStatus; 
             
         //create Next time line.    
               if (workTimeStatus==true) {   // if start here
               	  additionalTime = LocalTime.now().plusMinutes(workTime);
               	  currentLocalTime=LocalTime.now();
              	  dialogLabelMessage="work Time";
                 	lookAwayTime = currentLocalTime.plusMinutes(timeFromWHO);
                 	lookAwayTimeBool=true;
           	      captureRecord=true;
           	      blinkTime=10;
               }
               if(breakTimeStatus==true) {
               additionalTime = LocalTime.now().plusMinutes(breakTime);
               currentLocalTime=LocalTime.now();
               dialogLabelMessage="Break Time - Walk"; //"work Time";
                              captureRecord=true;
               blinkTime=blinkTime/2;
               } //if block close
  		                        }// if block ends here
  		 dialogLabel.setText(dialogLabelMessage);
		 
  		 // code for 25 minutes look away time is here
  	     lookAwayTimeValue = LocalTime.now().compareTo(lookAwayTime);	
	        if( lookAwayTimeValue>0 && lookAwayTimeBool)
	        {
	       for(int j=0;j<lookAwayBlinkTime;j++)
	        	{TrayIconDemo.trayIcon.displayMessage("Look away",
                     "Look away from the screen", TrayIcon.MessageType.INFO);}
	           lookAwayTimeBool=false;
	            new TableDemo().saveRecord();
	         } //lookAwayif block ends. */
  		 
  		 try {
			sleep(10000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
  		}  //while block 
        
  
  	
    
  	
  	}//run end
  	public void addARecord(String varWorkTimeText)
    {		
  		
  		TableDemo.addRow(varWorkTimeText+Boolean.toString(workTimeStatus),LocalTime.now().format(myFormatObj),
  additionalTime.format(myFormatObj),Long.toString((additionalTime.toSecondOfDay()-currentLocalTime.toSecondOfDay())/60)
   
  				);
  
  		} 
  //function end 
  	public void updateWorkBreakTime()  // update the new time based on GUI settings change.
    {	  
  	     workTime = FileUtil.fileDBData[0];
  		 breakTime = FileUtil.fileDBData[1]; 
  			
    
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

  	//restart the parameters in thread.
   public void setRestartThreadParameters()
     {   
     	breakTimeStatus=false;
     	workTimeStatus=true;
     	additionalTime=LocalTime.now().plusMinutes(workTime);
     	currentLocalTime=LocalTime.now();
     	dialogLabelMessage="Break Time - Walk";
     	lookAwayTimeBool=true;
     	lookAwayTime = currentLocalTime.plusMinutes(timeFromWHO);
     
     	addARecord("Reset Time:-");
 	
     }

}
