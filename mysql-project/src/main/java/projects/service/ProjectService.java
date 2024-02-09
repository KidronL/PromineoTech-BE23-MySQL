package projects.service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import projects.ProjectApp;
import projects.dao.ProjectDao;
import projects.entity.Project;
import projects.exception.DbException;

//Class declaration to be instantiated as an object in the main Class
public class ProjectService {
	
	//Declaring variables
	private static final String SCHEMA_FILE = "project_schema.sql";
	private ProjectDao projectDao = new ProjectDao();

	//Method to create the table (Using abstraction)
	public void createAndPopulateTables() {
		loadFromFile(SCHEMA_FILE);
	}
	
	//Method to pull in the file and read the content
	private void loadFromFile(String fileName) {
		String content = readFileContent(fileName);
		List<String> sqlStatements = convertContentToSqlStatements(content);
		
		sqlStatements.forEach(line -> System.out.println(line));
		
		projectDao.executeBatch(sqlStatements);
		
	}
	
	//Method to convert the content to SQL
	private List<String> convertContentToSqlStatements(String content) {
		content = removeComments(content);
		content = replaceWhiteSpace(content);
		
		return extractLinesFromContent(content);
		
	}
	
	//Method for separating lines from the content passed in
	private List<String> extractLinesFromContent(String content) {
		List<String> lines = new LinkedList<>();
		
		while(!content.isEmpty()) {
			int semicolon = content.indexOf(";");
			
			if(semicolon == -1) {
				if(!content.isBlank()) {
					lines.add(content);
				}
				content = "";
			}
			else {
				lines.add(content.substring(0, semicolon).trim());
				content = content.substring(semicolon + 1);
			}
		
		}
		return lines;
	}

	//Method to remove the white space from the lines
	private String replaceWhiteSpace(String content) {
		return content.replaceAll("\\s+", " ");
	}

	//Method for removing comments from content passed in
	private String removeComments(String content) {
		StringBuilder builder = new StringBuilder(content);
		int commentPos = 0;
		
		while ((commentPos = builder.indexOf("-- ", commentPos)) != -1) {
			int eolPos = builder.indexOf("\n", commentPos + 1);
			
			if(eolPos == -1) {
				builder.replace(commentPos, builder.length(), "");
			} else {
				builder.replace(commentPos, eolPos + 1, "");
			}
		}
		
		return builder.toString();
		
	}

	//Method to read the file content
	private String readFileContent(String fileName) {
		try {
			Path path = Paths.get(getClass().getClassLoader().getResource(fileName).toURI());
			return Files.readString(path);
		} catch (Exception e) {
			
			throw new DbException(e);
		}
		
	}

	public Project addProject(Project project) {
		return projectDao.insertProject(project);
	}
	
	//A method to call method in ProjectDao
	public List<Project> fetchProjects() {
		return projectDao.fetchAllProjects();
	}	
	
	//A method to call method in ProjectDao
	public Project fetchProjectById(Integer projectId) {
		return projectDao.fetchProjectById(projectId).orElseThrow(
				() -> new NoSuchElementException(
						"Project with project ID= " + projectId 
						+ " does not exist."));
	}

}
