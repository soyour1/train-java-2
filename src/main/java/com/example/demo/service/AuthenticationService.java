package com.example.demo.service;

import com.example.demo.service.dto.request.LoginRequestDto;
import com.example.demo.service.dto.response.LoginResponseDto;
import org.springframework.http.HttpHeaders;


public interface AuthenticationService {
	LoginResponseDto login(LoginRequestDto dto) throws Exception;
	
	void logout(HttpHeaders headers);
}
