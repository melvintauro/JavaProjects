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
 private static final ChangeEvent ChangeEvent = null;
 static URL imageURL = TrayIconDemo.class.getResource("images/setting.gif");	
 FileUtil FU = new FileUtil(); // create File utility object.
 MyThread MT = new MyThread();
 static JComboBox<String> cb;

 String[] cbOptions ={"WindowsLookAndFeel","MetalLookAndFeel","GTKLookAndFeel","MotifLookAndFeel"};
   

    // slider
    static JSlider s1,s2,s3;
    

    // label
    static JLabel l1,l2,l3,l4;
      Button bu;


      public GuiSettings() {
    	  super (new BorderLayout());
      
  		createSlidersGUI();
  		
  	//Lay out the main panel.
	    setPreferredSize(new Dimension(350, 300));
	    setLocation(500,220);
	    
      }
     
     
    public void  createSlidersGUI ()
    {  JTabbedPane tp = new JTabbedPane();
    
    	    JPanel jp1 = new JPanel();
    	      jp1.setLayout(new GridLayout(3,2));
    	    JPanel jp2 = new JPanel();
    	    JPanel jp3 = new JPanel();
    	    jp3.setLayout(new FlowLayout());
    	    
    	    // Create a JComboBox with the String array
            cb = new JComboBox<>(cbOptions);
           
        
    	   s1 =createSliders(25,50,TrayIconDemo.t1.workTime,true,true,true,5,1);
    	   s2 =createSliders(10,20,TrayIconDemo.t1.breakTime,true,true,true,2,1);
    	   s3 =createSliders(10,20,FileUtil.fileDBData[2],true,true,true,2,1);
    	   
    		// create label
           l1 = JLabelCreateLabel("  Set Work  Time=" + s1.getValue());
           l2 = JLabelCreateLabel("  Set Break Time=" + s2.getValue());
           l3 = JLabelCreateLabel("       Set  Font=" + s3.getValue());
           l4= JLabelCreateLabel("Select Look & Feel");
    	   
       
       jp1.add(l1);
       jp1.add( s1);
       jp1.add(l2);
       jp1.add( s2);
       jp1.add(l3); 
       jp1.add( s3);
        bu= new Button("Save");
        bu.setFont(TrayIconDemo.allFont);
       //Add an action listener to the button
       bu.addActionListener(this);  
       cb.addActionListener(this);
       add(new Label(""),BorderLayout.BEFORE_FIRST_LINE);      
       tp.addTab("Timer&Font",jp1);
       tp.addTab("L&F", jp3);
       add(tp,BorderLayout.CENTER);
       
       jp3.add(l4);
       jp3.add(cb);
       
       jp2.add(bu);
       add(jp2,BorderLayout.PAGE_END);
    
    }

    public JLabel JLabelCreateLabel(String s)
    {  JLabel jl = new JLabel();
       jl.setFont(TrayIconDemo.allFont);
       jl.setText(s);
    	 
    	
    	   return jl;
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
        js.setFont(TrayIconDemo.allFont);
  
           
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
          
           	 FU.fileWrite(Integer.toString(s1.getValue()),Integer.toString(s2.getValue()),Integer.toString(s3.getValue()),Integer.toString(cb.getSelectedIndex()+10));
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
     frame.setFont(TrayIconDemo.allFont);
     frame.setLocation(450,300);
  
     //Display the window.
     frame.pack();
     frame.setVisible(true);
   
 
 }









}