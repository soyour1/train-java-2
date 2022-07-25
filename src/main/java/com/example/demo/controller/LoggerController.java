package com.example.demo.controller;

import com.example.demo.service.LoggerService;
import com.example.demo.service.dto.response.LoggerResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("api/logs")
@RestController
@RequiredArgsConstructor
public class LoggerController {
	
	private final LoggerService loggerService;
	
	@GetMapping
	public ResponseEntity<List<LoggerResponseDto>> getAllLogs() {
		return ResponseEntity.ok(loggerService.getAllLog());
	}
}
