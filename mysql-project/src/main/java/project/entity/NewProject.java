package project.entity;

import java.util.LinkedList;
import java.util.List;

public class NewProject {
	private Integer projectId;
	private String projectName;
	private Double estimatedHours;
	private Double actualHours;
	private Integer difficulty;
	private String notes;
	
	private List<Material> materials = new LinkedList<>();
	private List<Step> steps = new LinkedList<>();
	private List<Category> categories = new LinkedList<>();
	
	public Integer getProjectId() {
		return projectId;
	}
	public void setProjectId(Integer projectId) {
		this.projectId = projectId;
	}
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public Double getEstimatedHours() {
		return estimatedHours;
	}
	public void setEstimatedHours(Double estimatedHours) {
		this.estimatedHours = estimatedHours;
	}
	public Double getActualHours() {
		return actualHours;
	}
	public void setActualHours(Double actualHours) {
		this.actualHours = actualHours;
	}
	public Integer getDifficulty() {
		return difficulty;
	}
	public void setDifficulty(Integer difficulty) {
		this.difficulty = difficulty;
	}
	public String getNotes() {
		return notes;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}
	public List<Material> getMaterials() {
		return materials;
	}
	public List<Step> getSteps() {
		return steps;
	}
	public List<Category> getCategories() {
		return categories;
	}
	
	@Override
	public String toString() {
		return "ID: " + projectId + "\nProject Name: " + projectName + "\nEstimated Hours: "
				+ estimatedHours + "\nActual Hours:" + actualHours + "\nDifficulty: " + difficulty + "\nNotes: " + notes
				+ "\nMaterials: " + materials + "\nSteps: " + steps + "\nCategories: " + categories;
	}
	
	
	
	
}
