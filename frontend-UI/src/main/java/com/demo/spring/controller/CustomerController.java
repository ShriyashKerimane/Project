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
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import com.demo.spring.dto.AccountDTO;
import com.demo.spring.dto.AccountNumberInput;
import com.demo.spring.dto.Statement;
import com.demo.spring.dto.StatementInput;
import com.demo.spring.dto.TransactionInput;
import com.demo.spring.dto.TransferInput;
import com.demo.spring.util.Message;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
public class CustomerController {

	@Autowired
	RestTemplate restTemplate;

	@PostMapping(path = "/createAccount")
	public ModelAndView createAccount(AccountDTO accountDTO){
		ModelAndView mv = new ModelAndView();
		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
		HttpEntity<AccountDTO> request = new HttpEntity<>(accountDTO, headers);

		ResponseEntity<String> accountResponse = restTemplate.exchange("http://localhost:8484/customer/createaccount",
				HttpMethod.POST, request, String.class);
		mv.addObject("accountResponse", accountResponse.getBody());
		mv.setViewName("openAccountResponse");
		return mv;

	}

	@PostMapping(path = "/withdrawAmount")
	public ModelAndView withdrawAmount(TransactionInput input) {
		ModelAndView mv = new ModelAndView();
		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
		HttpEntity<TransactionInput> request = new HttpEntity<>(input, headers);

		ResponseEntity<String> response = restTemplate.exchange("http://localhost:8484/customer/withdraw",
				HttpMethod.PATCH, request, String.class);
		mv.addObject("response", response.getBody());
		mv.setViewName("withdrawInput");
		return mv;

	}

	@PostMapping(path = "/depositAmount")
	public ModelAndView depositAmount(TransactionInput input){
		ModelAndView mv = new ModelAndView();
		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
		HttpEntity<TransactionInput> request = new HttpEntity<>(input, headers);

		ResponseEntity<String> response = restTemplate.exchange("http://localhost:8484/customer/deposit",
				HttpMethod.PATCH, request, String.class);
		mv.addObject("response", response.getBody());
		mv.setViewName("depositInput");
		return mv;
	}

	@PostMapping(path = "/transferAmount")
	public ModelAndView transferAmount(TransferInput input) {
		ModelAndView mv = new ModelAndView();
		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
		HttpEntity<TransferInput> request = new HttpEntity<>(input, headers);

		ResponseEntity<String> response = restTemplate.exchange("http://localhost:8484/customer/transfer",
				HttpMethod.PATCH, request, String.class);
		mv.addObject("response", response.getBody());
		mv.setViewName("transferResponse");
		return mv;
	}

	@GetMapping(path = "/checkbalance")
	public ModelAndView checkBalance(AccountNumberInput accountInput){
		ModelAndView mv = new ModelAndView();
		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
		HttpEntity<AccountNumberInput> request = new HttpEntity<>(accountInput, headers);

		ResponseEntity<String> response = restTemplate.exchange("http://localhost:8484/customer/checkbalance",
				HttpMethod.POST, request, String.class);
		mv.addObject("response", response.getBody());
		mv.setViewName("checkBalance");
		return mv;

	}

	@GetMapping(path = "/statements")
	public ModelAndView fetchStatements(StatementInput input) throws JsonProcessingException {
		ModelAndView mv = new ModelAndView();
		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
		HttpEntity<StatementInput> req = new HttpEntity<>(input, headers);

		ObjectMapper mapper = new ObjectMapper();
		Message msg1 = new Message("Customer Not Found");
		Message msg2 = new Message("Account not found");
		String msgJson1 = mapper.writeValueAsString(msg1);
		String msgJson2 = mapper.writeValueAsString(msg2);

		String url = restTemplate
				.exchange("http://localhost:8484/customer/statement", HttpMethod.POST, req, String.class).getBody();

		if (url.equals(msgJson1)) {
			mv.addObject("response", "Customer Not Found");
			mv.setViewName("statementResponse");
			return mv;
		} else if (url.equals(msgJson2)) {

			mv.addObject("response", "Account not found");
			mv.setViewName("statementResponse");
			return mv;
		} else {

			ResponseEntity<List<Statement>> response = restTemplate.exchange("http://localhost:8484/customer/statement",
					HttpMethod.POST, req, new ParameterizedTypeReference<List<Statement>>() {
					});
			mv.addObject("statementList", response.getBody());
			mv.setViewName("statementResponse");
			return mv;
		}
	}

}
