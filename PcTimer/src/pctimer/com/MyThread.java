// Extending Thread in Class
package pctimer.com; 
import java.net.URL;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.Timer;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.awt.GridBagLayout;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.PointerInfo;

// Class inheriting Thread class
class MyThread extends Thread 
{    
	FileUtil fu=new FileUtil(); // create the file util object.
	URL imageURL = TrayIconDemo.class.getResource("images/bulb.gif");
	LocalTime currentLocalTime = LocalTime.now();
    int workTime =FileUtil.fileDBData[0];
	int breakTime=FileUtil.fileDBData[1];  
	int blinkTime = 10;
	int lookAwayBlinkTime=1;
	int sleepTime=30000;
	int  sleepCounter=0;
	int sleepCounterTime=120;
	String dialogLabelMessage= "Break Time ";
	boolean workTimeStatus = true;
	boolean breakTimeStatus = false;
	boolean captureRecord=true;
	boolean historyVisible = false;
	boolean lookAwayTimeBool =true;
	boolean PCNotSleepMode= true;
	boolean  userStatus=true; //  if user is active and using the pc
	boolean selectTableColumn=true;  // if table column is selected
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
    //user check using mouse
    PointerInfo pointerInfo =null;
    Point mouseCoordinates = null;
    public int oldMouseCoordinates= 1;
	private boolean blinkStatus;
		
	// Overriding the run method
  	@SuppressWarnings("static-access")
		

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

  			try {
  			// code checking  if user is working on pc
  	  			userWorkingCheck() ;  // check if user is around  	  	
  	  			// code checking  if the PC is in sleep mode 
				pcSleepModeCheck();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
				System.out.println(e.toString());
			} //PC Sleep mode Check

  			
  			//code for checking if work & break time is below
  			       if(captureRecord==true && userStatus)
  	        {    
  	        	   addARecord("Work Time:-", selectTableColumn );//add a record using function 
  	        	  TrayIconDemo.trayIcon.setToolTip(TrayIconDemo.getMessageInfoStatus());
  	        	   captureRecord=false;
  	        	   selectTableColumn=false;
  	        }
  	     
  		value = LocalTime.now().compareTo(additionalTime);		  	           	        
  	        if(value>0 && userStatus) { // if block starts here 
  	      
  	       	try {
		  	        if(workTimeStatus==true && blinkStatus ){// speaker will give a bell.
		  	       
					  	         	beepPCSpeaker(2);
					  	         	blink();
					  	         	
					 }else {
					  	        	beepPCSpeaker(3);
					  	        	blink();
									
								
		   	          }  // if block ends here 
  	        } catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			  } //catch ends here 
  	
  	  
        		
       
               // convert status of work/breaktime set next additionaltime                 
        		 workTimeStatus= !workTimeStatus;
                 breakTimeStatus=!breakTimeStatus; 
             
				         //create Next time line.    
				               if (workTimeStatus==true) {   // if start here
				               	  additionalTime = LocalTime.now().plusMinutes(workTime);
				               	  currentLocalTime=LocalTime.now();
				              	  dialogLabelMessage="Break Time - Walk";
				                  lookAwayTime = currentLocalTime.plusMinutes(timeFromWHO);
				                  lookAwayTimeBool=true;
				                  selectTableColumn=true;
				           	      captureRecord=true;
				           	      blinkTime=10;
				           	      blinkStatus=true;
				               }
               if(breakTimeStatus==true) {
               additionalTime = LocalTime.now().plusMinutes(breakTime);
               currentLocalTime=LocalTime.now();
               dialogLabelMessage="Start- work Time"; //"work Time";
               captureRecord=true;
               blinkTime=blinkTime/2;
               blinkStatus=true;
               } //if block close
  		  }// if block or check start and end time  ends here
  		 dialogLabel.setText(dialogLabelMessage);
  		 
  		 
		 
  		 // code for 25 minutes look away time is here
  	     lookAwayTimeValue = LocalTime.now().compareTo(lookAwayTime);	
	        if( lookAwayTimeValue>0 && lookAwayTimeBool) {
	        
	               for(int j=0;j<lookAwayBlinkTime;j++) {
	              	TrayIconDemo.trayIcon.displayMessage("Look away",
                     "Look away from the screen", TrayIcon.MessageType.INFO);}
	               lookAwayTimeBool=false;
	               new TableDemo().saveRecord();
	               } 
  		 
  
  		     }  //ode for 25 minutes look away time if ends here
        
  
  	
    
  	
  	}//run end
		  	private void blink() throws InterruptedException {
				// TODO Auto-generated method stub
		  	    // blink for loop is here          
				 	   for(int j=0;j<blinkTime;j++) {  //for starts here
		        		    dialog.setVisible(true); 
    	        		     MyThread.sleep(2000);
	    					dialog.setVisible(false);
		       				MyThread.sleep(2000);
				       } //for block  ends here 
		   }
	private void pcSleepModeCheck() throws InterruptedException {
	  	// code checking  if the PC is in sleep mode 
		long before = System.currentTimeMillis();
					sleep(sleepTime);
		  		 	long after = System.currentTimeMillis();
			 if (after - before > sleepTime+1000) { // If gap is > 30+1=31 seconds
			     sleepCounter++;
			   if (sleepCounter>3) {
			     	  setRestartThreadParameters();
					  beepPCSpeaker(2);
				   }   
	        }// pc in sleep mode code ends here 
	}
	// add record code here 
	public void addARecord(String varWorkTimeText,Boolean select)
    {	  		
  		TableDemo.addRow(varWorkTimeText+Boolean.toString(workTimeStatus),LocalTime.now().format(myFormatObj),
        additionalTime.format(myFormatObj),Long.toString((additionalTime.toSecondOfDay()-currentLocalTime.toSecondOfDay())/60),select
   			);
  
  		} 
  //function end 
  	public void updateWorkBreakTime()  // update the new time based on GUI settings change.
    {	  
  	     workTime = FileUtil.fileDBData[0];
  		 breakTime = FileUtil.fileDBData[1]; 
      } 
  	
  	public void beepPCSpeaker(int j ) throws InterruptedException
  	{
  		for(int i=0;i<j;i++)
  		Toolkit.getDefaultToolkit().beep();//beep from pc speaker
  		Thread.sleep(1000);
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
     
     	addARecord("Reset Time:-",true);
 	
     }
	 // user working check by checking  mouse location check 
		    public void  userWorkingCheck() throws NullPointerException {
	           		    // Start the real-time clock   
		            	// Get general container info for the mouse pointer
		            	pointerInfo = MouseInfo.getPointerInfo();
		    		     // Extract the specific point containing X and Y coordinates
		    	    	 mouseCoordinates = pointerInfo.getLocation();
		        
		    	    	 if (mouseCoordinates.x == oldMouseCoordinates) {
		    	    	   	 	  sleepCounter++;
		    	    		    	    	 	  
		    	    	   }else {
		    	    		          sleepCounter=0; 
		    	    	              if(!userStatus) {setRestartThreadParameters();}
		    	    	              userStatus=true;
		    	    	             if(breakTimeStatus&& TableDemo.table.getModel().getValueAt(TableDemo.table.getModel().getRowCount()-1,4).toString().equals("false")) {TableDemo.table.getModel().setValueAt(true,TableDemo.table.getModel().getRowCount()-1,4);}  
		    	    	   
		    	    	   }
		    	    	  if (sleepCounter >10 && userStatus ){  
		    	    		  System.out.println("inside time mod  "+ TableDemo.table.getModel().getRowCount());
		    	    		  blinkStatus=false;
		    	    		  userStatus=false; 
		    	    		      if(breakTimeStatus || workTimeStatus) {
		    	    		    	     LocalTime newCurrentLocalTime= LocalTime.now();
		    	    		    	      TableDemo.table.getModel().setValueAt(newCurrentLocalTime.format(myFormatObj),TableDemo.table.getModel().getRowCount()-1,2);
										 TableDemo.table.getModel().setValueAt(Long.toString(Math.max((newCurrentLocalTime.toSecondOfDay()-currentLocalTime.toSecondOfDay()-300),1)/60),TableDemo.table.getModel().getRowCount()-1,3);
										 TableDemo.table.getModel().setValueAt(true,TableDemo.table.getModel().getRowCount()-1,4);
		    	    		     
		    	    		      }
		    	    		  
		    	    		  
		    	    	  } 
		    	            	 oldMouseCoordinates= mouseCoordinates.x;

		    }
		    
}            
