package com.example.demo.service.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@Builder
@AllArgsConstructor
public class LoginRequestDto {
	@NotBlank(message = "Email is required")
	private String email;
	
	@NotBlank(message = "password is required")
	private String password;
}
