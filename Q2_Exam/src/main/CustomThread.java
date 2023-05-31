package main;

import java.io.ObjectInputStream;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Random;
import ti208.log.Log;

public class CustomThread extends Thread {
	Socket connection;
	Random random = new Random();
	
	public CustomThread(Socket connection) {
		this.connection = connection;
	}

	public void run() {
		try {
			ObjectInputStream input = new ObjectInputStream(connection.getInputStream());
			
			while(true) {
				int logId = random.nextInt(1000000);
				String customerId = (String) input.readObject();
				Log log = (Log) input.readObject();
			
				try(Connection conn = DriverManager.getConnection("jdbc:sqlite:sample.db");
					PreparedStatement insertLog = conn.prepareStatement("INSERT INTO log (Id, DateTime, CustomerId, Text, SeverityLevel) VALUES (?, ?, ?, ?, ?)")){

					insertLog.setLong(1, logId);
					insertLog.setString(2, log.getDatetime().toString());
					insertLog.setString(3, customerId);
					insertLog.setString(4, log.getText());
					insertLog.setString(5, log.getLevel().toString());
					
					//Vérifications de création
					//System.out.println("");
					//System.out.println("| id: " + logId + " | time: " + log.getDatetime().toString() + " | text: " + log.getText() + " | level: " + log.getLevel().toString() + " | customer_id: " + customerId + " |");
					
					insertLog.executeUpdate();
					
				} catch(SQLException e) {
					e.printStackTrace();
				}
			}
		} catch(Exception e) {
		}
	}
}