package projects;

import java.math.BigDecimal;
//Imports for classes and methods
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

import projects.entity.Project;
import projects.exception.DbException;
import projects.service.ProjectService;

public class ProjectApp {
	
		//Declaring variables for scanner and Project Service Object.
		private Scanner scanner = new Scanner(System.in);
		private ProjectService projectService = new ProjectService();
		private Project curProject;
		
		private List<String> operations = List.of(
				"1) Add a project",
				"2) List projects",
				"3) Select a project",
				"4) Update project details",
				"5) Delete a project"
		);

	public static void main(String[] args) {
		new ProjectApp().menu();

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
				addProject();
				break;
				
			case 2:
				listProjects();
				break;
				
			case 3:
				selectProject();
				break;
				
			case 4:
				updateProjectDetails();
				break;
				
			case 5:
				deleteProject();
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
	
	//Method to handle deleting a project
	private void deleteProject() {
		listProjects();
		
		Integer projectId = getIntInput("Enter the ID of the project to delete");
		
		projectService.deleteProject(projectId);
		System.out.println("Project " + projectId + " was deleted successfully.");
		
		if(Objects.nonNull(curProject) && curProject.getProjectId().equals(projectId)) {
			
			}
		}

	//Method in order update the project
	private void updateProjectDetails() {
		if(Objects.isNull(curProject)) {
			System.out.println("\nPlease select a project.");
			return;
		}
		
		String projectName = getStringInput("Enter the project name [" + curProject.getProjectName() + "]");
		
		BigDecimal estimatedHours = getDecimalInput("Enter the estimated hours [" + curProject.getEstimatedHours() + "]");
		
		BigDecimal actualHours = getDecimalInput("Enter the actual hours [" + curProject.getActualHours() + "]");
		
		Integer difficulty = getIntInput("Enter the project difficulty (1-10) [" + curProject.getDifficulty() + "]");
		
		String notes = getStringInput("Enter the project notes [" + curProject.getNotes() + "]");
		
		Project project = new Project();
		
		project.setProjectId(curProject.getProjectId());
		project.setProjectName(Objects.isNull(projectName) ? curProject.getProjectName() : projectName);
		project.setEstimatedHours(Objects.isNull(estimatedHours) ? curProject.getEstimatedHours() : estimatedHours);
		project.setActualHours(Objects.isNull(actualHours) ? curProject.getActualHours() : actualHours);
		project.setDifficulty(Objects.isNull(difficulty) ? curProject.getDifficulty() : difficulty);
		project.setNotes(Objects.isNull(notes) ? curProject.getNotes() : notes);
		
		projectService.modifyProjectDetails(project);
		
		curProject = projectService.fetchProjectById(curProject.getProjectId());
	}

	//Method to select a project using a method in ProjectService
	private void selectProject() {
		listProjects();
		Integer projectId = getIntInput("Enter a project ID to select a project");
		
		curProject = null;
		
		curProject = projectService.fetchProjectById(projectId);
		
		System.out.println("Current Project: \n" + curProject);
		
	}
	
	//Method to list the projects using a method in ProjectService
	private void listProjects() {
		List<Project> projects = projectService.fetchProjects();
		
		System.out.println("\nProjects: ");
		
		projects.forEach(project -> System.out.println("    " + project.getProjectId() + ": " + project.getProjectName()));
		
	}

	private void addProject() {
		String name = getStringInput("Enter the project name");
		BigDecimal estHours = getDecimalInput("Enter estimated hours for project");
		BigDecimal actHours = getDecimalInput("Enter actual hours spent on project");
		Integer difficulty = getIntInput("Enter the difficulty of the project (1-10)");
		String notes = getStringInput("Enter the project notes");
		
		Project project = new Project();
		
		project.setProjectName(name);
		project.setEstimatedHours(estHours);
		project.setActualHours(actHours);
		project.setDifficulty(difficulty);
		project.setNotes(notes);
		
		Project dbProject = projectService.addProject(project);
		System.out.println("Success in adding \n" + dbProject);
		
	}

	//Logic to create tables
	/*private void createTables() {
		projectService.createAndPopulateTables();
		System.out.println("\nTables have been created");
		
	}*/

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
	
	private BigDecimal getDecimalInput(String prompt) {
			String input = getStringInput(prompt);
		
		if(Objects.isNull(input)) {
			return null;
		}
		
		try {
			return new BigDecimal(input).setScale(2);
		}
		catch(NumberFormatException e) {
			throw new DbException(input + " is not valid number.");
		}
			
	}

}



