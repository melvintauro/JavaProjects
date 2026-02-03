// java Program to create a slider with min and 
// max value and major and minor ticks painted.
package pctimer.com;  

import javax.swing.event.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

import javax.swing.*;
class GuiSettings extends JPanel implements ChangeListener, ActionListener {
 static URL imageURL = TrayIconDemo.class.getResource("images/setting.gif");	
 FileUtil FU = new FileUtil(); // create File utility object.
 MyThread MT = new MyThread();
 
 
   

    // slider
    static JSlider s1;
    static JSlider s2;
    static JSlider s3;

    // label
    static JLabel l1,l2,l3;
      Button bu;


      public GuiSettings() {
    	  super (new BorderLayout());
      
  		createSlidersGUI();
  		
  	//Lay out the main panel.
	    setPreferredSize(new Dimension(350, 200));
	    setLocation(500,220);
	    
      }
     
     
    public void  createSlidersGUI ()
    {  
    	    JPanel jp1 = new JPanel();
    	      jp1.setLayout(new GridLayout(3,3));
    	    JPanel jp2 = new JPanel();
    	  //  jp2.setLayout(new FlowLayout());
    	    
    	    
    	// create label
        l1 = new JLabel();
        l2 = new JLabel();
        l3 = new JLabel();
        
        
    	   s1 =createSliders(25,50,TrayIconDemo.t1.workTime,true,true,true,5,1);
    	   s2 =createSliders(10,20,TrayIconDemo.t1.breakTime,true,true,true,2,1);
    	   s3 =createSliders(12,20,FU.fileDBData[2],true,true,true,2,1);
    	   
    	       l1.setText("  Set Work  Time=" + s1.getValue());
           l2.setText("  Set Break Time=" + s2.getValue());
           l3.setText("       Set  Font=" + s3.getValue());
           
    	   
    	   
       
       jp1.add(l1);
       jp1.add( s1);
       jp1.add(l2);
       jp1.add( s2);
       jp1.add(l3); 
       jp1.add( s3);
        bu= new Button("Save");
       //Add an action listener to the button
       bu.addActionListener(this);  
      
       add(new Label(""),BorderLayout.BEFORE_FIRST_LINE);      
       add(jp1,BorderLayout.CENTER);
       jp2.add(bu);
       add(jp2,BorderLayout.PAGE_END);
    
    }

   public JSlider createSliders(int start, int end, int currentValue,Boolean tracks,Boolean tricks,Boolean labels
		   ,int majorTick,int minorTick )
    {
	    JSlider js = new JSlider(start,end,currentValue);	
	    js.setPaintTrack(tracks);
        js.setPaintTicks(tricks);
        js.setPaintLabels(labels);
        // set spacing
        js.setMajorTickSpacing(majorTick);
        js.setMinorTickSpacing(minorTick);
        
        js.addChangeListener(this);
  
           
      return js ;
    }
    
    
    
    // if JSlider value is changed
    public void stateChanged(ChangeEvent e)
    {
      	l1.setText("  Set Work  Time=" + s1.getValue());
        l2.setText("  Set Break Time=" + s2.getValue());
        l3.setText("       Set  Font=" + s3.getValue());
    }
    
    
    
    public void actionPerformed(ActionEvent e){
            	System.out.println("inside button");
           	 FU.fileWrite(Integer.toString(s1.getValue()),Integer.toString(s2.getValue()),Integer.toString(s3.getValue()));
             
           	 TrayIconDemo.t1.workTime = s1.getValue();
           	TrayIconDemo.t1.breakTime = s2.getValue();
           			 }
    

    static void createAndShowGUI() {
  	  
  	  
	     //Create and set up the window.
    JFrame  frame = new JFrame("Settings");
     frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
      
        
     //Add content to the window.
     frame.add(new GuiSettings());
     try {
		frame.setIconImage(new ImageIcon(imageURL).getImage());
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
     frame.setTitle("Settings");
     frame.setFont(new Font("Verdana", Font.PLAIN, 18));
     frame.setLocation(450,300);
  
     //Display the window.
     frame.pack();
     frame.setVisible(true);
   
 
 }









}