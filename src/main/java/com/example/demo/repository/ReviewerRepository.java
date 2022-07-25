package com.example.demo.repository;

import com.example.demo.entity.Reviewer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReviewerRepository extends JpaRepository<Reviewer, Long> {
	
	Optional<Reviewer> findByEmail(String email);
}
