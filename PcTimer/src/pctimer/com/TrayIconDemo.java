/*
 * TrayIconDemo.java
 */
package pctimer.com;

import java.awt.*;
import java.awt.event.*;
import java.net.URL;
import java.time.format.DateTimeFormatter;
import com.formdev.flatlaf.*;
import com.formdev.flatlaf.intellijthemes.materialthemeuilite.*;
import com.formdev.flatlaf.intellijthemes.*;
import javax.swing.*;
import javax.swing.plaf.FontUIResource;

import com.formdev.flatlaf.FlatDarculaLaf;

public class TrayIconDemo {
static String threadMessage=null;
static DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("HH:mm");	
public static MyThread timerThread = new MyThread();
public static FileUtil fu1=new FileUtil();

public static TrayIcon trayIcon =new TrayIcon(createImage("images/pcworker.gif"), "tray icon"); 

public static String textTotalWorkHours =null;
public static JLabel labelTotalWorkHours =null;
public static Font allFont=null;
public static SystemTray tray=null;
 


static Object[] lfOptions = {new FlatLightLaf(),
		           new FlatMTLightOwlIJTheme(),
		 new FlatMTMonokaiProIJTheme(),
		 new FlatMTMaterialOceanicIJTheme(), "com.sun.java.swing.plaf.gtk.GTKLookAndFeel","com.sun.java.swing.plaf.motif.MotifLookAndFeel","com.sun.java.swing.plaf.windows.WindowsLookAndFeel"   };



    public static void main(String[] args)  {
        /* Use an appropriate Look and Feel */
    	   	// Creating thread
    	allFont = new Font("Verdana", Font.PLAIN, FileUtil.fileDBData[2]);
       	// Starting thread to count workbreaktiem
    	timerThread.start(); 
       //additional time display on diaglog box when double clicking
    	
       try {
		UIManager.setLookAndFeel( (LookAndFeel) lfOptions[FileUtil.fileDBData[3]-10]);
	} catch (UnsupportedLookAndFeelException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
    //	FlatMTMonokaiProIJTheme.setup();
		// 2. Adjust component styling keys via UIManager properties
		
        /* Turn off metal's use of bold fonts */
     //   UIManager.put("swing.boldMetal", Boolean.FALSE);
    	UIManager.put("Button.arc", 0);                   // Remove round corners on buttons (0 = square)
    	UIManager.put("Component.arc", 0);                // Remove round corners on inputs/spinners
    	UIManager.put("ProgressBar.arc", 4);              // Semi-round progress bars
    	UIManager.put("TitlePane.unifiedBackground", true);
        
        //Schedule a job for the event-dispatching thread:
        //adding TrayIcon.
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }
    
 private static void createAndShowGUI() {
	allFont = new Font("Verdana", Font.PLAIN, FileUtil.fileDBData[2]);
	// Get the URL of the image from the classpath

	if (!SystemTray.isSupported()) {
            System.out.println("SystemTray is not supported");
            return;
        }
        final PopupMenu popup = new PopupMenu();
        
        //set the font  
        popup.setFont(allFont);
        UIManager.put("OptionPane.font", new FontUIResource(new Font("Verdana", Font.PLAIN, FileUtil.fileDBData[2])));
                
         tray = SystemTray.getSystemTray();
                         trayIcon.setImageAutoSize(true);
        // Create a popup menu components
        MenuItem resetItem = new MenuItem("Restart");
        MenuItem aboutItem = new MenuItem("About");
        MenuItem historyItem = new MenuItem("Event History");
        MenuShortcut shortcut = new MenuShortcut(KeyEvent.VK_E);
        historyItem.setShortcut(shortcut);
      
        CheckboxMenuItem cb1 = new CheckboxMenuItem("Set auto size");
        CheckboxMenuItem cb2 = new CheckboxMenuItem("Set tooltip");
        Menu displayMenu = new Menu("Actions");
        MenuItem errorItem = new MenuItem("Settings");
        MenuItem warningItem = new MenuItem("Data");
        MenuItem infoItem = new MenuItem("Info");
        MenuItem noneItem = new MenuItem("Restart");
        MenuItem exitItem = new MenuItem("Exit");
        
        //Add components to popup menu
        popup.add(resetItem);
        popup.addSeparator();
        popup.add(historyItem);
        popup.add(aboutItem);
        popup.addSeparator();
        popup.add(cb1);
        popup.add(cb2);
        popup.addSeparator();
        popup.add(displayMenu);
        popup.add(aboutItem);
        displayMenu.add(errorItem);
        displayMenu.add(warningItem);
        displayMenu.add(infoItem);
        displayMenu.add(noneItem);
        popup.add(exitItem);
        
        trayIcon.setPopupMenu(popup);
        
     
        
        try {
            tray.add(trayIcon);
        } catch (AWTException e) {
            System.out.println("TrayIcon could not be added.");
                        
            return;
        }
        
        trayIcon.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	 SwingUtilities.invokeLater(new Runnable() {
                     public void run() {
                         //Turn off metal's use of bold fonts
         	       // UIManager.put("swing.boldMetal", Boolean.FALSE);
         	       new TableDemo().createAndShowGUI();
                     }
                 });
          
            	
            	//  	JOptionPane.showMessageDialog(null,getMessageInfoStatus()  ); //called when tray icon is double clicked
               
            }
        });
        
        aboutItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null,
                        "Project 21-NOV-2025 \n" + "https://github.com/melvintauro/JavaProjects");
               
            }
        });
        resetItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	//restart the parameters in thread.
              	timerThread.setRestartThreadParameters()   ; 
             	trayIcon.setToolTip(getMessageInfoStatus()); //reset the tool tip message
             //	trayIcon.displayMessage(getMessageInfoStatus(),""                        , TrayIcon.MessageType.NONE);
               
            }
        });
        historyItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
           
                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        //Turn off metal's use of bold fonts
        	        UIManager.put("swing.boldMetal", Boolean.FALSE);
        	        new TableDemo().createAndShowGUI();
                    }
                });
               
            }
        });
        
        
        
        // check box action event below.
        cb1.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                int cb1Id = e.getStateChange();
                if (cb1Id == ItemEvent.SELECTED){
                    trayIcon.setImageAutoSize(true);
                } else {
                    trayIcon.setImageAutoSize(false);
                    
                }
            }
        });
        
        cb2.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                int cb2Id = e.getStateChange();
                if (cb2Id == ItemEvent.SELECTED){
                    trayIcon.setToolTip(getMessageInfoStatus());
                } else {
                    trayIcon.setToolTip(null);
                }
            }
        });
        
        ActionListener listener = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                MenuItem item = (MenuItem)e.getSource();
                //TrayIcon.MessageType type = null;
                System.out.println(item.getLabel());
                if ("Settings".equals(item.getLabel())) {
                    //type = TrayIcon.MessageType.ERROR;
                    trayIcon.displayMessage("Sun TrayIcon Demo",
                            "This is an error message", TrayIcon.MessageType.ERROR);
                    
                   
                    
                } else if ("Data".equals(item.getLabel())) {
                    //type = TrayIcon.MessageType.WARNING;
                    trayIcon.displayMessage("Sun TrayIcon Demo",
                            "This is a warning message", TrayIcon.MessageType.WARNING);
                    //table frame activate here 
                    SwingUtilities.invokeLater(new Runnable() {
                        public void run() {
                            //Turn off metal's use of bold fonts
            	        UIManager.put("swing.boldMetal", Boolean.FALSE);
            	    //    ToolBarDemo.createAndShowGUI();
                        }
                    });
                    
                    
                    
                } else if ("Info".equals(item.getLabel())) {
                  
                    trayIcon.displayMessage("Sun TrayIcon Demo",
                            "This is an info message", TrayIcon.MessageType.INFO);
                    
                } else if ("Restart".equals(item.getLabel())) {
                    
                	timerThread.setRestartThreadParameters()   ; 
              
                }
            }
        };
        
        errorItem.addActionListener(listener);
        warningItem.addActionListener(listener);
        infoItem.addActionListener(listener);
        noneItem.addActionListener(listener);
        
        exitItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                tray.remove(trayIcon);
                System.exit(0);
            }
        });
    }
    
    //Obtain the image URL
    protected static Image createImage(String path) {
        URL imageURL = new TrayIconDemo().getClass().getResource(path);
        
        if (imageURL == null) {
            System.err.println("Resource not found: " + path);
            return null;
        } else {
            return (new ImageIcon(imageURL)).getImage();
        }
    }
  //Create a status Message
    protected static String getMessageInfoStatus()
    {
    	   String messageInfoStatus = new String(
    			      "\n   WorkTime:- " + Boolean.toString(new MyThread().workTimeStatus)+
    			      "\n  StartTime:- " + new MyThread().currentLocalTime.format(myFormatObj)+
                   "\n NextAlarm:- " + new MyThread().additionalTime.format(myFormatObj) +
                   "\n "+ TrayIconDemo.textTotalWorkHours
             
    			   
    			   );
    
    return messageInfoStatus;
    }

  
    

}