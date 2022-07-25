package com.example.demo.service.impl;

import com.example.demo.entity.Message;
import com.example.demo.service.AuthenticationService;
import com.example.demo.service.dto.request.LoginRequestDto;
import com.example.demo.service.dto.response.LoginResponseDto;
import com.example.demo.util.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.data.redis.core.RedisTemplate;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
	private final AuthenticationManager authenticationManager;
	private final JwtTokenUtil jwtTokenUtil;
	private final UserDetailsServiceImpl detailsServiceImpl;
	private final RedisTemplate<String, String> redisTemplate;
	
	private final HttpServletRequest request;
	private final KafkaTemplate<String, String> kafkaTemplate;
	@Override
	public LoginResponseDto login(LoginRequestDto dto) throws Exception {
		
		authenticate(dto.getEmail(), dto.getPassword());
		final UserDetails userDetails = detailsServiceImpl.loadUserByUsername(dto.getEmail());
		final String token = jwtTokenUtil.generateToken(userDetails);
		Message message = new Message(dto.getEmail(), request.getRemoteAddr(), "Login");
		kafkaTemplate.send("log", message.toString());
		return new LoginResponseDto(token);
	}
	
	private void authenticate(String username, String password) throws Exception {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch (DisabledException e) {
			throw new Exception("USER_DISABLED", e);
		} catch (BadCredentialsException e) {
			throw new Exception("INVALID_CREDENTIALS", e);
		}
	}
	
	@Override
	public void logout(HttpHeaders headers) {
		String token = headers.getFirst(HttpHeaders.AUTHORIZATION);
		
		token = token.split(" ")[1];
		Long timeout = jwtTokenUtil.getExpirationDateFromToken(token).getTime() - new Date().getTime();
		redisTemplate.opsForValue().set(token, token, timeout, TimeUnit.MILLISECONDS);
		Message message = new Message(jwtTokenUtil.getUsernameFromToken(token), request.getRemoteAddr(), "Login");
		kafkaTemplate.send("log", message.toString());
	}
}
