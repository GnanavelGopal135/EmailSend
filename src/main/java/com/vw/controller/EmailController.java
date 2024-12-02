package com.vw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.vw.entity.Email;
import com.vw.service.EmailService;

@RequestMapping
public class EmailController {
	@Autowired
	private EmailService emailService;

	@PostMapping("/sendEmail")
	public ResponseEntity<String> sendEmail(Email email) {
		return new ResponseEntity<String>(emailService.sendEmail(email), HttpStatus.CREATED);
	}

	@PostMapping("/sendEmailWithAttachment")
	public ResponseEntity<String> sendEmailWithAttachment(Email email) {
		return new ResponseEntity<String>(emailService.sendEmailWithAttachment(email), HttpStatus.CREATED);
	}
}
