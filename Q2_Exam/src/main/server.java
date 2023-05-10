package main;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.time.*;
import ti208.log.*;

public class server {
//utilisation de PrepareStatement pour les injections SQL sujettes à répétition (protège aussi des attaques SQL mais ne nous concernent pas)
//static String command = "CREATE TABLE log (id int PRIMARY KEY, date time, customer_id int, info varchar (200), level varchar(10))";

	public static void main(String args[]) throws IOException {
		try(Connection conn = DriverManager.getConnection("jdbc:sqlite:sample.db"); 
			Statement st = conn.createStatement();)
		
		{
			// Use connection
			System.out.println("Connexion réussie !");
			
			//Reset de la base de données
			st.execute("DROP TABLE log");
			st.execute("CREATE TABLE log (id int, time varchar(200) , customer_id varchar(100), text varchar (200), level varchar(10))");
			
			//Insertion d'une nouvelle ligne
			Log log = LogGenerator.readLog();
			LocalDateTime timeInput = log.getDatetime();
			LogLevel levelInput = log.getLevel();
			String textInput = log.getText();
			String insertCommand = "INSERT INTO log (id, time, text, level) VALUES (2, '" + timeInput + "', '" + textInput + "', '" + levelInput + "')";
			st.executeUpdate(insertCommand);
			
			//Récupération de données
			String resultCommand = "Select * FROM log";
			ResultSet result = st.executeQuery(resultCommand);
			
			while(result.next()) {
				int id = result.getInt("id");
				String timeOutput = result.getString("time");
				String textOutput = result.getString("text");
				String levelOutput = result.getString("level");
				
				String output = "| id: " + id + " | time: " + timeOutput + " | text: " + textOutput + " | level: " + levelOutput + " |";
				//affichage du log dans le terminal
				System.out.println(output);
				
				//écriture du log dans le fichier texte
				FileWriter fw = new FileWriter("BD.txt", true);
				fw.write("\n" + output);
				fw.close();
				
				
				}
			
			}
		
		catch(SQLException e) {
			// Handle connection error
			e.printStackTrace();
			}
	}
}