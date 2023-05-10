package Main;

import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import ti208.log.*;

public class Server {
	public static void main(String[] args) {
		
		ServerSocket server;
		Socket connection;
		ObjectInputStream input;
		
		
		try {
			server = new ServerSocket(5678);
			connection = server.accept();
			
			input = new ObjectInputStream(connection.getInputStream());
			
			for(int i = 0; i < 5; i++){
				Log message = (Log) input.readObject();
				String id = (String) input.readObject();
				System.out.println("'Le client' " + id + " a envoyé le log suivant :\n" + message);
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
