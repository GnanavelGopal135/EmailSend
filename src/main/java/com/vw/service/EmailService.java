package com.vw.service;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.vw.entity.Email;
import com.vw.repository.EmailRepository;

import jakarta.mail.internet.MimeMessage;
@Service
public class EmailService implements EmailServiceInterface {
	@Autowired
	private EmailRepository emailRepository;
	@Autowired
	private JavaMailSender javaMailSender;
	@Value("${spring.mail.username}")
	private String sender;

	@Override
	public String sendEmail(Email email) {
		try {
			SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
			simpleMailMessage.setFrom(sender);
			simpleMailMessage.setTo(email.getReceiver());
			simpleMailMessage.setSubject(email.getSubject());
			simpleMailMessage.setText(email.getBody());
			javaMailSender.send(simpleMailMessage);
			return "Mail has been sent successfully" + emailRepository.save(email);
		} catch (MailException e) {
			return "Mail has not been sent Successfully" + e.getMessage();
		}

	}

	@Override
	public String sendEmailWithAttachment(Email email) {
		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		try {
			MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
			mimeMessageHelper.setFrom(sender);
			mimeMessageHelper.setTo(email.getReceiver());
			mimeMessageHelper.setSubject(email.getSubject());
			mimeMessageHelper.setText(email.getBody());
			FileSystemResource fileSystem = new FileSystemResource(new File(email.getAttachment()));
			mimeMessageHelper.addAttachment(fileSystem.getFilename(), fileSystem);
			javaMailSender.send(mimeMessage);
		} catch (Exception e) {
			return "Mail With attachment is not send to the receiver" + e.getMessage();
		}
		return "Mail has been sent to the receiver with attachment" + emailRepository.save(email);
	}

}
