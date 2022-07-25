package com.example.demo.service.mapper;

import com.example.demo.entity.Reviewer;
import com.example.demo.service.dto.request.ReviewerRequestDto;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ReviewerMapper {
	
	private final PasswordEncoder encoder;
	
	public Reviewer convertToEntity(ReviewerRequestDto dto) {
		Reviewer reviewer = new Reviewer();
		reviewer.setName(dto.getName());
		reviewer.setEmail(dto.getEmail());
		reviewer.setPassword(encoder.encode(dto.getPassword()));
		reviewer.setPicture(dto.getPicture());
		return reviewer;
	}
}
