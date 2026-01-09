

package pctimer.com;  
/*
 * TrayIconDemo.java
 */

import java.awt.*;
import java.awt.event.*;
import java.net.URL;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;

public class TrayIconDemo {
static String threadMessage;
static DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("HH:mm");	
public static MyThread t1 = new MyThread();
public static FileUtil fu1=new FileUtil();
public static TrayIcon trayIcon =
new TrayIcon(createImage("images/pcworker.png", "tray icon"));
	
    public static void main(String[] args) {
        /* Use an appropriate Look and Feel */
    	   	// Creating thread
      
       	// Starting thread to count workbreaktiem
        t1.start(); 
        
        //additional time display on diaglog box when double clicking
      
      
      	
        try {
            //UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
            UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
        } catch (UnsupportedLookAndFeelException ex) {
            ex.printStackTrace();
        } catch (IllegalAccessException ex) {
            ex.printStackTrace();
        } catch (InstantiationException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        /* Turn off metal's use of bold fonts */
        UIManager.put("swing.boldMetal", Boolean.FALSE);
        //Schedule a job for the event-dispatching thread:
        //adding TrayIcon.
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }
    
 private static void createAndShowGUI() {
   
    	GuiSettings Gs= new GuiSettings();
        //Check the SystemTray support
        if (!SystemTray.isSupported()) {
            System.out.println("SystemTray is not supported");
            return;
        }
        final PopupMenu popup = new PopupMenu();
        
        //set the font  
        popup.setFont(new Font("Verdana", Font.PLAIN, 12));
        UIManager.put("OptionPane.font", new FontUIResource(new Font("Verdana", Font.PLAIN, 12)));
                
        final SystemTray tray = SystemTray.getSystemTray();
                         trayIcon.setImageAutoSize(true);
        // Create a popup menu components
        MenuItem resetItem = new MenuItem("Restart");
        MenuItem aboutItem = new MenuItem("About");
        MenuItem historyItem = new MenuItem("Event History");
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
              
            	JOptionPane.showMessageDialog(null,
                		getMessageInfoStatus()  //called when tray icon is double clicked
                );
            }
        });
        
        aboutItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null,
                        "Project Melvin Tauro 21-NOV-2025");
               
            }
        });
        resetItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	//restart the parameters in thread.
              	setRestartThreadParameters()   ; 
             	trayIcon.setToolTip(getMessageInfoStatus()); //reset the tool tip message
             //	trayIcon.displayMessage(getMessageInfoStatus(),""                        , TrayIcon.MessageType.NONE);
               
            }
        });
        historyItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            //	t1.td.setVisible(true);
            	 //table frame activate here 
                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        //Turn off metal's use of bold fonts
        	        UIManager.put("swing.boldMetal", Boolean.FALSE);
        	        TableDemo.createAndShowGUI();
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
                    Gs.slider();  // call the slider GUI 
                   
                    
                } else if ("Data".equals(item.getLabel())) {
                    //type = TrayIcon.MessageType.WARNING;
                    trayIcon.displayMessage("Sun TrayIcon Demo",
                            "This is a warning message", TrayIcon.MessageType.WARNING);
                    //table frame activate here 
                    SwingUtilities.invokeLater(new Runnable() {
                        public void run() {
                            //Turn off metal's use of bold fonts
            	        UIManager.put("swing.boldMetal", Boolean.FALSE);
            	        ToolBarDemo.createAndShowGUI();
                        }
                    });
                    
                    
                    
                } else if ("Info".equals(item.getLabel())) {
                    //type = TrayIcon.MessageType.INFO;
                    trayIcon.displayMessage("Sun TrayIcon Demo",
                            "This is an info message", TrayIcon.MessageType.INFO);
                    
                } else if ("Restart".equals(item.getLabel())) {
                    //type = TrayIcon.MessageType.NONE;
                	//restart the parameters in thread.
                	setRestartThreadParameters()   ; 
              //  	trayIcon.setToolTip(getMessageInfoStatus()); //reset the tool tip message
               // 	trayIcon.displayMessage(getMessageInfoStatus(),"" , TrayIcon.MessageType.NONE);
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
    protected static Image createImage(String path, String description) {
        URL imageURL = TrayIconDemo.class.getResource(path);
        
        if (imageURL == null) {
            System.err.println("Resource not found: " + path);
            return null;
        } else {
            return (new ImageIcon(imageURL, description)).getImage();
        }
    }
  //Create a status Message
    protected static String getMessageInfoStatus()
    {
    	   String messageInfoStatus = new String(
    			      "\n   WorkTime:- " + Boolean.toString(t1.workTimeStatus)+
    			      "\n  StartTime:- " + t1.currentLocalTime.format(myFormatObj)+
                   "\nNext Alarm:- " + t1.additionalTime.format(myFormatObj)	   
    			   
    			   );
    
    return messageInfoStatus;
    }

  //restart the parameters in thread.
    protected static void setRestartThreadParameters()
    {   
      	t1.breakTimeStatus=false;
        t1.workTimeStatus=true;
        	t1.additionalTime=LocalTime.now().plusMinutes(t1.workTime);
        	t1.currentLocalTime=LocalTime.now();
        
        	 System.out.println(t1.workTime+" setRestartThreadParameters " + t1.breakTime +" " +t1.additionalTime );
  
	t1.addARecord("Reset Time:-");
	
    }
    

}