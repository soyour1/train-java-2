package com.example.demo.service.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoggerResponseDto {
	
	private Long id;
	
	private String email;
	
	private String ipAdress;
	
	private String message;
}
