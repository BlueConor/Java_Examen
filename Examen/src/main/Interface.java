package main;

import java.util.Scanner;

public class Interface extends Thread{
	String options = "Entrez '1' pour exporter des logs ou " + "'2' pour supprimer des logs : ";
	
	public final void run() {
		System.out.println("Bienvenue sur l'interface de gestion.");
		System.out.print(options);
		
		Scanner scanner = new Scanner(System.in);
		int choice = scanner.nextInt();
		
		while(true) {
			if(choice == 1) {
				Action.exportLogs();
				
				System.out.print(options);
				choice = scanner.nextInt();
			} else if (choice == 2) {
				Action.deleteLogs();
				
				System.out.print(options);
				choice = scanner.nextInt();
			} else {
				System.out.println("\nVous avez peut-être mal tapé, veuillez :");
				System.out.print(options);
				choice = scanner.nextInt();
			}
		}
	}
}