package com.demo.spring.service;

import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.spring.dto.CredentialsDTO;
import com.demo.spring.entity.Users;
import com.demo.spring.repository.LoginRepository;
import com.demo.spring.util.Message;

@Service
public class LoginService {
	
	@Autowired
	LoginRepository loginRepository;
	
	@Autowired
	Notification notification;
	
	private static final String LOGINFAIL = "Incorrect username or password";  
	
	private Logger logger = LogManager.getLogger(this.getClass().getName());

	
	public Message saveCredential(Users user) {
		loginRepository.save(user);
		logger.info("User saved with Id {}", user.getUserId());
		return new Message("User credential saved");
	}

	public Message checkLogin(CredentialsDTO credentialsDTO) {
        Optional<Users> userOp = loginRepository.findById(credentialsDTO.getUserId());
        if(userOp.isPresent()) {
            if(credentialsDTO.getPassword().equals(userOp.get().getPassword()) && credentialsDTO.getUserType().equals(userOp.get().getUser())) {
                if(credentialsDTO.getUserType().equalsIgnoreCase("customer")) {
                     logger.info("login successfull");
                    return new Message ("customer");
                }else if (credentialsDTO.getUserType().equalsIgnoreCase("employee"))
                {
                           logger.info("login successfull");
                          return new Message ("employee");
                    
                }
               }else {
                   logger.info("login failed");
                   return new Message(LOGINFAIL);
               }  
        }
        logger.info("Provide correct password or username");
        return new Message(LOGINFAIL);
    }
	
}
