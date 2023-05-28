package main;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

	public static void main(String args[]) throws IOException{
		
		try (ServerSocket server = new ServerSocket(5678)) {
			Thread menu = new Interface();
			menu.start();
			
			while(true) {
				Socket connection = server.accept();
				
				CustomThread thread = new CustomThread(connection);
				thread.start();
			}
		}
	}
}