package com.demo.spring;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.demo.spring.dto.AccountDTO;
import com.demo.spring.entity.Account;
import com.demo.spring.entity.Customer;
import com.demo.spring.entity.Statement;
import com.demo.spring.entity.input.AccountNumberInput;
import com.demo.spring.util.Message;

class CustomerApplicationDTOTest {
	
	
	@Test
	void testCustomer() {
		Customer customer = new Customer();
		customer.setCustomerId(123);
		customer.setCustomerName("abc");
		customer.setEmailId("abc");
		customer.setPhoneNumber("123");
		
		assertEquals(123, customer.getCustomerId());
		assertEquals("abc",customer.getCustomerName());
		assertEquals("abc",customer.getEmailId());
		assertEquals("123",customer.getPhoneNumber());
		
	}
	
	@Test
	void testAccount() {
		Account account = new Account();
		account.setAccountNumber(123);
		account.setAccountType("salary");
		account.setBalance(123.0);
		account.setBranch("sirsi");
		account.setCustomerId(123);
		account.setStatus("active");
		
		assertEquals(123, account.getAccountNumber());
		assertEquals("salary", account.getAccountType());
		assertEquals(123.0, account.getBalance());
		assertEquals("sirsi", account.getBranch());
		assertEquals(123, account.getCustomerId());
		assertEquals("active", account.getStatus());

	}
	
	@Test
	void testAccountDTO() {
		AccountDTO account = new AccountDTO("FD", 100.0, 123, "sirsi");
		assertEquals(100.0, account.getBalance());
	}
	
	@Test
	void testAccountNumber() {
		AccountNumberInput input =new AccountNumberInput(123,123);
		assertEquals(123, input.getAccountNumber());
		assertEquals(123, input.getCustomerId());
		
	}
	
	@Test
	void testMessage() {
		Message message = new Message();
		message.setStatus("hi");
		assertEquals("hi",message.getStatus());
	}
	
	@Test
	void testStatement() {
		Statement statement = new Statement();
		statement.setAccountNumber(123);
		statement.setAmount(100.0);
		statement.setBalance(100.0);
		statement.setDate("2022");
		statement.setTransactionType("deposit");
		
		assertEquals(123, statement.getAccountNumber());
		assertEquals(100.0, statement.getAmount());
		assertEquals(100.0, statement.getBalance());
		assertEquals("2022", statement.getDate());
		assertEquals("deposit", statement.getTransactionType());
		
	}
}
