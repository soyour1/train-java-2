package com.example.demo.service.impl;

import com.example.demo.entity.Application;
import com.example.demo.entity.Message;
import com.example.demo.repository.ApplicationRepository;
import com.example.demo.service.ApplicationService;
import com.example.demo.service.LoggerService;
import com.example.demo.service.dto.request.ApplicationRequestDto;
import com.example.demo.service.dto.response.ApplicationResponseDto;
import com.example.demo.service.mapper.ApplicationMapper;
import com.example.demo.service.mapper.ProjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Transactional
public class ApplicationServiceImpl implements ApplicationService {
	
	private  final ApplicationRepository applicationRepository;
	
//	private  final ProjectResponseDto projectResponseDto;
	
	private final LoggerService loggerService;
	
	private  final ApplicationMapper applicationMapper;
	
	private final ProjectMapper projectMapper;
	
	private final HttpServletRequest htttpRequest;
	private final KafkaTemplate<String, String> kafkaTemplate;
	@Override
	public ApplicationResponseDto addApplication(ApplicationRequestDto request){
		Message message = new Message(request.getEmail(), htttpRequest.getRemoteAddr(), "Add application");
		kafkaTemplate.send("log", message.toString());
		return saveApplication(request);
	}
	
	@Override
	public List<ApplicationResponseDto> getApplication(){
		return applicationRepository.findAll().stream().map(applicationMapper::convertToResponseDto).collect(Collectors.toList());
	}
	
	@Override
	public void updateApplication(Long id, ApplicationRequestDto request){
		Application application = applicationRepository.findById(id).orElseThrow(() -> new RuntimeException("Application is not found"));
		if(applicationRepository.existsByEmail(request.getEmail())){
//			applicationRepository.deleteByEmail(request.getEmail());
			applicationRepository.delete(application);
			saveApplication(request);
			return;
		}
		application.setEmail(request.getEmail());
		application.setName(request.getName());
		application.setGitUser(request.getGitUser());
//		loggerService.sendLogService(request.getEmail(), "updated application");
		
		Message message = new Message(request.getEmail(), htttpRequest.getRemoteAddr(), "Update application");
		kafkaTemplate.send("log", message.toString());
	}
	
	@Override
	@Transactional
	public void deleteApplication(Long id){
		applicationRepository.deleteById(id);
	}
	
	public ApplicationResponseDto saveApplication(ApplicationRequestDto request){
		Application application = applicationMapper.convertToApplication(request);
//		request.getProjects().stream().forEach(project -> application.addProjects(projectMapper.convertToProject(project)));
//		loggerService.sendLogService(request.getEmail(), "deleted application");
		
		Message message = new Message(request.getEmail(), htttpRequest.getRemoteAddr(), "Delete application");
		kafkaTemplate.send("log", message.toString());
		return applicationMapper.convertToResponseDto(applicationRepository.save(application));
	}
}
