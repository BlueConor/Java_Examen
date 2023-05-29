package main;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Tasks {

	public static void export() throws IOException {
		Scanner scanner = new Scanner(System.in);
		
		System.out.println("Entrez l'ID du client souhaité (pour tous les exporter, entrez 'all') : ");
		String customerId = scanner.next();
		System.out.println("Entrez le niveau de sévérité minimum (Pour tous les exporter, entrez 'all') : ");
		String severityLevel = scanner.next();
		System.out.println("Entrez l'emplacement du fichier: ");
		String exportPath = scanner.next();
		
		try(Connection conn = DriverManager.getConnection("jdbc:sqlite:sample.db")){
			PreparedStatement insertCommand;

			//Vérifie d'abord la valeur de customerId (doit être en dernière position dans la commande de sélection pour ne pas laisser un vide
			if(customerId.equals("all")) {
				insertCommand = conn.prepareStatement("SELECT * FROM log WHERE SeverityLevel IN ((?), (?), (?))");}
			
			else {
				insertCommand = conn.prepareStatement("SELECT * FROM log WHERE SeverityLevel IN ((?), (?), (?)) and CustomerId = (?) ");
				insertCommand.setString(4, customerId);}
			
			if(severityLevel.equals("WARNING")) {
				insertCommand.setString(1, "WARNING");
				insertCommand.setString(2, "WARNING");
				insertCommand.setString(3, "WARNING");
				
			} else if(severityLevel.equals("ERROR")) {
				insertCommand.setString(1, "WARNING");
				insertCommand.setString(2, "ERROR");
				insertCommand.setString(3, "ERROR");
			} else {
				insertCommand.setString(1, "INFO");
				insertCommand.setString(2, "ERROR");
				insertCommand.setString(3, "WARNING");
			}
			//System.out.println(insertCommand);
			
			ResultSet result = insertCommand.executeQuery();
			
			
			
			while(result.next()) {
				int IdOutput = result.getInt("Id");
				String DateTimeOutput = result.getString("DateTime");
				String TextOutput = result.getString("Text");
				String SeverityLevelOutput = result.getString("SeverityLevel");
				String CustomerIdOutput = result.getString("CustomerId");
				
				String output = "| Id: " + IdOutput + " | DateTime: " + DateTimeOutput + " | Text: " + TextOutput + " | level: " + SeverityLevelOutput + " | CusomerId: " + CustomerIdOutput + " |";
				//System.out.println(output);
				
				//écriture du log dans le fichier texte (true utilisé pour pouvoir ajouter tout les lignes une par une)
				FileWriter fw = new FileWriter(exportPath, true);
				fw.write("\n" + output);
				fw.close();
			}
			
		} catch (SQLException e) {
			System.out.println("Une erreur est survenue, vérifiez vos données et réessayer");
		}
	}
	
	
	
	public static void delete() {
		Scanner scanner = new Scanner(System.in);
		
		System.out.println("Entrez l'ID du client souhaité (pour tous les supprimer, entrez 'all') : ");
		String customerId = scanner.next();
		System.out.println("Entrez le niveau de sévérité minimum (Pour tous les supprimer, entrez 'all') : ");
		String severityLevel = scanner.next();
		
		try(Connection conn = DriverManager.getConnection("jdbc:sqlite:sample.db")){
			PreparedStatement insertCommand;

			//Vérifie d'abord la valeur de customerId (doit être en dernière position dans la commande de sélection pour ne pas laisser un vide
			if(customerId.equals("all")) {
				insertCommand = conn.prepareStatement("DELETE FROM log WHERE SeverityLevel IN ((?), (?), (?))");}
			
			else {
				insertCommand = conn.prepareStatement("DELETE FROM log WHERE SeverityLevel IN ((?), (?), (?)) and CustomerId = (?) ");
				insertCommand.setString(4, customerId);}
			
			if(severityLevel.equals("WARNING")) {
				insertCommand.setString(1, "WARNING");
				insertCommand.setString(2, "WARNING");
				insertCommand.setString(3, "WARNING");
				
			} else if(severityLevel.equals("ERROR")) {
				insertCommand.setString(1, "WARNING");
				insertCommand.setString(2, "ERROR");
				insertCommand.setString(3, "ERROR");
			} else {
				insertCommand.setString(1, "INFO");
				insertCommand.setString(2, "ERROR");
				insertCommand.setString(3, "WARNING");
			}

			
			int DeleteCount = insertCommand.executeUpdate();
			System.out.println(DeleteCount + " données supprimées");
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
