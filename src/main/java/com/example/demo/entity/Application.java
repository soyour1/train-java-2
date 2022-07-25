package com.example.demo.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Data
@Entity
@Table(name = "t_application")
public class Application {
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "email")
	private String email;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "git_user")
	private String gitUser;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "application", cascade = CascadeType.ALL)
	private List<Project> projects = new ArrayList<>();
	
	public void addProjects(Project project) {
		this.projects.add(project);
		project.setApplication(this);
	}
}
