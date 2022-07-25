package com.example.demo.service.mapper;

import com.example.demo.entity.Application;
import com.example.demo.service.dto.request.ApplicationRequestDto;
import com.example.demo.service.dto.response.ApplicationResponseDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class ApplicationMapper {
	
	private final ProjectMapper projectMapper;
	
	@Transactional
	public Application convertToApplication(ApplicationRequestDto request) {
		Application application = new Application();
		application.setEmail(request.getEmail());
		application.setName(request.getName());
		application.setGitUser(request.getGitUser());
		request.getProjects().stream().forEach(project -> application.addProjects(projectMapper.convertToProject(project)));
		return application;
	}
	
	@Transactional
	public ApplicationResponseDto convertToResponseDto(Application application) {
		return ApplicationResponseDto
				.builder()
				.id(application.getId())
				.name(application.getName())
				.email(application.getEmail())
				.gitUser(application.getGitUser())
				.projects(application.getProjects().stream().map(projectMapper::convertToResponseDto).collect(Collectors.toList()))
				.build();
	}
}
