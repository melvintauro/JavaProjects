package com.timer.breaktimer;


import java.awt.*;
import java.awt.event.*;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import java.time.*;
import java.time.format.DateTimeFormatter;

public class SystemTrayIcon {
      public static  LocalTime varStartTime = LocalTime.now();
      public static  LocalTime varStopTime = LocalTime.now().plusMinutes(1);
      public static  LocalTime varBreakTime = LocalTime.now().plusMinutes(2);
      public static String varMessage = new String();             

  
      
    public static void main(String[] args) {
        // Ensure the code runs on the Event Dispatch Thread (EDT)
        SwingUtilities.invokeLater(() -> {
            createAndShowGUI();
        });
    
    MyTask task = new MyTask();
   Thread thread = new Thread(task);
     thread.start();
    
    }

    private static void createAndShowGUI() {
        
       
        // Check if SystemTray is supported on the current platform
        if (!SystemTray.isSupported()) {
            System.out.println("System tray is not supported!");
            return;
        }

        final PopupMenu popup = new PopupMenu();
        // Load an image for the tray icon (replace with your own image path)
        // Ensure the path is correct, e.g., "images/icon.png" if in a package
        Image image = Toolkit.getDefaultToolkit().getImage("path/to/your/icon.png"); 
        
        // You can use the SystemTray.getSystemTray().getTrayIconSize() to get the optimal size and scale the image manually
        // e.g., Image scaledImage = image.getScaledInstance(tray.getTrayIconSize().width, tray.getTrayIconSize().height, Image.SCALE_SMOOTH);

        final TrayIcon trayIcon = new TrayIcon(image, "SystemTray Demo Application");
                                    

                //trayIcon.setToolTip("hi there " + LocalTime.now().toString()); //commented now can be removed and added later
            varMessage =  "start: "+ varStartTime.format(DateTimeFormatter.ofPattern("HH:mm")) +" break: " + varStopTime.format(DateTimeFormatter.ofPattern("HH:mm"))
                        +" start "+ varBreakTime.format(DateTimeFormatter.ofPattern("HH:mm")) ;
                     // + varStartTime.toString()+"stop: " + varStopTime.toString() + varBreakTime.toString()";
               
        final SystemTray tray = SystemTray.getSystemTray();

        // Create a menu item for the "Action"
        MenuItem actionItem = new MenuItem("Perform Action");
         
        actionItem.addActionListener((ActionEvent e) -> {
            JOptionPane.showMessageDialog(null, "Action performed!");
        });
        popup.add(actionItem);

        // Add a separator
        popup.addSeparator();

        // Create a menu item for the "Exit" action
        MenuItem exitItem = new MenuItem("Exit");
        exitItem.addActionListener((ActionEvent e) -> {
            tray.remove(trayIcon); // Remove the icon before exiting
            System.exit(0);
        });
        popup.add(exitItem);

        // Set the popup menu for the tray icon
        trayIcon.setPopupMenu(popup);

        // Add an action listener for the default action (e.g., double-click)
        trayIcon.addActionListener((ActionEvent e) -> {
         // need to comple this code    
            
                
                
                JOptionPane.showMessageDialog(null, varMessage);
        });

        // Add the tray icon to the system tray
        try {
              
            tray.add(trayIcon);
        } catch (AWTException e) {
            System.err.println("TrayIcon could not be added.");
        }
    
      
    
    }
 

}
