package Main;

import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
	public static void main(String[] args) {
		
		ServerSocket server;
		Socket socket;
		ObjectInputStream input;
		
		
		try {
			server = new ServerSocket(5678);
			socket = server.accept();
			
			input = new ObjectInputStream(socket.getInputStream());
			System.out.println(input.readObject());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
