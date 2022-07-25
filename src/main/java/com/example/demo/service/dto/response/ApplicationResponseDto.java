package com.example.demo.service.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@Builder
public class ApplicationResponseDto {
	private Long id;
	
	private String email;
	
	private String name;
	
	private String gitUser;
	
	private List<ProjectResponseDto> projects;
}
