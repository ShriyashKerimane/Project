package com.demo.spring.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.spring.dto.AccountDTO;
import com.demo.spring.entity.Account;
import com.demo.spring.entity.Statement;
import com.demo.spring.entity.input.AccountNumberInput;
import com.demo.spring.entity.input.StatementInput;
import com.demo.spring.entity.input.TransactionInput;
import com.demo.spring.entity.input.TransferInput;
import com.demo.spring.exception.AccountNotFoundException;
import com.demo.spring.exception.CustomerNotFoundException;
import com.demo.spring.exception.LowBalanceException;
import com.demo.spring.service.CustomerService;
import com.demo.spring.util.Message;

import io.micrometer.core.annotation.Timed;
import io.swagger.v3.oas.annotations.OpenAPI30;

@RestController
@OpenAPI30	
@RequestMapping(path = "/customer")
public class CustomerController {
	
	@Autowired
	CustomerService customerService;
	
	@PostMapping(path = "/createaccount", consumes=MediaType.APPLICATION_JSON_VALUE)
	@Timed(value = "requests.count.createAccount")
	public ResponseEntity<Message> createAccount(@RequestBody AccountDTO accountDTO){
		Account account = new Account(accountDTO.getAccountType(), accountDTO.getBalance(), accountDTO.getCustomerId(), accountDTO.getBranch());
		return ResponseEntity.ok(new Message(customerService.create(account).getStatus()));
		
	}
	
	@PatchMapping(path = "/deposit", consumes=MediaType.APPLICATION_JSON_VALUE)
	@Timed(value = "requests.count.depositAmount")
	public ResponseEntity<Message> depositAmount(@RequestBody TransactionInput transaction){
		
		return ResponseEntity.ok(customerService.deposit(transaction));
		
	}
	
	@PatchMapping(path = "/withdraw", consumes=MediaType.APPLICATION_JSON_VALUE)
	@Timed(value = "requests.count.withdrawAmount")
	public ResponseEntity<Message> withdrawAmount(@RequestBody TransactionInput transaction){
		
		return ResponseEntity.ok(customerService.withdraw(transaction));
		
	}
	
	@PatchMapping(path = "/transfer", consumes=MediaType.APPLICATION_JSON_VALUE)
	@Timed(value = "requests.count.transferAmount")
	public ResponseEntity<Message> transferAmount(@RequestBody TransferInput transferInput){
			return ResponseEntity.ok(customerService.transfer(transferInput));
	}
	
	@PostMapping(path="/checkbalance",consumes=MediaType.APPLICATION_JSON_VALUE)
	@Timed(value = "requests.count.checkBalance")
	public ResponseEntity<Message> checkBalance(@RequestBody AccountNumberInput input){
		return ResponseEntity.ok(customerService.fetchBalance(input));
		
	}
	
	@PostMapping(path="/statement",consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Statement>> fetchStatement(@RequestBody StatementInput statementInput){
		return ResponseEntity.ok(customerService.statement(statementInput));
		
	}
	
	@ExceptionHandler(AccountNotFoundException.class)
	public ResponseEntity<Message> handleAccountNotFound(AccountNotFoundException ex){
		return ResponseEntity.ok(new Message("Account not found"));
	}
	
	@ExceptionHandler(CustomerNotFoundException.class)
	public ResponseEntity<Message> handleCustomerNotFound(CustomerNotFoundException ex){
		return ResponseEntity.ok(new Message("Customer Not Found"));
	}
	
	@ExceptionHandler(LowBalanceException.class)
	public ResponseEntity<Message> handleLowBalanceException(LowBalanceException ex){
		return ResponseEntity.ok(new Message("Low Balance"));
	}
}
