package com.example.demo.service.mapper;

import com.example.demo.entity.Logger;
import com.example.demo.service.dto.response.LoggerResponseDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Component
@AllArgsConstructor
public class LogMapper {
	
	@Transactional
	public LoggerResponseDto convertToResponseDto(Logger logger){
		return LoggerResponseDto.builder()
				  .id(logger.getId())
				  .email(logger.getEmail())
				  .ipAdress(logger.getIpAdress())
				  .message(logger.getMessage())
				  .build();
	}
}
