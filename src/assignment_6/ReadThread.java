/*
* Assignment 6 
* Name: Rithvik Baddam
* EID: rrb2442 
*/
package assignment_6;

import java.io.*;
import java.net.*;

public class ReadThread extends Thread{
	 private Socket socket;
	 private MyClient client;
	 private BufferedReader reader;
	 private boolean exit;
	 
	 public ReadThread(Socket socket, MyClient client) {
	        this.socket = socket;
	        this.client = client;
	        exit = false;
	        try {
	            InputStream in = socket.getInputStream();
	            reader = new BufferedReader(new InputStreamReader(in));
	        } catch (IOException ex) {
	            System.out.println("Error getting input stream: ");
	        }
	    }
	 
	 public void run() {
		 while (true) {
			 try {
				 String input = reader.readLine();
				 
				 // displays message and client name
	             System.out.println("\n" + input);
	             if (client.getClientName() != null) {
	                 System.out.print("[" + client.getClientName() + "]: ");
	             }
	         } catch (IOException ex) {
	             System.out.println("Error reading from server: ");
	             break;
	         }
	     }
	 }
	 
	 public void pauseIndefinitely() {
		 exit = true;
	 }
}
