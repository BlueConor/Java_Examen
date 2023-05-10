package main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.*;

import ti208.log.*;

public class server {
//preparer les statements
//static String command = "CREATE TABLE log (id int PRIMARY KEY, date time, customer_id int, info varchar (200), level varchar(10))";

	public static void main(String args[]) {
		try(Connection conn = DriverManager.getConnection("jdbc:sqlite:sample.db"); Statement st = conn.createStatement()){
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
			String insertCommand = "INSERT INTO log (id, time, text, level) VALUES (1, '" + timeInput.toString() + "', '" + textInput + "', '" + levelInput.toString() + "')";
			st.executeUpdate(insertCommand);
			
			//Récupération de données
			String resultCommand = "Select * FROM log";
			ResultSet result = st.executeQuery(resultCommand);
			
			//Affichage des résultats
			while(result.next()) {
				int id = result.getInt("id");
				String timeOutput = result.getString("time");
				String textOutput = result.getString("text");
				String levelOutput = result.getString("level");
				System.out.println("| id: " + id + " | time: " + timeOutput + " | text: " + textOutput + " | level: " + levelOutput + " |");
				}
			
			}
		
		catch(SQLException e) {
			// Handle connection error
			e.printStackTrace();
			}
	}
}