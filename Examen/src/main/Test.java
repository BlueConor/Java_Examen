package main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Test {
	static String request;
	
	public static void exportLogs() {
		Scanner scanner = new Scanner(System.in);
		
		System.out.println("Entrez l'ID du client souhaité (pour tous les exporter, entrez 'all') : ");
		String customerId = scanner.next();
		System.out.println("Entrez le niveau de sévérité minimum (Pour tous les exporter, entrez 'all') : ");
		String severityLevel = scanner.next();
		
		try(Connection conn = DriverManager.getConnection("jdbc:sqlite:sample.db")){
			
			if(customerId.equals("all") && severityLevel.equals("all")) {
				PreparedStatement insertCommand = conn.prepareStatement("SELECT * FROM log");
				
			} else if (severityLevel.equals("all")) {
				PreparedStatement insertCommand = conn.prepareStatement("SELECT * FROM log WHERE CustomerId = (?) AND SeverityLevel IN (INFO, ERROR, WARNING)");
				insertCommand.setString(1, customerId);
				
			} else if (customerId.equals("all")) {
				if(severityLevel.equals("WARNING")) {
					PreparedStatement insertCommand = conn.prepareStatement("SELECT * FROM log WHERE SeverityLevel LIKE 'WARNING'");
				} else if(severityLevel.equals("ERROR")) {
					PreparedStatement insertCommand = conn.prepareStatement("SELECT * FROM log WHERE SeverityLevel IN ('WARNING', 'ERROR')");
				} else {
					PreparedStatement insertCommand = conn.prepareStatement("SELECT * FROM log WHERE SeverityLevel IN ('WARNING', 'ERROR','INFO')");
				}
				
			} else {
				if(severityLevel.equals("WARNING")) {
					PreparedStatement insertCommand = conn.prepareStatement("SELECT * FROM log WHERE CustomerId = (?) AND SeverityLevel LIKE 'WARNING'");
				} else if(severityLevel.equals("ERROR")) {
					PreparedStatement insertCommand = conn.prepareStatement("SELECT * FROM log WHERE CustomerId = (?) AND SeverityLevel IN ('WARNING', 'ERROR')");
				} else {
					PreparedStatement insertCommand = conn.prepareStatement("SELECT * FROM log WHERE CustomerId = (?) AND SeverityLevel IN ('WARNING', 'ERROR','INFO')");
				}
				insertCommand.setString(1, customerId);
			}
			
			ResultSet export = insertCommand.executeQuery();
			while(export.next()) {
					String log = export.getString(1);
					System.out.println(log);
				}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}