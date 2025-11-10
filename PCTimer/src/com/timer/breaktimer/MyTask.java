/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */
package com.timer.breaktimer;

import static com.timer.breaktimer.SystemTrayIcon.varMessage;
import java.time.LocalTime;
import javax.swing.JOptionPane;

/**
 *
 * @author mel
 */
public class MyTask implements Runnable {
        @Override
        public void run() {
            // Code to be executed by the thread
            System.out.println("MyTask is running in a new thread."+ SystemTrayIcon.varStopTime.toString() + LocalTime.now().toString());
            
            while (LocalTime.now().isBefore(SystemTrayIcon.varStopTime))
            {JOptionPane.showMessageDialog(null, varMessage );}
            
            
        }
    }
