/*
* Assignment 6 
* Name: Rithvik Baddam
* EID: rrb2442 
*/

package assignment_6;

import java.io.*;
import java.net.*;


// chat client program
// type "exit" to end
public class MyClient {
	private String userName;
	
	public MyClient() {

	}
	
	void setClientName(String userName) {
		this.userName = userName;
	}
	 
	String getClientName() {
		return this.userName;
	}
	
	public static void main(String[] args) {
		MyClient client = new MyClient();
		client.run();
	}
	
	public void run() {
		try{	
			Socket s=new Socket("localhost",6666);
			System.out.println("Connected to the server");
			new ReadThread(s, this).start();
            new WriteThread(s, this).start();

		}catch (UnknownHostException ex) {
            System.out.println("Server not found: " + ex.getMessage());
        } catch (IOException ex) {
            System.out.println("I/O Error: " + ex.getMessage());
        }
	}
	
}
