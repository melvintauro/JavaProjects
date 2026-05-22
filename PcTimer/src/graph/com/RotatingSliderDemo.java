package graph.com;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Hashtable;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import pctimer.com.CheckboxTableModel;
import pctimer.com.TrayIconDemo;

import java.awt.event.MouseEvent;

import java.awt.event.MouseMotionListener;

@SuppressWarnings("serial")
public class RotatingSliderDemo extends JPanel {
    private BufferedImage image;
    private JSlider slider;
    private int degrees = 0;
    static private ImagePanel imagePanel;
    private double lastAngle = 0;
    public BarLineChart bLC; 
   private int intLargeTicks=5;
   private int jump =11; 
    Hashtable<Integer, JLabel> labelTable = new Hashtable<>();
    
    
    // Calculate center point for rotation
    
	//Path path1 = Paths.get(location+"/config/");
	
	
    
    public RotatingSliderDemo(BufferedImage img) {
        this.image = img;
        imagePanel = new ImagePanel();
        bLC = new BarLineChart();
        slider = new JSlider(JSlider.HORIZONTAL,jump,bLC.varNestedList,bLC.varNestedList );

        slider.setMajorTickSpacing(intLargeTicks);
        slider.setMinorTickSpacing(1);
        slider.setPaintTicks(true);
        // 2. Set the labels and paint them
        slider.setLabelTable(sliderLabel());
        slider.setPaintLabels(true);
        slider.addChangeListener(new SliderListener());
     
        
        
        
        setLayout(new BorderLayout());
        add(bLC, BorderLayout.CENTER);
       // add(imagePanel, BorderLayout.PAGE_END);
        add(slider, BorderLayout.PAGE_END);
    }

    
    private Hashtable<Integer, JLabel> sliderLabel() {
		
    	for(int i=jump  ;i<bLC.varNestedList-1;i=i + intLargeTicks){
    		 labelTable.put(i, new JLabel(bLC.nestedList.get(0).get(i).substring(0, 5)));
             
           
    		}
    	
    	   return labelTable;
   }
    
    private class SliderListener implements ChangeListener {
        @Override
        public void stateChanged(ChangeEvent e) {
           // degrees = slider.getValue();
           // imagePanel.repaint(); // Repaint the panel to reflect the new rotation
           bLC.varNestedList = slider.getValue();
           bLC.changeBarParameters();
           bLC.repaint();
           
           
        }
    
    
    }
    private class KnobMove implements MouseMotionListener {

		@Override
		public void mouseDragged(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}
// code for Mouse movement listener
		@Override
		public void mouseMoved(MouseEvent e) {
			 // Calculate current angle relative to center
	        double currentAngle = Math.atan2(e.getY() - imagePanel.anchorX, e.getX() - imagePanel.anchorY);
	        
	        // Calculate difference and handle the wrap-around (PI to -PI)
	        double delta = currentAngle - lastAngle;
	        if (delta > Math.PI) delta -= 2 * Math.PI;
	        if (delta < -Math.PI) delta += 2 * Math.PI;

	        if (delta > 0) {
	            System.out.println("Clockwise");
	            degrees= degrees+1;
	        } else if (delta < 0) {
	            System.out.println("Counter");
	            degrees= degrees-1;
	        }

	        lastAngle = currentAngle;
	       
	        imagePanel.repaint(); // Repaint the panel to reflect the new rotation
		}
       
    }
    // code for creating the Image .
    
    private class ImagePanel extends JPanel {
    	   double anchorX =0;
    	   double anchorY=0;
    	@Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g.create(); // Create a copy of the Graphics object

            // Apply anti-aliasing for smoother rotation
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
           
             anchorX = getWidth() / 2.0;
            anchorY = getHeight() / 2.0;
            System.out.println(anchorX);
            System.out.println(anchorY);    
            // Create and apply the rotation transform
            double theta = Math.toRadians(degrees);
            AffineTransform af = AffineTransform.getRotateInstance(theta, anchorX, anchorY);
            g2.setTransform(af);

            // Draw the image centered within the panel
            int x = (int) (anchorX - image.getWidth() / 2.0);
            int y = (int) (anchorY - image.getHeight() / 2.0);
            g2.drawImage(image, x, y, null);
            imagePanel.addMouseMotionListener(new KnobMove());
            g2.dispose(); // Dispose of the copy
        }
  
     
        
        @Override
        public Dimension getPreferredSize() {
            return new Dimension(40, 100);
        }
    }
    
  //coustom 
    public void createAndShowGUI() {
    	  
	     //Create and set up the window.
    JFrame  frame = new JFrame("TableDemo");
     frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); //DISPOSE_ON_CLOSE);DO_NOTHING_ON_CLOSE);
    
        
     //Add content to the window.
     frame.add(new RotatingSliderDemo(image));
     try {
		//frame.setIconImage(new ImageIcon(imageURL).getImage());
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
     frame.setTitle("BarChart");
     frame.setFont(TrayIconDemo.allFont);
     frame.setLocation(350,100);
         //Display the window.
     frame.pack();
     frame.setVisible(true);
   
 
 }

    
    
    
    
// main call 
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                	 // Create a File object with the image path
                	String location = System.getProperty("user.dir"); 
                	Path path = Paths.get(location+"/src/pctimer/com/images/rotatingKnob.jpg");
                	 File imageFile = new File(path.toString());
                	 BufferedImage bi = null;// new BufferedImage(100, 100, BufferedImage.TYPE_INT_ARGB);  
                	 // Read the file into a BufferedImage
                     bi = ImageIO.read(imageFile);
                	
                    // Load a sample image (replace with your image path)
                 //  bi = new BufferedImage(100, 100, BufferedImage.TYPE_INT_ARGB);
                    Graphics2D g2d = bi.createGraphics();
                   // g2d.setColor(Color.BLACK);
                   // g2d.fillRect(0, 0, 100, 100);
                   // g2d.fillOval(0, 0, 50, 50);
              
                  //  g2d.setColor(Color.WHITE);
                    

                    JFrame f = new JFrame("Rotating Slider in Java Graphics");
                    f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    f.add(new RotatingSliderDemo(bi));
                    f.pack();
                    f.setLocationRelativeTo(null);
                    f.setVisible(true);
                    g2d.dispose();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
