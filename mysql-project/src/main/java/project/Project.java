package project;

//Imports for classes and methods
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

import project.exception.DbException;
import project.service.ProjectService;

public class Project {
	
		//Declaring variables for scanner and Project Service Object.
		private Scanner scanner = new Scanner(System.in);
		private ProjectService projectService = new ProjectService();
		
		private List<String> operations = List.of(
				"1) Create and populate all tables"
		);

	public static void main(String[] args) {
		new Project().menu();

	}
	
	//Navigation component
	private void menu() {
		boolean done = false;
		
		while(!done) {
			int operation = getOperation();
			
		try {
			switch(operation) {
			case -1:
				done = exitMenu();
				break;
				
			case 1:
				createTables();
				break;
				
				default:
					System.out.println("Please enter a valid answer.");
					break;
			}
		}catch (Exception e) {
			System.out.println("\nError" + e.toString() + "Try again");
		}
	  }
	}
	
	//Logic to create tables
	private void createTables() {
		projectService.createAndPopulateTables();
		System.out.println("\nTables have been created");
		
	}

	//Logic to exit the menu
	private boolean exitMenu() {
		System.out.println("\nLeaving menu now.");
		return true;
	}

	//Logic to call operations
	private int getOperation() {
		printOperations();
		Integer op = getIntInput("Enter an operation number (press Enter to quit)");
		
		return Objects.isNull(op) ? -1 : op;
	}
	
	//Logic to update options
	private void printOperations() {
		System.out.println();
		System.out.println("Here's what you can do: ");
		
		operations.forEach(op -> System.out.println(op));
			
		}
	
	//Getters for operations
	private Integer getIntInput(String prompt) {
		String input = getStringInput(prompt);
		
		if(Objects.isNull(input)) {
			return null;
		}
		
		try {
			return Integer.parseInt(input);
		}
		catch(NumberFormatException e) {
			throw new DbException(input + " is not valid number.");
		}
			
	}

	private String getStringInput(String prompt) {
		System.out.println(prompt + ": ");
		String line = scanner.nextLine();
		
		return line.isBlank() ? null : line.trim();
	}
	
	private Double getDoubleInput(String prompt) {
		String input = getStringInput(prompt);
		
		if(Objects.isNull(input)) {
			return null;
		}
		
		try {
			return Double.parseDouble(input);
		}
		catch(NumberFormatException e) {
			throw new DbException(input + " is not valid number.");
		}
			
	}

}



