package main;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import ti208.log.LogGenerator;


public class Customer {
	public static void main(String[] args) {
		try (Socket server = new Socket("localhost", 5678)) {
			
			ObjectOutputStream output = new ObjectOutputStream(server.getOutputStream());
			
			//Création de 5 logs
			for(int i = 0; i < 5; i++) {
				output.writeObject(args[0]);
				output.writeObject(LogGenerator.readLog());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}