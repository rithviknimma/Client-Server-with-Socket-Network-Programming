/*
* Assignment 6 
* Name: Rithvik Baddam
* EID: rrb2442 
*/

package assignment_6;

import java.net.*;
import java.util.ArrayList;

/*
 * Chat server program
 */
public class MyServer {
	private static ArrayList<String> clientNames = new ArrayList<String>();
	private static ArrayList<ClientThread> clientThreads = new ArrayList<ClientThread>();
	
	public static void main(String[] args){
		try (ServerSocket ss = new ServerSocket(6666)){
			System.out.println("Server on");
			while(true) {
				Socket s = ss.accept();//establishes connection 
				System.out.println("New client connected");
				
				ClientThread newClient = new ClientThread(s);
				clientThreads.add(newClient);
				newClient.start();
			}
		}catch(Exception e){
			System.out.println("Error in the server: "+e.getMessage());
			e.printStackTrace();
		}
		
	}
	
	// returns if there are clients connected 
	static boolean containsClients() {
		return !clientNames.isEmpty();
	}
	
	// adds a newly connected client
	 static void addClient(String client) {
		clientNames.add(client);
	}
	
	// deletes client name and client thread when client disconnects
	static void deleteClient(String clientName, ClientThread client) {
		boolean deleted = clientNames.remove(clientName);
		if(deleted){
			clientThreads.remove(client);
            System.out.println("The user " + clientName + " exited");
        }
	}
	
	// sends message to all clients
	static void send(String message, ClientThread clientToExclude) {
		for(ClientThread client: clientThreads) {
			if(client != clientToExclude)
				client.sendMessage(message);
		}
	}
	
	// sends message to specific client
	static void sendToClient(String message, ClientThread client) {
		client.sendMessage(message);
	}
	
	static ArrayList<String> getClientNames(){
		return clientNames;
	}
	
	// returns client thread given username
	static ClientThread getClientThread(String username) {
		return clientThreads.get(clientNames.indexOf(username));
	}
}





