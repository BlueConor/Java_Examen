package main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Action {
	static String request;
	
	public static String exportRequest(String customerId, String severity) {
		if(customerId.equals("all") && severity.equals("all")) {
			request = "SELECT * FROM log WHERE CustomerId = (?) AND SeverityLevel IN ((?), (?), (?))";
		} else if (severity.equals("all")) {
			request = "SELECT * FROM log WHERE CustomerId = (?) AND SeverityLevel IN ((?), (?), (?))";
		} else if (customerId.equals("all")) {
			request = "SELECT * FROM log WHERE CustomerId != (?) AND SeverityLevel IN ((?), (?), (?))";
		} else {
			request = "SELECT * FROM log WHERE CustomerId = (?) AND SeverityLevel IN ((?), (?), (?))";
		}
		
		return request;
	}
	
	public static void exportLogs() {
		Scanner scanner = new Scanner(System.in);
		
		System.out.println("Entrez l'ID du client souhaité (pour tous les exporter, entrez 'all') : ");
		String customerId = scanner.next();
		System.out.println("Entrez le niveau de sévérité minimum (Pour tous les exporter, entrez 'all') : ");
		String severityLevel = scanner.next();
		
		try(Connection conn = DriverManager.getConnection("jdbc:sqlite:sample.db")){
			PreparedStatement insertCommand = conn.prepareStatement(exportRequest(customerId, severityLevel));
			
			insertCommand.setString(1, customerId);
			
			if(severityLevel.equals("WARNING")) {
				insertCommand.setString(2, "INFO");
				insertCommand.setString(3, "ERROR");
				insertCommand.setString(4, "WARNING");
			} else if(severityLevel.equals("ERROR")) {
				insertCommand.setString(2, "INFO");
				insertCommand.setString(3, "ERROR");
				insertCommand.setString(4, "ERROR");
			} else {
				insertCommand.setString(2, severityLevel);
				insertCommand.setString(3, severityLevel);
				insertCommand.setString(4, severityLevel);
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
	
	
	public static String deleteRequest(String customerId, String maximumDate) {
		
		if(customerId.equals("all") && maximumDate.equals("all")) {
			request = "DELETE FROM log WHERE CustomerId != (?) AND != (?)";		
		}else if(maximumDate.equals("all")) {
			request = "DELETE FROM log WHERE CustomerId = (?) AND != (?)";
		}else if(customerId.equals("all")) {
			request = "DELETE FROM log WHERE CustomerId != (?) AND < (?)";
		}else {
			request = "DELETE FROM log WHERE CustomerId = (?) AND < (?)";
		}
		
		return request;
	}
	
	public static void deleteLogs() {
		Scanner scanner = new Scanner(System.in);
				
		System.out.println("Entrez l'ID du client souhaité (pour tous les supprimer, entrez 'all') : ");
		String customerId = scanner.next();
		System.out.println("Entrez la date de fin (Pour tous les supprimer, entrez 'all') : ");
		String maximumDate = scanner.next();
		
		
		try(Connection conn = DriverManager.getConnection("jdbc:sqlite:sample.db");
				PreparedStatement insertCommand = conn.prepareStatement(deleteRequest(customerId, maximumDate))){

			insertCommand.setString(1, customerId);
			insertCommand.setString(2, maximumDate);
			
			insertCommand.executeQuery();
			
			System.out.println("suppression effectuée");
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}
}