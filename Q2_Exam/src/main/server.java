package main;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.time.*;
import ti208.log.*;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class server {
//utilisation de PrepareStatement pour les injections SQL sujettes à répétition (protège aussi des attaques SQL mais ne nous concernent pas)

	public static void main(String args[]) throws IOException {

		String insertCommand = "INSERT INTO log (id, time, text, level, customer_id) VALUES (2, ?, ?, ?, ?)";
		
		try(Connection conn = DriverManager.getConnection("jdbc:sqlite:sample.db");
			Statement st = conn.createStatement();
			PreparedStatement insertInput = conn.prepareStatement(insertCommand)){
			
			
			//Création du serveur et de l'attente de connection cliente
			ServerSocket server = new ServerSocket(5678);
			Socket connection = server.accept();
			
			ObjectInputStream input = new ObjectInputStream(connection.getInputStream());
			
			//Vérification de la connection
			System.out.println("Connexion réussie !");
			
			//Reset de la base de données
			st.execute("DROP TABLE log");
			st.execute("CREATE TABLE log (id int, time varchar(200) , customer_id varchar(100), text varchar (200), level varchar(10))");
		
			
			for(int i = 0; i < 5; i++){
				Log log = (Log) input.readObject();
				//Insertion d'une nouvelle ligne
				LocalDateTime timeInput = log.getDatetime();
				LogLevel levelInput = log.getLevel();
				String textInput = log.getText();			
				//Démarche via un Prepared Statement pour régler le problème de lecture
				insertInput.setString(1, timeInput.toString());
				insertInput.setString(2, textInput);
				insertInput.setString(3, levelInput.toString());
				String customer_id = (String) input.readObject();
				insertInput.setString(4, customer_id);
				insertInput.executeUpdate();
				
			}
				
			//Récupération de données
			String resultCommand = "Select * FROM log";
			ResultSet result = st.executeQuery(resultCommand);
			
			while(result.next()) {
			
				int id = result.getInt("id");
				String timeOutput = result.getString("time");
				String textOutput = result.getString("text");
				String levelOutput = result.getString("level");
				String customer_idOutput = result.getString("customer_id");
				
				String output = "| id: " + id + " | time: " + timeOutput + " | text: " + textOutput + " | level: " + levelOutput + " | customer_id: " + customer_idOutput + " |";
			
				//affichage du log dans le terminal
				System.out.println(output);
			
				//écriture du log dans le fichier texte
				FileWriter fw = new FileWriter("BD.txt", true);
				fw.write("\n" + output);
				fw.close();
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
