package com.example.demo.service;

import com.example.demo.service.dto.response.LoggerResponseDto;

import java.util.List;

public interface LoggerService {
	
	void saveLog(String email, String ipAdress, String message);
	
	List<LoggerResponseDto> getAllLog();
}
