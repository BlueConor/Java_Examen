package Main;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import ti208.log.*;

public class Client {
	public static void main(String[] args) {
		String[] id = args;
		
		if(id != null) {
			System.out.println("Vous avez oublé de donnée un ID à votre client");
			System.exit(1);
		}
		
		Socket server;
		ObjectOutputStream output;
		
		try {
			server = new Socket("localhost", 5678);
			output = new ObjectOutputStream(server.getOutputStream());
			
			for(int i = 0; i < 5; i++) {
				output.writeObject(id);
				output.writeObject(LogGenerator.readLog());
			}
			
			output.close();
			server.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}