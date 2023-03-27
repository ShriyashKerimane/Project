package com.demo.spring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class Notification {
	
	@Autowired
    private JavaMailSender mailSender;
    
    public void sendEmail(String email,Integer customerId) {
        SimpleMailMessage message=new  SimpleMailMessage();
        message.setTo(email);
        message.setFrom("mmb.notify.mail@gmail.com");
        message.setText("Welcome to MMB family...your Id is "+customerId+" ....do not share this Id with anyone!!!");
        message.setSubject("Registration");
        
        mailSender.send(message);
        
    }

}
