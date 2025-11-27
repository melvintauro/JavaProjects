// java Program to create a slider with min and 
// max value and major and minor ticks painted.
package pctimer.com;  

import javax.swing.event.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
class GuiSettings extends JFrame implements ChangeListener, ActionListener {
 FileUtil FU = new FileUtil();
    // frame
    static JFrame f;

    // slider
    static JSlider b1;
    static JSlider b2;
    static JSlider b3;

    // label
    static JLabel l;
      Button bu;

    // main class
   // public static void main(String[] args)
    public void  slider()
    {
        // create a new frame
        f = new JFrame("frame");
        ImageIcon imgicon = new ImageIcon("images/pcworker.png");
                f.setIconImage(imgicon.getImage());

        // create a object
        GuiSettings s = new  GuiSettings();

        // create label
        l = new JLabel();

        // create a panel
        JPanel p = new JPanel();
        

        // create a slider
        b1 = new JSlider(25, 40, 40);
        b2 = new JSlider(5, 15, 15);
        b3 = new JSlider(0, 14, 12);
        bu= new Button("Save");
        
        // paint the ticks and tracks
        b1.setPaintTrack(true);
        b1.setPaintTicks(true);
        b1.setPaintLabels(true);
        b2.setPaintTrack(true);
        b2.setPaintTicks(true);
        b2.setPaintLabels(true);
        b3.setPaintTrack(true);
        b3.setPaintTicks(true);
        b3.setPaintLabels(true);



        // set spacing
        b1.setMajorTickSpacing(50);
        b1.setMinorTickSpacing(5);
        b2.setMajorTickSpacing(50);
        b2.setMinorTickSpacing(5);
        b3.setMajorTickSpacing(50);
        b3.setMinorTickSpacing(5);

        
        
        // setChangeListener
        b1.addChangeListener(s);
        b2.addChangeListener(s);
        b3.addChangeListener(s);
        // add slider to panel
        p.add(b1);
        p.add(b2);
        p.add(b3);
        p.add(l);
        p.add(bu);

        f.add(p);
        
        
        

        // set the text of label
        l.setText("value of Slider is =" + b1.getValue());
     
        // set the size of frame
        f.setSize(300, 300);

        f.show();
   
         //Add an action listener to the button
        bu.addActionListener(this); 
          
            // Override the actionPerformed() method
            
          
        
    
    
    }

    // if JSlider value is changed
    public void stateChanged(ChangeEvent e)
    {
        l.setText("value of Slider is =" + b1.getValue());
        l.setText("value of Slider is =" + b2.getValue());
        l.setText("value of Slider is =" + b3.getValue());
    }
    
    
    
    public void actionPerformed(ActionEvent e){
            	System.out.println("inside button");
           	 FU.fileWrite(Integer.toString(b1.getValue()),Integer.toString(b2.getValue()),Integer.toString(b3.getValue()));
            }
    
}