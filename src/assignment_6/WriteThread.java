/*
* Assignment 6 
* Name: Rithvik Baddam
* EID: rrb2442 
*/
package assignment_6;

import java.io.*;
import java.net.*;
 

public class WriteThread extends Thread {
	private Socket socket;
    private PrintWriter printWriter;
    private MyClient client;
 
    public WriteThread(Socket socket, MyClient client) {
        this.socket = socket;
        this.client = client;
 
        try {
            OutputStream out = socket.getOutputStream();
            printWriter = new PrintWriter(out, true);
        } catch (IOException ex) {
            System.out.println("Error with out stream: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
 
    public void run() {
    	Console console = System.console();
        String userName = console.readLine( "\nEnter your name: ");
        
        while(checkIfValid(userName) == false) {
        	userName = console.readLine("\nInvalid. Enter your name without spaces: ");
        }
        
        client.setClientName(userName); // record username
        printWriter.println(userName);
        String input;
 
        do {
            input = console.readLine("[" + userName + "]: ");
            printWriter.println(input);
 
        } while (!input.equals("exit"));
 
        try {
            socket.close();
        } catch (IOException e) {
            System.out.println("Error writing: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    boolean checkIfValid(String username) {
    	if(username.contains(" "))
    		return false;
    	return true;
    }
}





