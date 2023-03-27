package com.demo.spring.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.spring.dto.AccountDTO;
import com.demo.spring.dto.CustomerDTO;
import com.demo.spring.dto.CustomerDetailDTO;
import com.demo.spring.entity.Account;
import com.demo.spring.entity.Customer;
import com.demo.spring.entity.input.AccountNumberInput;
import com.demo.spring.entity.input.CustomerInput;
import com.demo.spring.exception.AccountNotFoundException;
import com.demo.spring.exception.CustomerNotFoundException;
import com.demo.spring.service.EmployeeService;
import com.demo.spring.util.Message;

import io.micrometer.core.annotation.Timed;
import io.swagger.v3.oas.annotations.OpenAPI30;

@RestController
@OpenAPI30	
@RequestMapping(path = "/employee")
public class EmployeeController {

	@Autowired
	EmployeeService employeeService;

	@PostMapping(path = "/customerdetails", consumes = MediaType.APPLICATION_JSON_VALUE)
	@Timed(value = "requests.count.getCustomerDetails")
	public ResponseEntity<CustomerDetailDTO> getCustomerDetails(@RequestBody CustomerInput input) {
		return ResponseEntity.ok(employeeService.customerDetail(input));
	}

	@PostMapping(path = "/accountstatus", consumes = MediaType.APPLICATION_JSON_VALUE)
	@Timed(value = "requests.count.changeAccountStatus")
	public ResponseEntity<Message> changeAccountStatus(@RequestBody AccountNumberInput input) {
		return ResponseEntity.ok(employeeService.changeStatus(input));
	}

	@PostMapping(path = "/createaccount", consumes = MediaType.APPLICATION_JSON_VALUE)
	@Timed(value = "requests.count.createAccount")
	public ResponseEntity<Message> createAccount(@RequestBody AccountDTO accountDTO) {
		Account account = new Account(accountDTO.getAccountType(),
				accountDTO.getBalance(), accountDTO.getCustomerId(), accountDTO.getBranch());
		return ResponseEntity.ok(new Message(employeeService.create(account).getStatus()));
	}

	@GetMapping(path = "/listall")
	@Timed(value = "requests.count.listAll")
	public ResponseEntity<List<Customer>> listAll() {
		return ResponseEntity.ok(employeeService.listAllCustomers());
	}
	
	@PostMapping(path = "/savecustomer", consumes = MediaType.APPLICATION_JSON_VALUE)
	@Timed(value = "requests.count.saveCustomer")
	public ResponseEntity<Message> saveCustomer(@RequestBody CustomerDTO input){
		Customer customer = new Customer(input.getCustomerId(), input.getCustomerName(), input.getPhoneNumber(), input.getEmailId());
		return ResponseEntity.ok(employeeService.saveCustomer(customer));
	}
	
	@ExceptionHandler(AccountNotFoundException.class)
	public ResponseEntity<Message> handleAccountNotFound(AccountNotFoundException ex){
		return ResponseEntity.ok(new Message("Account not found Exception"));
	}
	
	@ExceptionHandler(CustomerNotFoundException.class)
	public ResponseEntity<Message> handleCustomerNotFound(CustomerNotFoundException ex){
		return ResponseEntity.ok(new Message("Customer not found Exception"));
	}
	
}
