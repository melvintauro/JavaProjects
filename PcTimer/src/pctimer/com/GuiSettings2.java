// java Program to create a slider with min and 
// max value and major and minor ticks painted.
package pctimer.com;  

import javax.swing.event.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

import javax.swing.*;
class GuiSettings2 extends JPanel implements ChangeListener, ActionListener {
 URL imageURL = TrayIconDemo.class.getResource("images/settings.png");	
 FileUtil FU = new FileUtil(); // create File utility object.
 MyThread MT = new MyThread();
 
 
    // frame
    static JFrame f;

    // slider
    static JSlider s1;
    static JSlider s2;
    static JSlider s3;

    // label
    static JLabel l1,l2,l3;
      Button bu;


      public GuiSettings2() {
  		super (new GridLayout(0, 3));
      
  		addSliders();
  		
  	//Lay out the main panel.
	    setPreferredSize(new Dimension(450, 130));
  		
      }
      protected void addSliders() {
      
      }
      protected void  makeSliders(String imageName,
              String actionCommand,
              String toolTipText,
              String altText)
      {
    	  
    	  
      }
    public void  slider ()
    {
        // create a new frame
        f = new JFrame("frame");
        
    
        f.setIconImage(new ImageIcon(imageURL).getImage());

        // create a object
        GuiSettings2 s = new  GuiSettings2();

        // create label
        l1 = new JLabel();
        l2 = new JLabel();
        l3 = new JLabel();
        
        // create a panel
        JPanel p1 = new JPanel();
        JPanel p2 = new JPanel();
        GridLayout g1=new GridLayout(4,3);
        g1.setHgap(10);
        p1.setLayout(g1);
        GridLayout g2=new GridLayout(0, 3);
        g2.setHgap(10);
        p2.setLayout(g2);
        
 
        // create a slider
        s1 = new JSlider(25, 50,TrayIconDemo.t1.workTime);
        s2 = new JSlider(10, 20, TrayIconDemo.t1.breakTime);
        s3 = new JSlider(12, 20, FU.fileDBData[2]);
        bu= new Button("Save");
        p2.add(new Label(""));
        
        
        // paint the ticks and tracks
        s1.setPaintTrack(true);
        s1.setPaintTicks(true);
        s1.setPaintLabels(true);
        s2.setPaintTrack(true);
        s2.setPaintTicks(true);
        s2.setPaintLabels(true);
        s3.setPaintTrack(true);
        s3.setPaintTicks(true);
        s3.setPaintLabels(true);



        // set spacing
        s1.setMajorTickSpacing(50);
        s1.setMinorTickSpacing(5);
        s2.setMajorTickSpacing(50);
        s2.setMinorTickSpacing(5);
        s3.setMajorTickSpacing(50);
        s3.setMinorTickSpacing(5);

        
        
        // setChangeListener
        s1.addChangeListener(s);
        s2.addChangeListener(s);
        s3.addChangeListener(s);
        
        // add slider to panel
       // p1.add(new Label(" "));
        p1.add(l1,BorderLayout.CENTER);
        p1.add(s1,BorderLayout.CENTER);
        
        p1.add(l2,BorderLayout.CENTER);
        p1.add(s2,BorderLayout.CENTER);
        
        p1.add(l3,BorderLayout.CENTER);
        p1.add(s3,BorderLayout.CENTER);
        
        p2.add(new Label(""),BorderLayout.CENTER);
        p2.add(bu,BorderLayout.CENTER);
        p2.add(new Label(""),BorderLayout.CENTER);
        p1.add(p2);
        
        f.add(p1,BorderLayout.CENTER);
        
        
        
        

        // set the text of label
        l1.setText("Set Work Time =" + s1.getValue());
        l2.setText("Set Break Time =" + s2.getValue());
        l3.setText("Set Font =" + s3.getValue());
        // set the size of frame
        f.setSize(300, 300);
        f.setTitle("Settings");
        f.setLocation(500,220);

        f.show();
   
         //Add an action listener to the button
        bu.addActionListener(this); 
          
            // Override the actionPerformed() method
            
          
        
    
    
    }

    // if JSlider value is changed
    public void stateChanged(ChangeEvent e)
    {
        l1.setText("Set Work Time =" + s1.getValue());
        l2.setText("Set Break Time =" + s2.getValue());
        l3.setText("Set Font  =" + s3.getValue());
    }
    
    
    
    public void actionPerformed(ActionEvent e){
            	System.out.println("inside button");
           	 FU.fileWrite(Integer.toString(s1.getValue()),Integer.toString(s2.getValue()),Integer.toString(s3.getValue()));
             
           	 TrayIconDemo.t1.workTime = s1.getValue();
           	TrayIconDemo.t1.breakTime = s2.getValue();
           			 }
    
}