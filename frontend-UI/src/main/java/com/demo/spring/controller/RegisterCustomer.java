package com.demo.spring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import com.demo.spring.dto.CredentialsDTO;
import com.demo.spring.dto.RegisterInput;
import com.demo.spring.util.Message;

@Controller
public class RegisterCustomer {

	@Autowired
	RestTemplate restTemplate;

	@GetMapping(path = "/loginPage")
	public ModelAndView login(CredentialsDTO credentialsDTO) {
		ModelAndView mv = new ModelAndView();
		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
		HttpEntity<CredentialsDTO> request = new HttpEntity<>(credentialsDTO, headers);
		ResponseEntity<Message> response = restTemplate.exchange("http://localhost:8484/gateway/", HttpMethod.POST,
				request, Message.class);
		if (response.getBody().getStatus().equalsIgnoreCase("customer")) {

			mv.addObject("customer", response.getBody());
			mv.setViewName("customerHome");
			return mv;
		}

		else if (response.getBody().getStatus().equalsIgnoreCase("employee")) {

			mv.setViewName("employeeHome");
			return mv;

		} else {
			mv.addObject("response", "Invalid Credential");
			mv.setViewName("login");
			return mv;
		}

	}
	
	@GetMapping(path = "/registration")
	public ModelAndView register(RegisterInput input) {
		ModelAndView mv = new ModelAndView();
		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
		HttpEntity<RegisterInput> request = new HttpEntity<>(input, headers);
		restTemplate.exchange("http://localhost:8484/gateway/register", HttpMethod.POST,
				request, Message.class);
		mv.setViewName("login");
		return mv;
	}

}