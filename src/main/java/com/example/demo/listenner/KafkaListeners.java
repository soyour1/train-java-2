package com.example.demo.listenner;

import com.example.demo.service.LoggerService;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KafkaListeners {
	
	private final LoggerService loggerService;
	@KafkaListener(topics = "log", groupId= "group", containerFactory = "kafkaListenerContainerFactory")
	void listenr(String message){
		
		JSONObject json = new JSONObject(message);
		System.out.println(message);
		
		loggerService.saveLog(json.getString("email"), json.getString("ipAdress"), json.getString("message"));
	}
}
