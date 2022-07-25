package com.example.demo.service.dto.request;

import com.example.demo.entity.Project;
import lombok.Builder;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
@Builder
public class ApplicationRequestDto {
	@NotBlank(message = "Email address is required")
	private String email;
	
	@NotBlank(message = "Name is required")
	private String name;
	
	@NotBlank(message = "Git user is required")
	private String gitUser;
	
	@Valid
	@NotEmpty
	private List<ProjectRequestDto> projects;
}
