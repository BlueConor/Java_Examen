package Main;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import ti208.log.LogGenerator;

public class Client {
	public static void main(String[] args) {

		Socket client;
		ObjectOutputStream output;
		
		try {
			client = new Socket("localhost", 5678);
			output = new ObjectOutputStream(client.getOutputStream());
			
			
			for(int i = 0; i < 10; i++) {
				output.writeObject(LogGenerator.readLog());
			}
			
			output.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}