package Main;

import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import ti208.log.*;

public class Server {
	public static void main(String[] args) {
		
		try {
			
			//Création du serveur et de l'attente de connection cliente
			ServerSocket server = new ServerSocket(5678);
			Socket connection = server.accept();
			
			ObjectInputStream input = new ObjectInputStream(connection.getInputStream());
			
			//Boucle pour récuperer les logs et l'identifiant du client, ainsi que les afficher
			for(int i = 0; i < 5; i++){
				Log message = (Log) input.readObject();
				String id = (String) input.readObject();
				System.out.println("'Le client' " + id + " a envoyé le log suivant :\n" + message);
				
			}
			
			try {
				if(server != null) {
					server.close();
				} 
				if(connection != null) {
					connection.close();
				}
				if(input != null) {
					input.close();
				}
			} catch (Exception e) {
				System.out.println("Une erreur s'est produite lors de la fermeture des systèmes de communications avec le client.");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
