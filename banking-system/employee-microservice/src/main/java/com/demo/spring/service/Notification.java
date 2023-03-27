package com.demo.spring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.demo.spring.util.Message;

@Service
public class Notification {
	
	@Autowired
    private JavaMailSender mailSender;
    
    public void sendEmail(String email,Message mail) {
        SimpleMailMessage message=new  SimpleMailMessage();
        message.setTo(email);
        message.setFrom("mmb.notify.mail@gmail.com");
        message.setText(mail.getStatus());
        message.setSubject("Notification");
        
        mailSender.send(message);
        
    }

}
