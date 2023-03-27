package com.demo.spring.service;

import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.spring.dto.CustomerDetailDTO;
import com.demo.spring.entity.Account;
import com.demo.spring.entity.Customer;
import com.demo.spring.entity.input.AccountNumberInput;
import com.demo.spring.entity.input.CustomerInput;
import com.demo.spring.exception.AccountNotFoundException;
import com.demo.spring.exception.CustomerNotFoundException;
import com.demo.spring.repository.AccountRepository;
import com.demo.spring.repository.CustomerRepository;
import com.demo.spring.util.Message;

@Service
public class EmployeeService {
	
	@Autowired
	AccountRepository accountRepository;
	
	@Autowired
	CustomerRepository customerRepository;
	
	@Autowired
	Notification notification;
	
	private Logger logger = LogManager.getLogger(this.getClass().getName());
	
	public CustomerDetailDTO customerDetail(CustomerInput input){
		
		Optional<Customer> customer = customerRepository.findById(input.getCustomerId());
		if(customer.isPresent()) {
			Customer personalInfo = customer.get();
			List<Account> accounts = customerRepository.findAccounts(input.getCustomerId());
			logger.info("Customer detail retrived successfully");	
			return new CustomerDetailDTO(personalInfo.getCustomerId(), 
					personalInfo.getCustomerName(), personalInfo.getPhoneNumber(), personalInfo.getEmailId(), 
					accounts);
		}else {
			logger.error("Customer with Id {} not found",input.getCustomerId());	
			throw new CustomerNotFoundException();
		}
	}
	
	public Message changeStatus(AccountNumberInput input) {
		
		Optional<Account> account = accountRepository.findById(input.getAccountNumber());
		if(account.isPresent()) {
			if(account.get().getStatus().equals("active")) {
				logger.info("Account {} is deactivated",input.getAccountNumber());	
				accountRepository.updateStatus("deactive", input.getAccountNumber());
			}else {
				logger.info("Account {} is activated",input.getAccountNumber());	
				accountRepository.updateStatus("active", input.getAccountNumber());				
			}	
			return new Message("Account status updated");
		}else {
			logger.error("Account {} not found",input.getAccountNumber());
			throw new AccountNotFoundException();
		}
	}
	
	public Message create(Account account){
		Optional<Customer> customer = customerRepository.findById(account.getCustomerId());
		if(customer.isPresent()) {
		accountRepository.save(account);
		logger.info("Acoount created with account number {}",account.getAccountNumber());
		Message mail = new Message(
				"Your Account opened successfully...your new account number is " + account.getAccountNumber());
		notification.sendEmail(customer.get().getEmailId(), mail);
		return new Message("Account saved successfully");
		}else {
			logger.error("Customer detail retrived successfully");	
			throw new CustomerNotFoundException();
		}
	}
	
	public List<Customer> listAllCustomers(){
		logger.info("Customer list retrived successfully");	
		return customerRepository.findAll();
	}
	
	public Message saveCustomer(Customer customer) {
		customerRepository.save(customer);
		logger.info("New customer saved successfully with customerId {}",customer.getCustomerId());	
		return new Message("Customer save successfully");
	}

}
