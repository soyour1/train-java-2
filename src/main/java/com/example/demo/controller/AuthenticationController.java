package com.example.demo.controller;

import com.example.demo.service.AuthenticationService;
import com.example.demo.service.ReviewerService;
import com.example.demo.service.dto.request.LoginRequestDto;
import com.example.demo.service.dto.request.ReviewerRequestDto;
import com.example.demo.service.dto.response.LoginResponseDto;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpHeaders;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/authentication")
@RequiredArgsConstructor
@Validated
public class AuthenticationController {
	
	private final ReviewerService reviewerService;
	
	private final AuthenticationService authenticationService;
	
	
	
//	private final KafkaTemplate<String, Log> kafkaTemplate;
    
//    public AuthenticationController(KafkaTemplate<String, String> kafkaTemplate) {
//        this.kafkaTemplate = kafkaTemplate;
//        this.reviewerService = null;
//        this.authenticationService = null;
//    }
	@PostMapping("/signup")
	public ResponseEntity<?> addNewReviewer(@Valid @RequestBody ReviewerRequestDto dto) {
		reviewerService.addNewReviewer(dto);
//		kafkaTemplate.send("log", "signup");
		return ResponseEntity.noContent().build();
	}
	
	@PostMapping("/login")
	public ResponseEntity<LoginResponseDto> login(@Valid @RequestBody LoginRequestDto dto) throws Exception {
//	    kafkaTemplate.send("log", log);
	    return ResponseEntity.ok(authenticationService.login(dto));
	}
	
	@PostMapping("/logout")
	public ResponseEntity<?> logout(@RequestHeader HttpHeaders headers) {
		authenticationService.logout(headers);
		return ResponseEntity.noContent().build();
	}
}
