/*
* Assignment 6 
* Name: Rithvik Baddam
* EID: rrb2442 
*/
package assignment_6;

import java.io.*;
import java.net.*;

public class ClientThread extends Thread{
	private Socket s;
	private PrintWriter printWriter;
	//private MyServer server;
	
	 public ClientThread(Socket socket) {
	        this.s = socket;
	        //this.server = server;
	 }
	 
	 // run client thread
	 // to send a private message syntax: "/pm username" followed by the message
	 // exits is client message says exit
	 public void run() {
		 try {
			 InputStream in = s.getInputStream();
	         BufferedReader reader = new BufferedReader(new InputStreamReader(in));
	            
	         OutputStream out = s.getOutputStream();
	         printWriter = new PrintWriter(out, true);
	 
	         printClients(); // print all clients
	 
	         String userName = reader.readLine();
	         MyServer.addClient(userName);
	 
	         String serverMessage = "New client connected: " + userName;
	         MyServer.send(serverMessage, this);
	 
	         String clientMessage;
	 
	         do {
	        	 clientMessage = reader.readLine();
	             serverMessage = "[" + userName + "]: " + clientMessage;
	             
	             String[] messageArray = clientMessage.split(" ");
	             
	             if(checkIfPrivateMessage(clientMessage) && checkIfValidUsername(messageArray[1])) {
	            	 serverMessage = makePrivateMessage(messageArray, userName);
	            	 ClientThread receiver = MyServer.getClientThread(messageArray[1]);
	            	 MyServer.sendToClient(serverMessage, receiver);
	             }
	             else
	            	 MyServer.send(serverMessage, this);
	 
	         } while (!clientMessage.equals("exit"));
	 
	         MyServer.deleteClient(userName, this);
	         s.close();
	 
	         serverMessage = userName + " has exited.";
	         MyServer.send(serverMessage, this);	
	         
		 }catch (IOException e) {
			 System.out.println("Error in UserThread: " + e.getMessage());
	         e.printStackTrace();
		 }
	 }
	 
	 
	 // ignores the first two elements in array and adds in the username tag for private message
	 String makePrivateMessage(String[] messageArray, String userName) {
		 String clientMessage = "[" + userName + "]: ";
		 
		 for(int i = 2; i < messageArray.length; i++) {
			clientMessage += messageArray[i];
			clientMessage += " ";
		 }
		 
		 return clientMessage;
	 }
	 
	 // check if pm message or not
	 boolean checkIfPrivateMessage(String clientMessage) {
		 String[] message = clientMessage.split(" ");
		 if(message[0].equals("/pm")) // if first argument if /pm it's a private message to a user otherwise it is broadcasted to all 
			 return true;
		 
		 return false;
	 }
	 
	 // check if a username is valid
	 boolean checkIfValidUsername(String userName) {
		 if(MyServer.getClientNames().contains(userName))
			 return true;
		 
		 return false;
	 }
	 
	 // sends message to client
	 void sendMessage(String s) {
		 printWriter.println(s);
	 }
	 
	 /*
	  * Prints list of online users
	  */
	 void printClients() {
		 if(MyServer.containsClients()) {
			 printWriter.println("Connected users: " + MyServer.getClientNames());

		 }
		 else
			 printWriter.println("No other clients connected");
	 }
	 
}










