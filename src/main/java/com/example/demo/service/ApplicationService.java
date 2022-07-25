package com.example.demo.service;

import com.example.demo.entity.Application;
import com.example.demo.service.dto.request.ApplicationRequestDto;
import com.example.demo.service.dto.response.ApplicationResponseDto;
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Optional;

public interface ApplicationService {
	ApplicationResponseDto addApplication(ApplicationRequestDto request);
	
	List<ApplicationResponseDto> getApplication();
	
	void updateApplication(Long id, ApplicationRequestDto request);
	
	void deleteApplication(Long id);
}