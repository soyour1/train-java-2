package com.example.demo.service.mapper;

import com.example.demo.entity.Project;
import com.example.demo.service.dto.request.ProjectRequestDto;
import com.example.demo.service.dto.response.ProjectResponseDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ProjectMapper {
	public Project convertToProject(ProjectRequestDto request) {
		
		Project project = new Project();
		
		project.setName(request.getName());
		project.setEmploymentMode(request.getEmploymentMode());
		project.setCapacityStatus(request.getCapacityStatus());
		project.setDurationInMonths(request.getDurationInMonths());
		project.setStartYear(request.getStartYear());
		project.setRole(request.getRole());
		project.setTeamSize(request.getTeamSize());
		project.setLinkRepo(request.getLinkRepo());
		project.setLinkLive(request.getLinkLive());
		return project;
	}
	
	public ProjectResponseDto convertToResponseDto(Project projects){
		
		return ProjectResponseDto
				.builder()
				.id(projects.getId())
				.name(projects.getName())
				.employmentMode(projects.getEmploymentMode())
				.capacityStatus(projects.getCapacityStatus())
				.durationInMonths(projects.getDurationInMonths())
				.startYear(projects.getStartYear())
				.role(projects.getRole())
				.teamSize(projects.getTeamSize())
				.linkLive(projects.getLinkLive())
				.linkRepo(projects.getLinkRepo())
				.build();
	}
}
