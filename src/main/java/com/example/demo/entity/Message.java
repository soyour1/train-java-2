package com.example.demo.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

//@Data
//@Entity
//@Table(name = "t_loger")
//@NoArgsConstructor
public class Message {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="email")
	private String email;
	
	@Column(name="ip_address")
	private String ipAdress;
	
	@Column(name="message")
	private String message;
	@Override
	public String toString() {
		final StringBuffer sb = new StringBuffer("{");
		sb.append("\"email\":\"").append(email).append('\"');
		sb.append(", \"ipAdress\":\"").append(ipAdress).append('\"');
		sb.append(", \"message\":\"").append(message).append('\"');
		sb.append('}');
		return sb.toString();
	}
	
	public Message(String email, String ipAdress, String message) {
		this.email = email;
		this.ipAdress = ipAdress;
		this.message = message;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getIpAdress() {
		return ipAdress;
	}
	
	public void setIpAdress(String ipAdress) {
		this.ipAdress = ipAdress;
	}
	
	public String getMessage() {
		return message;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
}
