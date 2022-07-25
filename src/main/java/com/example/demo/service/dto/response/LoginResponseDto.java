package com.example.demo.service.dto.response;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginResponseDto {
	private static final long serialVersionUID = 1L;
	private String token;
}
