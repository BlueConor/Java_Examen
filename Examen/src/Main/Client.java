package Main;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import ti208.log.*;

public class Client {
	public static void main(String[] args) {
		
		if(args.length == 0) {
			System.out.println("Vous avez oublé de donnée un ID à votre client");
			System.exit(1);
		}
		
		final String id = args[0];
		final Socket server;
		final ObjectOutputStream output;
		
		try {
			server = new Socket("localhost", 5678);
			output = new ObjectOutputStream(server.getOutputStream());
			
			for(int i = 0; i < 5; i++) {
				output.writeObject(LogGenerator.readLog());
				output.writeObject(id);
			}
			
			output.close();
			server.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}