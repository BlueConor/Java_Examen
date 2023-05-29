package main;

import java.io.IOException;
import java.util.Scanner;

public class Interface extends Thread{
	String options = "Entrez 'e' pour exporter des logs ou " + "'d' pour supprimer des logs : ";
	
	public final void run() {
		System.out.println("Bienvenue sur l'interface de gestion.");
		System.out.print(options);
		
		Scanner scanner = new Scanner(System.in);
		String choice = scanner.next();
		
		while(true) {
			//Export
			if(choice.equals("e")) {
				//Ajout demandé après avoir mis le throws Exception dans Action (mis pour l'écriture dans un fichier)
				try {
					Tasks.export();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				System.out.print(options);
				choice = scanner.next();
			//Delete
			} else if (choice.equals("d")) {
				Tasks.delete();
				
				System.out.print(options);
				choice = scanner.next();
			//Error
			} else {
				System.out.println("\nVous avez peut-être mal tapé, veuillez :");
				System.out.print(options);
				choice = scanner.next();
			}
		}
	}
}