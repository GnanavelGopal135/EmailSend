package com.vw.service;

import com.vw.entity.Email;

public interface EmailServiceInterface {
	String sendEmail(Email email);

	String sendEmailWithAttachment(Email email);
}
