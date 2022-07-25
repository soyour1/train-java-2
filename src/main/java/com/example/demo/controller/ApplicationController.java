package com.example.demo.controller;

import com.example.demo.entity.Application;
import com.example.demo.entity.Message;
import com.example.demo.export.ApplicationPDFExporter;
import com.example.demo.service.ApplicationService;
import com.example.demo.service.dto.request.ApplicationRequestDto;
import com.example.demo.service.dto.response.ApplicationResponseDto;
import com.lowagie.text.DocumentException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RequestMapping("api/application")
@RestController
@RequiredArgsConstructor
public class ApplicationController {
	private final HttpServletRequest htttpRequest;
	private final KafkaTemplate<String, String> kafkaTemplate;
	@Autowired
	private ApplicationService applicationService;
	
	@GetMapping
	public ResponseEntity<List<ApplicationResponseDto>> getApplication(){
		return ResponseEntity.ok(applicationService.getApplication());
	}
	
	@PostMapping()
	public ResponseEntity<ApplicationResponseDto> addApplication(@Valid @RequestBody ApplicationRequestDto request) {
		return ResponseEntity.ok(applicationService.addApplication(request));
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteApplication(@PathVariable Long id){
		applicationService.deleteApplication(id);
		return ResponseEntity.noContent().build();
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<?> updateApplication(@PathVariable Long id, @Valid @RequestBody ApplicationRequestDto request){
		applicationService.updateApplication(id, request);
		return ResponseEntity.noContent().build();
	}
	
	@GetMapping("/export")
	public void exportToPdf(HttpServletResponse response) throws DocumentException, IOException{
		response.setContentType("application/pdf");
		DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd:hh:mm:ss");
		String currentDateTime = dateFormatter.format(new Date());
		
		String headerkey = "Content-Disposition";
		String headerValue = "attachment; filename=pdf_" + currentDateTime + ".pdf";
		response.setHeader(headerkey, headerValue);
		
		List<ApplicationResponseDto> applicationList = applicationService.getApplication();
		
		ApplicationPDFExporter exporter = new ApplicationPDFExporter(applicationList);
		exporter.export(response);
		
		Message message = new Message("user", htttpRequest.getRemoteAddr(), "export pdf");
		kafkaTemplate.send("log", message.toString());
	}
}
