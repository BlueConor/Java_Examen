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
			
			while(true) {
				String id = (String) input.readObject();
				Log message = (Log) input.readObject();
				System.out.println(id + message);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
