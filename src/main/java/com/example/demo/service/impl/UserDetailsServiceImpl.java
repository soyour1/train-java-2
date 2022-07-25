package com.example.demo.service.impl;

import com.example.demo.entity.Reviewer;
import com.example.demo.repository.ReviewerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
	
	private final ReviewerRepository reviewerRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Reviewer reviewer = reviewerRepository.findByEmail(username)
				  .orElseThrow(() -> new UsernameNotFoundException("User not found"));
		return new org.springframework.security.core.userdetails.User(reviewer.getEmail(), reviewer.getPassword(),
				  new ArrayList<>());
	}
	
	
}
