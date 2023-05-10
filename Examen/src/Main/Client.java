package Main;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import ti208.log.*;

public class Client {
	public static void main(String[] args) {
		
		//Vérification de la présence de l'argument/ID du client
		if(args.length == 0) {
			System.out.println("Vous avez oublé de donnée un ID à votre client");
			System.exit(1);
		}
		
		//Transmission des logs vers le serveur depuis le client
		try {
			
			//Création des systèmes de communation au serveur
			Socket server = new Socket("localhost", 5678);
			ObjectOutputStream output = new ObjectOutputStream(server.getOutputStream());
			
			//Boucle pour la transmission des logs du clients
			for(int i = 0; i < 5; i++) {
				output.writeObject(LogGenerator.readLog());
				output.writeObject(args[0]);
			}
			
			//Fermeture des systèmes de connections
			try {
				if(server != null) {
					server.close();
				}
				if(output != null) {
					output.close();
				}
			} catch (Exception e) {
				System.out.println("Une erreur s'est produite lors de la fermeture des systèmes de communications avec le serveur.");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}