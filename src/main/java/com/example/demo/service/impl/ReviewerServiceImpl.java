package com.example.demo.service.impl;

import com.example.demo.entity.Message;
import com.example.demo.entity.Reviewer;
import com.example.demo.repository.ReviewerRepository;
import com.example.demo.service.ReviewerService;
import com.example.demo.service.dto.request.ReviewerRequestDto;
import com.example.demo.service.mapper.ReviewerMapper;
import lombok.AllArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Service
@AllArgsConstructor
@Transactional(readOnly = true)
public class ReviewerServiceImpl implements ReviewerService {
	private final ReviewerRepository reviewerRepository;
	private final ReviewerMapper reviewerMapper;
	private final PasswordEncoder encoder;
	
	private final HttpServletRequest htttpRequest;
	private final KafkaTemplate<String, String> kafkaTemplate;
	@Override
	@Transactional
	public void addNewReviewer(ReviewerRequestDto dto) {
		Optional<Reviewer> reviewerOpt = reviewerRepository.findByEmail(dto.getEmail());
		if (reviewerOpt.isPresent()) {
			reviewerOpt.get().setName(dto.getName());
			reviewerOpt.get().setPassword(encoder.encode(dto.getPassword()));
			reviewerOpt.get().setPicture(dto.getPicture());
			return;
		}
		
		Message message = new Message(dto.getEmail(), htttpRequest.getRemoteAddr(), "Signup");
		kafkaTemplate.send("log", message.toString());
		reviewerRepository.save(reviewerMapper.convertToEntity(dto));
	}
}
