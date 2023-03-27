package com.demo.spring.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import com.demo.spring.dto.AccountDTO;
import com.demo.spring.dto.AccountNumberInput;
import com.demo.spring.dto.CustomerDTO;
import com.demo.spring.dto.CustomerDetailDTO;
import com.demo.spring.dto.CustomerInput;
import com.demo.spring.util.Message;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
public class EmployeeController {

	@Autowired
	RestTemplate restTemplate;

	@PostMapping(path = "/openaccount")
	public ModelAndView createAccount(AccountDTO accountDTO) {
		ModelAndView mv = new ModelAndView();
		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
		HttpEntity<AccountDTO> request = new HttpEntity<>(accountDTO, headers);
		ResponseEntity<String> accountResponse = restTemplate.exchange("http://localhost:8484/employee/createaccount",
				HttpMethod.POST, request, String.class);
		mv.addObject("accountResponseEmployee", accountResponse.getBody());
		mv.setViewName("openAccountResponseEmployee");
		return mv;
	}

	@GetMapping(path = "/listcustomer")
	public ModelAndView listCustomer() {
		ModelAndView mv = new ModelAndView();
		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
		HttpEntity<Void> request = new HttpEntity<>(headers);
		ResponseEntity<List<CustomerDTO>> response = restTemplate.exchange("http://localhost:8484/employee/listall",
				HttpMethod.GET, request, new ParameterizedTypeReference<List<CustomerDTO>>() {
				});
		mv.addObject("listCustomer", response.getBody());
		mv.setViewName("customerList");
		return mv;

	}

	@GetMapping(path = "/find")
	public ModelAndView findById(@RequestParam(name = "customerId", required = true) Integer customerId)
			throws JsonProcessingException {
		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
		CustomerInput input = new CustomerInput(customerId);
		HttpEntity<CustomerInput> req = new HttpEntity<>(input, headers);

		ModelAndView mv = new ModelAndView();
		ObjectMapper mapper = new ObjectMapper();
		Message msg = new Message("Customer not found Exception");
		String msgJson = mapper.writeValueAsString(msg);

		String url = restTemplate
				.exchange("http://localhost:8484/employee/customerdetails", HttpMethod.POST, req, String.class)
				.getBody();

		if (url.equals(msgJson)) {
			mv.addObject("response", "Customer Not Found");
			mv.setViewName("customerDetails");
			return mv;
		} else {

			ResponseEntity<CustomerDetailDTO> response = restTemplate.exchange(
					"http://localhost:8484/employee/customerdetails", HttpMethod.POST, req, CustomerDetailDTO.class);
			mv.addObject("customer", response.getBody());
			mv.addObject("accounts", response.getBody().getAccounts());
			mv.setViewName("customerDetails");
			return mv;
		}
	}

	@PostMapping(path = "/changeAccountStatus")
	public ModelAndView activateAccount(AccountNumberInput input) {
		ModelAndView mv = new ModelAndView();
		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
		HttpEntity<AccountNumberInput> request = new HttpEntity<>(input, headers);
		ResponseEntity<String> response = restTemplate.exchange("http://localhost:8484/employee/accountstatus",
				HttpMethod.POST, request, String.class);
		mv.addObject("response", response.getBody());
		mv.setViewName("accountStatus");
		return mv;
	}

}
